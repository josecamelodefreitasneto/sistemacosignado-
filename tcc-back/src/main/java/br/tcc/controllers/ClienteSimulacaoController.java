package br.tcc.controllers;

import br.tcc.service.ClienteSimulacaoService;
import gm.utils.map.MapSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente-simulacao/")
public class ClienteSimulacaoController extends ControllerModelo {

	@Autowired ClienteSimulacaoService clienteSimulacaoService;

	@Override
	protected ClienteSimulacaoService getService() {
		return clienteSimulacaoService;
	}

	@RequestMapping(value = "contratar", method = RequestMethod.POST)
	public ResponseEntity<Object> contratar(@RequestBody MapSO map) {
		return ok(() -> {
			start(map, "contratar");
			getService().contratar(map.id());
			return getService().toMap(map.id(), true);
		});
	}
}
