package com.levins.webportal.certificate.client;

import java.awt.Label;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import com.levins.webportal.certificate.data.UserGenerator;

public class Client {
	private static final String USER_MENU = "Select a setting:\n1:singleUser\n2:listUsers\n3:exit";
	private static final int PORT = 3333;
	// private static String host = "172.20.10.103";

	// private static String host = "192.168.5.148";
	private static String host = "localhost";

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket socket = new Socket(host, PORT);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		try {
			Scanner console = new Scanner(System.in);
			String welcomeMessage = in.readUTF();
			System.out.println(welcomeMessage);
			while (true) {
				System.out.printf(USER_MENU);
				String option = console.nextLine();
				// 3 - exit
				if (option.equals("exit")) {
					break;
				}
				userChoise(in, out, console, option);
			}
		} finally {
			socket.close();
		}
	}

	private static void userChoise(DataInputStream in, DataOutputStream out,
			Scanner console, String option) {

		UserGenerator userGenerator = new UserGenerator();
		// create single user
		if (option.equals("singleUser")) {
			String newUserSendToServer = userGenerator.createNewUser();
			try {
				out.writeUTF(newUserSendToServer);
				out.flush();
				String report = in.readUTF();
				System.out.println(report);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// create users from list
		if (option.equals("listUsers")) {
			System.out.println("Enter the path into file with ");
			File file = new File(console.nextLine());
			List<String> newUserSendToServer;
			try {
				newUserSendToServer = userGenerator
						.createListOfUserFromFile(file);

				for (String line : newUserSendToServer) {
					out.writeUTF(line); // remove quotes from the csv file
					out.flush();
					String report = in.readUTF();
					System.out.println(report);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
