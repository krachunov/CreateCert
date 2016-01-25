package com.levins.webportal.certificate.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {
	/**
	 * http://www.mkyong.com/regular-expressions/how-to-validate-email-address-
	 * with-regular-expression/
	 */
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
					Pattern.CASE_INSENSITIVE);

	/**
	 * 
	 * @param emailStr
	 * @return - true if mail is OK or false if isn't
	 */
	static boolean validateMail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
	public static void main(String[] args) {
		System.out.println(validateMail("krachunov@lev-ins.com"));
	}
}
