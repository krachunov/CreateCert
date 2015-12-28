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

public class Client extends Thread {
	private static final int PORT = 3333;

	public static final String EXIT = "exit";
	public static final String SINGLE_USER = "singleUser";
	public static final String LIST_USER = "listUsers";

	private String userSender;
	private String passwordSender;
	private String pathToCertFile;
	private String host;
	private String option;
	private File file;

	public Client() {

	}

	public Client(String host, String userSender, String passwordSender,
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

			if (this.option.equals(EXIT)) {
				return;
			} else {
				if (this.option.equals(SINGLE_USER)) {
					createSingleCert(in, out);
				} else {
					if (this.option.equals(LIST_USER)) {
						createUserFromList(in, out, file);
					}
				}
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

	private void createUserFromList(DataInputStream in, DataOutputStream out,
			File file) {
		UserGenerator userGenerator = new UserGenerator();
		MailSender mailSender = new MailSender();

		List<String> newUserSendToServer;
		try {
			newUserSendToServer = userGenerator.createListOfUserFromFile(file);

			for (String line : newUserSendToServer) {
				out.writeUTF(line);
				out.flush();
				String returnedFromServer = in.readUTF();
				System.out.println("Incoming INFO from server: "+ returnedFromServer);
				mailSender.sendMail(userSender, passwordSender,returnedFromServer, pathToCertFile);
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
