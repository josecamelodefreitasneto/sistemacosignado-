package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric4 extends Numeric<Numeric4> {

	public Numeric4(String s) {
		super(s, 4);
	}

	public Numeric4(Integer value) {
		super(value, 4);
	}

	public Numeric4(int inteiros, int centavos) {
		super(inteiros, centavos, 4);
	}

	public Numeric4() {
		super(4);
	}

	public Numeric4(Double value) {
		super(value, 4);
	}

	public Numeric4(BigDecimal valor) {
		super(valor, 4);
	}
	
}
