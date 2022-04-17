package gm.utils.string;

public class StringBox {
	private String s;
	public StringBox(String s) {
		set(s);
	}
	public StringBox() {}
	public String get() {
		return this.s;
	}
	public String set(String s) {
		this.s = s;
		return this.s;
	}
	public StringBox add(String s) {
		set(get()+s);
		return this;
	}
	@Override
	public String toString() {
		return s;
	}
	public boolean notEmpty() {
		return UString.notEmpty(s);
	}
	public String removeLeft(int count) {
		String x = s.substring(0, count);
		s = s.substring(count);
		return x;
	}
}
