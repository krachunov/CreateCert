package com.levins.webportal.certificate.data;

public class CertificateInfo {
	private String userName;
	private String firstName;
	private String lastName;
	private int password;
	private String email;
	private String pathToCertificateFile;

	/**
	 * 
	 * @param userName
	 *            - String
	 * @param firsName
	 *            - String
	 * @param lastName
	 *            - String
	 * @param email
	 *            - String
	 */
	public CertificateInfo(String userName, String firsName, String lastName,
			String email) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.email = email;
	}

	/**
	 * 
	 * @param userName
	 *            - String
	 * @param firsName
	 *            - String
	 * @param lastName
	 *            - String
	 * @param password
	 *            - Integer
	 * @param email
	 *            - String
	 * @param path
	 *            - String
	 */
	public CertificateInfo(String userName, String firsName, String lastName,
			int password, String email, String path) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.pathToCertificateFile = path;
	}

	public String getPathToCertificateFile() {
		return pathToCertificateFile;
	}

	public void setPathToCertificateFile(String pathToCertificateFile) {
		this.pathToCertificateFile = pathToCertificateFile;
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%d;%s;%s", userName, firstName,
				lastName, password, email, pathToCertificateFile);
	}
}
