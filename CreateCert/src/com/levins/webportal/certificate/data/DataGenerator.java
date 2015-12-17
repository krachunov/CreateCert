package com.levins.webportal.certificate.data;

import java.util.Scanner;

public class DataGenerator {
	
	
	public String createNewUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the user name");
		String userNameAndPassword = sc.nextLine();
		System.out.println("Enter the first name");
		String firstName = sc.nextLine();
		System.out.println("Enter the last name");
		String lastName = sc.nextLine();
		System.out.println("Enter the e-mail");
		String mail = sc.nextLine();
		UserInfo newUser = new UserInfo(userNameAndPassword, firstName,
				lastName, mail);
		return newUser.toString();

	}

}
