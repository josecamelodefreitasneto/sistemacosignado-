package gm.utils.jpa.select;

import gm.utils.number.Numeric15;

public class SelectNumeric15<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric15> {
	public SelectNumeric15(TS x, String campo) {
		super(x, campo);
	}
}
