package com.levins.webportal.certificate.client;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {

	/**
	 * 
	 * @param userName
	 *            - the sender's user name
	 * @param password
	 *            - the sender's password
	 * @param input
	 *            - return string from server
	 * @param hasAttached
	 *            - if has attached mark true
	 */
	public void sendMail(final String userName, final String password,
			String input, boolean hasAttached) {
		System.out.println("FINAL " + input);
		// result[0] - cert; result[3] - password's certification;
		// result[4] - mail;result[5] - path to cert fail;

		String[] splited = input.split(";");

		String to = splited[4].replace("\"", "");
		String fileExtend = ".pfx";
		String fileName = splited[0] + fileExtend;
		String path = splited[5];

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

			if (hasAttached) {
				attachFile(message, fileName, path);
			}

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
		sb.append("<br>User portal: " + user);
		sb.append("\n");
		sb.append("<br>password portal: " + password);
		sb.append("\n\n");
		sb.append("<br>Password certificat: " + certPassword);
		sb.append("\n");
		return sb.toString();
	}

	private void attachFile(MimeMessage message, String fileName, String path)
			throws MessagingException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		Multipart multipart = new MimeMultipart();

		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(path + fileName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);
	}
}
