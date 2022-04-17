package gm.utils.jpa.select;

public class SelectBoolean<TS extends SelectBase<?,?,?>> extends SelectTyped<TS, Boolean> {
	public SelectBoolean(TS x, String campo) {
		super(x, campo);
	}
}
