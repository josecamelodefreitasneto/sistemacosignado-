package br.auto.service;

import br.auto.model.ArquivoExtensao;
import br.auto.select.ArquivoExtensaoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class ArquivoExtensaoServiceAbstract extends ServiceModelo<ArquivoExtensao> {
	@Override
	public Class<ArquivoExtensao> getClasse() {
		return ArquivoExtensao.class;
	}
	@Override
	public MapSO toMap(final ArquivoExtensao o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected ArquivoExtensao fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ArquivoExtensao o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		return o;
	}
	@Override
	public ArquivoExtensao newO() {
		ArquivoExtensao o = new ArquivoExtensao();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final ArquivoExtensao o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Arquivo Extensão > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Arquivo Extensão > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueNome(final ArquivoExtensao o) {
		ArquivoExtensaoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("arquivoextensao_nome"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.ArquivoExtensao.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, ArquivoExtensao> func) {
		final ArquivoExtensaoSelect<?> select = select();
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
		Lst<ArquivoExtensao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected ArquivoExtensao buscaUnicoObrig(final MapSO params) {
		final ArquivoExtensaoSelect<?> select = select();
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		ArquivoExtensao o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um ArquivoExtensao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected ArquivoExtensao setOld(final ArquivoExtensao o) {
		ArquivoExtensao old = newO();
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}
	public ArquivoExtensaoSelect<?> select(final Boolean excluido) {
		ArquivoExtensaoSelect<?> o = new ArquivoExtensaoSelect<ArquivoExtensaoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ArquivoExtensaoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final ArquivoExtensao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final ArquivoExtensao o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ArquivoExtensao");
		list.add("nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("arquivoextensao_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}
}
