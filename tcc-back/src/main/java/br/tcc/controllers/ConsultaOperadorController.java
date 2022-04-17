package br.tcc.controllers;

import br.tcc.service.ConsultaOperadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("consulta-operador/")
public class ConsultaOperadorController extends ControllerModelo {

	@Autowired ConsultaOperadorService consultaOperadorService;

	@Override
	protected ConsultaOperadorService getService() {
		return consultaOperadorService;
	}
}
