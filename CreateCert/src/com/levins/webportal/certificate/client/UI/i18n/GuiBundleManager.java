package com.levins.webportal.certificate.client.UI.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class GuiBundleManager {

	private String filePrefix = "language.lang";
	private ResourceBundle rb = null;
	private LocaleChangedListener listener = null;

	private static GuiBundleManager instance = null;

	private GuiBundleManager() {
		setLocale(Locale.getDefault());
	}

	public String getString(String key) {
		return rb.getString(key);
	}

	public String[] getStringArray(String key) {
		return rb.getStringArray(key);
	}

	public Locale getLocale() {
		return rb.getLocale();
	}

	public void setLocale(Locale l) {
		rb = ResourceBundle.getBundle(filePrefix, l);
		if (listener != null) {
			listener.localeChanged(rb);
		}
	}

	public LocaleChangedListener getLocaleChangedListener() {
		return listener;
	}

	public void setLocaleChangedListener(LocaleChangedListener listener) {
		this.listener = listener;
		if (listener != null) {
			listener.localeChanged(rb);
		}
	}

	public static GuiBundleManager get() {
		if (instance == null) {
			instance = new GuiBundleManager();
		}
		return instance;
	}
}