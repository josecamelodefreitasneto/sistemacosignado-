package br.tcc.controllers;

import br.tcc.service.IndiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("indice/")
public class IndiceController extends ControllerModelo {

	@Autowired IndiceService indiceService;

	@Override
	protected IndiceService getService() {
		return indiceService;
	}
}
