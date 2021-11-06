package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class MandadorDeSalas extends Thread {
	ArrayList<Socket> socketsClientes;
	HashMap<String,Integer> listaSalas = new HashMap<>();
	public MandadorDeSalas(ArrayList<Socket> socketsClientes, HashMap<String,Integer> listaSalas) {
		this.socketsClientes = socketsClientes;
		this.listaSalas = listaSalas;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				String salas = "";
				for (String sala : listaSalas.keySet()) {
					salas += sala + ":" + listaSalas.get(sala) + ";";
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
