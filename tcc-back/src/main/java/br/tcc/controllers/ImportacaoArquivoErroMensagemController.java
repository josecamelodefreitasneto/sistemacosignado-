package br.tcc.controllers;

import br.tcc.service.ImportacaoArquivoErroMensagemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("importacao-arquivo-erro-mensagem/")
public class ImportacaoArquivoErroMensagemController extends ControllerModelo {

	@Autowired ImportacaoArquivoErroMensagemService importacaoArquivoErroMensagemService;

	@Override
	protected ImportacaoArquivoErroMensagemService getService() {
		return importacaoArquivoErroMensagemService;
	}
}
