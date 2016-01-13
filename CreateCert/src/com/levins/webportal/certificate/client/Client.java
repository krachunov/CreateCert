package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.data.UserGenerator;

public class Client extends Thread {
	private static final int PORT = 3333;

	public static final String EXIT = "exit";
	public static final String SINGLE_USER = "singleUser";
	public static final String FILE_WITH_USERS = "fileWithUsers";
	public static final String LIST_USER = "listUsers";

	private String userSender;
	private String passwordSender;
	private String pathToCertFile;
	private String host;
	private String option;
	private File file;
	private List<String> listWithUsers;
	private String inputSingleUser;

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
			ClientPanel.getOutputConsoleArea().append(welcomeMessage);

			if (this.option != null) {
				if (this.option.equals(EXIT)) {
					return;
				} else if (this.option.equals(SINGLE_USER)) {
					createSingleCert(in, out, getInputSingleUser());
				} else if (this.option.equals(FILE_WITH_USERS)) {
					createUserFromFile(in, out, getFile());
				} else if (this.option.equals(LIST_USER)) {
					createUserFromList(in, out, getListWithUsers());
				}
			} else {
				Exception e = new Exception();
				ClientPanel
						.popUpMessageException(
								e,
								"Not selected option. Please choose method to create certificate: List of user or Single user");
			}

		} catch (UnknownHostException e) {
			ClientPanel.popUpMessageException(e, "Problem with host");
		} catch (IOException e) {
			ClientPanel.popUpMessageException(e, "Problem with IO");
		} finally {
			if (console != null) {
				console.close();
			}
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				ClientPanel.popUpMessageException(e,
						"Problem with closing connection to server");
			}
		}
	}

	// TODO - searching user
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
			ClientPanel
					.popUpMessageException(e,
							"Problem with communication with server certificate creator,when search user ");
		}
	}

	private void createSingleCert(DataInputStream in, DataOutputStream out,
			String newUserSendToServer) {
		MailSender mailSender = new MailSender();

		try {
			out.writeUTF(newUserSendToServer);
			out.flush();
			String returnedFromServer = in.readUTF();
			// TODO - add recepient
			mailSender.sendMail(userSender, passwordSender, returnedFromServer,
					pathToCertFile);

			// TODO - ADD insert into DataBase
			ClientPanel.getOutputConsoleArea().append(returnedFromServer);
		} catch (IOException e) {
			ClientPanel
					.popUpMessageException(
							e,
							"Problem with communication with server certificate creator,when create single user ");
		}
	}

	private void createUserFromFile(DataInputStream in, DataOutputStream out,
			File file) {
		UserGenerator userGenerator = new UserGenerator();
		MailSender mailSender = new MailSender();

		List<String> newUserSendToServer;
		try {
			newUserSendToServer = userGenerator.createListOfUserFromFile(file);

			for (String line : newUserSendToServer) {
				// TODO add info for sender to server
				out.writeUTF(line);
				out.flush();
				String returnedFromServer = in.readUTF();
				ClientPanel.getOutputConsoleArea().append(
						String.format("Incoming INFO from server: %s\n",
								returnedFromServer));
				// TODO - ADD insert into DataBase
				mailSender.sendMail(userSender, passwordSender,
						returnedFromServer, pathToCertFile);
			}
		} catch (IOException e) {
			ClientPanel
					.popUpMessageException(
							e,
							"Problem with communication with server certificate creator,when create users from file ");
		}
	}

	private void createUserFromList(DataInputStream in, DataOutputStream out,
			List<String> list) {
		MailSender mailSender = new MailSender();

		List<String> newUserSendToServer;
		try {
			newUserSendToServer = list;

			for (String line : newUserSendToServer) {
				out.writeUTF(line);
				out.flush();
				String returnedFromServer = in.readUTF();
				ClientPanel.getOutputConsoleArea().append(
						String.format("Incoming INFO from server: %s\n",
								returnedFromServer));
				// TODO - ADD insert into DataBase
				mailSender.sendMail(userSender, passwordSender,
						returnedFromServer, pathToCertFile);
			}
		} catch (IOException e) {
			ClientPanel
					.popUpMessageException(
							e,
							"Problem with communication with server certificate creator,when create users from List (Useing SQL) ");
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

	public List<String> getListWithUsers() {
		return listWithUsers;
	}

	public void setListWithUsers(List<String> listWithUsers) {
		this.listWithUsers = listWithUsers;
	}

	public String getInputSingleUser() {
		return inputSingleUser;
	}

	public void setInputSingleUser(String inputSingleUser) {
		this.inputSingleUser = inputSingleUser;
	}

}
