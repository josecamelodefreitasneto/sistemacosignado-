package br.auto.service;

import br.auto.model.SystemConfig;
import br.auto.select.SystemConfigSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class SystemConfigServiceAbstract extends ServiceModelo<SystemConfig> {

	public static final int VERSAO_DE_ATUALIZACAO_DISPONIVEL = 1;

	public static final int VERSAO_DE_ATUALIZACAO_EXECUTADA = 2;

	public static final int VERSAO_DE_SCRIPT_DISPONIVEL = 3;

	public static final int VERSAO_DE_SCRIPT_EXECUTADA = 4;

	@Override
	public Class<SystemConfig> getClasse() {
		return SystemConfig.class;
	}

	@Override
	public MapSO toMap(final SystemConfig o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected SystemConfig fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final SystemConfig o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		o.setValor(mp.getString("valor"));
		return o;
	}

	@Override
	public SystemConfig newO() {
		SystemConfig o = new SystemConfig();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(final SystemConfig o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo System Config > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo System Config > Nome aceita no máximo 50 caracteres");
		}
		o.setValor(tratarString(o.getValor()));
		if (UString.length(o.getValor()) > 50) {
			throw new MessageException("O campo System Config > Valor aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNome(final SystemConfig o) {
		SystemConfigSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("systemconfig_nome"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.SystemConfig.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, SystemConfig> func) {
		final SystemConfigSelect<?> select = select();
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
		Lst<SystemConfig> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected SystemConfig buscaUnicoObrig(final MapSO params) {
		final SystemConfigSelect<?> select = select();
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String valor = params.getString("valor");
		if (!UString.isEmpty(valor)) select.valor().eq(valor);
		SystemConfig o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (valor != null) {
			s += "&& valor = '" + valor + "'";
		}
		s = "Não foi encontrado um SystemConfig com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public SystemConfig versaoDeAtualizacaoDisponivel() {
		return find(VERSAO_DE_ATUALIZACAO_DISPONIVEL);
	}

	public SystemConfig versaoDeAtualizacaoExecutada() {
		return find(VERSAO_DE_ATUALIZACAO_EXECUTADA);
	}

	public SystemConfig versaoDeScriptDisponivel() {
		return find(VERSAO_DE_SCRIPT_DISPONIVEL);
	}

	public SystemConfig versaoDeScriptExecutada() {
		return find(VERSAO_DE_SCRIPT_EXECUTADA);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected SystemConfig setOld(final SystemConfig o) {
		SystemConfig old = newO();
		old.setNome(o.getNome());
		old.setValor(o.getValor());
		o.setOld(old);
		return o;
	}

	public SystemConfigSelect<?> select(final Boolean excluido) {
		SystemConfigSelect<?> o = new SystemConfigSelect<SystemConfigSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public SystemConfigSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(final SystemConfig o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(final SystemConfig o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("SystemConfig");
		list.add("nome;valor");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("systemconfig_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}
}
