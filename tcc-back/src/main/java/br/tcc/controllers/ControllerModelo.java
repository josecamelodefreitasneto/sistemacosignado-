package br.tcc.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.model.AuditoriaEntidade;
import br.tcc.model.Comando;
import br.tcc.model.Entidade;
import br.tcc.model.Observacao;
import br.tcc.model.TipoAuditoriaEntidade;
import br.tcc.outros.EntityModelo;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import br.tcc.outros.ThreadScopeStart;
import br.tcc.service.AuditoriaEntidadeService;
import br.tcc.service.AuditoriaTransacaoService;
import br.tcc.service.ComandoService;
import br.tcc.service.EntidadeService;
import br.tcc.service.LoginService;
import br.tcc.service.ObservacaoService;
import br.tcc.service.TipoAuditoriaEntidadeService;
import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.exception.UException;
import gm.utils.lambda.FT;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.string.UString;

public abstract class ControllerModelo {
	
	@Autowired protected ThreadScopeStart startThreadScope;
	@Autowired protected ComandoService comandoService;
	@Autowired protected EntidadeService entidadeService;
	@Autowired protected LoginService loginService;
	@Autowired protected ObservacaoService observacaoService;
	@Autowired protected AuditoriaTransacaoService auditoriaTransacaoService;
	@Autowired protected AuditoriaEntidadeService auditoriaEntidadeService;
	@Autowired protected TipoAuditoriaEntidadeService tipoAuditoriaEntidadeService;
	protected abstract ServiceModelo<? extends EntityModelo> getService();
	
	@SuppressWarnings("unchecked")
	protected ResponseEntity<Object> ok(FT<Object> func) {
		try {
			long time = System.currentTimeMillis();
			Object o = func.call();
			if (ThreadScope.get() != null && ThreadScope.getDataHora() > 0 && ThreadScope.getDataHora() < time) {
				ThreadScope.setDataHora(time);
			}
			ThreadScope.finalizarComSucesso();
			if (o instanceof ResponseEntity) {
				return (ResponseEntity<Object>) o;
			} else {
				return ResponseEntity.ok(o);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			ThreadScope.dispose();
			if (e instanceof MessageException) {
				return erro500(e.getMessage());	
			} else {
				UException.trata(e).printStackTrace();
				return erro500("Ocorreu um erro no Servidor! Contacte a ??rea de desenvolvimento.");
			}
		}
	}
	protected ResponseEntity<Object> ok(MapSO map, String comando, FT<Object> func) {
		return ok(() -> {
			start(map, comando);
			return func.call();
		}); 
	}
	protected ResponseEntity<Object> erro500(String message) {
		return ResponseEntity.status(500).body(new MapSO().add("message", message));
	}
	
	protected void start(MapSO map, String comando) {
		startThreadScope.start(getEntidade(), map, comando);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<Object> save(@RequestBody final String body) {
		return ok(() -> {
			final MapSO map = MapSoFromJson.get(body);
			if (map.getInt("id") == null) {
				start(map, "insert");
			} else {
				start(map, "update");
			}
			return getService().saveAndMap(map);
		});
	}
	
	protected Comando getComando(final String nome) {
		return comandoService.get(getEntidade(), nome);
	}

	@RequestMapping(value="edit", method=RequestMethod.POST)
	public ResponseEntity<Object> edit(@RequestBody final MapSO map) {
		return ok(map, "edit", () -> getService().toMap(map.id(), true));
	}
	
	public int getIdEntidade() {
		return getService().getIdEntidade();
	}
	public Entidade getEntidade() {
		return entidadeService.find(getIdEntidade());
	}
	
	@RequestMapping(value="consultar", method=RequestMethod.GET)
	public ResponseEntity<Object> consulta(@RequestBody final MapSO map, @RequestHeader("token") String token) {
		if (UString.notEmpty(token)) {
			map.put("token", token);
		}
		return ok(map, "see", () -> getService().consulta(map));
	}	

	@RequestMapping(value="consulta", method=RequestMethod.POST)
	public ResponseEntity<Object> consulta(@RequestBody final MapSO map) {
		return ok(map, "see", () -> getService().consulta(map));
	}

	@RequestMapping(value="consulta-select", method=RequestMethod.POST)
	public ResponseEntity<Object> consultaSelect(@RequestBody final MapSO map) {
		return ok(map, "see", () -> {
			map.put("busca", map.getString("text"));
			return getService().consultaSelect(map);
		});
	}
	
	@RequestMapping(value="get-auditoria", method=RequestMethod.POST)
	public ResponseEntity<Object> logs(@RequestBody final MapSO map) {
		if (!getService().auditar()) {
			return erro500("Esta entidade n??o utiliza Auditoria!");
		}
		return ok(map, "see", () -> {
			final Lst<AuditoriaEntidade> list = auditoriaEntidadeService.list(getEntidade(), map.id());
			final List<MapSO> lst = UList.map(list, o -> {
				final MapSO mp = new MapSO();
				TipoAuditoriaEntidade tipo = tipoAuditoriaEntidadeService.find(o.getTipo());
				mp.put("id", o.getId());
				mp.put("idTipo", tipo.getId());
				mp.put("tipo", tipo.getNome());
				mp.put("usuario", o.getTransacao().getLogin().getUsuario().getNome());
				mp.put("data", new Data(o.getTransacao().getData()).format_dd_mm_yyyy_hh_mm_ss());
				mp.put("tempo", o.getTransacao().getTempo());
				return mp;
			});
			return lst;
		});
	}
	
	@RequestMapping(value="get-observacoes", method=RequestMethod.POST)
	public ResponseEntity<Object> getObservacoes(@RequestBody final MapSO map) {
		if (!getService().utilizaObservacoes()) {
			return erro500("Esta entidade n??o utiliza Observa????es!");
		}
		return ok(map, "see", () -> {
			final List<Observacao> list = observacaoService.get(getIdEntidade(), map.id());
			final List<MapSO> maps = UList.map(list, o -> observacaoService.toMap(o, true));
			return maps;
		});
	}

	@RequestMapping(value="excluir", method=RequestMethod.POST)
	public ResponseEntity<Object> excluir(@RequestBody final MapSO map) {
		return ok(map, "delete", () -> {
			getService().delete(map.id());
			return new MapSO().add("message", "Registro Exclu??do com Sucesso!");
		});
	}
	
	@RequestMapping(value = "download-template-importacao", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadTemplateImportacao() {
		try {
			final byte[] bytes = getService().getTemplateImportacao().toString("\n").getBytes();
			ByteArrayInputStream is = new ByteArrayInputStream(bytes);
			InputStreamResource resource = new InputStreamResource(is);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+getService().getClasse().getSimpleName()+"-templateImportacao.csv")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(bytes.length)
				.body(resource);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
}
