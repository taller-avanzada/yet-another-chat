package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ServidorHilo extends Thread {

	Socket socketCliente;
	private ArrayList<Socket> socketsclientes;
	private HashMap<String,Integer> listaSalas;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public ServidorHilo(Socket socketcliente, ArrayList<Socket> listaClientes, HashMap<String,Integer> listaSalas) {
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
				int valorRead = socketCliente.getInputStream().read();
				switch (valorRead) {
				case 0:
					texto = new DataInputStream(socketCliente.getInputStream()).readUTF();
					for (Socket socket : socketsclientes) {
						DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
						String time = formatter.format(LocalTime.now());
						dout.writeByte(0);
						dout.writeUTF(time + ";" + texto);
						System.out.println(time + ";" + texto);
					}
					break;
				case 1:
					String nombreSala = new DataInputStream(socketCliente.getInputStream()).readUTF();
					if(!listaSalas.keySet().contains(nombreSala))
						listaSalas.put(nombreSala,1);
					else
					{
						Integer valor = listaSalas.get(nombreSala);
						listaSalas.put(nombreSala, ++valor);
					}
					break;
					//No usen 2 acá, está maldito
				case 3:	
					String nombreSalaDisconnect = new DataInputStream(socketCliente.getInputStream()).readUTF();
					Integer valor = listaSalas.get(nombreSalaDisconnect);
					listaSalas.put(nombreSalaDisconnect, --valor);
					if(valor.equals(0))
					{
						listaSalas.remove(nombreSalaDisconnect);
					}
					break;
					
				}
			} catch (Exception e) {
				System.out.println("cliente desconectado");
				socketsclientes.remove(socketCliente);
				return;
			}
		}
	}
}
