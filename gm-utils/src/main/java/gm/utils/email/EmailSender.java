package gm.utils.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.string.UString;

public class EmailSender {

	private List<InternetAddress> destinatarios;
	
	private Multipart multipart = new MimeMultipart();
	private Message message;
	private boolean assunto = false;
	private boolean texto = false;
	
	public EmailSender(String emailRemetente, String senha) {
		this(new EmailAuthenticator(emailRemetente, senha));
	}

	public EmailSender(EmailAuthenticator authenticator) {
		this( PropriedadesDeEmailDeProvedoresConhecidos.descobrir(authenticator), authenticator );
	}
	
	public EmailSender(Properties props, String emailRemetente, String senha) {
		this( props, new EmailAuthenticator(emailRemetente, senha) );
	}

	public EmailSender(Properties props, Authenticator authenticator) {
		if (props == null) {
			throw UException.runtime("props == null");
		}
		Session sessao = Session.getInstance(props, authenticator);
		message = new MimeMessage(sessao);
	}
	
	public void setAssunto(String s) {
		if (UString.isEmpty(s)) {
			throw UException.runtime("A assunto n"+UConstantes.a_til+"o pode ser branco");
		}
		try {
			message.setSubject(s);
			assunto = true;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public void setNomeRemetente(String s) {
		try {
			message.setFrom(new InternetAddress(s));
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public void addDestinatario(String s) {
		if (!UEmail.isValid(s)) {
			throw UException.runtime("O email '" + s + "' parece n"+UConstantes.a_til+"o ser v"+UConstantes.a_agudo+"lido!" );
		}
		if (destinatarios == null) {
			destinatarios = new ArrayList<>();
		}
		try {
			destinatarios.add(InternetAddress.parse(s)[0]);
		} catch (Exception e) {
			throw UException.runtime("O email '" + s + "' n"+UConstantes.a_til+"o passou na valida"+UConstantes.cedilha+""+UConstantes.a_til+"o!");
		}
	}
	
	public void setTexto(String s) {
		try {
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(s);
			multipart.addBodyPart(messageBodyPart);
			texto = true;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public void addAnexo(File file, String nomeParaEnvio) {
		
		UFile.assertExists(file);
		
		try {
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(nomeParaEnvio);
			multipart.addBodyPart(messageBodyPart);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public void send() {

		if (destinatarios == null) {
			throw UException.runtime("A lista de destinat"+UConstantes.a_agudo+"rios est"+UConstantes.a_agudo+" vazia");
		}
		if (!assunto) {
			throw UException.runtime("Preencha um assunto para o e-mail");
		}
		if (!texto) {
			throw UException.runtime("Preencha um texto para o e-mail");
		}
		
		InternetAddress[] array = new InternetAddress[destinatarios.size()];
		for (int i = 0; i < destinatarios.size(); i++) {
			array[i] = destinatarios.get(i);
		}
		
		try {
			message.setRecipients(Message.RecipientType.TO, array);
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

//	exemplo
	public static void main(String[] args) throws MessagingException {

//		Properties props = PropriedadesDeEmailDeProvedoresConhecidos.GMAIL_SSL;
//		EmailObject mail = new EmailObject(props, "francisco.gamarra@gmail.com", "@Gamarra004");

//		EmailSender mail = new EmailSender("francisco.gamarra@gmail.com", "@Gamarra004");
//		EmailSender mail = new EmailSender("francisco.gamarra@gmail.com", "rxndhzbxesvuokea");
		EmailSender mail = new EmailSender("sistema.tcc.2020@gmail.com", "fikhahgqdcsstvat");
		
		mail.setAssunto("assunto teste");
		mail.setNomeRemetente("francisco.gamarra@gmail.com");
		mail.setTexto("texto teste");
//		mail.addAnexo(new File("/java/m21/settings.xml"), "Um nome para o arquivo");
//		mail.addAnexo(new File("/java/m21/settings.xml"), "Outro arquivo");
		mail.addDestinatario("miria.paixao.de.ramos@gmail.com");
		mail.send();
	}
	
}
