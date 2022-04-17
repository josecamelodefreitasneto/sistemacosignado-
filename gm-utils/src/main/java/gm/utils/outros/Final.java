package gm.utils.outros;

public class Final<T> {
	private T o;
	public Final(T o) {
		set(o);
	}
	public Final() {}
	public T get() {
		return this.o;
	}
	public void set(T o) {
		if (this.o == o) {
			return;
		}
		if (isNotNull()) {
			throw new RuntimeException("is final");
		}
		this.o = o;
	}
	public boolean isNotNull() {
		return o != null;
	}
	public boolean isNull() {
		return o == null;
	}
}
