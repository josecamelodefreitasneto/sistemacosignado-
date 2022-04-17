package gm.utils.email;

import java.util.Properties;

public class PropriedadesDeEmailDeProvedoresConhecidos {

	public static final Properties GMAIL_TLS = new Properties();
	
	static {
		GMAIL_TLS.put("mail.smtp.auth", "true");
		GMAIL_TLS.put("mail.smtp.starttls.enable", "true");
		GMAIL_TLS.put("mail.smtp.host", "smtp.gmail.com");
		GMAIL_TLS.put("mail.smtp.port", "587");
	}
	
	public static final Properties GMAIL_SSL = new Properties();
	
	static {
		GMAIL_SSL.put("mail.smtps.host", "smtp.gmail.com");
		GMAIL_SSL.put("mail.smtp.host", "smtp.gmail.com");
		GMAIL_SSL.put("mail.smtp.socketFactory.port", "465");
		GMAIL_SSL.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		GMAIL_SSL.put("mail.smtp.auth", "true");
		GMAIL_SSL.put("mail.smtp.port", "465");
		GMAIL_SSL.put("mail.smtps.auth", "true");	
		GMAIL_SSL.put("mail.smtp.socketFactory.fallback", "false");
		GMAIL_SSL.put("mail.smtps.quitwait", "false");
	}
	
	public static Properties descobrir(EmailAuthenticator authenticator) {
		Properties props = authenticator.getProps();
		if (props == null) {
			props = descobrir(authenticator.getEmail());
			authenticator.setProps(props);
		}
		return props;
	}
	
	public static Properties descobrir(String email) {
		if (email.endsWith("@gmail.com")) {
			return GMAIL_SSL; 
		}
		return null;
	}
	
}
