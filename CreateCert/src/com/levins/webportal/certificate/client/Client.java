package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static final int PORT = 3333;
	static String host = "localhost";

	// static String host = "192.168.5.148";

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket socket = new Socket(host, PORT);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		try (Scanner console = new Scanner(System.in)) {
			String welcomeMessage = in.readUTF();
			System.out.println(welcomeMessage);
			while (true) {
				String newUserSendToServer = createNewUser();
				out.writeUTF(newUserSendToServer);
				out.flush();
				// String translation = in.readUTF();
				// System.out.println(translation);
			}
		} finally {
			socket.close();
		}
	}

	private static String createNewUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the user name");
		String userNameAndPassword = sc.nextLine();
		System.out.println("Enter the first name");
		String firstName = sc.nextLine();
		System.out.println("Enter the last name");
		String lastName = sc.nextLine();
		System.out.println("Enter the e-mail");
		String mail = sc.nextLine();
		UserInfo newUser = new UserInfo(userNameAndPassword, firstName,
				lastName, mail);
		return newUser.toString();

	}
}
