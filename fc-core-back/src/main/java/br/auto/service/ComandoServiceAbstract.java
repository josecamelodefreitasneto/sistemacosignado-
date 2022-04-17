package br.auto.service;

import br.auto.model.Comando;
import br.auto.select.ComandoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaCampoService;
import br.impl.service.EntidadeService;
import br.impl.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComandoServiceAbstract extends ServiceModelo<Comando> {

	@Autowired
	protected EntidadeService entidadeService;

	@Autowired
	protected ObservacaoService observacaoService;

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Comando> getClasse() {
		return Comando.class;
	}
	@Override
	public MapSO toMap(final Comando o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Comando fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Comando o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setEntidade(find(entidadeService, mp, "entidade"));
		o.setNome(mp.getString("nome"));
		return o;
	}
	@Override
	public Comando newO() {
		Comando o = new Comando();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Comando o) {
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Comando > Entidade é obrigatório");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Comando > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Comando > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNomeEntidade(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueNomeEntidade(final Comando o) {
		ComandoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		select.entidade().eq(o.getEntidade());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("comando_nome_entidade"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Comando.idEntidade;
	}
	@Override
	protected void saveObservacoes(final Comando o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Comando> func) {
		final ComandoSelect<?> select = select();
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
		Lst<Comando> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Comando buscaUnicoObrig(final MapSO params) {
		final ComandoSelect<?> select = select();
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Comando o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (entidade != null) {
			s += "&& entidade = '" + entidade + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um Comando com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return true;
	}
	@Override
	protected Comando setOld(final Comando o) {
		Comando old = newO();
		old.setEntidade(o.getEntidade());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}
	@Override
	protected boolean houveMudancas(final Comando o) {
		Comando old = o.getOld();
		if (o.getEntidade() != old.getEntidade()) return true;
		if (!UString.equals(o.getNome(), old.getNome())) return true;
		return false;
	}
	@Override
	protected void registrarAuditoriaInsert(final Comando o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUpdate(final Comando o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Comando old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Comando.entidade, old.getEntidade(), o.getEntidade());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Comando.nome, old.getNome(), o.getNome());
	}
	@Override
	protected void registrarAuditoriaDelete(final Comando o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUndelete(final Comando o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaBloqueio(final Comando o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaDesbloqueio(final Comando o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}
	public ComandoSelect<?> select(final Boolean excluido) {
		ComandoSelect<?> o = new ComandoSelect<ComandoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ComandoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final Comando o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Comando o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Comando");
		list.add("entidade;nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("comando_nome_entidade", "A combinação de campos Nome + Entidade não pode se repetir. Já existe um registro com esta combinação.");
	}
}
