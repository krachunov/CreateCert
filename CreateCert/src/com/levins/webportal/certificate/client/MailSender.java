package com.levins.webportal.certificate.client;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {

	public static void main(String[] args) throws UnsupportedEncodingException,
			MessagingException {
		// MailSender mail = new MailSender();

		// String senderUser = "krachunov";
		// String senderPass = "Cipokrilo";
		// String recipient = "krachunov@lev-ins.com";
		// String mess = "<h1>This is actual message</h1>";
		// String path = "D:\\19_12_2015\\";
		// String fileName = "krach.pfx";

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
	public String crateMessageContent(String user, String password,
			String certPassword) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br>User portal: " + user);
		sb.append("\n");
		sb.append("<br>password portal: " + password);
		sb.append("\n");
		sb.append("<br>Password certificat: " + certPassword);
		sb.append("\n");

		return sb.toString();
	}

	/**
	 * 
	 * @param userName
	 *            - the sender's user name
	 * @param password
	 *            - the sender's password
	 * @param recipient
	 *            - recipien
	 * @param messageBody
	 *            - message content
	 * @param path
	 *            - destination to file who want to attached
	 * @param fileName
	 *            - the attached's file name If you wasn't attached file add
	 *            like argument null into @path and @fileName
	 */
	public void sendMail(final String userName, final String password,
			String input, boolean hasAttached) {

		// result[0] - cert; result[3] - password's certification;
		// result[4] - mail;
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

			// Send the actual HTML message, as big as you like
			// Example - "<h1>This is actual message</h1>";
			String messageBody = crateMessageContent(splited[0], splited[0],
					null);
			message.setContent(messageBody, "text/html");

			if (hasAttached) {
				// Attach file
				MimeBodyPart messageBodyPart = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();

				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(path + fileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);
			}

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
