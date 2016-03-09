//package com.levins.webportal.certificate.connection;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//import org.apache.commons.vfs2.FileObject;
//import org.apache.commons.vfs2.FileSystemException;
//import org.apache.commons.vfs2.FileSystemOptions;
//import org.apache.commons.vfs2.Selectors;
//import org.apache.commons.vfs2.impl.StandardFileSystemManager;
//import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
//
////import sun.rmi.runtime.Log;
//
//public class SftpExample {
//
//	public void upload() {
//		try {
//			JSch jsch = new JSch();
//			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//			session.setPassword(SFTPPASS);
//			java.util.Properties config = new java.util.Properties();
//			config.put("StrictHostKeyChecking", "no");
//			session.setConfig(config);
//			session.connect();
//			channel = session.openChannel("sftp");
//			channel.connect();
//			channelSftp = (ChannelSftp) channel;
//			channelSftp.cd(SFTPWORKINGDIR);
//
//			File f1 = new File("ext_files/" + FILETOTRANSFER1);
//			channelSftp.put(new FileInputStream(f1), f1.getName());
//			File f2 = new File("ext_files/" + FILETOTRANSFER2);
//			channelSftp.put(new FileInputStream(f2), f2.getName());
//
//			channelSftp.exit();
//			session.disconnect();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		String hostName = "192.168.12.124";
//		String username = "root";
//		String password = "l3v1ns";
//		String localFilePath = "d:\\";
//		String remoteFilePath = "srv/drupal/sites/default/files/webportal";
//
//		upload(hostName, username, password, localFilePath, remoteFilePath);
//		// exist(hostName, username, password, remoteFilePath);
//		// download(hostName, username, password, localFilePath,
//		// remoteFilePath);
//		// delete(hostName, username, password, remoteFilePath);
//	}
//
//	// Method to upload a file in Remote server
//	public static void upload(String hostName, String username,
//			String password, String localFilePath, String remoteFilePath) {
//
//		File file = new File(localFilePath);
//		if (!file.exists())
//			throw new RuntimeException("Error. Local file not found");
//
//		StandardFileSystemManager manager = new StandardFileSystemManager();
//
//		try {
//			manager.init();
//
//			// Create local file object
//			FileObject localFile = manager.resolveFile(file.getAbsolutePath());
//
//			// Create remote file object
//			FileObject remoteFile = manager.resolveFile(
//					createConnectionString(hostName, username, password,
//							remoteFilePath), createDefaultOptions());
//
//			// Copy local file to sftp server
//			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
//
//			System.out.println("File upload success");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			manager.close();
//		}
//	}
//
//	// Download file function:
//	public static void download(String hostName, String username,
//			String password, String localFilePath, String remoteFilePath) {
//
//		StandardFileSystemManager manager = new StandardFileSystemManager();
//
//		try {
//			manager.init();
//
//			String downloadFilePath = localFilePath.substring(0,
//					localFilePath.lastIndexOf("."))
//					+ "_downlaod_from_sftp"
//					+ localFilePath.substring(localFilePath.lastIndexOf("."),
//							localFilePath.length());
//
//			// Create local file object
//			FileObject localFile = manager.resolveFile(downloadFilePath);
//
//			// Create remote file object
//			FileObject remoteFile = manager.resolveFile(
//					createConnectionString(hostName, username, password,
//							remoteFilePath), createDefaultOptions());
//
//			// Copy local file to sftp server
//			localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
//
//			System.out.println("File download success");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			manager.close();
//		}
//	}
//
//	// Delete file in remote system:
//	public static void delete(String hostName, String username,
//			String password, String remoteFilePath) {
//		StandardFileSystemManager manager = new StandardFileSystemManager();
//
//		try {
//			manager.init();
//
//			// Create remote object
//			FileObject remoteFile = manager.resolveFile(
//					createConnectionString(hostName, username, password,
//							remoteFilePath), createDefaultOptions());
//
//			if (remoteFile.exists()) {
//				remoteFile.delete();
//				System.out.println("Delete remote file success");
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			manager.close();
//		}
//	}
//
//	// Check remote file is exist function:
//	public static boolean exist(String hostName, String username,
//			String password, String remoteFilePath) {
//		StandardFileSystemManager manager = new StandardFileSystemManager();
//
//		try {
//			manager.init();
//
//			// Create remote object
//			FileObject remoteFile = manager.resolveFile(
//					createConnectionString(hostName, username, password,
//							remoteFilePath), createDefaultOptions());
//
//			System.out.println("File exist: " + remoteFile.exists());
//
//			return remoteFile.exists();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			manager.close();
//		}
//	}
//
//	// Establishing connection
//	public static String createConnectionString(String hostName,
//			String username, String password, String remoteFilePath) {
//		return "ftp://" + username + ":" + password + "@" + hostName + "/"
//				+ remoteFilePath;
//	}
//
//	// public static String createConnectionString(String hostName,
//	// String username, String password, String remoteFilePath) {
//	// return "sftp://" + hostName + "/" + remoteFilePath;
//	// }
//
//	// Method to setup default SFTP config:
//	public static FileSystemOptions createDefaultOptions()
//			throws FileSystemException {
//		// Create SFTP options
//		FileSystemOptions opts = new FileSystemOptions();
//
//		// SSH Key checking
//		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
//				opts, "no");
//
//		// Root directory set to user home
//		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
//
//		// Timeout is count by Milliseconds
//		SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
//
//		return opts;
//	}
//
// }