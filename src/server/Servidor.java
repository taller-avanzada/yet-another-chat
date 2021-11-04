package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	private ArrayList<Socket> socketsclientes;
	// MandadorDeSalas mandadorDeSalas = new MandadorDeSalas();

	public Servidor(int puerto) throws IOException {
		int i = 0;
		socketsclientes = new ArrayList<Socket>();
		ServerSocket servidor = new ServerSocket(puerto);
		// mandadorDeSalas.start();

		System.out.println("Servidor en linea . . . . ");

		while (i < 100) {
			Socket cliente = servidor.accept();
			System.out.println("Acepto cliente nuevo");
			socketsclientes.add(cliente);
			new ServidorHilo(cliente, socketsclientes).start();
			i++;
		}
		servidor.close();
		System.out.println("El servidor se ha cerrado!");

	}

	public static void main(String[] args) {

		try {
			new Servidor(10000);
		} catch (Exception e) {
			System.err.println("Se cerro la conexion");
		}

	}

}
