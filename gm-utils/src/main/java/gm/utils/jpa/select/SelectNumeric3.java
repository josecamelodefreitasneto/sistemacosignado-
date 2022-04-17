package gm.utils.jpa.select;

import gm.utils.number.Numeric3;

public class SelectNumeric3<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric3> {
	public SelectNumeric3(TS x, String campo) {
		super(x, campo);
	}
}
