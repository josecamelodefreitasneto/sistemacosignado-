package gm.utils.number;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric3 extends Numeric<Numeric3> implements Serializable {

	private static final long serialVersionUID = -3672369795178806057L;

	public Numeric3(final String s, final boolean realBrasil) {
		super( (realBrasil ? s.replace(".", "").replace(",", ".") : "") , 3);
	}
	
	public Numeric3(final String s) {
		super(s, 3);
	}

	public Numeric3(final Integer value) {
		super(value, 3);
	}

	public Numeric3(final int inteiros, final int centavos) {
		super(inteiros, centavos, 3);
	}

	public Numeric3() {
		super(3);
	}

	public Numeric3(final Double value) {
		super(value, 3);
	}

	public Numeric3(final BigDecimal valor) {
		super(valor, 3);
	}
	
	public Numeric3 restoDivisao(final int x) {
		return this.menos(this.dividido(x).inteiros() * x);
	}

}
