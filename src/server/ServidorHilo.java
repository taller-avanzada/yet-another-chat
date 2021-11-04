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
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public ServidorHilo(Socket socketcliente, ArrayList<Socket> listaClientes) {
		super();
		this.socketCliente = socketcliente;
		this.socketsclientes = listaClientes;
	}

	@Override
	public void run() {
		String texto;
		while (true) {
			try {
				texto = new DataInputStream(socketCliente.getInputStream()).readUTF();
				for (Socket socket : socketsclientes) {
					DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
					String time = formatter.format(LocalTime.now());
					dout.writeByte(0);
					dout.writeUTF(time + ";" + texto);
					System.out.println(time + ";" + texto);
				}
			} catch (Exception e) {
				System.out.println("cliente desconectado");
				socketsclientes.remove(socketCliente);
				return;
			}
		}
	}

}
