package br.tcc.outros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component @Getter
public class ApplicationProperties {
	
	@Value("${file.system}")
	private String fileSystem;
	
	@Value("${spring.jpa.properties.hibernate.default_schema}")
	private String schema;
	
	@Value("${email.conta}")
	private String emailConta;
	
	@Value("${email.senha}")
	private String emailSenha;

}
