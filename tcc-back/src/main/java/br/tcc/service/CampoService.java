package br.tcc.service;

import br.tcc.model.Campo;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.CampoSelect;
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
import org.springframework.stereotype.Component;

@Component
public class CampoService extends ServiceModelo<Campo> {

	@Autowired EntidadeService entidadeService;

	@Autowired ObservacaoService observacaoService;

	@Override
	public Class<Campo> getClasse() {
		return Campo.class;
	}

	@Override
	public MapSO toMap(Campo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Campo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Campo o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setEntidade(find(entidadeService, mp, "entidade"));
		o.setNome(mp.getString("nome"));
		o.setNomeNoBanco(mp.getString("nomeNoBanco"));
		o.setTipo(find(entidadeService, mp, "tipo"));
		return o;
	}

	@Override
	public Campo newO() {
		Campo o = new Campo();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Campo o) {
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Campo > Entidade é obrigatório");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Campo > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 100) {
			throw new MessageException("O campo Campo > Nome aceita no máximo 100 caracteres");
		}
		o.setNomeNoBanco(tratarString(o.getNomeNoBanco()));
		if (o.getNomeNoBanco() == null) {
			throw new MessageException("O campo Campo > Nome no Banco é obrigatório");
		}
		if (UString.length(o.getNomeNoBanco()) > 50) {
			throw new MessageException("O campo Campo > Nome no Banco aceita no máximo 50 caracteres");
		}
		if (o.getTipo() == null) {
			throw new MessageException("O campo Campo > Tipo é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNomeNoBancoEntidade(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNomeNoBancoEntidade(Campo o) {
		CampoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nomeNoBanco().eq(o.getNomeNoBanco());
		select.entidade().eq(o.getEntidade());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("campo_nomenobanco_entidade"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.Campo.idEntidade;
	}

	@Override
	protected void saveObservacoes(Campo o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Campo> func) {
		final CampoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "entidade", select.entidade());
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.string(params, "nomeNoBanco", select.nomeNoBanco());
		FiltroConsulta.fk(params, "tipo", select.tipo());
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
		Lst<Campo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Campo buscaUnicoObrig(MapSO params) {
		final CampoSelect<?> select = select();
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String nomeNoBanco = params.getString("nomeNoBanco");
		if (!UString.isEmpty(nomeNoBanco)) select.nomeNoBanco().eq(nomeNoBanco);
		Integer tipo = getId(params, "tipo");
		if (tipo != null) {
			select.tipo().id().eq(tipo);
		}
		Campo o = select.unique();
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
		if (nomeNoBanco != null) {
			s += "&& nomeNoBanco = '" + nomeNoBanco + "'";
		}
		if (tipo != null) {
			s += "&& tipo = '" + tipo + "'";
		}
		s = "Não foi encontrado um Campo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Campo setOld(Campo o) {
		Campo old = newO();
		old.setEntidade(o.getEntidade());
		old.setNome(o.getNome());
		old.setNomeNoBanco(o.getNomeNoBanco());
		old.setTipo(o.getTipo());
		o.setOld(old);
		return o;
	}

	public CampoSelect<?> select(Boolean excluido) {
		CampoSelect<?> o = new CampoSelect<CampoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public CampoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Campo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Campo o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Campo");
		list.add("entidade;tipo;nome;nomeNoBanco");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("campo_nomenobanco_entidade", "A combinação de campos Nome no Banco + Entidade não pode se repetir. Já existe um registro com esta combinação.");
	}
}
