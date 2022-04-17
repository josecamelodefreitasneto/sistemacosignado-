package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ExpressaoMatematica {
	
	public static String resolve(String s) {

		s = UString.trimPlus(s).replace(" ", "");
		
		while (s.contains("(")) {
			String b = UString.beforeFirst(s, ")");
			String a = UString.afterLast(b, "(");
			s = s.replace("("+a+")", resolve(a));
		}
		
		String before;
		
		do {
			before = s;
			s = s.replace("*-", "*n");
			s = s.replace("*-", "*n");
			s = s.replace("+-", "-");
			s = s.replace("--", "+");
			s = s.replace("++", "+");
			s = s.replace("-+", "-");
		} while (!before.contentEquals(s));
		
		if (s.startsWith("-")) {
			s = "n" + s.substring(1);
		}
		
		ListString list = ListString.separaPalavras(s);
		
		list.juntarComASuperiorSeEquals(".");
		for (int i = 0; i < 10; i++) {
			list.juntarFimComComecos(".", ""+i, "");
		}
		
		while (list.contains("*") || list.contains("/")) {
			
			int index;
			
			if (list.contains("*")) {
				if (list.contains("/")) {
					if (list.indexOf("*") < list.indexOf("/")) {
						index = list.indexOf("*");
					} else {
						index = list.indexOf("/");
					}
				} else {
					index = list.indexOf("*");
				}
			} else {
				index = list.indexOf("/");
			}
			
			index--;
			
			String sa = list.remove(index).replace("n", "-");
			String sinal = list.remove(index);
			String sb = list.remove(index).replace("n", "-");

			boolean fracional = sa.contains(".") || sb.contains(".");
			
			if (fracional) {

				BigDecimal a = UBigDecimal.toBigDecimal(sa);
				BigDecimal b = UBigDecimal.toBigDecimal(sb);
				BigDecimal r;
				if (sinal.contentEquals("*")) {
					r = a.multiply(b);
				} else {
					r = a.divide(b, 250, RoundingMode.HALF_UP);
				}
				
				list.add(index, r.toString()); 

			} else {

				int a = UInteger.toInt(sa);
				int b = UInteger.toInt(sb);
				int r;
				if (sinal.contentEquals("*")) {
					r = a * b;
				} else {
					r = a / b;
				}

				list.add(index, "" + r); 

			}
			
		}
		
		while (list.size() > 1) {

			String sa = list.remove(0).replace("n", "-");
			String sinal = list.remove(0);
			String sb = list.remove(0).replace("n", "-");

			boolean fracional = sa.contains(".") || sb.contains(".");
			
			if (fracional) {

				BigDecimal a = UBigDecimal.toBigDecimal(sa);
				BigDecimal b = UBigDecimal.toBigDecimal(sb);
				BigDecimal r;
				if (sinal.contentEquals("+")) {
					r = a.add(b);
				} else {
					r = a.subtract(b);
				}

				list.add(0, r.toString()); 

			} else {

				int a = UInteger.toInt(sa);
				int b = UInteger.toInt(sb);
				int r;
				if (sinal.contentEquals("+")) {
					r = a + b;
				} else {
					r = a - b;
				}

				list.add(0, "" + r); 

			}
			
		}
		
		s = list.get(0);
		
		if (s.contains(".")) {
			while (s.endsWith("0")) {
				s = UString.ignoreRigth(s);
			}
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(resolve("1.0/2"));
		System.out.println(resolve("1./2"));
		System.out.println(resolve("1.00000000000000000000000000/2"));
		System.out.println(resolve("1/2"));
		System.out.println(resolve("5/5+(5*5)*5*(8./9)+(8*(8-(14+8)))"));
		System.out.println(resolve("-++--++++++++++--8*(8-(14+8))"));
		System.out.println(resolve("8.0/9.0"));
		System.out.println(resolve("x*7"));
	}
	
}
