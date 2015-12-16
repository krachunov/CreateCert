package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int LISTENING_PORT = 3333;

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocet = new ServerSocket(LISTENING_PORT)) {
			System.out.println("Server started listening on TCP port "
					+ LISTENING_PORT + ".");

			Socket socet = serverSocet.accept();
			DataInputStream in = new DataInputStream(socet.getInputStream());
			DataOutputStream out = new DataOutputStream(socet.getOutputStream());
			out.writeUTF("Test server start");
			out.flush();

			String word = in.readUTF();
			System.out.println(word);

		}
	}

	private class CertificateCreateThread {

	}

}
