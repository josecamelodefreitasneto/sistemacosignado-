package gm.utils.jpa.select;

import gm.utils.number.UInteger;

public class SelectInteger<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, Integer> {
	public SelectInteger(TS x, String campo) {
		super(x, campo);
	}
	public TS isNullOrZero() {
		isNull();
		c().or();
		eq(0);
		return ts;
	}
	public Integer sum() {
		return UInteger.toInt( c().sum(getCampo()) );
	}
	public Integer max() {
		return UInteger.toInt( c().max(getCampo()) );
	}
	public Integer min() {
		return UInteger.toInt( c().min(getCampo()) );
	}
}
