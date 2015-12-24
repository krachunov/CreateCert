package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import com.levins.webportal.certificate.data.UserGenerator;

public class Client {
	private static final String USER_MENU = "Select a setting:\n1:singleUser\n2:listUsers\n3:exit";
	private static final int PORT = 3333;
	private static String host = "172.20.10.103";
	// private static String host = "192.168.5.148";
//	 private static String host = "localhost";

	static String userSender = "krachunov";
	static String passwordSender = "Cipokrilo";
	static String pathToCertFile = "\\\\172.20.10.103\\cert\\";

	public static void main(String[] args) throws UnknownHostException,
			IOException {

		Socket socket = new Socket(host, PORT);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		Scanner console = null;
		try {
			console = new Scanner(System.in);
			String welcomeMessage = in.readUTF();
			System.out.println(welcomeMessage);

			while (true) {
				System.out.printf(USER_MENU);
				String option = console.nextLine();
				if (option.equals("exit")) {
					break;
				}
				userChoise(in, out, option);
			}
		} finally {
			if (console != null) {
				console.close();
			}
			socket.close();
		}
	}

	private static void userChoise(DataInputStream in, DataOutputStream out,
			String option) {

		if (option.equals("singleUser")) {
			createSingleCert(in, out);
		}
		if (option.equals("listUsers")) {
			createUserFromList(in, out);
		}
	}

	private static void createSingleCert(DataInputStream in,
			DataOutputStream out) {
		UserGenerator userGenerator = new UserGenerator();
		MailSender mailSender = new MailSender();

		String newUserSendToServer = userGenerator.createNewUser();
		try {
			out.writeUTF(newUserSendToServer);
			out.flush();
			String returnedFromServer = in.readUTF();
			mailSender.sendMail(userSender, passwordSender, returnedFromServer,
					pathToCertFile);
			System.out.println(returnedFromServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createUserFromList(DataInputStream in,
			DataOutputStream out) {
		UserGenerator userGenerator = new UserGenerator();
		MailSender mailSender = new MailSender();
		Scanner console = new Scanner(System.in);

		System.out.println("Enter the path into file with ");
		File file = new File(console.nextLine());
		List<String> newUserSendToServer;
		try {
			newUserSendToServer = userGenerator.createListOfUserFromFile(file);

			for (String line : newUserSendToServer) {
				out.writeUTF(line);
				out.flush();
				String returnedFromServer = in.readUTF();
				System.out.println("Incoming INFO from server: "
						+ returnedFromServer);
				mailSender.sendMail(userSender, passwordSender,
						returnedFromServer, pathToCertFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
