package br.tcc.service;

import br.tcc.model.ConsultaOperador;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ConsultaOperadorSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConsultaOperadorService extends ServiceModelo<ConsultaOperador> {

	public static final int IGUAL = 1;

	public static final int COMECA_COM = 2;

	public static final int CONTEM = 3;

	public static final int TERMINA_COM = 4;

	public static final int MAIOR_OU_IGUAL = 5;

	public static final int MENOR_OU_IGUAL = 6;

	public static final int ENTRE = 7;

	public static final int EM = 8;

	public static final int HOJE = 10;

	public static final int HOJE_MENOS = 11;

	public static final int HOJE_MAIS = 12;

	public static final int VAZIOS = 13;

	public static final int MAIS_OPCOES = 15;

	public static final int DESMEMBRAR = 16;

	public static final int SIM = 17;

	public static final int NAO = 18;

	public static final int TODOS = 9999;

	@Override
	public Class<ConsultaOperador> getClasse() {
		return ConsultaOperador.class;
	}

	@Override
	public MapSO toMap(ConsultaOperador o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getNome() != null) {
			map.put("nome", o.getNome());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected ConsultaOperador fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ConsultaOperador o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		return o;
	}

	@Override
	public ConsultaOperador newO() {
		ConsultaOperador o = new ConsultaOperador();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(ConsultaOperador o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Consulta Operador > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 20) {
			throw new MessageException("O campo Consulta Operador > Nome aceita no máximo 20 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNome(ConsultaOperador o) {
		ConsultaOperadorSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("consultaoperador_nome"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.ConsultaOperador.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ConsultaOperador> func) {
		final ConsultaOperadorSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
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
		Lst<ConsultaOperador> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ConsultaOperador buscaUnicoObrig(MapSO params) {
		final ConsultaOperadorSelect<?> select = select();
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		ConsultaOperador o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um ConsultaOperador com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public ConsultaOperador igual() {
		return find(IGUAL);
	}

	public ConsultaOperador comecaCom() {
		return find(COMECA_COM);
	}

	public ConsultaOperador contem() {
		return find(CONTEM);
	}

	public ConsultaOperador terminaCom() {
		return find(TERMINA_COM);
	}

	public ConsultaOperador maiorOuIgual() {
		return find(MAIOR_OU_IGUAL);
	}

	public ConsultaOperador menorOuIgual() {
		return find(MENOR_OU_IGUAL);
	}

	public ConsultaOperador entre() {
		return find(ENTRE);
	}

	public ConsultaOperador em() {
		return find(EM);
	}

	public ConsultaOperador hoje() {
		return find(HOJE);
	}

	public ConsultaOperador hojeMenos() {
		return find(HOJE_MENOS);
	}

	public ConsultaOperador hojeMais() {
		return find(HOJE_MAIS);
	}

	public ConsultaOperador vazios() {
		return find(VAZIOS);
	}

	public ConsultaOperador maisOpcoes() {
		return find(MAIS_OPCOES);
	}

	public ConsultaOperador desmembrar() {
		return find(DESMEMBRAR);
	}

	public ConsultaOperador sim() {
		return find(SIM);
	}

	public ConsultaOperador nao() {
		return find(NAO);
	}

	public ConsultaOperador todos() {
		return find(TODOS);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected ConsultaOperador setOld(ConsultaOperador o) {
		ConsultaOperador old = newO();
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public ConsultaOperadorSelect<?> select(Boolean excluido) {
		ConsultaOperadorSelect<?> o = new ConsultaOperadorSelect<ConsultaOperadorSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.id().asc();
		return o;
	}

	public ConsultaOperadorSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(ConsultaOperador o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ConsultaOperador o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ConsultaOperador");
		list.add("nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("consultaoperador_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}
}
