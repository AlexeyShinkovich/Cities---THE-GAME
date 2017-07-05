package by.htp.CityGameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import by.htp.CityGame.CityBase;
import by.htp.CityGame.IntellectCity;
import by.htp.CityGame.MainClientCity;

public class ServerGame {
	private static final int SERVER_PORT_HTTP = 8080;
	private static List<String> cityName = new ArrayList<>();

	public static void main(String[] args) {
		IntellectCity intellect = new IntellectCity();

		CityBase base = new CityBase();
		cityName = base.getCities(2);
		intellect.initCities(cityName);

		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(SERVER_PORT_HTTP);
			System.out.println("Waiting for a client...");
			Socket socket = ss.accept();
			System.out.println("Got a client ");
			System.out.println();
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			String answerCity = null;
			while (true) {
				answerCity = in.readUTF();
				if (answerCity.equals(MainClientCity.END_OF_GAME)) {
					System.out.println("Cient: I Lose");
					System.out.println("Server: I Wins");
					return;
				}
				System.out.println(answerCity);
				String myCity = intellect.getInBoxCity(answerCity);
				out.writeUTF(myCity);
				out.flush();

				if (myCity.equals(MainClientCity.END_OF_GAME)) {
					return;
				}
				System.out.println("Waiting for the next line...");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}