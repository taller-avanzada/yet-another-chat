package cliente;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalTime;

import javax.swing.JLabel;

public class TiempoThread extends Thread {
	JLabel lblNombre;
	LocalTime startTime;
	String nombreSala;

	public TiempoThread(JLabel lblNombre) {
		nombreSala = lblNombre.getText();
		this.lblNombre = lblNombre;
		startTime = LocalTime.now();
	}

	@Override
	public void run() {
		while (true) {
			long segundos = startTime.until(LocalTime.now(), SECONDS);
			long minutos = segundos / 60;
			segundos %= 60;
			String tiempo = String.format("%02d:%02d", minutos, segundos);
			lblNombre.setText(nombreSala + " " + tiempo);
		}

	}
}