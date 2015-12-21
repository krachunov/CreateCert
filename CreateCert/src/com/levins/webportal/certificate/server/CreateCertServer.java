package com.levins.webportal.certificate.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXB;

import com.levins.webportal.certificate.data.CertificateInfo;

public class CreateCertServer {
	public static final int LISTENING_PORT = 3333;
	final static String STAR_SERVER_MESSAGE = "Server started listening on TCP port ";
	final static String GREETING_MESSAGE_TO_CLIENT = "You are connected to server.\n";
	private static List<CertificateInfo> certificationList = new ArrayList<CertificateInfo>();

	public static void main(String[] args) throws IOException {
		// TODO load xml file with old users

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
		} finally {
			serverSocket.close();
			File xmlFile = new File("resources/ListWithCertification.xml");
			JAXB.marshal(CreateCertServer.getCertificationList(), xmlFile);
		}

	}

	public static List<CertificateInfo> getCertificationList() {
		return certificationList;
	}

	public static void setCertificationList(List<CertificateInfo> certificationList) {
		CreateCertServer.certificationList = certificationList;
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

				out.writeUTF(GREETING_MESSAGE_TO_CLIENT);
				out.flush();
				while (!isInterrupted()) {
					String input = in.readUTF();

					CertificateInfo certificate = batGenerator.generateCert(input);
					CreateCertServer.getCertificationList().add(certificate);

					String statusRequest = "You send to server "
							+ certificate.toString()
							+ " and thread who done is this its " + getName();
					out.writeUTF(String.format(statusRequest));
					out.flush();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// TODO save xml file with new users

			System.out.printf("%s : Connection lost  : %s:%s\n", new Date(),
					connection.getInetAddress().getHostAddress(),
					connection.getPort());
		}
	}
}
