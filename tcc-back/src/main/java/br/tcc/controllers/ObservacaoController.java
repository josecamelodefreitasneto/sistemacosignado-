package br.tcc.controllers;

import br.tcc.service.ObservacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("observacao/")
public class ObservacaoController extends ControllerModelo {

	@Autowired ObservacaoService observacaoService;

	@Override
	protected ObservacaoService getService() {
		return observacaoService;
	}
}
