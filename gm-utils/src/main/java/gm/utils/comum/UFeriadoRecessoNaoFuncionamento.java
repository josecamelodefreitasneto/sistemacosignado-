package gm.utils.comum;

import java.util.ArrayList;
import java.util.List;

import gm.utils.date.Data;
import gm.utils.string.ListString;

public class UFeriadoRecessoNaoFuncionamento {
	
	private static List<Test> tests;
	
	public static interface Test {
		boolean test(Data data);
	}
	
	public static void addTest(Test test) {
		if (tests == null) {
			tests = new ArrayList<>();
		}
		tests.add(test);
	}
	
	private static final ListString defaults = ListString.array("01/01", "21/04", "01/05", "07/09", "12/10", "02/11", "15/11", "25/12");
	private static final ListString knows = ListString.array("10/04/2020","11/06/2020");
	
	public static boolean test(final Data data) {
		if (defaults.contains(data.format("[dd]/[mm]"))) {
			return true;
		}
		if (knows.contains(data.format("[dd]/[mm]/[yyyy]"))) {
			return true;
		}
		if (tests == null) {
			return false;	
		}
		for (Test test : tests) {
			if (test.test(data)) {
				return true;
			}
		}
		return false;
	}
}
