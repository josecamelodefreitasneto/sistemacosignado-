package br.tcc.service;

import br.tcc.model.Indice;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.number.UBigDecimal;
import gm.utils.string.UString;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class IndiceService extends ServiceModelo<Indice> {

	public static final int DIA_01 = 1;

	public static final int DIA_02 = 2;

	public static final int DIA_03 = 3;

	public static final int DIA_04 = 4;

	public static final int DIA_05 = 5;

	public static final int DIA_06 = 6;

	public static final int DIA_07 = 7;

	public static final int DIA_08 = 8;

	public static final int DIA_09 = 9;

	public static final int DIA_10 = 10;

	public static final int DIA_11 = 11;

	public static final int DIA_12 = 12;

	public static final int DIA_13 = 13;

	public static final int DIA_14 = 14;

	public static final int DIA_15 = 15;

	public static final int DIA_16 = 16;

	public static final int DIA_17 = 17;

	public static final int DIA_18 = 18;

	public static final int DIA_19 = 19;

	public static final int DIA_20 = 20;

	public static final int DIA_21 = 21;

	public static final int DIA_22 = 22;

	public static final int DIA_23 = 23;

	public static final int DIA_24 = 24;

	public static final int DIA_25 = 25;

	public static final int DIA_26 = 26;

	public static final int DIA_27 = 27;

	public static final int DIA_28 = 28;

	public static final int DIA_29 = 29;

	public static final int DIA_30 = 30;

	public static final int DIA_31 = 31;

	public static final Map<Integer, Indice> map = new HashMap<>();

	@Override
	public Class<Indice> getClasse() {
		return Indice.class;
	}

	@Override
	public MapSO toMap(Indice o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Indice fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		if (id == null || id < 1) {
			return null;
		} else {
			return find(id);
		}
	}

	@Override
	public int getIdEntidade() {
		throw new MessageException("?");
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected Indice buscaUnicoObrig(MapSO params) {
		BigDecimal em12 = params.get("em12");
		BigDecimal em15 = params.get("em15");
		BigDecimal em18 = params.get("em18");
		BigDecimal em24 = params.get("em24");
		BigDecimal em30 = params.get("em30");
		BigDecimal em36 = params.get("em36");
		BigDecimal em48 = params.get("em48");
		BigDecimal em60 = params.get("em60");
		BigDecimal em72 = params.get("em72");
		BigDecimal em84 = params.get("em84");
		BigDecimal em96 = params.get("em96");
		String nome = params.getString("nome");
		Lst<Indice> list = new Lst<Indice>();
		list.addAll(map.values());
		Indice o = list.unique(item -> {
			if (em12 != null && item.getEm12() != em12) {
				return false;
			}
			if (em15 != null && item.getEm15() != em15) {
				return false;
			}
			if (em18 != null && item.getEm18() != em18) {
				return false;
			}
			if (em24 != null && item.getEm24() != em24) {
				return false;
			}
			if (em30 != null && item.getEm30() != em30) {
				return false;
			}
			if (em36 != null && item.getEm36() != em36) {
				return false;
			}
			if (em48 != null && item.getEm48() != em48) {
				return false;
			}
			if (em60 != null && item.getEm60() != em60) {
				return false;
			}
			if (em72 != null && item.getEm72() != em72) {
				return false;
			}
			if (em84 != null && item.getEm84() != em84) {
				return false;
			}
			if (em96 != null && item.getEm96() != em96) {
				return false;
			}
			if (!UString.isEmpty(nome) && !UString.equals(item.getNome(), nome)) {
				return false;
			}
			return true;
		});
		if (o != null) {
			return o;
		}
		String s = "";
		if (em12 != null) {
			s += "&& em12 = '" + em12 + "'";
		}
		if (em15 != null) {
			s += "&& em15 = '" + em15 + "'";
		}
		if (em18 != null) {
			s += "&& em18 = '" + em18 + "'";
		}
		if (em24 != null) {
			s += "&& em24 = '" + em24 + "'";
		}
		if (em30 != null) {
			s += "&& em30 = '" + em30 + "'";
		}
		if (em36 != null) {
			s += "&& em36 = '" + em36 + "'";
		}
		if (em48 != null) {
			s += "&& em48 = '" + em48 + "'";
		}
		if (em60 != null) {
			s += "&& em60 = '" + em60 + "'";
		}
		if (em72 != null) {
			s += "&& em72 = '" + em72 + "'";
		}
		if (em84 != null) {
			s += "&& em84 = '" + em84 + "'";
		}
		if (em96 != null) {
			s += "&& em96 = '" + em96 + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um Indice com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(Indice o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public Indice findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(DIA_01, new Indice(DIA_01, UBigDecimal.toBigDecimal(0.09726), UBigDecimal.toBigDecimal(0.08033), UBigDecimal.toBigDecimal(0.06902), UBigDecimal.toBigDecimal(0.05492), UBigDecimal.toBigDecimal(0.0465), UBigDecimal.toBigDecimal(0.04094), UBigDecimal.toBigDecimal(0.03414), UBigDecimal.toBigDecimal(0.0302), UBigDecimal.toBigDecimal(0.02769), UBigDecimal.toBigDecimal(0.02601), UBigDecimal.toBigDecimal(0.02481), "Dia 01"));
		map.put(DIA_02, new Indice(DIA_02, UBigDecimal.toBigDecimal(0.09719), UBigDecimal.toBigDecimal(0.08028), UBigDecimal.toBigDecimal(0.06898), UBigDecimal.toBigDecimal(0.05488), UBigDecimal.toBigDecimal(0.04647), UBigDecimal.toBigDecimal(0.04091), UBigDecimal.toBigDecimal(0.03411), UBigDecimal.toBigDecimal(0.03018), UBigDecimal.toBigDecimal(0.02767), UBigDecimal.toBigDecimal(0.02599), UBigDecimal.toBigDecimal(0.0248), "Dia 02"));
		map.put(DIA_03, new Indice(DIA_03, UBigDecimal.toBigDecimal(0.09713), UBigDecimal.toBigDecimal(0.08022), UBigDecimal.toBigDecimal(0.06893), UBigDecimal.toBigDecimal(0.05484), UBigDecimal.toBigDecimal(0.04643), UBigDecimal.toBigDecimal(0.04088), UBigDecimal.toBigDecimal(0.03409), UBigDecimal.toBigDecimal(0.03016), UBigDecimal.toBigDecimal(0.02766), UBigDecimal.toBigDecimal(0.02597), UBigDecimal.toBigDecimal(0.02478), "Dia 03"));
		map.put(DIA_04, new Indice(DIA_04, UBigDecimal.toBigDecimal(0.09706), UBigDecimal.toBigDecimal(0.08017), UBigDecimal.toBigDecimal(0.06888), UBigDecimal.toBigDecimal(0.0548), UBigDecimal.toBigDecimal(0.0464), UBigDecimal.toBigDecimal(0.04086), UBigDecimal.toBigDecimal(0.03407), UBigDecimal.toBigDecimal(0.03014), UBigDecimal.toBigDecimal(0.02764), UBigDecimal.toBigDecimal(0.02596), UBigDecimal.toBigDecimal(0.02476), "Dia 04"));
		map.put(DIA_05, new Indice(DIA_05, UBigDecimal.toBigDecimal(0.097), UBigDecimal.toBigDecimal(0.08011), UBigDecimal.toBigDecimal(0.06884), UBigDecimal.toBigDecimal(0.05477), UBigDecimal.toBigDecimal(0.04637), UBigDecimal.toBigDecimal(0.04083), UBigDecimal.toBigDecimal(0.03404), UBigDecimal.toBigDecimal(0.03012), UBigDecimal.toBigDecimal(0.02762), UBigDecimal.toBigDecimal(0.02594), UBigDecimal.toBigDecimal(0.02475), "Dia 05"));
		map.put(DIA_06, new Indice(DIA_06, UBigDecimal.toBigDecimal(0.09693), UBigDecimal.toBigDecimal(0.08006), UBigDecimal.toBigDecimal(0.06879), UBigDecimal.toBigDecimal(0.05473), UBigDecimal.toBigDecimal(0.04634), UBigDecimal.toBigDecimal(0.0408), UBigDecimal.toBigDecimal(0.03402), UBigDecimal.toBigDecimal(0.0301), UBigDecimal.toBigDecimal(0.0276), UBigDecimal.toBigDecimal(0.02592), UBigDecimal.toBigDecimal(0.02473), "Dia 06"));
		map.put(DIA_07, new Indice(DIA_07, UBigDecimal.toBigDecimal(0.09686), UBigDecimal.toBigDecimal(0.08001), UBigDecimal.toBigDecimal(0.06874), UBigDecimal.toBigDecimal(0.05469), UBigDecimal.toBigDecimal(0.04631), UBigDecimal.toBigDecimal(0.04077), UBigDecimal.toBigDecimal(0.034), UBigDecimal.toBigDecimal(0.03008), UBigDecimal.toBigDecimal(0.02758), UBigDecimal.toBigDecimal(0.0259), UBigDecimal.toBigDecimal(0.02471), "Dia 07"));
		map.put(DIA_08, new Indice(DIA_08, UBigDecimal.toBigDecimal(0.0968), UBigDecimal.toBigDecimal(0.07995), UBigDecimal.toBigDecimal(0.0687), UBigDecimal.toBigDecimal(0.05466), UBigDecimal.toBigDecimal(0.04628), UBigDecimal.toBigDecimal(0.04074), UBigDecimal.toBigDecimal(0.03397), UBigDecimal.toBigDecimal(0.03006), UBigDecimal.toBigDecimal(0.02756), UBigDecimal.toBigDecimal(0.02588), UBigDecimal.toBigDecimal(0.0247), "Dia 08"));
		map.put(DIA_09, new Indice(DIA_09, UBigDecimal.toBigDecimal(0.09673), UBigDecimal.toBigDecimal(0.0799), UBigDecimal.toBigDecimal(0.06865), UBigDecimal.toBigDecimal(0.05462), UBigDecimal.toBigDecimal(0.04625), UBigDecimal.toBigDecimal(0.04072), UBigDecimal.toBigDecimal(0.03395), UBigDecimal.toBigDecimal(0.03004), UBigDecimal.toBigDecimal(0.02754), UBigDecimal.toBigDecimal(0.02587), UBigDecimal.toBigDecimal(0.02468), "Dia 09"));
		map.put(DIA_10, new Indice(DIA_10, UBigDecimal.toBigDecimal(0.09667), UBigDecimal.toBigDecimal(0.07984), UBigDecimal.toBigDecimal(0.0686), UBigDecimal.toBigDecimal(0.05458), UBigDecimal.toBigDecimal(0.04621), UBigDecimal.toBigDecimal(0.04069), UBigDecimal.toBigDecimal(0.03393), UBigDecimal.toBigDecimal(0.03002), UBigDecimal.toBigDecimal(0.02753), UBigDecimal.toBigDecimal(0.02585), UBigDecimal.toBigDecimal(0.02466), "Dia 10"));
		map.put(DIA_11, new Indice(DIA_11, UBigDecimal.toBigDecimal(0.09852), UBigDecimal.toBigDecimal(0.08137), UBigDecimal.toBigDecimal(0.06992), UBigDecimal.toBigDecimal(0.05563), UBigDecimal.toBigDecimal(0.0471), UBigDecimal.toBigDecimal(0.04147), UBigDecimal.toBigDecimal(0.03458), UBigDecimal.toBigDecimal(0.03059), UBigDecimal.toBigDecimal(0.02805), UBigDecimal.toBigDecimal(0.02634), UBigDecimal.toBigDecimal(0.02513), "Dia 11"));
		map.put(DIA_12, new Indice(DIA_12, UBigDecimal.toBigDecimal(0.09845), UBigDecimal.toBigDecimal(0.08132), UBigDecimal.toBigDecimal(0.06987), UBigDecimal.toBigDecimal(0.05559), UBigDecimal.toBigDecimal(0.04707), UBigDecimal.toBigDecimal(0.04144), UBigDecimal.toBigDecimal(0.03455), UBigDecimal.toBigDecimal(0.03057), UBigDecimal.toBigDecimal(0.02803), UBigDecimal.toBigDecimal(0.02633), UBigDecimal.toBigDecimal(0.02512), "Dia 12"));
		map.put(DIA_13, new Indice(DIA_13, UBigDecimal.toBigDecimal(0.09838), UBigDecimal.toBigDecimal(0.08126), UBigDecimal.toBigDecimal(0.06982), UBigDecimal.toBigDecimal(0.05555), UBigDecimal.toBigDecimal(0.04703), UBigDecimal.toBigDecimal(0.04141), UBigDecimal.toBigDecimal(0.03453), UBigDecimal.toBigDecimal(0.03055), UBigDecimal.toBigDecimal(0.02801), UBigDecimal.toBigDecimal(0.02631), UBigDecimal.toBigDecimal(0.0251), "Dia 13"));
		map.put(DIA_14, new Indice(DIA_14, UBigDecimal.toBigDecimal(0.09832), UBigDecimal.toBigDecimal(0.08121), UBigDecimal.toBigDecimal(0.06977), UBigDecimal.toBigDecimal(0.05551), UBigDecimal.toBigDecimal(0.047), UBigDecimal.toBigDecimal(0.04138), UBigDecimal.toBigDecimal(0.03451), UBigDecimal.toBigDecimal(0.03053), UBigDecimal.toBigDecimal(0.02799), UBigDecimal.toBigDecimal(0.02629), UBigDecimal.toBigDecimal(0.02508), "Dia 14"));
		map.put(DIA_15, new Indice(DIA_15, UBigDecimal.toBigDecimal(0.09825), UBigDecimal.toBigDecimal(0.08115), UBigDecimal.toBigDecimal(0.06973), UBigDecimal.toBigDecimal(0.05548), UBigDecimal.toBigDecimal(0.04697), UBigDecimal.toBigDecimal(0.04136), UBigDecimal.toBigDecimal(0.03448), UBigDecimal.toBigDecimal(0.03051), UBigDecimal.toBigDecimal(0.02798), UBigDecimal.toBigDecimal(0.02627), UBigDecimal.toBigDecimal(0.02507), "Dia 15"));
		map.put(DIA_16, new Indice(DIA_16, UBigDecimal.toBigDecimal(0.09818), UBigDecimal.toBigDecimal(0.0811), UBigDecimal.toBigDecimal(0.06968), UBigDecimal.toBigDecimal(0.05544), UBigDecimal.toBigDecimal(0.04694), UBigDecimal.toBigDecimal(0.04133), UBigDecimal.toBigDecimal(0.03446), UBigDecimal.toBigDecimal(0.03049), UBigDecimal.toBigDecimal(0.02796), UBigDecimal.toBigDecimal(0.02626), UBigDecimal.toBigDecimal(0.02505), "Dia 16"));
		map.put(DIA_17, new Indice(DIA_17, UBigDecimal.toBigDecimal(0.09812), UBigDecimal.toBigDecimal(0.08104), UBigDecimal.toBigDecimal(0.06963), UBigDecimal.toBigDecimal(0.0554), UBigDecimal.toBigDecimal(0.04691), UBigDecimal.toBigDecimal(0.0413), UBigDecimal.toBigDecimal(0.03444), UBigDecimal.toBigDecimal(0.03047), UBigDecimal.toBigDecimal(0.02794), UBigDecimal.toBigDecimal(0.02624), UBigDecimal.toBigDecimal(0.02503), "Dia 17"));
		map.put(DIA_18, new Indice(DIA_18, UBigDecimal.toBigDecimal(0.09805), UBigDecimal.toBigDecimal(0.08099), UBigDecimal.toBigDecimal(0.06959), UBigDecimal.toBigDecimal(0.05536), UBigDecimal.toBigDecimal(0.04688), UBigDecimal.toBigDecimal(0.04127), UBigDecimal.toBigDecimal(0.03441), UBigDecimal.toBigDecimal(0.03045), UBigDecimal.toBigDecimal(0.02792), UBigDecimal.toBigDecimal(0.02622), UBigDecimal.toBigDecimal(0.02502), "Dia 18"));
		map.put(DIA_19, new Indice(DIA_19, UBigDecimal.toBigDecimal(0.09798), UBigDecimal.toBigDecimal(0.08093), UBigDecimal.toBigDecimal(0.06954), UBigDecimal.toBigDecimal(0.05533), UBigDecimal.toBigDecimal(0.04684), UBigDecimal.toBigDecimal(0.04124), UBigDecimal.toBigDecimal(0.03439), UBigDecimal.toBigDecimal(0.03043), UBigDecimal.toBigDecimal(0.0279), UBigDecimal.toBigDecimal(0.0262), UBigDecimal.toBigDecimal(0.025), "Dia 19"));
		map.put(DIA_20, new Indice(DIA_20, UBigDecimal.toBigDecimal(0.09792), UBigDecimal.toBigDecimal(0.08088), UBigDecimal.toBigDecimal(0.06949), UBigDecimal.toBigDecimal(0.05529), UBigDecimal.toBigDecimal(0.04681), UBigDecimal.toBigDecimal(0.04122), UBigDecimal.toBigDecimal(0.03437), UBigDecimal.toBigDecimal(0.03041), UBigDecimal.toBigDecimal(0.02788), UBigDecimal.toBigDecimal(0.02618), UBigDecimal.toBigDecimal(0.02498), "Dia 20"));
		map.put(DIA_21, new Indice(DIA_21, UBigDecimal.toBigDecimal(0.09785), UBigDecimal.toBigDecimal(0.08082), UBigDecimal.toBigDecimal(0.06944), UBigDecimal.toBigDecimal(0.05525), UBigDecimal.toBigDecimal(0.04678), UBigDecimal.toBigDecimal(0.04119), UBigDecimal.toBigDecimal(0.03434), UBigDecimal.toBigDecimal(0.03039), UBigDecimal.toBigDecimal(0.02786), UBigDecimal.toBigDecimal(0.02617), UBigDecimal.toBigDecimal(0.02497), "Dia 21"));
		map.put(DIA_22, new Indice(DIA_22, UBigDecimal.toBigDecimal(0.09779), UBigDecimal.toBigDecimal(0.08077), UBigDecimal.toBigDecimal(0.0694), UBigDecimal.toBigDecimal(0.05521), UBigDecimal.toBigDecimal(0.04675), UBigDecimal.toBigDecimal(0.04116), UBigDecimal.toBigDecimal(0.03432), UBigDecimal.toBigDecimal(0.03037), UBigDecimal.toBigDecimal(0.02784), UBigDecimal.toBigDecimal(0.02615), UBigDecimal.toBigDecimal(0.02495), "Dia 22"));
		map.put(DIA_23, new Indice(DIA_23, UBigDecimal.toBigDecimal(0.09772), UBigDecimal.toBigDecimal(0.08071), UBigDecimal.toBigDecimal(0.06935), UBigDecimal.toBigDecimal(0.05518), UBigDecimal.toBigDecimal(0.04672), UBigDecimal.toBigDecimal(0.04113), UBigDecimal.toBigDecimal(0.0343), UBigDecimal.toBigDecimal(0.03035), UBigDecimal.toBigDecimal(0.02782), UBigDecimal.toBigDecimal(0.02613), UBigDecimal.toBigDecimal(0.02493), "Dia 23"));
		map.put(DIA_24, new Indice(DIA_24, UBigDecimal.toBigDecimal(0.09765), UBigDecimal.toBigDecimal(0.08066), UBigDecimal.toBigDecimal(0.0693), UBigDecimal.toBigDecimal(0.05514), UBigDecimal.toBigDecimal(0.04669), UBigDecimal.toBigDecimal(0.0411), UBigDecimal.toBigDecimal(0.03427), UBigDecimal.toBigDecimal(0.03032), UBigDecimal.toBigDecimal(0.02781), UBigDecimal.toBigDecimal(0.02611), UBigDecimal.toBigDecimal(0.02491), "Dia 24"));
		map.put(DIA_25, new Indice(DIA_25, UBigDecimal.toBigDecimal(0.09759), UBigDecimal.toBigDecimal(0.0806), UBigDecimal.toBigDecimal(0.06926), UBigDecimal.toBigDecimal(0.0551), UBigDecimal.toBigDecimal(0.04665), UBigDecimal.toBigDecimal(0.04108), UBigDecimal.toBigDecimal(0.03425), UBigDecimal.toBigDecimal(0.0303), UBigDecimal.toBigDecimal(0.02779), UBigDecimal.toBigDecimal(0.0261), UBigDecimal.toBigDecimal(0.0249), "Dia 25"));
		map.put(DIA_26, new Indice(DIA_26, UBigDecimal.toBigDecimal(0.09752), UBigDecimal.toBigDecimal(0.08055), UBigDecimal.toBigDecimal(0.06921), UBigDecimal.toBigDecimal(0.05506), UBigDecimal.toBigDecimal(0.04662), UBigDecimal.toBigDecimal(0.04105), UBigDecimal.toBigDecimal(0.03423), UBigDecimal.toBigDecimal(0.03028), UBigDecimal.toBigDecimal(0.02777), UBigDecimal.toBigDecimal(0.02608), UBigDecimal.toBigDecimal(0.02488), "Dia 26"));
		map.put(DIA_27, new Indice(DIA_27, UBigDecimal.toBigDecimal(0.09746), UBigDecimal.toBigDecimal(0.08049), UBigDecimal.toBigDecimal(0.06916), UBigDecimal.toBigDecimal(0.05503), UBigDecimal.toBigDecimal(0.04659), UBigDecimal.toBigDecimal(0.04102), UBigDecimal.toBigDecimal(0.0342), UBigDecimal.toBigDecimal(0.03026), UBigDecimal.toBigDecimal(0.02775), UBigDecimal.toBigDecimal(0.02606), UBigDecimal.toBigDecimal(0.02486), "Dia 27"));
		map.put(DIA_28, new Indice(DIA_28, UBigDecimal.toBigDecimal(0.09739), UBigDecimal.toBigDecimal(0.08044), UBigDecimal.toBigDecimal(0.06912), UBigDecimal.toBigDecimal(0.05499), UBigDecimal.toBigDecimal(0.04656), UBigDecimal.toBigDecimal(0.04099), UBigDecimal.toBigDecimal(0.03418), UBigDecimal.toBigDecimal(0.03024), UBigDecimal.toBigDecimal(0.02773), UBigDecimal.toBigDecimal(0.02604), UBigDecimal.toBigDecimal(0.02485), "Dia 28"));
		map.put(DIA_29, new Indice(DIA_29, UBigDecimal.toBigDecimal(0.09732), UBigDecimal.toBigDecimal(0.08039), UBigDecimal.toBigDecimal(0.06907), UBigDecimal.toBigDecimal(0.05495), UBigDecimal.toBigDecimal(0.04653), UBigDecimal.toBigDecimal(0.04097), UBigDecimal.toBigDecimal(0.03416), UBigDecimal.toBigDecimal(0.03022), UBigDecimal.toBigDecimal(0.02771), UBigDecimal.toBigDecimal(0.02603), UBigDecimal.toBigDecimal(0.02483), "Dia 29"));
		map.put(DIA_30, new Indice(DIA_30, UBigDecimal.toBigDecimal(0.09726), UBigDecimal.toBigDecimal(0.08033), UBigDecimal.toBigDecimal(0.06902), UBigDecimal.toBigDecimal(0.05492), UBigDecimal.toBigDecimal(0.0465), UBigDecimal.toBigDecimal(0.04094), UBigDecimal.toBigDecimal(0.03414), UBigDecimal.toBigDecimal(0.0302), UBigDecimal.toBigDecimal(0.02769), UBigDecimal.toBigDecimal(0.02601), UBigDecimal.toBigDecimal(0.02481), "Dia 30"));
		map.put(DIA_31, new Indice(DIA_31, UBigDecimal.toBigDecimal(0.09726), UBigDecimal.toBigDecimal(0.08033), UBigDecimal.toBigDecimal(0.06902), UBigDecimal.toBigDecimal(0.05492), UBigDecimal.toBigDecimal(0.0465), UBigDecimal.toBigDecimal(0.04094), UBigDecimal.toBigDecimal(0.03414), UBigDecimal.toBigDecimal(0.0302), UBigDecimal.toBigDecimal(0.02769), UBigDecimal.toBigDecimal(0.02601), UBigDecimal.toBigDecimal(0.02481), "Dia 31"));
	}
}
