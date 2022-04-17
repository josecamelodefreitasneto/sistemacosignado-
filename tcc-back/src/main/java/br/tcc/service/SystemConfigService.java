package br.tcc.service;

import br.tcc.model.SystemConfig;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.SystemConfigSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigService extends ServiceModelo<SystemConfig> {

	public static final int VERSAO_DE_ATUALIZACAO_DISPONIVEL = 1;

	public static final int VERSAO_DE_ATUALIZACAO_EXECUTADA = 2;

	public static final int VERSAO_DE_SCRIPT_DISPONIVEL = 3;

	public static final int VERSAO_DE_SCRIPT_EXECUTADA = 4;

	@Override
	public Class<SystemConfig> getClasse() {
		return SystemConfig.class;
	}

	@Override
	public MapSO toMap(SystemConfig o, final boolean listas) {
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
	protected final void validar(SystemConfig o) {
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

	public void validarUniqueNome(SystemConfig o) {
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
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, SystemConfig> func) {
		final SystemConfigSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.string(params, "valor", select.valor());
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
		Lst<SystemConfig> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected SystemConfig buscaUnicoObrig(MapSO params) {
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
	protected SystemConfig setOld(SystemConfig o) {
		SystemConfig old = newO();
		old.setNome(o.getNome());
		old.setValor(o.getValor());
		o.setOld(old);
		return o;
	}

	public SystemConfigSelect<?> select(Boolean excluido) {
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
	protected void setBusca(SystemConfig o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(SystemConfig o) {
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

	@Override
	protected void registrarAuditoriaInsert(SystemConfig o) {}

	public void validaVersaoDeScript() {
		if (!equals(versaoDeScriptDisponivel(), versaoDeScriptExecutada())) {
			throw new RuntimeException("A versão do script está diferente!");
		}
	}

	private static boolean equals(SystemConfig a, SystemConfig b) {
		if (a == null) {
			return b == null;
		} else if (b == null) {
			return false;
		} else {
			return UString.equals(a.getValor(), b.getValor());
		}
	}

	public boolean atualizado() {
		validaVersaoDeScript();
		return equals(versaoDeAtualizacaoDisponivel(), versaoDeAtualizacaoExecutada());
	}

	public void atualizar() {
		SystemConfig executada = versaoDeAtualizacaoExecutada();
		executada.setValor(versaoDeAtualizacaoDisponivel().getValor());
		save(executada);
	}

}
