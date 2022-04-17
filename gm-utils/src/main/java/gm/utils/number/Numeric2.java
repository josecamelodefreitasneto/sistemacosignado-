package gm.utils.number;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric2 extends Numeric<Numeric2> implements Serializable {

	private static final long serialVersionUID = -3672369795178806057L;

	public Numeric2(String s, boolean realBrasil) {
		super( (realBrasil ? s.replace(".", "").replace(",", ".") : "") , 2);
	}
	
	public Numeric2(String s) {
		super(s, 2);
	}

	public Numeric2(Integer value) {
		super(value, 2);
	}

	public Numeric2(int inteiros, int centavos) {
		super(inteiros, centavos, 2);
	}

	public Numeric2() {
		super(2);
	}

	public Numeric2(Double value) {
		super(value, 2);
	}

	public Numeric2(BigDecimal valor) {
		super(valor, 2);
	}
	
	public Numeric2 restoDivisao(int x) {
		return menos(dividido(x).inteiros() * x);
	}

}
