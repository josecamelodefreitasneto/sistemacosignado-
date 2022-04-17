package br.auto.service;

import br.auto.model.Perfil;
import br.auto.select.PerfilSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class PerfilServiceAbstract extends ServiceModelo<Perfil> {
	@Override
	public Class<Perfil> getClasse() {
		return Perfil.class;
	}
	@Override
	public MapSO toMap(final Perfil o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Perfil fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Perfil o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		return o;
	}
	@Override
	public Perfil newO() {
		Perfil o = new Perfil();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Perfil o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Perfil > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Perfil > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueNome(final Perfil o) {
		PerfilSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("perfil_nome"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Perfil.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Perfil> func) {
		final PerfilSelect<?> select = select();
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
		Lst<Perfil> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Perfil buscaUnicoObrig(final MapSO params) {
		final PerfilSelect<?> select = select();
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Perfil o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um Perfil com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected Perfil setOld(final Perfil o) {
		Perfil old = newO();
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}
	public PerfilSelect<?> select(final Boolean excluido) {
		PerfilSelect<?> o = new PerfilSelect<PerfilSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public PerfilSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final Perfil o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Perfil o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Perfil");
		list.add("nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("perfil_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}
}
