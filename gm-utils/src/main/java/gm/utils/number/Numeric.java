package gm.utils.number;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.nevec.rjm.BigDecimalMath;

import gm.utils.classes.UClass;
import gm.utils.comum.UGenerics;
import gm.utils.comum.ULog;
import gm.utils.string.UString;
import lombok.Getter;
@Getter
public class Numeric<T extends Numeric<T>> {

	BigDecimal valor;
	private int casas;
	
	private Class<T> classe;

	public Class<T> getClasse() {
		if (this.classe == null) {
			this.classe = UGenerics.getGenericClass(this);
		}
		return this.classe;
	}
	
	public Numeric(final int casas) {
		this.casas = casas;
		this.valor = BigDecimal.ZERO;
	}
	public Numeric(final String s, final int casas) {
		this( UBigDecimal.toBigDecimal(s, casas), casas );
	}
	public Numeric(final int inteiros, final int centavos, final int casas) {
		this(inteiros + "." + UNumber.format00(centavos, casas), casas);
	}
	public Numeric(final BigDecimal valor, final int casas) {
		this(casas);
		this.setValor(valor);
	}
	public Numeric(final Double value, final int casas) {
		this( UBigDecimal.toBigDecimal(value, casas), casas );
	}
	public Numeric(final Integer value, final int casas) {
		this( UBigDecimal.toBigDecimal(value, casas), casas );
	}
	public T inc(final Numeric<?> x){
		return this.inc(x.getValor());
	}
	public T dec(final Numeric<?> x){
		return this.dec(x.getValor());
	}
	public void inc(final Integer x){
		this.add(UBigDecimal.toBigDecimal(x));
	}
	public void inc(final Double x){
		this.add(UBigDecimal.toBigDecimal(x));
	}
	public void add(final Integer value){
		if (value == null) {
			return;
		}
		final BigDecimal money = UBigDecimal.toBigDecimal(value, this.casas);
		this.add(money);
	}
	public void add(final Double value){
		if (value == null) {
			return;
		}
		final BigDecimal money = UBigDecimal.toBigDecimal(value, this.casas);
		this.add(money);
	}
	public void add(final BigDecimal value) {
		if (value != null){
			this.valor = this.valor.add(value);			
		}
	}
	public void add(final Numeric<?> o) {
		this.add(o.valor);
	}
	public void menosIgual(final Numeric<?> x) {
		this.menosIgual( x.getValor() );
	}
	public void menosIgual(final BigDecimal valor){
		this.setValor( this.menos(valor) );
	}
	@Override
	public String toString() {
		
		if (this.isZeroOrEmpty()) {
			return "0," + UNumber.format00(0, this.casas);
		}
		
		String s = this.valor.toString().toLowerCase();
		
		if (s.contains("e")) {
			s = this.toDouble().toString();
		}
		
		String ints = UString.beforeFirst(s, ".");
		s = "," + UString.afterFirst(s, ".");
		
		if (UString.isEmpty(ints)) {
			ints = "0";
		} else while (ints.length() > 3) {
			s = "." + UString.right(ints, 3) + s;
			ints = UString.ignoreRight(ints, 3);
		}
		s = ints + s;
		return s;
	}
	
