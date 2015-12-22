package com.levins.webportal.certificate.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
		}

	}

	public static List<CertificateInfo> getCertificationList() {
		return certificationList;
	}

	public static void setCertificationList(
			List<CertificateInfo> certificationList) {
		CreateCertServer.certificationList = certificationList;
	}

}
