package gm.utils.lambda;

@FunctionalInterface
public interface FT<OUTPUT> {
	OUTPUT call();
}
