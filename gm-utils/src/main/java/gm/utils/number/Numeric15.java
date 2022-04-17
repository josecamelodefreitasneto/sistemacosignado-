package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric15 extends Numeric<Numeric15> {

	public Numeric15(final String s) {
		super(s, 15);
	}

	public Numeric15(final Integer value) {
		super(value, 15);
	}

	public Numeric15(final int inteiros, final int centavos) {
		super(inteiros, centavos, 15);
	}

	public Numeric15() {
		super(15);
	}

	public Numeric15(final Double value) {
		super(value, 15);
	}

	public Numeric15(final BigDecimal valor) {
		super(valor, 15);
	}
	
}
