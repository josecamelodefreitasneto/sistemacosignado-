package gm.utils.outros;

public class Box<T> {
	private T o;
	public Box(T o) {
		set(o);
	}
	public Box() {}
	public T get() {
		return this.o;
	}
	public void set(T o) {
		this.o = o;
	}
	public boolean isNotNull() {
		return o != null;
	}
	public boolean isNull() {
		return o == null;
	}
}
