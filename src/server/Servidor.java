package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	private ArrayList<Socket> socketsClientes = new ArrayList<>();
	private ArrayList<String> listaSalas = new ArrayList<>();
	MandadorDeSalas mandadorDeSalas = new MandadorDeSalas(socketsClientes, listaSalas);

	public Servidor(int puerto) throws IOException {
		ServerSocket servidor = new ServerSocket(puerto);
		mandadorDeSalas.start();

		Boolean running = true;
		System.out.println("Servidor en linea . . . . ");
		while (running) {
			Socket cliente = servidor.accept();
			System.out.println("Acepto cliente nuevo");
			socketsClientes.add(cliente);
			new ServidorHilo(cliente, socketsClientes, listaSalas).start();
		}
		servidor.close(); // por ahora solo lo cerramos cuando se mata el proceso, esto esta pa q eclipse
							// no moleste

	}

	public static void main(String[] args) {

		try {
			new Servidor(10000);
		} catch (Exception e) {

			System.err.println("Se cerro la conexion");
		}
	}

}
