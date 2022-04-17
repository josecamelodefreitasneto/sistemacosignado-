package br.tcc.controllers;

import br.tcc.model.Cep;
import br.tcc.service.CepService;

import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gm.utils.date.Cronometro;
import gm.utils.string.ListString;

@RestController @RequestMapping(value="cep/")
public class CepController extends ControllerModelo {

	@Autowired CepService cepService;

	@Override
	protected CepService getService() {
		return cepService;
	}

	@RequestMapping(value="teste", method=RequestMethod.GET)
	public ResponseEntity<Object> t1() {
		return ok(() -> {
			return teste();
		});
	}

	@Transactional
	@RequestMapping(value="teste2", method=RequestMethod.GET)
	public ResponseEntity<Object> t2() {
		return ok(() -> {
			return teste();
		});
	}

	private int teste() {
		CepService service = getService();
		Cronometro cron = new Cronometro();
		ListString list = new ListString();
		list.load("/opt/desen/gm/cs2019/extras/tcc/tcc-back/src/main/resources/cargas/ceps.csv");
		for (String s : list) {
			Cep o = new Cep();
			o.setNumero(s);
			service.validarUniqueNumero(o);
		}
		return cron.segundos();
	}

}
