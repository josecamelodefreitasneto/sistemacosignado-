package gm.utils.number;

import java.util.Comparator;

public class ComparatorInteger implements Comparator<Integer> {
	@Override
	public int compare(Integer a, Integer b) {
		if (UInteger.equals(a, b)) {
			return 0;
		}
		if (a == null) {
			return -1;
		}
		if (b == null) {
			return +1;
		}
		if (a < b) {
			return -1;
		}
		return +1;
	}
}
