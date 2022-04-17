package br.tcc.controllers;

import br.tcc.service.AnoMesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("ano-mes/")
public class AnoMesController extends ControllerModelo {

	@Autowired AnoMesService anoMesService;

	@Override
	protected AnoMesService getService() {
		return anoMesService;
	}
}
