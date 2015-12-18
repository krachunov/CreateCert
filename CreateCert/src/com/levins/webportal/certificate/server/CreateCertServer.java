package com.levins.webportal.certificate.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class CreateCertServer {
	public static final int LISTENING_PORT = 3333;

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(LISTENING_PORT)) {
			System.out.println("Server started listening on TCP port "
					+ LISTENING_PORT + ".");
			while (true) {
				Socket socket = serverSocket.accept();
				CertificateCreateThread dictionaryClientThread = new CertificateCreateThread(
						socket);
				dictionaryClientThread.start();
			}
		}

	}

	static class CertificateCreateThread extends Thread {
		private int CLIENT_REQUEST_TIMEOUT = 15 * 60 * 1000; // 15 min.
		private Socket connection;
		private DataInputStream in;
		private DataOutputStream out;
		private CreateNewBatFile batGenerator;

		public CertificateCreateThread(Socket connection) throws IOException {
			this.connection = connection;
			this.connection.setSoTimeout(CLIENT_REQUEST_TIMEOUT);
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
			batGenerator = new CreateNewBatFile();
		}

		public void run() {
			try {
				out.writeUTF("You are connected to server.\n");
				out.flush();
				while (!isInterrupted()) {
					String word = in.readUTF();
					if (word == null) {
						break; // Client closed the socket
					}
					batGenerator.generateBatFile(word);
					out.writeUTF(String.format(("You send to server " + word
							+ " and thread who done is this its " + getName())));
					out.flush();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.printf("%s : Connection lost  : %s:%s\n", new Date(),
					connection.getInetAddress().getHostAddress(),
					connection.getPort());
		}
	}

}
