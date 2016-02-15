package com.levins.webportal.certificate.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateCertServer extends Thread {
	public static final int LISTENING_PORT = 3333;
	final static String STAR_SERVER_MESSAGE = "Server started listening on TCP port ";
	final static String GREETING_MESSAGE_TO_CLIENT = "You are connected to server.\n";

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
			System.out.println(STAR_SERVER_MESSAGE + LISTENING_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
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

}
