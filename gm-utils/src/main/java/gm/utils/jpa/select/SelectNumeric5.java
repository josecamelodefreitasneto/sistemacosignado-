package gm.utils.jpa.select;

import gm.utils.number.Numeric5;

public class SelectNumeric5<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric5> {
	public SelectNumeric5(TS x, String campo) {
		super(x, campo);
	}
}
