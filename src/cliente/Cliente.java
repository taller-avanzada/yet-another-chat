package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {

	private String name;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ClienteRecibe recibe;
	private int cantSalas;

	public Cliente(int puerto, String ip) {
		try {
			socket = new Socket(ip, puerto);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			cantSalas = 0;
			// falta la parte de leer
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void escribir(String sala, String mensaje) {
		try {
			out.write(0);
			out.writeUTF(sala + ";" + name + ";" + mensaje);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataOutputStream getDataOutputStream() {
		return out;
	}

	public DataInputStream getDataInputStream() {
		return in;
	}

	public void setRecibe(ClienteRecibe recibe) {
		this.recibe = recibe;
	}

	public ClienteRecibe getRecibe() {
		return recibe;
	}

	public void enviarNuevaSala(String nombreSala) {
		try {
			++cantSalas;
			out.write(1);
			out.writeUTF(nombreSala);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int getCantSalas() {
		return cantSalas;
	}

	public void bajarCounterSala() {
		cantSalas--;
	}

	public void conectarseASala(String nombreSala) {
		try {
			++cantSalas;
			out.write(1);
			out.writeUTF(nombreSala);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
