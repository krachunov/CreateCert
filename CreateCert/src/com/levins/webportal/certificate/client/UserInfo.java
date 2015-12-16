package com.levins.webportal.certificate.client;

public class UserInfo {
	private String userNamePassword;
	private String firstName;
	private String lastName;
	private String email;

	public UserInfo(String userNamePassword, String firsName, String lastName,
			String email) {
		this.userNamePassword = userNamePassword;
		this.firstName = firsName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%s", userNamePassword, firstName,
				lastName, email);
	}

}
