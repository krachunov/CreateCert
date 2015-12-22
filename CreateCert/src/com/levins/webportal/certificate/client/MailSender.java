package com.levins.webportal.certificate.client;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {
	private static String userStatic;
	private static String passStatic;

	public static void main(String[] args) throws UnsupportedEncodingException,
			MessagingException {
		MailSender mail = new MailSender();

		String mess = "<h1>This is actual message</h1>";
		mail.sendMail("krachunov", "Cipokrilo", "krachunov@lev-ins.com",mess);
	}

	private void sendMail(final String userName, final String password,
			String recipient, String messageBody) {
		// Recipient's email ID needs to be mentioned.
		String to = recipient;

		// Sender's email ID needs to be mentioned
		String domain = "@lev-ins.com";
		String from = userName + domain;

		// Assuming you are sending email from localhost
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

			// Attach file
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = "D:\\19_12_2015\\krach.pfx";
			String fileName = "krach.pfx";
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
