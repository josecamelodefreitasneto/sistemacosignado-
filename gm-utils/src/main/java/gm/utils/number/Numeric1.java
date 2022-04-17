package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric1 extends Numeric<Numeric1> {
	public static final Numeric1 ZERO = new Numeric1(0);
	
	public Numeric1(String s) {
		super(s, 1);
	}

	public Numeric1(Integer value) {
		super(value, 1);
	}

	public Numeric1(int inteiros, int centavos) {
		super(inteiros, centavos, 1);
	}

	public Numeric1() {
		super(1);
	}

	public Numeric1(Double value) {
		super(value, 1);
	}

	public Numeric1(BigDecimal valor) {
		super(valor, 1);
	}

}
