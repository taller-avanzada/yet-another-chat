package cliente;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class ClienteRecibe extends Thread {
	// En esta clase se hace todo el processing de los mensajes que llegan del
	// server
	Cliente client;
	ArrayList<String> salas = new ArrayList<>();
	ArrayList<JTextArea> textareas = new ArrayList<>();

	public ClienteRecibe(Cliente client) {
		super();
		this.client = client;
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte tipo = client.getDataInputStream().readByte();

				switch (tipo) {
				case 0:
					String texto = client.getDataInputStream().readUTF();

					// modificar todo esto segun que manda el server
					String[] splittedText = texto.split(";", 4);
					String hora = splittedText[0];
					String nombreSala = splittedText[1];
					String user = splittedText[2];
					String mensaje = splittedText[3];

					for (int i = 0; i < salas.size(); i++) {
						if (nombreSala.equals(salas.get(i))) {
							textareas.get(i).append(hora + " " + user + ": " + mensaje);
							textareas.get(i).append(System.lineSeparator());
						}
					}
					break;
				case 1:
					System.out.println("case 1");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSala(String salaName, JTextArea salaTextArea) {
		salas.add(salaName);
		textareas.add(salaTextArea);
	}

	public void removeSala(String salaName) {
		int index = salas.indexOf(salaName);
		salas.remove(index);
		textareas.remove(index);
	}

}
