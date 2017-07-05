package by.htp.CityGame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainClientCity {
	public static final String END_OF_GAME = "I Lose";
	private static final int SERVER_PORT = 8080;
	private static final String SERVER_HOST = "127.0.0.1";
	private static List<String> citiesNames = new ArrayList<>();

	public static void main(String[] args) {

		CityBase base = new CityBase();
		IntellectCity intellect = new IntellectCity();
		citiesNames = base.getCities(1);
		intellect.initCities(citiesNames);

		Socket clientSocket = null;
		try {
			InetAddress ipAddress = InetAddress.getByName(SERVER_HOST);
			clientSocket = new Socket(ipAddress, SERVER_PORT);

			InputStream is = clientSocket.getInputStream();
			OutputStream os = clientSocket.getOutputStream();

			DataInputStream in = new DataInputStream(is);
			DataOutputStream out = new DataOutputStream(os);

			String answerCity = null;
			out.writeUTF(intellect.getInitialName());
			out.flush();
			while (true) {
				answerCity = in.readUTF();

				if (answerCity.equals(END_OF_GAME)) {
					System.out.println("Server: I Lose");
					System.out.println("Client: I Wins");
					return;
				}

				System.out.println(answerCity);
				String myCity = intellect.getInBoxCity(answerCity);
				out.writeUTF(myCity);
				out.flush();

				if (myCity.equals(END_OF_GAME)) {
					return;
				}
				System.out.println("Waiting for the next line...");
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}