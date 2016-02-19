package com.levins.webportal.certificate.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.levins.webportal.certificate.data.DateCreator;

public class CreateCertServer extends Thread implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final static String FILE_TO_LOAD_SETTINGS = "serverLog";
	public static final int LISTENING_PORT = 3333;
	final static String STAR_SERVER_MESSAGE = "Server started listening on TCP port ";
	final static String GREETING_MESSAGE_TO_CLIENT = "You are connected to server.\n";
	private Map<SocketAddress, String> connectedUserLog;

	@Override
	public void run() {
		connectedUserLog = new HashMap<SocketAddress, String>();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
			System.out.println(STAR_SERVER_MESSAGE + LISTENING_PORT);
			while (true) {
				Socket socket = serverSocket.accept();

				addConnectionToLog(socket);

				CertificateCreateThread certificateCreateClientThread = new CertificateCreateThread(
						socket);
				certificateCreateClientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addConnectionToLog(Socket socket) {
		SocketAddress remoteSocketAddress;
		remoteSocketAddress = socket.getRemoteSocketAddress();
		DateCreator dateCreate = new DateCreator();
		String connectionDate = dateCreate.createdDateAndTime();
		connectedUserLog.put(remoteSocketAddress, connectionDate);
	}

	public void serialize(Map<String, Object> client) throws IOException {
		File file = new File(FILE_TO_LOAD_SETTINGS);
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

		try {
			objectOutput.writeObject(client);
		} finally {
			if (fileOutput != null) {
				fileOutput.close();
			}
			if (objectOutput != null) {
				objectOutput.close();
			}
		}
	}

	public static Map<String, Object> deserialize(String fileToDeserialize)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		FileInputStream fileInput = new FileInputStream(fileToDeserialize);
		ObjectInputStream objectImput = new ObjectInputStream(fileInput);
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> deserializeClient = (Map<String, Object>) objectImput
					.readObject();
			return deserializeClient;
		} finally {
			if (fileInput != null) {
				fileInput.close();
			}
			if (objectImput != null) {
				objectImput.close();
			}
		}
	}

}
