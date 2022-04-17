package br.impl.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.ImportacaoArquivo;
import br.auto.model.ImportacaoArquivoErro;
import br.auto.service.ImportacaoArquivoErroServiceAbstract;

@Component
public class ImportacaoArquivoErroService extends ImportacaoArquivoErroServiceAbstract {
	
	@Autowired ImportacaoArquivoService importacaoArquivoService;
	@Autowired ImportacaoArquivoErroMensagemService importacaoArquivoErroMensagemService;

	@Transactional
	public void add(int importacaoArquivoId, int numero, String msg) {
		add(importacaoArquivoService.find(importacaoArquivoId), numero, msg);
	}
	
	public void add(ImportacaoArquivo importacaoArquivo, int numero, String msg) {
		ImportacaoArquivoErro o = newO();
		o.setImportacaoArquivo(importacaoArquivo);
		o.setLinha(numero);
		o.setErro(importacaoArquivoErroMensagemService.get(msg));
		save(o);
	}
	
}
