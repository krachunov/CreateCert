package com.levins.webportal.certificate.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ErrorLog {
	public static final String ERROR_LOG_FILE_NAME = "errorLog.log";
	public static final String SKIPPED_USERS_LOG_FILE_NAME = "skippedUserLog.log";

	public static Writer createLogFile(Exception e,String errorLogFileName) {
		Writer writer = null;
		try {
			writer = new FileWriter(errorLogFileName, true);
		} catch (IOException e1) {
			e1.printStackTrace(new PrintWriter(new BufferedWriter(writer), true));
		}
		e.printStackTrace(new PrintWriter(new BufferedWriter(writer), true));
		return writer;
	}
}
