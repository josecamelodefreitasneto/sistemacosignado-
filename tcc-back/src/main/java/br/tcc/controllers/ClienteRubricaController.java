package br.tcc.controllers;

import br.tcc.service.ClienteRubricaService;
import gm.utils.map.MapSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente-rubrica/")
public class ClienteRubricaController extends ControllerModelo {

	@Autowired ClienteRubricaService clienteRubricaService;

	@Override
	protected ClienteRubricaService getService() {
		return clienteRubricaService;
	}

	@RequestMapping(value = "rubrica-lookups", method = RequestMethod.POST)
	public ResponseEntity<Object> rubricaLookups(@RequestBody MapSO map) {
		return ok(map, "lookups", () -> getService().rubricaLookups(map.id()));
	}
}
