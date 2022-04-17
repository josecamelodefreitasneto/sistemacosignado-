package br.tcc.controllers;

import br.tcc.service.ClienteService;
import gm.utils.map.MapSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente/")
public class ClienteController extends ControllerModelo {

	@Autowired ClienteService clienteService;

	@Override
	protected ClienteService getService() {
		return clienteService;
	}

	@RequestMapping(value = "cep-lookups", method = RequestMethod.POST)
	public ResponseEntity<Object> cepLookups(@RequestBody MapSO map) {
		return ok(map, "lookups", () -> getService().cepLookups(map.id()));
	}
}