	public String toStringPonto() {
		final String s = this.toString();
		final String ints = UString.beforeFirst(s, ",");
		String decimais = UString.afterFirst(s, ",");
		while (UString.length(decimais) < this.casas) {
			decimais += "0";
		}
		decimais = UString.maxLength(decimais, this.casas);
		return ints.replace(".", "") + "." + decimais;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.casas;
		result = prime * result + ((this.classe == null) ? 0 : this.classe.hashCode());
		result = prime * result + ((this.valor == null) ? 0 : this.valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object o) {
		final BigDecimal money = UBigDecimal.toBigDecimal(o, this.casas);
		final BigDecimal result = this.valor.subtract(money);
		if (result.equals(BigDecimal.ZERO)) {
			return true;
		}
		String s = result.toString();
		s = s.replace("0", "");
		s = s.replace(".", "");
		return s.isEmpty();
	}
	public T dividido(final GetNumeric valor) {
		return this.dividido(valor.valor());
	}
	public T dividido(final Double valor) {
		return this.dividido( UBigDecimal.toBigDecimal(valor, this.casas) );
	}
	public T dividido(final Numeric<?> valor) {
		return this.dividido(valor.valor);
	}
	public T dividido(final int peso) {
		if (this.isZero()) {
			return this.newT(0.);
		}
		return this.dividido( UBigDecimal.toBigDecimal(peso, this.casas) );
	}
	
	public T dividido(final BigDecimal divisor) {
		final BigDecimal resultado = this.valor.divide(divisor, UNumber.ROUNDING_MODE);
		return this.newT(resultado);
	}
	public void setValor(final Double valor){
		this.setValor( UBigDecimal.toBigDecimal(valor, this.casas) );
	}
	public void setValor(BigDecimal valor){
		if (valor == null) {
			valor = BigDecimal.ZERO;
		} else {
			valor = valor.setScale(this.casas, UNumber.ROUNDING_MODE);
		}
		this.valor = valor;
	}
	public void setValor(final Integer valor){
		this.setValor(UBigDecimal.toMoney(valor));
	}
	public void setValor(final Numeric<?> valor){
		this.setValor(valor.getValor());
	}
	public Double toDouble(){
		return this.valor.doubleValue();
	}
	public Integer toInt() {
		return this.valor.intValue();
	}
	public boolean menor(final Numeric<?> x) {
		return this.menor(x.getValor());
	}
	public boolean maior(final Numeric<?> x) {
		return this.maior(x.getValor());
	}
	public boolean menor(final GetNumeric x) {
		return this.menor(x.valor());
	}
	public boolean maior(final GetNumeric x) {
		return this.maior(x.valor());
	}
	public boolean menor(final Integer x) {
		return this.menor(UBigDecimal.toBigDecimal(x, this.casas));
	}
	public boolean menor(final BigDecimal x) {
		return this.valor.compareTo(x) < 0;
	}
	public boolean maior(final Double x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maior(final Long x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maior(final Integer x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean menorOuIgual(final GetNumeric x) {
		return this.menorOuIgual(x.valor());
	}
	public boolean menorOuIgual(final BigDecimal x) {
		return this.eq(x)||this.menor(x);
	}
	public boolean menorOuIgual(final Integer x) {
		return this.eq(x)||this.menor(x);
	}
	public boolean entre(final Integer x, final Integer y) {
		return this.maiorOuIgual( UInteger.menor(x, y) ) && this.menorOuIgual( UInteger.maior(x, y) );
	}
	public boolean maior(final BigDecimal x) {
		return this.valor.compareTo(x) > 0;
	}
	public boolean maiorOuIgual(final Numeric<?> x) {
		return this.maiorOuIgual(x.getValor());
	}
	public boolean maiorOuIgual(final GetNumeric x) {
		return this.maiorOuIgual(x.valor().getValor());
	}
	public boolean menorOuIgual(final Numeric<?> x) {
		return this.menorOuIgual(x.getValor());
	}
	public boolean maiorOuIgual(final Double x) {
		return this.maiorOuIgual( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maiorOuIgual(final Integer x) {
		return this.maiorOuIgual( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maiorOuIgual(final BigDecimal x) {
		return this.maior(x) || this.eq(x);
	}
	public boolean eq(final Integer x) {
		return this.eq(this.newT(x));
	}
	public boolean eq(final Double x) {
		return this.eq(this.newT(x));
	}
	public boolean eq(final Numeric<?> x) {
		return this.eq(x.getValor());
	}
	public boolean ne(final Numeric<?> x) {
		return !this.eq(x);
	}
	public boolean ne(final GetNumeric x) {
		return this.ne(x.valor());
	}
	public boolean ne(final Integer x) {
		return !this.eq(x);
	}
	public boolean ne(final BigDecimal x) {
		return !this.eq(x);
	}
	public boolean eq(final BigDecimal x) {
		if (x == null) {
			return this.isZero();
		}
		return this.valor.compareTo(x) == 0;
	}
	public void print() {
		ULog.debug(this + " / " + this.getValor());
	}
	public T vezes(final Numeric<?> x) {
		return this.vezes(x.valor);
	}
	public T vezes(BigDecimal x) {
		x = this.valor.multiply(x);
		return this.newT(x);
	}
	public T vezes(final Double x) {
		final BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.vezes(b);
	}
	public T vezes(final Integer x) {
		final BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.vezes(b);
	}
	public void divididoIgual(final Integer i) {
		this.valor = this.dividido(i).valor;
	}
	public void vezesIgual(final Integer i) {
		this.valor = this.vezes(i).valor;
	}
	public void vezesIgual(final Numeric<?> x) {
		this.valor = this.vezes(x).valor;
	}
	public boolean menor(final Double valor) {
		return this.menor( this.newT(valor) );
	}
	public T newT(final Integer x){
		final BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.newT(b);
	}
	public T newT(final Double v){
		final BigDecimal b = UBigDecimal.toBigDecimal(v, this.casas);
		return this.newT(b);
	}
	public T newT(final BigDecimal v){
		final BigDecimal b = UBigDecimal.toBigDecimal(v, this.casas);
		final T t = UClass.newInstance(this.getClasse());
		t.setValor(b);
		return t;
	}
	@SuppressWarnings("unchecked")
	public T menos(final T... values) {
		BigDecimal o = this.valor;
		for (final T v : values) {
			o = o.subtract(v.valor);
		}
		return this.newT(o);
	}

	private List<T> toList(final GetNumeric... values) {
		final List<T> list = new ArrayList<>();
		for (final GetNumeric v : values) {
			final Numeric<?> x = v.valor();
			if (x == null) {
				continue;
			}
			final BigDecimal y = x.getValor();
			if (y == null) {
				continue;
			}
			final T t = this.newT(y);
			list.add(t);
		}
		return list;
	}
	
	public T menos(final GetNumeric... values) {
		return this.menos( this.toList(values) );
	}
	public T mais(final GetNumeric... values) {
		return this.mais( this.toList(values) );
	}
	
	public T menos(final BigDecimal... values) {
		final List<T> valores = new ArrayList<>(); 
		for (final BigDecimal o : values) {
			if (o != null) {
				valores.add(this.newT(o));
			}
		}
		return this.menos(valores);
	}
	
	public T mais(final Double d) {
		return this.mais( UBigDecimal.toBigDecimal(d, this.casas) );
	}
	
	public T mais(final BigDecimal... values) {
		final List<T> valores = new ArrayList<>(); 
		for (final BigDecimal o : values) {
			if (o != null) {
				valores.add(this.newT(o));
			}
		}
		return this.mais(valores);
	}
	
	public T menos(final List<T> valores) {
		if (valores.isEmpty()) {
			return this.newT(this.getValor());
		}
		BigDecimal o = this.valor;
		for (final T v : valores) {
			if (v == null || v.valor == null || v.isZero()) {
				continue;
			}
			o = o.subtract(v.valor);
		}
		return this.newT(o);
	}

	public T mais(final List<T> valores) {
		if (valores.isEmpty()) {
			return this.newT(this.getValor());
		}
		BigDecimal o = this.valor;
		for (final T v : valores) {
			if (v == null || v.valor == null || v.isZero()) {
				continue;
			}
			o = o.add(v.valor);
		}
		return this.newT(o);
	}
	
	@SuppressWarnings("unchecked")
	public T mais(final T... values) {
		BigDecimal o = this.valor;
		for (final T v : values) {
			if (v == null || v.valor == null) {
				continue;
			}
			o = o.add(v.valor);
		}
		return this.newT(o);
	}
	public T menos(final Integer x) {
		return this.newT( this.getValor().subtract( UBigDecimal.toBigDecimal(x) ) );
	}
	public T mais(final Integer x) {
		return this.newT( this.getValor().add( UBigDecimal.toBigDecimal(x) ) );
	}
	public T dec(final Double d) {
		return this.dec(this.newT(d));
	}
	public T dec(final Integer x) {
		return this.dec(this.newT(x));
	}
	@SuppressWarnings("unchecked")
	public T dec(final BigDecimal o) {
		if (o != null){
			this.valor = this.valor.subtract(o);
		}
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public T inc(final BigDecimal o) {
		if (o != null){
			this.valor = this.valor.add(o);
		}
		return (T) this;
	}
	
	private boolean isZeroOrEmpty() {
		return this.valor == null || BigDecimal.ZERO.equals(this.valor);
	}
	
	public boolean isZero() {
		if (isZeroOrEmpty()) {
			return true;
		}
		if ( toString().replace("0", "").replace(",", "").isEmpty() ) {
			return true;
		}
		return false;
	}
	
	public T comoPercentualDe(final Object valor) {
		return this.comoPercentualDe( UBigDecimal.toMoney(valor) );
	}
	public T comoPercentualDe(final BigDecimal valor) {
		Numeric5 n = new Numeric5(valor);
		n = n.dividido(100).vezes(this);
		return this.newT( n.getValor() );
	}
	public T centavos(){
		return this.menos(this.inteiros());
	}
	
	public Integer inteiros(){
		String s = this.getValor().toString();
		if (s.contains(".")) {
			s = UString.beforeFirst(s, ".");
		}
		return UInteger.toInt(s);
	}
	
	public T percentual(final int x) {
		return this.vezes(x / 100.);
	}
	
	@SuppressWarnings("unchecked")
	public T menosPercentual(final int percentual) {
		final T x = this.percentual(percentual);
		return this.menos(x);
	}
	public void menosIgualPercentual(final int percentual) {
		final T x = this.menosPercentual(percentual);
		this.setValor(x.getValor());
	}
	public T pow(Numeric<?> valor) {
		return pow(valor.getValor());
	}
	public T pow(BigDecimal valor) {
		return newT(BigDecimalMath.pow(getValor(), valor));
	}
	public static Numeric<?> toNumeric(BigDecimal o, int precision) {
		if (precision == 1) return new Numeric1(o);
		if (precision == 2) return new Numeric2(o);
		if (precision == 3) return new Numeric3(o);
		if (precision == 4) return new Numeric4(o);
		if (precision == 5) return new Numeric5(o);
		if (precision == 15) return new Numeric15(o);
		throw new RuntimeException("precision nao tratada: " + precision);
	}
}
