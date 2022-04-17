package br.tcc.controllers;

import br.tcc.service.ImportacaoArquivoErroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("importacao-arquivo-erro/")
public class ImportacaoArquivoErroController extends ControllerModelo {

	@Autowired ImportacaoArquivoErroService importacaoArquivoErroService;

	@Override
	protected ImportacaoArquivoErroService getService() {
		return importacaoArquivoErroService;
	}
}
