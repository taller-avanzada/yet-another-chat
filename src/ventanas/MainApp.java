package ventanas;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import cliente.Cliente;
import cliente.ClienteRecibe;

public class MainApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Cliente cliente = new Cliente(10000, "localhost");
		ClienteRecibe clienteRecibe = new ClienteRecibe(cliente);
		cliente.setName("Benito Cala");
		clienteRecibe.start();
		cliente.setRecibe(clienteRecibe);

		frame = new JFrame();
		frame.setBounds(100, 100, 671, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setTitle("Yet Another Chat!");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setName("tabs");
		frame.getContentPane().add(tabbedPane);

		JScrollPane lobby = new JScrollPane(new Lobby(tabbedPane, cliente));
		lobby.setName("lobby");
		tabbedPane.addTab("Lobby", lobby);
	}

}
