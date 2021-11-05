package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServidorHilo extends Thread {

	Socket socketCliente;
	private ArrayList<Socket> socketsclientes;
	private ArrayList<String> listaSalas;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public ServidorHilo(Socket socketcliente, ArrayList<Socket> listaClientes, ArrayList<String> listaSalas) {
		super();
		this.socketCliente = socketcliente;
		this.socketsclientes = listaClientes;
		this.listaSalas = listaSalas;
	}

	@Override
	public void run() {
		String texto;
		while (true) {
			try {
				switch (socketCliente.getInputStream().read()) {
				case 0:
					texto = new DataInputStream(socketCliente.getInputStream()).readUTF();
					for (Socket socket : socketsclientes) {
						DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
						String time = formatter.format(LocalTime.now());
						dout.writeByte(0);
						dout.writeUTF(time + ";" + texto);
						System.out.println(time + ";" + texto);
					}
				case 1:
					String nombreSala = new DataInputStream(socketCliente.getInputStream()).readUTF();
					listaSalas.add(nombreSala);
				}
			} catch (Exception e) {
				System.out.println("cliente desconectado");
				socketsclientes.remove(socketCliente);
				return;
			}
		}
	}
}
