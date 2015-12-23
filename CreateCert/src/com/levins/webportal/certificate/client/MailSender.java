package com.levins.webportal.certificate.client;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {
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

		String to = splited[4].replace("\"", "");
		String fileExtend = ".pfx";
		String fileName = splited[0] + fileExtend;
		String pathToCurrentCertificateFile = splited[5];

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

			String messageBody = crateMessageContent(splited[0], splited[0],
					splited[3]);
			message.setContent(messageBody, "text/html");

			Multipart multipart = new MimeMultipart(); // Declared multipart
														// here, so it can to
														// attach multiple file
			String pathToAttach = pathToCertFileRoot
					+ pathToCurrentCertificateFile;
			attachFile(message, multipart, fileName, pathToAttach);

			// TODO
			pathToAttach = pathToCertFileRoot + DESTINATION_TO_FILE_INSTRUCTION;
			attachFile(message, multipart, "Firefox_WebPortal.pdf",
					pathToAttach);

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
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
		sb.append("Потребител и парола за портала и сертификат");
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

		// Multipart multipart = new MimeMultipart();
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		DataSource source = new FileDataSource(path + fileName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);
	}
}
