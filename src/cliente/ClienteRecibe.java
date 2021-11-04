package cliente;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class ClienteRecibe extends Thread {
	// En esta clase se hace todo el processing de los mensajes que llegan del
	// server
	Cliente client;
	JTextArea lobbyTextArea;
	ArrayList<String> salasConectado = new ArrayList<>();
	ArrayList<JTextArea> textareas = new ArrayList<>();
	ArrayList<String> listaSalas = new ArrayList<>();

	public ClienteRecibe(Cliente client) {
		super();
		this.client = client;
	}

	@Override
	public void run() {
		String texto;

		while (true) {
			try {
				switch (client.getDataInputStream().readByte()) {
				case 0:
					texto = client.getDataInputStream().readUTF();

					// modificar todo esto segun que manda el server
					String[] splittedText = texto.split(";", 4);
					String hora = splittedText[0];
					String nombreSala = splittedText[1];
					String user = splittedText[2];
					String mensaje = splittedText[3];

					for (int i = 0; i < salasConectado.size(); i++) {
						if (nombreSala.equals(salasConectado.get(i))) {
							textareas.get(i).append(hora + " " + user + ": " + mensaje);
							textareas.get(i).append(System.lineSeparator());
						}
					}
					break;
				case 1:
					texto = client.getDataInputStream().readUTF();
					listaSalas.clear();
					lobbyTextArea.setText("");
					for (String i : texto.split(";")) {
						lobbyTextArea.append(i + System.lineSeparator());
						listaSalas.add(i);
					}
				}

			} catch (Exception e) {
				// ignore
			}
		}
	}

	public void addSala(String salaName, JTextArea salaTextArea) {
		salasConectado.add(salaName);
		textareas.add(salaTextArea);
	}

	public void removeSala(String salaName) {
		int index = salasConectado.indexOf(salaName);
		salasConectado.remove(index);
		textareas.remove(index);
	}

	public void setLobbyTextArea(JTextArea lobbyTextArea) {
		this.lobbyTextArea = lobbyTextArea;
	}

}
