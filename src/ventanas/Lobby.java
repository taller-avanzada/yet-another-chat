package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import cliente.Cliente;

public class Lobby extends JPanel {
	public JTabbedPane tabbedPane;
	Cliente cliente;

	public Lobby(JTabbedPane jtabbedpane, Cliente cliente) {
		cliente.setName(JOptionPane.showInputDialog(this, "Nombre"));
		this.cliente = cliente;
		this.tabbedPane = jtabbedpane;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton btnNuevaSala = new JButton("Nueva Sala");
		btnNuevaSala.putClientProperty("tabbedPane", tabbedPane);
		btnNuevaSala.putClientProperty("viewPort", this);
		btnNuevaSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cliente.getCantSalas() >= 3) {
					JOptionPane.showMessageDialog(tabbedPane, "No puede estar en más de tres salas a la vez",
							"Lobby Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String nombreSala = JOptionPane.showInputDialog(btnNuevaSala.getParent(), "Nombre de la sala: ");
				if (nombreSala != null && !nombreSala.isBlank()) {
					JTabbedPane tabbedPane = (JTabbedPane) btnNuevaSala.getClientProperty("tabbedPane");
					tabbedPane.addTab(nombreSala, new Sala(nombreSala, tabbedPane, cliente));
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
					cliente.enviarNuevaSala(nombreSala);
				}
			}
		});

		springLayout.putConstraint(SpringLayout.NORTH, btnNuevaSala, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnNuevaSala, -10, SpringLayout.EAST, this);

		add(btnNuevaSala);

		JLabel lblSalasAbiertas = new JLabel("Salas Abiertas :");
		springLayout.putConstraint(SpringLayout.NORTH, lblSalasAbiertas, 0, SpringLayout.NORTH, btnNuevaSala);
		springLayout.putConstraint(SpringLayout.WEST, lblSalasAbiertas, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblSalasAbiertas, 0, SpringLayout.SOUTH, btnNuevaSala);
		springLayout.putConstraint(SpringLayout.EAST, lblSalasAbiertas, -10, SpringLayout.WEST, btnNuevaSala);
		add(lblSalasAbiertas);

		JTextArea txaListaSalas = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, txaListaSalas, 20, SpringLayout.SOUTH, lblSalasAbiertas);
		springLayout.putConstraint(SpringLayout.WEST, txaListaSalas, 0, SpringLayout.WEST, lblSalasAbiertas);
		springLayout.putConstraint(SpringLayout.SOUTH, txaListaSalas, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, txaListaSalas, 0, SpringLayout.EAST, lblSalasAbiertas);
		add(txaListaSalas);

		JButton btnUnirseSala = new JButton("Unirse a sala");
		btnUnirseSala.putClientProperty("tabbedPane", tabbedPane);
		btnUnirseSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cliente.getCantSalas() >= 3) {
					JOptionPane.showMessageDialog(tabbedPane, "No puede estar en más de tres salas a la vez",
							"Lobby Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String nombreSala = JOptionPane.showInputDialog(btnUnirseSala.getParent(), "Nombre de la sala: ");
				if (nombreSala != null && !nombreSala.isBlank() && cliente.getRecibe().existeSala(nombreSala)) {
					if (!estoyUnido(nombreSala))
					{
						JTabbedPane tabbedPane = (JTabbedPane) btnUnirseSala.getClientProperty("tabbedPane");
						tabbedPane.addTab(nombreSala, new Sala(nombreSala, tabbedPane, cliente));
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
						cliente.conectarseASala(nombreSala);
					}
					
				} else {
					JOptionPane.showMessageDialog(tabbedPane, "Esa sala no existe", "Lobby Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private boolean estoyUnido(String nombreSala)
			{
				for(int i = 0; i < tabbedPane.getTabCount(); i++)
				{
					if (tabbedPane.getTitleAt(i).equals(nombreSala))
						return true;
				}
				return false;
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnUnirseSala, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnUnirseSala, -100, SpringLayout.EAST, this);
		add(btnUnirseSala);

		cliente.getRecibe().setLobbyTextArea(txaListaSalas); // temporal
	}
}
