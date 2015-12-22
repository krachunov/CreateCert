package com.levins.webportal.certificate.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

import com.levins.webportal.certificate.data.CertificateInfo;

class CertificateCreateThread extends Thread {
	private int CLIENT_REQUEST_TIMEOUT = 15 * 60 * 1000; // 15 min.
	private Socket connection;
	private DataInputStream in;
	private DataOutputStream out;
	private CreateNewBatFile batGenerator;
	private InetAddress server;

	public CertificateCreateThread(Socket connection, InetAddress inetAddress)
			throws IOException {
		this.connection = connection;
		this.connection.setSoTimeout(CLIENT_REQUEST_TIMEOUT);
		in = new DataInputStream(connection.getInputStream());
		out = new DataOutputStream(connection.getOutputStream());
		batGenerator = new CreateNewBatFile();
		this.server = inetAddress;
	}

	public void run() {
		try {

			out.writeUTF(CreateCertServer.GREETING_MESSAGE_TO_CLIENT);
			out.flush();
			System.out.println("My server: "+this.server);
			while (!isInterrupted()) {
				String input = in.readUTF();

				CertificateInfo certificate = batGenerator.generateCert(input);
				CreateCertServer.getCertificationList().add(certificate);

				String result = certificate.toString();

				out.writeUTF(result);
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