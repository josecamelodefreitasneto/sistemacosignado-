package br.tcc.service;

import br.tcc.model.FilaScripts;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.FilaScriptsSelect;
import br.tcc.service.AuditoriaTransacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilaScriptsService extends ServiceModelo<FilaScripts> {

	@Autowired AuditoriaTransacaoService auditoriaTransacaoService;

	@Override
	public Class<FilaScripts> getClasse() {
		return FilaScripts.class;
	}

	@Override
	public MapSO toMap(FilaScripts o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected FilaScripts fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final FilaScripts o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setOperacao(find(auditoriaTransacaoService, mp, "operacao"));
		o.setSql(mp.getString("sql"));
		return o;
	}

	@Override
	public FilaScripts newO() {
		FilaScripts o = new FilaScripts();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(FilaScripts o) {
		if (o.getOperacao() == null) {
			throw new MessageException("O campo Fila Scripts > Operação é obrigatório");
		}
		o.setSql(tratarString(o.getSql()));
		if (o.getSql() == null) {
			throw new MessageException("O campo Fila Scripts > Sql é obrigatório");
		}
		if (UString.length(o.getSql()) > 500) {
			throw new MessageException("O campo Fila Scripts > Sql aceita no máximo 500 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueSqlOperacao(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueSqlOperacao(FilaScripts o) {
		FilaScriptsSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.sql().eq(o.getSql());
		select.operacao().eq(o.getOperacao());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("filascripts_sql_operacao"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.FilaScripts.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, FilaScripts> func) {
		final FilaScriptsSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "operacao", select.operacao());
		FiltroConsulta.string(params, "sql", select.sql());
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
		Lst<FilaScripts> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected FilaScripts buscaUnicoObrig(MapSO params) {
		final FilaScriptsSelect<?> select = select();
		Integer operacao = getId(params, "operacao");
		if (operacao != null) {
			select.operacao().id().eq(operacao);
		}
		String sql = params.getString("sql");
		if (!UString.isEmpty(sql)) select.sql().eq(sql);
		FilaScripts o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (operacao != null) {
			s += "&& operacao = '" + operacao + "'";
		}
		if (sql != null) {
			s += "&& sql = '" + sql + "'";
		}
		s = "Não foi encontrado um FilaScripts com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected FilaScripts setOld(FilaScripts o) {
		FilaScripts old = newO();
		old.setOperacao(o.getOperacao());
		old.setSql(o.getSql());
		o.setOld(old);
		return o;
	}

	public FilaScriptsSelect<?> select(Boolean excluido) {
		FilaScriptsSelect<?> o = new FilaScriptsSelect<FilaScriptsSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public FilaScriptsSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(FilaScripts o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(FilaScripts o) {
		if (o == null) return null;
		return o.getSql();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("FilaScripts");
		list.add("operacao;sql");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("filascripts_sql_operacao", "A combinação de campos Sql + Operação não pode se repetir. Já existe um registro com esta combinação.");
	}

	public void add(String sql) {
		final FilaScripts o = newO();
		o.setSql(sql);
		this.save(o);
	}

}
