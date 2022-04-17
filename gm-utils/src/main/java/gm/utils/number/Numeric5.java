package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric5 extends Numeric<Numeric5> {

	public Numeric5(String s) {
		super(s, 5);
	}

	public Numeric5(Integer value) {
		super(value, 5);
	}

	public Numeric5(int inteiros, int centavos) {
		super(inteiros, centavos, 5);
	}

	public Numeric5() {
		super(5);
	}

	public Numeric5(Double value) {
		super(value, 5);
	}

	public Numeric5(BigDecimal valor) {
		super(valor, 5);
	}
	
}
