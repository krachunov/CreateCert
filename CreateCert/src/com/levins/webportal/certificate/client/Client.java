package com.levins.webportal.certificate.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static final int PORT = 3333;
//	static String host = "localhost";
	static String host = "192.168.5.148";

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket socket = new Socket(host, PORT);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		try (Scanner sc = new Scanner(System.in)) {
			String welcome = in.readUTF();
			System.out.println(welcome);
			String word = sc.nextLine();
			out.writeUTF(word);
			out.flush();
		}
	}
}
