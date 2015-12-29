package com.levins.webportal.certificate.client;

import java.io.File;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.JAXB;

public class Test {
	private static String val1;
	private static JTextField userNameTextField;
	private static JPasswordField passwordTextField;
	private static JTextField serverHostTextField;

	public static void main(String[] args) {
		userNameTextField = new JTextField();
		
		JAXB.marshal(userNameTextField, new File("test.xml"));
	}
}
