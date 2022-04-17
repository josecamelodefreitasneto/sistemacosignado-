package br.tcc.service;

import br.tcc.model.Banco;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.BancoSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import gm.utils.number.UInteger;
import org.springframework.stereotype.Component;

@Component
public class BancoService extends ServiceModelo<Banco> {

	public static final int _001_BANCO_DO_BRASIL_S_A_BB = 1;

	public static final int _341_BANCO_ITAU_S_A_ITAU = 2;

	public static final int _033_BANCO_SANTANDER_BRASIL_S_A_SANTANDER = 3;

	public static final int _356_BANCO_REAL_S_A_ANTIGO_REAL = 4;

	public static final int _652_ITAU_UNIBANCO_HOLDING_S_A_ITAU_UNIBANCO = 5;

	public static final int _237_BANCO_BRADESCO_S_A_BRADESCO = 6;

	public static final int _745_BANCO_CITIBANK_S_A_CITIBANK = 7;

	public static final int _399_HSBC_BANK_BRASIL_S_A_BANCO_MULTIPLO_HSBC = 8;

	public static final int _104_CAIXA_ECONOMICA_FEDERAL_CAIXA = 9;

	public static final int _389_BANCO_MERCANTIL_DO_BRASIL_S_A_MERCANTIL = 10;

	public static final int _453_BANCO_RURAL_S_A_RURAL = 11;

	public static final int _422_BANCO_SAFRA_S_A_SAFRA = 12;

	public static final int _633_BANCO_RENDIMENTO_S_A = 13;

	public static final int _246_BANCO_ABC_BRASIL_S_A = 14;

	public static final int _025_BANCO_ALFA_S_A = 15;

	public static final int _641_BANCO_ALVORADA_S_A = 16;

	public static final int _029_BANCO_BANERJ_S_A = 17;

	public static final int _038_BANCO_BANESTADO_S_A = 18;

	public static final int _0_BANCO_BANKPAR_S_A = 19;

	public static final int _740_BANCO_BARCLAYS_S_A = 20;

	public static final int _107_BANCO_BBM_S_A = 21;

	public static final int _031_BANCO_BEG_S_A = 22;

	public static final int _096_BANCO_BM_F_DE_SERVICOS_DE_LIQUIDACAO_E_CUSTODIA_S_A = 23;

	public static final int _318_BANCO_BMG_S_A = 24;

	public static final int _752_BANCO_BNP_PARIBAS_BRASIL_S_A = 25;

	public static final int _248_BANCO_BOAVISTA_INTERATLANTICO_S_A = 26;

	public static final int _036_BANCO_BRADESCO_BBI_S_A = 27;

	public static final int _204_BANCO_BRADESCO_CARTOES_S_A = 28;

	public static final int _225_BANCO_BRASCAN_S_A = 29;

	public static final int _044_BANCO_BVA_S_A = 30;

	public static final int _263_BANCO_CACIQUE_S_A = 31;

	public static final int _473_BANCO_CAIXA_GERAL_BRASIL_S_A = 32;

	public static final int _222_BANCO_CALYON_BRASIL_S_A = 33;

	public static final int _040_BANCO_CARGILL_S_A = 34;

	public static final int M_08_BANCO_CITICARD_S_A = 35;

	public static final int M_19_BANCO_CNH_CAPITAL_S_A = 36;

	public static final int _215_BANCO_COMERCIAL_E_DE_INVESTIMENTO_SUDAMERIS_S_A = 37;

	public static final int _756_BANCO_COOPERATIVO_DO_BRASIL_S_A_BRASIL_S_A = 38;

	public static final int _748_BANCO_COOPERATIVO_SICREDI_S_A = 39;

	public static final int _505_BANCO_CREDIT_SUISSE_BRASIL_S_A = 40;

	public static final int _229_BANCO_CRUZEIRO_DO_SUL_S_A = 41;

	public static final int _003_BANCO_DA_AMAZONIA_S_A = 42;

	public static final int _0833_BANCO_DA_CHINA_BRASIL_S_A = 43;

	public static final int _707_BANCO_DAYCOVAL_S_A = 44;

