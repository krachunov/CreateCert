package com.levins.webportal.certificate.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.client.UI.popUp.PopUpWindow;
import com.levins.webportal.certificate.data.ErrorLog;
import com.levins.webportal.certificate.data.UserToken;

public class MailSender {

	private static final String DESTINATION_TO_FILE_INSTRUCTION = "\\FileToAttach\\";
	ClientPanel clientPanel;

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
	 * @throws MessagingException
	 */
	public void sendMail(final String userName, final String password,
			String input, String pathToCertFileRoot, String... otherRecipient)
			throws MessagingException {
		System.out.println("Mail user " + userName);
		System.out.println("Mail pass " + password);
		System.out.println("Mail input " + input);
		System.out.println("Mail path to cert " + pathToCertFileRoot);
		// W00000001_01;firstName;lastName;mail;password;pathToCurrentCertificateFile
		String[] splited = input.split(";");

		String fileExtend = ".pfx";
		String fileName = splited[UserToken.USERPORTAL] + fileExtend;
		String userAndPassCertificate = splited[UserToken.USERPORTAL];
		String certPassword = splited[UserToken.PASSWORD];
		String to = null;
		if (otherRecipient != null && otherRecipient.length > 0) {
			to = otherRecipient[0];
		} else {
			to = splited[UserToken.MAIL].replace("\"", "");
		}

		System.out.println("Mail TO " + to);
		String pathToCurrentCertificateFile = splited[UserToken.PATHTOCERT];

		String domain = "@lev-ins.com";
		String from = userName + domain;
		// String host = "mail.lev-ins.com";

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "mail.lev-ins.com");
		properties.setProperty("mail.smtp.port", "25");
		properties.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
		} catch (AddressException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with sender's address");
		} catch (MessagingException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with message");
		}
		try {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
		} catch (AddressException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with recipient's address");
		} catch (MessagingException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with message");
		}

		String subjectNewPortalMail = "Portal Lev Ins";
		try {
			message.setSubject(subjectNewPortalMail);
		} catch (MessagingException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with subject");
		}
		String fullName = splited[UserToken.FIRSTNAME] + " "
				+ splited[UserToken.LASTNAME];
		String messageBody = crateMessageContent(userAndPassCertificate,
				userAndPassCertificate, certPassword, fullName);

		BodyPart messageBodyPart = new MimeBodyPart();
		try {
			messageBodyPart.setText(messageBody);
			messageBodyPart.setContent(messageBody, "text/html");
		} catch (MessagingException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with message body");
		}

		Multipart multipart = new MimeMultipart();
		try {
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			String pathToAttach = pathToCertFileRoot + "\\"
					+ pathToCurrentCertificateFile;
			attachFile(message, multipart, fileName, pathToAttach);
			attachMultipleFile(message, multipart, pathToCertFileRoot);
		} catch (MessagingException e) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e, "Problem with attached");
		}

		try {
			Transport transport = session.getTransport("smtp");
			transport.connect(userName, password); // username, password
													// to
			// connect to smtp server
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			// TODO need to stop this operation when popup error
			e.printStackTrace();
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e,
					"Problem with sending. MailSender.class line:115");
		}
		// Save message into Sent item
		Store store = session.getStore("imap");
		store.connect("mail.lev-ins.com", userName, password);

		Folder folder = store.getFolder("Sent Items");
		folder.open(Folder.READ_WRITE);
		message.setFlag(Flag.SEEN, true);
		folder.appendMessages(new Message[] { message });

		store.close();

		ClientPanel.getOutputConsoleArea().append(
				"Sent message successfully....\n");

	}

	private void attachMultipleFile(MimeMessage message, Multipart multipart,
			String pathToCertFileRoot) throws MessagingException {
		String pathToAttach;
		pathToAttach = pathToCertFileRoot + DESTINATION_TO_FILE_INSTRUCTION;
		File[] fileList = addListOfFileFromDirectory(pathToAttach);

		if (fileList != null) {
			for (File file : fileList) {
				String absolutePath = file.getPath();
				String filePath = absolutePath.substring(0,
						absolutePath.lastIndexOf(File.separator));
				attachFile(message, multipart, file.getName(), filePath + "\\");
			}
		} else {
			Exception fnf = new FileNotFoundException();
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(fnf,
					"The files to be attached missing. Please check "
							+ pathToAttach + " folder");
		}

	}

	private File[] addListOfFileFromDirectory(String path) {
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
			String certPassword, String... fullRecipientName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br>" + fullRecipientName[0]);
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

	public boolean sendErrorLog(String from_email, String password) {

		String to_email = "krachunov@lev-ins.com";
		String subject = "Error Log from Creator";
		String body = "This message has contain error log from create certificate program";
		String type = "txt";

		boolean sent = false;
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.smtp.host", "mail.lev-ins.com");
			prop.setProperty("mail.smtp.port", "25");
			Session session = Session.getDefaultInstance(prop);

			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(subject);

			InternetAddress from = new InternetAddress(from_email
					+ "@lev-ins.com");
			InternetAddress to = new InternetAddress(to_email);
			msg.addRecipient(Message.RecipientType.TO, to);
			msg.setFrom(from);

			Multipart multipart = new MimeMultipart("related");

			BodyPart htmlPart = new MimeBodyPart();
			if (type.equals("html"))
				htmlPart.setContent(body, "text/html");
			else
				htmlPart.setContent(body, "text/plain");

			multipart.addBodyPart(htmlPart);
			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(from_email, password);

			String path = "";

			attachFile(msg, multipart, ErrorLog.ERROR_LOG_FILE_NAME, path);

			// connect to smtp server
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			Store store = session.getStore("imap");
			store.connect("mail.lev-ins.com", from_email, password);

			Folder folder = store.getFolder("Sent Items");
			folder.open(Folder.READ_WRITE);
			msg.setFlag(Flag.SEEN, true);
			folder.appendMessages(new Message[] { msg });

			store.close();

		} catch (Exception e) {
			System.err.println("[MailTool] send() : " + e.getMessage());
			e.printStackTrace();
		}
		ClientPanel.getOutputConsoleArea().append("Error log is sended" + "\n");
		return sent;
	}

}
