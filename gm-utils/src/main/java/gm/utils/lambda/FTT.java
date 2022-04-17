package gm.utils.lambda;

@FunctionalInterface
public interface FTT<OUTPUT, INPUT> {
	OUTPUT call(INPUT o);
}
