package gm.utils.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import lombok.Getter;
import lombok.Setter;

@Getter
public class EmailAuthenticator extends Authenticator {

	private String email, senha;
	
	@Setter
	private Properties props;
	
	public EmailAuthenticator(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(email, senha);
	}
	
}
