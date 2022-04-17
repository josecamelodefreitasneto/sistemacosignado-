package gm.utils.lambda;

@FunctionalInterface
public interface FTTT<OUTPUT, INPUT, INPUT2> {
	OUTPUT call(INPUT o, INPUT2 b);
}
