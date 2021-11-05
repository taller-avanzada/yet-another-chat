package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.DefaultCaret;

import cliente.Cliente;

public class Sala extends JPanel {
	private JTextField textField;
	private JTabbedPane tabbedPane;
	Cliente cliente;

	public Sala(String name, JTabbedPane tabbedPane, Cliente cliente) {
		this.cliente = cliente;
		this.tabbedPane = tabbedPane;
		setName(name);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		textField = new JTextField();

		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, this);
		add(textField);
		textField.setColumns(10);

		JTextArea textArea = new JTextArea();
		JScrollPane textAreaScrollPane = new JScrollPane(textArea);
		DefaultCaret textAreaCaret = (DefaultCaret) textArea.getCaret();
		textAreaCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setEditable(false);
		springLayout.putConstraint(SpringLayout.WEST, textAreaScrollPane, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, textAreaScrollPane, -10, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, textAreaScrollPane, 0, SpringLayout.EAST, textField);
		add(textAreaScrollPane);

		JButton btnSalir = new JButton("Salir");
		btnSalir.putClientProperty("tabbedPane", tabbedPane);
		btnSalir.putClientProperty("sala", this);
		springLayout.putConstraint(SpringLayout.NORTH, textAreaScrollPane, 10, SpringLayout.SOUTH, btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane) btnSalir.getClientProperty("tabbedPane");
				JPanel sala = (JPanel) btnSalir.getClientProperty("sala");
				tabbedPane.remove(sala);
				cliente.bajarCounterSala();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnSalir, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSalir, -10, SpringLayout.EAST, this);
		add(btnSalir);

		JLabel lblNombre = new JLabel(name);
		springLayout.putConstraint(SpringLayout.NORTH, lblNombre, 0, SpringLayout.NORTH, btnSalir);
		springLayout.putConstraint(SpringLayout.WEST, lblNombre, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNombre, 0, SpringLayout.SOUTH, btnSalir);
		springLayout.putConstraint(SpringLayout.EAST, lblNombre, -10, SpringLayout.WEST, btnSalir);
		add(lblNombre);

		textField.putClientProperty("cliente", cliente);
		textField.putClientProperty("sala", this);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sala sala = (Sala) textField.getClientProperty("sala");
				Cliente cliente = (Cliente) textField.getClientProperty("cliente");

				cliente.escribir(sala.getName(), textField.getText());
				textField.setText("");
			}
		});

		JButton btnDownloadLog = new JButton("Descargar log");
		btnDownloadLog.putClientProperty("tabbedPane", tabbedPane);
		btnDownloadLog.putClientProperty("sala", this);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 10, SpringLayout.SOUTH, btnDownloadLog);
		btnDownloadLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane) btnSalir.getClientProperty("tabbedPane");
				JPanel sala = (JPanel) btnDownloadLog.getClientProperty("sala");
				String log = textArea.getText();
				PrintWriter writer;
				try {
					writer = new PrintWriter("archivo.out");
					writer.write(log);
					writer.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				tabbedPane.remove(sala);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnDownloadLog, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnDownloadLog, -100, SpringLayout.EAST, this);
		add(btnDownloadLog);

		cliente.getRecibe().addSala(name, textArea);
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		cliente.getRecibe().removeSala(getName());
	}
}
