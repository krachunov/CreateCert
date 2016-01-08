package com.levins.webportal.certificate.data;

public enum UserToken {
	USERPORTAL(0), FIRSTNAME(1), LASTNAME(2), PASSWORD(3), MAIL(4), PATHTOCERT(
			5), EGN(6);
	private int index;

	private UserToken(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
