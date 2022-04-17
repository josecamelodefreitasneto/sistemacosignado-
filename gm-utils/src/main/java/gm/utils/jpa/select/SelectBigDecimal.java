package gm.utils.jpa.select;

import java.math.BigDecimal;

import gm.utils.classes.UClass;
import gm.utils.comum.UGenerics;
import gm.utils.number.Numeric;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

public class SelectBigDecimal<TS extends SelectBase<?,?,?>, NUMERIC extends Numeric<?>> extends SelectTypedLogical<TS, BigDecimal> {
	public SelectBigDecimal(TS x, String campo) {
		super(x, campo);
	}
	private int precision = 0;
	private Class<NUMERIC> genericClass;
	private int getPrecision() {
		if (precision == 0) {
			genericClass = UGenerics.getGenericClass(this, 1);
			String simpleName = genericClass.getSimpleName();
			String s = UString.afterFirst(simpleName, "Numeric");
			precision = UInteger.toInt(s);
		}
		return precision;
	}
	private NUMERIC cast(Object o) {
		BigDecimal bigDecimal = UBigDecimal.toBigDecimal(o, getPrecision());
		return UClass.newInstance(genericClass, bigDecimal);
	}
	public TS isNullOrZero() {
		isNull();
		c().or();
		eq(0);
		return ts;
	}
	public NUMERIC sum() {
		return cast(c().sum(getCampo()));
	}
	public NUMERIC max() {
		return cast(c().max(getCampo()));
	}
	public NUMERIC min() {
		return cast(c().min(getCampo()));
	}
	public TS eq(NUMERIC value) {
		return eq(value.getValor());
	}
	public TS ne(NUMERIC value) {
		return ne(value.getValor());
	}
	public TS maior(NUMERIC value) {
		return maior(value.getValor());
	}
	public TS menor(NUMERIC value) {
		return menor(value.getValor());
	}
	public TS maiorOuIgual(NUMERIC value) {
		return maiorOuIgual(value.getValor());
	}
	public TS menorOuIgual(NUMERIC value) {
		return menorOuIgual(value.getValor());
	}
	public TS entre(NUMERIC a, NUMERIC b) {
		return entre(a.getValor(), b.getValor());
	}
	public TS naoEntre(NUMERIC a, NUMERIC b) {
		return naoEntre(a.getValor(), b.getValor());
	}
	public TS eq(Integer value) {
		return eq(UBigDecimal.toBigDecimal(value));
	}
	public TS ne(Integer value) {
		return ne(UBigDecimal.toBigDecimal(value));
	}
	public TS maior(Integer value) {
		return maior(UBigDecimal.toBigDecimal(value));
	}
	public TS menor(Integer value) {
		return menor(UBigDecimal.toBigDecimal(value));
	}
	public TS maiorOuIgual(Integer value) {
		return maiorOuIgual(UBigDecimal.toBigDecimal(value));
	}
	public TS menorOuIgual(Integer value) {
		return menorOuIgual(UBigDecimal.toBigDecimal(value));
	}
	public TS entre(Integer a, Integer b) {
		return entre(UBigDecimal.toBigDecimal(a), UBigDecimal.toBigDecimal(b));
	}
	public TS naoEntre(Integer a, Integer b) {
		return naoEntre(UBigDecimal.toBigDecimal(a), UBigDecimal.toBigDecimal(b));
	}
	public TS eq(Double value) {
		return eq(UBigDecimal.toBigDecimal(value));
	}
	public TS ne(Double value) {
		return ne(UBigDecimal.toBigDecimal(value));
	}
	public TS maior(Double value) {
		return maior(UBigDecimal.toBigDecimal(value));
	}
	public TS menor(Double value) {
		return menor(UBigDecimal.toBigDecimal(value));
	}
	public TS maiorOuIgual(Double value) {
		return maiorOuIgual(UBigDecimal.toBigDecimal(value));
	}
	public TS menorOuIgual(Double value) {
		return menorOuIgual(UBigDecimal.toBigDecimal(value));
	}
	public TS entre(Double a, Double b) {
		return entre(UBigDecimal.toBigDecimal(a), UBigDecimal.toBigDecimal(b));
	}
	public TS naoEntre(Double a, Double b) {
		return naoEntre(UBigDecimal.toBigDecimal(a), UBigDecimal.toBigDecimal(b));
	}
}
