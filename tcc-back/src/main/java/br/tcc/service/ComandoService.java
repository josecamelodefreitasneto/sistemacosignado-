package br.tcc.service;

import br.tcc.model.Comando;
import br.tcc.model.Entidade;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ComandoSelect;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.EntidadeService;
import br.tcc.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ComandoService extends ServiceModelo<Comando> {

	@Autowired EntidadeService entidadeService;

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Comando> getClasse() {
		return Comando.class;
	}

	@Override
	public MapSO toMap(Comando o, final boolean listas) {
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
	protected final void validar(Comando o) {
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

	public void validarUniqueNomeEntidade(Comando o) {
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
	protected void saveObservacoes(Comando o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Comando> func) {
		final ComandoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "entidade", select.entidade());
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
		Lst<Comando> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Comando buscaUnicoObrig(MapSO params) {
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
	protected Comando setOld(Comando o) {
		Comando old = newO();
		old.setEntidade(o.getEntidade());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(Comando o) {
		Comando old = o.getOld();
		if (o.getEntidade() != old.getEntidade()) {
			return true;
		}
		if (!UString.equals(o.getNome(), old.getNome())) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(Comando o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(Comando o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Comando old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Comando.entidade, old.getEntidade(), o.getEntidade());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Comando.nome, old.getNome(), o.getNome());
	}

	@Override
	protected void registrarAuditoriaDelete(Comando o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(Comando o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(Comando o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(Comando o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public ComandoSelect<?> select(Boolean excluido) {
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
	protected void setBusca(Comando o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Comando o) {
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


	@Transactional
	public Comando get(int idEntidade, String nome) {
		return get(entidadeService.find(idEntidade), nome);
	}

	@Transactional
	public int getId(int idEntidade, String nome) {
		return get(idEntidade, nome).getId();
	}

	@Transactional
	public Comando get(Entidade entidade, String nome) {
		Comando o = select(null).entidade().eq(entidade).nome().eq(nome).unique();
		if (o == null) {
			o = newO();
			o.setNome(nome);
			o.setEntidade(entidade);
			insertSemAuditoria(o);
		}
		return o;
	}

}
