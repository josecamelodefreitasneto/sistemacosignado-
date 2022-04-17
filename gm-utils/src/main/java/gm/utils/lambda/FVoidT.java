package gm.utils.lambda;

@FunctionalInterface
public interface FVoidT<T> {
	void call(T o);
}
