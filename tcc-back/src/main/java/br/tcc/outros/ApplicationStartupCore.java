package br.tcc.outros;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.config.CargaInicial;
import gm.utils.jpa.USchema;

@Component
public class ApplicationStartupCore {

	@Autowired CargaInicial cargaInicial;
	@Autowired ApplicationProperties applicationProperties;
	
	@PostConstruct
	public void postConstruct() {
		USchema.SCHEMA_DEFAULT = applicationProperties.getSchema();
		cargaInicial.exec();
	}
	
}
