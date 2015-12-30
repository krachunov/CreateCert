package com.levins.webportal.certificate.client;

import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class MailSender {

	private static final int USER_PORTAL = 0;
	private static final int PASSWORD = 3;
	private static final int MAIL = 4;
	private static final int PATH_TO_CERT = 5;

	private static final String DESTINATION_TO_FILE_INSTRUCTION = "\\FileToAttach\\";

	/**
	 * 
	 * @param userName
	 *            - the sender's user name
	 * @param password
	 *            - the sender's password
	 * @param input
	 *            - return string from server
	 * @param pathToCertFileRoot
	 *            - path to root directory, where running server
	 */
	public void sendMail(final String userName, final String password,
			String input, String pathToCertFileRoot) {

		// W00000001_01;firstName;lastName;password;mail;pathToCurrentCertificateFile
		String[] splited = input.split(";");

		String fileExtend = ".pfx";
		String fileName = splited[USER_PORTAL] + fileExtend;
		String userAndPassCertificate = splited[USER_PORTAL];
		String certPassword = splited[PASSWORD];
		String to = splited[MAIL].replace("\"", "");
		String pathToCurrentCertificateFile = splited[PATH_TO_CERT];

		String domain = "@lev-ins.com";
		String from = userName + domain;
		String host = "mail.lev-ins.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.imap.starttls.enable", "true");

		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});
		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			String subjectNewPortalMail = "Portal Lev Ins";
			message.setSubject(subjectNewPortalMail);

			String messageBody = crateMessageContent(userAndPassCertificate,
					userAndPassCertificate, certPassword);
			// message.setContent(messageBody, "text/html");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageBody);
			messageBodyPart.setContent(messageBody, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			String pathToAttach = pathToCertFileRoot
					+ pathToCurrentCertificateFile;
			attachFile(message, multipart, fileName, pathToAttach);
			attachMultipleFile(message, multipart, pathToCertFileRoot);

			Transport.send(message);
			// TODO report
			// System.out.println("Sent message successfully....");
			ClientPanel.getOutputConsoleArea().append(
					"Sent message successfully....\n");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private void attachMultipleFile(MimeMessage message, Multipart multipart,
			String pathToCertFileRoot) throws MessagingException {
		String pathToAttach;
		pathToAttach = pathToCertFileRoot + DESTINATION_TO_FILE_INSTRUCTION;
		File[] fileList = listAllFilePath(pathToAttach);
		for (File file : fileList) {

			String absolutePath = file.getPath();
			String filePath = absolutePath.substring(0,
					absolutePath.lastIndexOf(File.separator));

			attachFile(message, multipart, file.getName(), filePath + "\\");
		}
	}

	private File[] listAllFilePath(String path) {
		File currentLocation = new File(path);
		if (currentLocation.isDirectory()) {
			File[] listFiles = currentLocation.listFiles();
			return listFiles;
		}
		return null;

	}

	/**
	 * 
	 * @param user
	 *            - user to webportal
	 * @param password
	 *            - password's user
	 * @param certPassword
	 *            - password's certifica
	 * @return
	 */
	private String crateMessageContent(String user, String password,
			String certPassword) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br>User portal: " + user);
		sb.append("\n");
		sb.append("<br>password portal: " + password);
		sb.append("\n\n");
		sb.append("<br>Password certificat: " + certPassword);
		sb.append("\n");
		return sb.toString();
	}

	private void attachFile(MimeMessage message, Multipart multipart,
			String fileName, String path) throws MessagingException {

		MimeBodyPart messageBodyPart = new MimeBodyPart();

		DataSource source = new FileDataSource(path + fileName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

	}
}
