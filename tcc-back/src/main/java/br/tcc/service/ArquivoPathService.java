package br.tcc.service;

import br.tcc.model.ArquivoExtensao;
import br.tcc.model.ArquivoPath;
import br.tcc.outros.ApplicationProperties;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ArquivoPathSelect;
import br.tcc.service.ArquivoExtensaoService;
import br.tcc.service.FilaScriptsService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import org.springframework.stereotype.Component;

import gm.utils.date.Data;

@Component
public class ArquivoPathService extends ServiceModelo<ArquivoPath> {

	@Autowired ArquivoExtensaoService arquivoExtensaoService;

	@Autowired FilaScriptsService filaScriptsService;

	@Override
	public Class<ArquivoPath> getClasse() {
		return ArquivoPath.class;
	}

	@Override
	public MapSO toMap(ArquivoPath o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected ArquivoPath fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ArquivoPath o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setExtensao(find(arquivoExtensaoService, mp, "extensao"));
		o.setNome(mp.getString("nome"));
		return o;
	}

	@Override
	public ArquivoPath newO() {
		ArquivoPath o = new ArquivoPath();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(ArquivoPath o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Arquivo Path > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Arquivo Path > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
		}
		validar2(o);
		if (o.getItens() == null) {
			throw new MessageException("O campo Arquivo Path > Itens é obrigatório");
		}
		validar3(o);
	}

	public void validarUniqueNome(ArquivoPath o) {
		ArquivoPathSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("arquivopath_nome"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.ArquivoPath.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ArquivoPath> func) {
		final ArquivoPathSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "extensao", select.extensao());
		FiltroConsulta.integer(params, "itens", select.itens());
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.bool(params, "excluido", select.excluido());
		FiltroConsulta.bool(params, "registroBloqueado", select.registroBloqueado());
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.pagina = 1;
			result.registros = select.count();
			result.paginas = result.registros / 30;
			if (result.registros > result.paginas * 30) {
				result.paginas++;
			}
		} else {
			select.page(pagina);
			result.pagina = pagina;
		}
		select.limit(30);
		Lst<ArquivoPath> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ArquivoPath buscaUnicoObrig(MapSO params) {
		final ArquivoPathSelect<?> select = select();
		Integer extensao = getId(params, "extensao");
		if (extensao != null) {
			select.extensao().id().eq(extensao);
		}
		Integer itens = params.get("itens");
		if (itens != null) select.itens().eq(itens);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		ArquivoPath o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (extensao != null) {
			s += "&& extensao = '" + extensao + "'";
		}
		if (itens != null) {
			s += "&& itens = '" + itens + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um ArquivoPath com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public void itensInc(ArquivoPath o) {
		filaScriptsService.add("update null.ArquivoPath set itens = itens + 1 where id = " + o.getId());
	}

	public void itensDec(ArquivoPath o) {
		filaScriptsService.add("update null.ArquivoPath set itens = itens - 1 where id = " + o.getId());
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected ArquivoPath setOld(ArquivoPath o) {
		ArquivoPath old = newO();
		old.setExtensao(o.getExtensao());
		old.setItens(o.getItens());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public ArquivoPathSelect<?> select(Boolean excluido) {
		ArquivoPathSelect<?> o = new ArquivoPathSelect<ArquivoPathSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public ArquivoPathSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(ArquivoPath o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ArquivoPath o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ArquivoPath");
		list.add("nome;extensao");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("arquivopath_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}

	@Autowired ApplicationProperties applicationProperties;

	public ArquivoPath get(ArquivoExtensao extensao) {
		ArquivoPath o = select().extensao().eq(extensao).itens().menor(1000).first();
		if (o == null) {
			o = newO();
			o.setExtensao(extensao);
			o.setItens(0);
			final Data data = Data.now();
			String fileSystem = applicationProperties.getFileSystem();
			if (!fileSystem.endsWith("/")) {
				fileSystem += "/";
			}
			o.setNome(fileSystem + data.getAno() + "/" + extensao.getNome() + "/" + data.format("[mm][dd][yy][nn][ss]"));
			final File diretorio = new File(o.getNome());
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}
			o = this.save(o);
		}
		return setOld(o);
	}

//	@Autowired JmsTemplate jmsTemplate;
//
	public void incrementarItem(ArquivoPath o) {
//		this.jmsTemplate.convertAndSend("queue.sample", o.getId());
	}
//
//	@JmsListener(destination = "queue.sample")
//	public void onReceiverQueue(Integer id) {
//		final ArquivoPath o = this.find(id);
//		o.setItens(o.getItens()+1);
//		this.save(o);
//	}


}
