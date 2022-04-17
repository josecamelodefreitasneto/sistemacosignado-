package gm.utils.jpa.select;

import gm.utils.number.Numeric2;

public class SelectNumeric2<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric2> {
	public SelectNumeric2(TS x, String campo) {
		super(x, campo);
	}
}
