package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.levins.webportal.certificate.data.UserGenerator;
import com.levins.webportal.certificate.data.UserInfo;

public class Client {
	private static final int PORT = 3333;
	private static String host = "localhost";

	// static String host = "192.168.5.148";

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket socket = new Socket(host, PORT);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		UserGenerator dateGenerator = new UserGenerator();

		try (Scanner console = new Scanner(System.in)) {
			String welcomeMessage = in.readUTF();
			System.out.println(welcomeMessage);
			while (true) {
				String newUserSendToServer = dateGenerator.createNewUser();
				out.writeUTF(newUserSendToServer);
				out.flush();
				String report = in.readUTF();
				System.out.println(report);
			}
		} finally {
			socket.close();
		}
	}

}
