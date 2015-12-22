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
		MailSender mail = new MailSender();

		String senderUser = "krachunov";
		String senderPass = "Cipokrilo";
		String recipient = "krachunov@lev-ins.com";
		String mess = "<h1>This is actual message</h1>";
		String path = "D:\\19_12_2015\\";
		String fileName = "krach.pfx";

		mail.sendMail(senderUser, senderPass, recipient, mess, path, fileName);
	}

	public String crateMessageContent(String user, String password,
			String certPassword) {
		String.format(
				"User portal:%s\npassword portal:%s\nPassword certificat:%s\n",
				user, password, certPassword);
		return String.format(
				"User portal:%s\npassword portal:%s\nPassword certificat:%s\n",
				user, password, certPassword);
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
			String recipient, String messageBody, String path, String fileName) {

		String to = recipient;

		// Sender's email ID needs to be mentioned
		String domain = "@lev-ins.com";
		String from = userName + domain;

		String host = "mail.lev-ins.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.imap.starttls.enable", "true");

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);

		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			String subjectNewPortalMail = "Portal Lev Ins";
			message.setSubject(subjectNewPortalMail);

			// Send the actual HTML message, as big as you like
			// Example - "<h1>This is actual message</h1>";
			message.setContent(messageBody, "text/html");

			if (path != null && fileName != null) {
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
