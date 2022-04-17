package br.impl.outros;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.utils.jpa.USchema;

@Component
public class ApplicationStartupCore {

	@Autowired CargaInicialCore cargaInicialDefault;
	@Autowired ApplicationProperties applicationProperties;
	
	@PostConstruct
	public void postConstruct() {
		USchema.SCHEMA_DEFAULT = applicationProperties.getSchema();
		cargaInicialDefault.exec();
	}
	
}