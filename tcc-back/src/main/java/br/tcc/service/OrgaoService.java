package br.tcc.service;

import br.tcc.model.Orgao;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.OrgaoSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrgaoService extends ServiceModelo<Orgao> {

	public static final int _26201_COLEGIO_PEDRO_II = 1;

	public static final int _21000_COMANDO_DA_AERONAUTICA = 2;

	public static final int _45205_FUND_INST_BRASIL_GEOG_E_ESTATISTICA = 3;

	public static final int _36205_FUNDACAO_NACIONAL_DE_SAUDE = 4;

	public static final int _26277_FUNDACAO_UNIV_FEDERAL_DE_OURO_PRETO = 5;

	public static final int _26274_FUNDACAO_UNIV_FEDERAL_DE_UBERLANDIA = 6;

	public static final int _40803_GOVERNO_DO_EX_TERRITORIO_DE_RONDONIA = 7;

	public static final int _40804_GOVERNO_DO_EX_TERRITORIO_DE_RORAIMA = 8;

	public static final int _40801_GOVERNO_DO_EX_TERRITORIO_DO_AMAPA = 9;

	public static final int _40701_INST_BR_MEIO_AMB_REC_NAT_RENOVAVEIS = 10;

	public static final int _26403_INSTITUTO_FEDERAL_DO_AMAZONAS = 11;

	public static final int _30204_INSTITUTO_NAC_DA_PROPRIEDADE_INDUSTRIAL = 12;

	public static final int _57202_INSTITUTO_NACIONAL_DE_SEGURO_SOCIAL = 13;

	public static final int _40108_MINISTERIO_DA_CIENCIA_E_TECNOLOGIA = 14;

	public static final int _17000_MINISTERIO_DA_FAZENDA = 15;

	public static final int _25000_MINISTERIO_DA_SAUDE = 16;

	public static final int _49000_MINISTERIO_DOS_TRANSPORTES = 17;

	public static final int _40805_ORGAO_40805 = 18;

	public static final int _26269_UNIVERSIDADE_DO_RIO_DE_JANEIRO = 19;

	public static final int _26239_UNIVERSIDADE_FEDERAL_DO_PARA = 20;

	public static final int _26248_UNIVERSIDADE_FEDERAL_RURAL_DE_PERNAMBUCO = 21;

	public static final int _13000_MINIST_DA_AGRICULTURA_PECUARIA_E_ABAST = 22;

	public static final int _15000_MINISTERIO_DA_EDUCACAO = 23;

	public static final int _16000_COMANDO_DO_EXERCITO = 24;

	@Override
	public Class<Orgao> getClasse() {
		return Orgao.class;
	}

	@Override
	public MapSO toMap(Orgao o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Orgao fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Orgao o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		return o;
	}

	@Override
	public Orgao newO() {
		Orgao o = new Orgao();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Orgao o) {
		validar2(o);
		o.setCodigo(tratarString(o.getCodigo()));
		if (o.getCodigo() == null) {
			throw new MessageException("O campo Orgão > Código é obrigatório");
		}
		if (UString.length(o.getCodigo()) > 5) {
			throw new MessageException("O campo Orgão > Código aceita no máximo 5 caracteres");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Orgão > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 131) {
			throw new MessageException("O campo Orgão > Nome aceita no máximo 131 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueCodigo(o);
		}
		validar3(o);
	}

	public void validarUniqueCodigo(Orgao o) {
		OrgaoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.codigo().eq(o.getCodigo());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("orgao_codigo"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.Orgao.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Orgao> func) {
		final OrgaoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "codigo", select.codigo());
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
		Lst<Orgao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Orgao buscaUnicoObrig(MapSO params) {
		final OrgaoSelect<?> select = select();
		String codigo = params.getString("codigo");
		if (!UString.isEmpty(codigo)) select.codigo().eq(codigo);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Orgao o = select.unique();
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
		s = "Não foi encontrado um Orgao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Orgao setOld(Orgao o) {
		Orgao old = newO();
		old.setCodigo(o.getCodigo());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public OrgaoSelect<?> select(Boolean excluido) {
		OrgaoSelect<?> o = new OrgaoSelect<OrgaoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.id().asc();
		return o;
	}

	public OrgaoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Orgao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Orgao o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		return null;
	}

	static {
		CONSTRAINTS_MESSAGES.put("orgao_codigo", "O campo Código não pode se repetir. Já existe um registro com este valor.");
	}
}
