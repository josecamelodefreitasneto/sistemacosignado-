package gm.utils.jpa.select;

public class SelectTypedLogical<TS extends SelectBase<?,?,?>, T> extends SelectTyped<TS, T> {
	public SelectTypedLogical(TS x, String campo) {
		super(x, campo);
	}
	public TS maior(T value) {
		checkNotNull(value);
		c().maior(getCampo(), value);
		return ts;
	}
	public TS menor(T value) {
		checkNotNull(value);
		c().menor(getCampo(), value);
		return ts;
	}
	public TS maiorOuIgual(T value) {
		checkNotNull(value);
		c().maiorOuIgual(getCampo(), value);
		return ts;
	}
	public TS menorOuIgual(T value) {
		checkNotNull(value);
		c().menorOuIgual(getCampo(), value);
		return ts;
	}
	public TS entre(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		c().entre(getCampo(), a, b);
		return ts;
	}
	public TS naoEntre(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		c().naoEntre(getCampo(), a, b);
		return ts;
	}
}
