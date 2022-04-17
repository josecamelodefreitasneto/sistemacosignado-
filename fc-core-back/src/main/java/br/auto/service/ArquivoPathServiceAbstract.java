package br.auto.service;

import br.auto.model.ArquivoPath;
import br.auto.select.ArquivoPathSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.ArquivoExtensaoService;
import br.impl.service.FilaScriptsService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ArquivoPathServiceAbstract extends ServiceModelo<ArquivoPath> {

	@Autowired
	protected ArquivoExtensaoService arquivoExtensaoService;

	@Autowired
	protected FilaScriptsService filaScriptsService;

	@Override
	public Class<ArquivoPath> getClasse() {
		return ArquivoPath.class;
	}
	@Override
	public MapSO toMap(final ArquivoPath o, final boolean listas) {
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
	protected final void validar(final ArquivoPath o) {
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
	public void validarUniqueNome(final ArquivoPath o) {
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
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, ArquivoPath> func) {
		final ArquivoPathSelect<?> select = select();
		Integer pagina = params.getInt("pagina");
		String busca = params.getString("busca");
		if (!UString.isEmpty(busca)) select.busca().like(UString.toCampoBusca(busca));
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.registros = select.count();
		} else {
			select.page(pagina);
		}
		select.limit(30);
		Lst<ArquivoPath> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected ArquivoPath buscaUnicoObrig(final MapSO params) {
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
	public void itensInc(final ArquivoPath o) {
		filaScriptsService.add("update null.ArquivoPath set itens = itens + 1 where id = " + o.getId());
	}
	public void itensDec(final ArquivoPath o) {
		filaScriptsService.add("update null.ArquivoPath set itens = itens - 1 where id = " + o.getId());
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected ArquivoPath setOld(final ArquivoPath o) {
		ArquivoPath old = newO();
		old.setExtensao(o.getExtensao());
		old.setItens(o.getItens());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}
	public ArquivoPathSelect<?> select(final Boolean excluido) {
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
	protected void setBusca(final ArquivoPath o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final ArquivoPath o) {
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
}
