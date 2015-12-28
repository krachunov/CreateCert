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

public class Client_terminal extends Thread {
	private static final String USER_MENU = "Select a setting:\n1:singleUser\n2:listUsers\n3:search\n4:exit";
	private static final int PORT = 3333;

	private String userSender;
	private String passwordSender;
	private String pathToCertFile;
	private String host;

	public Client_terminal() {

	}

	public Client_terminal(String host, String userSender, String passwordSender,
			String pathToCertFile) {
		this.host = host;
		this.userSender = userSender;
		this.passwordSender = passwordSender;
		this.pathToCertFile = pathToCertFile;
	}

	@Override
	public void run() {
		Socket socket = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		Scanner console = null;
		try {
			socket = new Socket(host, PORT);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (console != null) {
				console.close();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void userChoise(DataInputStream in, DataOutputStream out,
			String option) {

		if (option.equals("singleUser")) {
			createSingleCert(in, out);
		} else if (option.equals("listUsers")) {
			createUserFromList(in, out);
		}
	}

	// TODO
	@SuppressWarnings("unused")
	private void searchExistCert(DataInputStream in, DataOutputStream out) {
		UserGenerator userGenerator = new UserGenerator();
		MailSender mailSender = new MailSender();
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);

		System.out.println("Enter the webportal user name ");
		String searchinguserName = console.nextLine();

		try {
			out.writeUTF(searchinguserName);
			String returnedFromServer = in.readUTF();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSingleCert(DataInputStream in, DataOutputStream out) {
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

	private void createUserFromList(DataInputStream in, DataOutputStream out) {
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

	public String getUserSender() {
		return userSender;
	}

	public void setUserSender(String userSender) {
		this.userSender = userSender;
	}

	public String getPasswordSender() {
		return passwordSender;
	}

	public void setPasswordSender(String passwordSender) {
		this.passwordSender = passwordSender;
	}

	public String getPathToCertFile() {
		return pathToCertFile;
	}

	public void setPathToCertFile(String pathToCertFile) {
		this.pathToCertFile = pathToCertFile;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
