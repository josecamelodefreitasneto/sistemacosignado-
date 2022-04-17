package br.tcc.controllers;

import br.tcc.service.ImportacaoArquivoStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("importacao-arquivo-status/")
public class ImportacaoArquivoStatusController extends ControllerModelo {

	@Autowired ImportacaoArquivoStatusService importacaoArquivoStatusService;

	@Override
	protected ImportacaoArquivoStatusService getService() {
		return importacaoArquivoStatusService;
	}
}
