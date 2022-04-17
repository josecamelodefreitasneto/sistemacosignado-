package gm.utils.jpa.select;

import gm.utils.number.ULong;

public class SelectLong<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, Long> {
	public SelectLong(TS x, String campo) {
		super(x, campo);
	}
	public TS isNullOrZero() {
		isNull();
		c().or();
		eq(0L);
		return ts;
	}
	public Long sum() {
		return ULong.toLong( c().sum(getCampo()) );
	}
	public Long max() {
		return ULong.toLong( c().max(getCampo()) );
	}
	public Long min() {
		return ULong.toLong( c().min(getCampo()) );
	}
}
