package br.tcc.service;

import br.tcc.model.Perfil;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.PerfilSelect;
import br.tcc.service.AuditoriaCampoService;
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
public class PerfilService extends ServiceModelo<Perfil> {

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Perfil> getClasse() {
		return Perfil.class;
	}

	@Override
	public MapSO toMap(Perfil o, final boolean listas) {
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
	protected final void validar(Perfil o) {
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

	public void validarUniqueNome(Perfil o) {
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
	protected void saveObservacoes(Perfil o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Perfil> func) {
		final PerfilSelect<?> select = select(null);
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
		Lst<Perfil> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Perfil buscaUnicoObrig(MapSO params) {
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
		return true;
	}

	@Override
	protected Perfil setOld(Perfil o) {
		Perfil old = newO();
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(Perfil o) {
		Perfil old = o.getOld();
		if (!UString.equals(o.getNome(), old.getNome())) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(Perfil o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(Perfil o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Perfil old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Perfil.nome, old.getNome(), o.getNome());
	}

	@Override
	protected void registrarAuditoriaDelete(Perfil o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(Perfil o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(Perfil o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(Perfil o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public PerfilSelect<?> select(Boolean excluido) {
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
	protected void setBusca(Perfil o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Perfil o) {
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

	@Transactional
	public Perfil add(int id, String nome) {
		if (exists(id)) {
			return find(id);
		} else {
			Perfil o = newO();
			o.setId(id);
			o.setNome(nome);
			o = insertSemAuditoria(o);
			return o;
		}
	}

}
