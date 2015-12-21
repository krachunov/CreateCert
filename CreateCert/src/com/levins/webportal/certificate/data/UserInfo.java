package com.levins.webportal.certificate.data;

public class UserInfo {
	private String userName;
	private String firstName;
	private String lastName;
	private int password;
	private String email;

	public UserInfo(String userName, String firsName, String lastName,
			String email) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserInfo(String userName, String firsName, String lastName,
			int password, String email) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%d;%s", userName, firstName, lastName,
				password, email);
	}
}
