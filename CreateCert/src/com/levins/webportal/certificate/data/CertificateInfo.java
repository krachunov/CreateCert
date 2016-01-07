package com.levins.webportal.certificate.data;

public class CertificateInfo {
	private String userName;
	private String firstName;
	private String lastName;
	private int password;
	private String email;
	private String pathToCertificateFile;
	private long egn;

/**
 * 
 * @param userName
 * @param firsName
 * @param lastName
 * @param email
 */
	public CertificateInfo(String userName, String firsName, String lastName,
			String email) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.email = email;
	}
	public CertificateInfo(String userName, String firsName, String lastName,
			int password, String email, String path) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.pathToCertificateFile = path;
	}
	
/**
 * 
 * @param userName
 * @param firsName
 * @param lastName
 * @param password
 * @param email
 * @param path
 * @param egn
 */
	public CertificateInfo(String userName, String firsName, String lastName,
			int password, String email, String path, long egn) {
		this.userName = userName;
		this.firstName = firsName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.pathToCertificateFile = path;
		this.egn = egn;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	// Return password like String to save into file
	public String getPassword() {
		return String.valueOf(password);
	}

	public String getEmail() {
		return email;
	}

	public String getPathToCertificateFile() {
		return pathToCertificateFile;
	}

	public void setPathToCertificateFile(String pathToCertificateFile) {
		this.pathToCertificateFile = pathToCertificateFile;
	}

	public long getEgn() {
		return egn;
	}

	public void setEgn(long egn) {
		this.egn = egn;
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%d;%s;%s", userName, firstName,
				lastName, password, email, pathToCertificateFile);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CertificateInfo)) {
			return false;
		}
		CertificateInfo that = (CertificateInfo) other;
		return this.getUserName().equals(that.getUserName())
				&& this.getFirstName().equals(that.getFirstName())
				&& this.getLastName().equals(that.getLastName());
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		hashCode = hashCode * 37 + this.getUserName().hashCode();
		hashCode = hashCode * 37 + this.getFirstName().hashCode();
		hashCode = hashCode * 37 + this.getLastName().hashCode();
		return hashCode;
	}
}
