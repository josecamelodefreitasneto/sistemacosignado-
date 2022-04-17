package gm.utils.jpa.select;

import gm.utils.number.Numeric1;

public class SelectNumeric1<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric1> {
	public SelectNumeric1(TS x, String campo) {
		super(x, campo);
	}
}
