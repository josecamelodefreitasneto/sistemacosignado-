package br.tcc.service;

import br.tcc.model.Rubrica;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.RubricaSelect;
import br.tcc.service.RubricaTipoService;
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
public class RubricaService extends ServiceModelo<Rubrica> {

	public static final int _00596_PENSAO_CIVIL = 596;

	public static final int _55986_PENSAO_MILITAR = 55986;

	public static final int _53341_SERVIDOR_CIVIL = 53341;

	public static final int _98020_CONTRIBUICAO_PLANO_SEGURO_SOCIAL_PENSIONISTA = 98020;

	public static final int _99015_IMPOSTO_DE_RENDA_APOSENTADO_PENSIONISTA = 99015;

	public static final int _93389_IRRF_IMPOSTO_DE_RENDA_RETIDO_NA_FONTE = 93389;

	public static final int _95534_PSS_RPGS_PREVIDENCIA_SOCIAL = 95534;

	@Autowired RubricaTipoService rubricaTipoService;

	@Override
	public Class<Rubrica> getClasse() {
		return Rubrica.class;
	}

	@Override
	public MapSO toMap(Rubrica o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Rubrica fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Rubrica o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setCodigo(mp.getString("codigo"));
		o.setNome(mp.getString("nome"));
		o.setTipo(findId(rubricaTipoService, mp, "tipo"));
		return o;
	}

	@Override
	public Rubrica newO() {
		Rubrica o = new Rubrica();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Rubrica o) {
		o.setCodigo(tratarString(o.getCodigo()));
		if (o.getCodigo() == null) {
			throw new MessageException("O campo Rubrica > Código é obrigatório");
		}
		if (UString.length(o.getCodigo()) > 50) {
			throw new MessageException("O campo Rubrica > Código aceita no máximo 50 caracteres");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Rubrica > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 100) {
			throw new MessageException("O campo Rubrica > Nome aceita no máximo 100 caracteres");
		}
		if (o.getTipo() == null) {
			throw new MessageException("O campo Rubrica > Tipo é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueCodigo(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueCodigo(Rubrica o) {
		RubricaSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.codigo().eq(o.getCodigo());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("rubrica_codigo"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.Rubrica.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Rubrica> func) {
		final RubricaSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "codigo", select.codigo());
		FiltroConsulta.string(params, "nome", select.nome());
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
		Lst<Rubrica> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Rubrica buscaUnicoObrig(MapSO params) {
		final RubricaSelect<?> select = select();
		String codigo = params.getString("codigo");
		if (!UString.isEmpty(codigo)) select.codigo().eq(codigo);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Integer tipo = getId(params, "tipo");
		if (tipo != null) {
			select.tipo().eq(tipo);
		}
		Rubrica o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (codigo != null) {
			s += "&& codigo = '" + codigo + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (tipo != null) {
			s += "&& tipo = '" + tipo + "'";
		}
		s = "Não foi encontrado um Rubrica com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Rubrica setOld(Rubrica o) {
		Rubrica old = newO();
		old.setCodigo(o.getCodigo());
		old.setNome(o.getNome());
		old.setTipo(o.getTipo());
		o.setOld(old);
		return o;
	}

	public RubricaSelect<?> select(Boolean excluido) {
		RubricaSelect<?> o = new RubricaSelect<RubricaSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.id().asc();
		return o;
	}

	public RubricaSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Rubrica o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Rubrica o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Rubrica");
		list.add("tipo;codigo;nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("rubrica_codigo", "O campo Código não pode se repetir. Já existe um registro com este valor.");
	}
}
