package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MandadorDeSalas extends Thread {
	ArrayList<Socket> socketsClientes;
	ArrayList<String> listaSalas;

	public MandadorDeSalas(ArrayList<Socket> socketsClientes, ArrayList<String> listaSalas) {
		this.socketsClientes = socketsClientes;
		this.listaSalas = listaSalas;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
				String salas = "";
				for (String sala : listaSalas) {
					salas += sala + ";";
				}
				System.out.println(salas);
				for (Socket socket : socketsClientes) {
					socket.getOutputStream().write(1);
					new DataOutputStream(socket.getOutputStream()).writeUTF(salas);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				// ignore basically, dejo la trace por si me comi algo
			}
		}
	}
}