	public static final int M_06_BANCO_DE_LAGE_LANDEN_BRASIL_S_A = 45;

	public static final int _024_BANCO_DE_PERNAMBUCO_S_A_BANDEPE = 46;

	public static final int _456_BANCO_DE_TOKYO_MITSUBISHI_UFJ_BRASIL_S_A = 47;

	public static final int _214_BANCO_DIBENS_S_A = 48;

	public static final int _047_BANCO_DO_ESTADO_DE_SERGIPE_S_A = 49;

	public static final int _037_BANCO_DO_ESTADO_DO_PARA_S_A = 50;

	public static final int _041_BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL_S_A = 51;

	public static final int _004_BANCO_DO_NORDESTE_DO_BRASIL_S_A = 52;

	public static final int _265_BANCO_FATOR_S_A = 53;

	public static final int M_03_BANCO_FIAT_S_A = 54;

	public static final int _224_BANCO_FIBRA_S_A = 55;

	public static final int _626_BANCO_FICSA_S_A = 56;

	public static final int _394_BANCO_FINASA_BMC_S_A = 57;

	public static final int M_18_BANCO_FORD_S_A = 58;

	public static final int _233_BANCO_GE_CAPITAL_S_A = 59;

	public static final int _734_BANCO_GERDAU_S_A = 60;

	public static final int M_07_BANCO_GMAC_S_A = 61;

	public static final int _612_BANCO_GUANABARA_S_A = 62;

	public static final int M_22_BANCO_HONDA_S_A = 63;

	public static final int _063_BANCO_IBI_S_A_BANCO_MULTIPLO = 64;

	public static final int M_11_BANCO_IBM_S_A = 65;

	public static final int _604_BANCO_INDUSTRIAL_DO_BRASIL_S_A = 66;

	public static final int _320_BANCO_INDUSTRIAL_E_COMERCIAL_S_A = 67;

	public static final int _653_BANCO_INDUSVAL_S_A = 68;

	public static final int _630_BANCO_INTERCAP_S_A = 69;

	public static final int _249_BANCO_INVESTCRED_UNIBANCO_S_A = 70;

	public static final int _184_BANCO_ITAU_BBA_S_A = 71;

	public static final int _479_BANCO_ITAU_BANK_S_A = 72;

	public static final int M_09_BANCO_ITAUCRED_FINANCIAMENTOS_S_A = 73;

	public static final int _376_BANCO_J_P_MORGAN_S_A = 74;

	public static final int _074_BANCO_J_SAFRA_S_A = 75;

	public static final int _217_BANCO_JOHN_DEERE_S_A = 76;

	public static final int _65_BANCO_LEMON_S_A = 77;

	public static final int _600_BANCO_LUSO_BRASILEIRO_S_A = 78;

	public static final int _755_BANCO_MERRILL_LYNCH_DE_INVESTIMENTOS_S_A = 79;

	public static final int _746_BANCO_MODAL_S_A = 80;

	public static final int _151_BANCO_NOSSA_CAIXA_S_A = 81;

	public static final int _45_BANCO_OPPORTUNITY_S_A = 82;

	public static final int _623_BANCO_PANAMERICANO_S_A = 83;

	public static final int _611_BANCO_PAULISTA_S_A = 84;

	public static final int _643_BANCO_PINE_S_A = 85;

	public static final int _638_BANCO_PROSPER_S_A = 86;

	public static final int _747_BANCO_RABOBANK_INTERNATIONAL_BRASIL_S_A = 87;

	public static final int M_16_BANCO_RODOBENS_S_A = 88;

	public static final int _072_BANCO_RURAL_MAIS_S_A = 89;

	public static final int _250_BANCO_SCHAHIN_S_A = 90;

	public static final int _749_BANCO_SIMPLES_S_A = 91;

	public static final int _366_BANCO_SOCIETE_GENERALE_BRASIL_S_A = 92;

	public static final int _637_BANCO_SOFISA_S_A = 93;

	public static final int _464_BANCO_SUMITOMO_MITSUI_BRASILEIRO_S_A = 94;

