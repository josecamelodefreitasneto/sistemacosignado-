package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gm.utils.comum.UConstantes;
import gm.utils.comum.UObject;
import gm.utils.exception.UException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;

public class UNumber {
	
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;
	
	public static void trataSetInteger(Integer valor, Integer minimo, Integer maximo, String nome) {
		if (valor == null) return;
		if (minimo != null && valor < minimo) {
			throw UException.runtime("O valor m"+UConstantes.i_agudo+"nimo para o campo " + nome + " "+UConstantes.e_agudo+" " + minimo + ", por isso o valor " + valor + " for rejeitado!");
		}
		if (maximo != null && valor > maximo) {
			throw UException.runtime("O valor m"+UConstantes.a_agudo+"ximo para o campo " + nome + " "+UConstantes.e_agudo+" " + maximo + ", por isso o valor " + valor + " for rejeitado!");
		}
	}

	public static BigDecimal roundBigDecimal(BigDecimal valor, int integers, int fraction) {
		
		if (valor == null) {
			return null;
		}
		
		Numeric<?> n;
		if (fraction == 1) {
			n = new Numeric1(valor);
		} else if (fraction == 2) {
			n = new Numeric2(valor);
		} else {
			throw UException.runtime("N"+UConstantes.a_til+"o tratado: " + fraction);
		}
		int maxLen = integers + 1;
		String string = n.toString().replace(".", "");
		if (string.length() > maxLen) {
			throw UException.runtime("Ultrapassou: " + string);
		}
		return n.getValor();
	}

	public static String format00(String s, Integer casas) {
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}
	public static String format00(Integer i, Integer casas) {
		if (i == null) {
			i = 0;
		}
		return format00(i.longValue(), casas);
	}
	public static String format00(Long i, Integer casas) {
		if (casas < 1) {
			throw UException.runtime("O par"+UConstantes.a_circunflexo+"metro casas deve ser > 0!");
		}
		String s = "";
		if (!UObject.isEmpty(i)) {
			s = i.toString();
		}
		return format00(s, casas);
	}

	public static String format00(Integer i) {
		return format00(i.longValue(), 2);
	}

	public static boolean isNumber(Object a) {
		if (UInteger.isInt(a)) {
			return true;
		}
		if (a instanceof Double) {
			return true;
		}
		if (a instanceof BigDecimal) {
			return true;
		}
		if (a instanceof Numeric) {
			return true;
		}
		return false;
	}
	
	public static MapSO getSimpleMap(BigDecimal o) {
		return getSimpleMap( new Numeric2(o) );
	}
	
	public static MapSO getSimpleMap(Numeric2 o) {
		if (o == null) {
			return null;
		}
		MapSO map = new MapSO();
		map.put("id", o.toDouble());
		map.put("text", o.toString());
		map.put("tipo", "Numeric2");
		return map;
	}

	public static BigDecimal toMoney(Double d) {
		BigDecimal o = new BigDecimal(d);
		o = o.setScale(2, ROUNDING_MODE);
		return o;
	}

	public static BigDecimal toBigDecimal(Double d, int decimais) {
		if (d == null) {
			d = 0.0;
		}
		BigDecimal o = new BigDecimal(d);
		o = o.setScale(decimais, ROUNDING_MODE);
		return o;
	}

	public static String formatMoney(Double d) {
		return formatMoney(toMoney(d));
	}
	
	public static String formatMoney(BigDecimal o) {
		String s = o.toString();
		s = separarMilhares(UString.beforeFirst(s, ".")) + "," + UString.afterFirst(s, ".");
		return "R$ " + s;
	}
	public static String separarMilhares(Integer i) {
		if (i == null) {
			return null;
		} else {
			return separarMilhares(i.toString());
		}
	}
	public static String separarMilhares(String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UString.mantemSomenteNumeros(s);
		if (UString.length(s) < 4) {
			return s;
		}
		String result = "";
		while (UString.length(s) > 3) {
			result = "." + UString.right(s, 3) + result;
			s = UString.ignoreRight(s, 3);
		}
		return s + result;
	}
	
}
