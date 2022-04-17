package br.auto.service;

import br.auto.model.Entidade;
import br.auto.select.EntidadeSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class EntidadeServiceAbstract extends ServiceModelo<Entidade> {
	@Override
	public Class<Entidade> getClasse() {
		return Entidade.class;
	}
	@Override
	public MapSO toMap(final Entidade o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Entidade fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Entidade o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		o.setNomeClasse(mp.getString("nomeClasse"));
		o.setPrimitivo(UBoolean.toBoolean(mp.get("primitivo")));
		return o;
	}
	@Override
	public Entidade newO() {
		Entidade o = new Entidade();
		o.setPrimitivo(false);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Entidade o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Entidade > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 100) {
			throw new MessageException("O campo Entidade > Nome aceita no máximo 100 caracteres");
		}
		o.setNomeClasse(tratarString(o.getNomeClasse()));
		if (o.getNomeClasse() == null) {
			throw new MessageException("O campo Entidade > Nome Classe é obrigatório");
		}
		if (UString.length(o.getNomeClasse()) > 100) {
			throw new MessageException("O campo Entidade > Nome Classe aceita no máximo 100 caracteres");
		}
		if (o.getPrimitivo() == null) {
			throw new MessageException("O campo Entidade > Primitivo é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
			validarUniqueNomeClasse(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueNome(final Entidade o) {
		EntidadeSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("entidade_nome"));
		}
	}
	public void validarUniqueNomeClasse(final Entidade o) {
		EntidadeSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nomeClasse().eq(o.getNomeClasse());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("entidade_nomeclasse"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Entidade.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Entidade> func) {
		final EntidadeSelect<?> select = select();
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
		Lst<Entidade> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Entidade buscaUnicoObrig(final MapSO params) {
		final EntidadeSelect<?> select = select();
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String nomeClasse = params.getString("nomeClasse");
		if (!UString.isEmpty(nomeClasse)) select.nomeClasse().eq(nomeClasse);
		Boolean primitivo = params.get("primitivo");
		if (primitivo != null) select.primitivo().eq(primitivo);
		Entidade o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (nomeClasse != null) {
			s += "&& nomeClasse = '" + nomeClasse + "'";
		}
		if (primitivo != null) {
			s += "&& primitivo = '" + primitivo + "'";
		}
		s = "Não foi encontrado um Entidade com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected Entidade setOld(final Entidade o) {
		Entidade old = newO();
		old.setNome(o.getNome());
		old.setNomeClasse(o.getNomeClasse());
		old.setPrimitivo(o.getPrimitivo());
		o.setOld(old);
		return o;
	}
	public EntidadeSelect<?> select(final Boolean excluido) {
		EntidadeSelect<?> o = new EntidadeSelect<EntidadeSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public EntidadeSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final Entidade o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Entidade o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Entidade");
		list.add("nome;nomeClasse;primitivo");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("entidade_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
		CONSTRAINTS_MESSAGES.put("entidade_nomeclasse", "O campo Nome Classe não pode se repetir. Já existe um registro com este valor.");
	}
}
