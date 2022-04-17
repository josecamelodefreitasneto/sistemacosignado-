package gm.utils.jpa.select;

import gm.utils.number.Numeric4;

public class SelectNumeric4<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric4> {
	public SelectNumeric4(TS x, String campo) {
		super(x, campo);
	}
}
