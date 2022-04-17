package br.impl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.auto.controllers.ImportacaoArquivoControllerAbstract;
import br.impl.service.ImportacaoArquivoProcessador;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;

@RestController @RequestMapping(value="importacao-arquivo/")
public class ImportacaoArquivoController extends ImportacaoArquivoControllerAbstract {
	
	@Autowired ImportacaoArquivoProcessador importacaoArquivoProcessador;
	
	@RequestMapping(value="processar", method=RequestMethod.POST)
	public ResponseEntity<Object> processar(@RequestBody final String body) {
		return ok(() -> {
			final MapSO map = MapSoFromJson.get(body);
			importacaoArquivoProcessador.processar(map.id());
			return getService().toMap(map.id(), true);
		});
	}

	@RequestMapping(value="gerar-arquivo-de-erros", method=RequestMethod.POST)
	public ResponseEntity<Object> gerarArquivoDeErros(@RequestBody final String body) {
		return ok(() -> {
			final MapSO map = MapSoFromJson.get(body);
			start(map, "gerar-arquivo-de-erros");
			getService().gerarArquivoDeErros(map.id());
			return getService().toMap(map.id(), true);
		});
	}
	
}
