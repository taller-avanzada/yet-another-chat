package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Sala extends JPanel {
	private JTextField textField;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private JTabbedPane tabbedPane;

	public Sala(String name, JTabbedPane tabbedPane) {
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
		textArea.setEditable(false);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -10, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, textField);
		add(textArea);

		JButton btnSalir = new JButton("Salir");
		btnSalir.putClientProperty("tabbedPane", tabbedPane);
		btnSalir.putClientProperty("sala", this);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 10, SpringLayout.SOUTH, btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane) btnSalir.getClientProperty("tabbedPane");
				JPanel sala = (JPanel) btnSalir.getClientProperty("sala");
				tabbedPane.remove(sala);
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

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append(
						LocalTime.now().format(formatter) + " Diego: " + textField.getText() + System.lineSeparator());
				textField.setText("");

			}
		});

	}
}
