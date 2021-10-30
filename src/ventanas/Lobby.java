package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

public class Lobby extends JPanel {
	public JTabbedPane tabbedPane;

	public Lobby(JTabbedPane jtabbedpane) {
		this.tabbedPane = jtabbedpane;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton btnNuevaSala = new JButton("Nueva Sala");
		btnNuevaSala.putClientProperty("tabbedPane", tabbedPane);
		btnNuevaSala.putClientProperty("viewPort", this);
		btnNuevaSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreSala = JOptionPane.showInputDialog(btnNuevaSala.getParent(), "Nombre de la sala: ");
				if (nombreSala != null && !nombreSala.isBlank()) {
					JTabbedPane tabbedPane = (JTabbedPane) btnNuevaSala.getClientProperty("tabbedPane");
					tabbedPane.addTab(nombreSala, new Sala(nombreSala, tabbedPane));
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
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
	}
}
