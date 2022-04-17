package gm.utils.number;

public class IntegerBox {
	private int i = 0;
	public IntegerBox() {}
	public IntegerBox(int i) {
		set(i);
	}
	public Integer set(int i) {
		this.i = i;
		return this.i;
	}
	public int get() {
		return i;
	}
	public int inc() {
		return inc(1);
	}
	public int inc(int value) {
		return set(i+value);
	}
	public int dec() {
		return dec(1);
	}
	public Integer dec(Integer value) {
		return set(i-value);
	}
	@Override
	public String toString() {
		return ""+i;
	}

}