	public static final int _0825_BANCO_TOPAZIO_S_A = 95;

	public static final int M_20_BANCO_TOYOTA_DO_BRASIL_S_A = 96;

	public static final int _634_BANCO_TRIANGULO_S_A = 97;

	public static final int _208_BANCO_UBS_PACTUAL_S_A = 98;

	public static final int M_14_BANCO_VOLKSWAGEN_S_A = 99;

	public static final int _655_BANCO_VOTORANTIM_S_A = 100;

	public static final int _610_BANCO_VR_S_A = 101;

	public static final int _370_BANCO_WEST_L_B_DO_BRASIL_S_A = 102;

	public static final int _021_BANESTES_S_A_BANCO_DO_ESTADO_DO_ESPIRITO_SANTO_BANESTES = 103;

	public static final int _719_BANIF_BANCO_INTERNACIONAL_DO_FUNCHAL_BRASIL_S_A = 104;

	public static final int _073_BB_BANCO_POPULAR_DO_BRASIL_S_A = 105;

	public static final int _078_BES_INVESTIMENTO_DO_BRASIL_S_A_BANCO_DE_INVESTIMENTO = 106;

	public static final int _069_BPN_BRASIL_BANCO_MULTIPLO_S_A = 107;

	public static final int _070_BANCO_DE_BRASILIA_S_A_BRB = 108;

	public static final int _477_CITIBANK_N_A = 109;

	public static final int _0817_CONCORDIA_BANCO_S_A = 110;

	public static final int _487_DEUTSCHE_BANK_S_A_BANCO_ALEMAO = 111;

	public static final int _751_DRESDNER_BANK_BRASIL_S_A_BANCO_MULTIPLO = 112;

	public static final int _062_HIPERCARD_BANCO_MULTIPLO_S_A = 113;

	public static final int _492_ING_BANK_N_V = 114;

	public static final int _488_J_P_MORGAN_CHASE_BANK = 115;

	public static final int _409_UNIAO_DE_BANCOS_BRASILEIROS_S_A_UNIBANCO = 116;

	public static final int _230_UNICARD_BANCO_MULTIPLO_S_A = 117;

	@Override
	public Class<Banco> getClasse() {
		return Banco.class;
	}

	@Override
	public MapSO toMap(Banco o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Banco fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Banco o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		return o;
	}

	@Override
	public Banco newO() {
		Banco o = new Banco();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Banco o) {
		validar2(o);
		o.setCodigo(tratarString(o.getCodigo()));
		if (o.getCodigo() == null) {
			throw new MessageException("O campo Banco > Código é obrigatório");
		}
		if (UString.length(o.getCodigo()) > 5) {
			throw new MessageException("O campo Banco > Código aceita no máximo 5 caracteres");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Banco > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 131) {
			throw new MessageException("O campo Banco > Nome aceita no máximo 131 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueCodigo(o);
		}
		validar3(o);
	}

	public void validarUniqueCodigo(Banco o) {
		BancoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.codigo().eq(o.getCodigo());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("banco_codigo"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.Banco.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Banco> func) {
		final BancoSelect<?> select = select(null);
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
		Lst<Banco> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Banco buscaUnicoObrig(MapSO params) {

		final BancoSelect<?> select = select();
		String codigo = params.getString("codigo");
		if (!UString.isEmpty(codigo)) {
			if (UInteger.isInt(codigo) && codigo.length() < 3) {
				codigo = UInteger.format00(UInteger.toInt(codigo), 3);
			}
			select.codigo().eq(codigo);
		}

		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Banco o = select.unique();
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
		s = "Não foi encontrado um Banco com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Banco setOld(Banco o) {
		Banco old = newO();
		old.setCodigo(o.getCodigo());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public BancoSelect<?> select(Boolean excluido) {
		BancoSelect<?> o = new BancoSelect<BancoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.id().asc();
		return o;
	}

	public BancoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Banco o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Banco o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		return null;
	}

	static {
		CONSTRAINTS_MESSAGES.put("banco_codigo", "O campo Código não pode se repetir. Já existe um registro com este valor.");
	}

}
