package gm.utils.comum;

import java.text.SimpleDateFormat;
import java.util.Date;

import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

public class Tripa {

	String s;
	int i = 0;
	
	public Tripa(String s){
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
	public String get(int x){
		String r = s.substring(0,x);
		s = s.substring(x);
//		br.com.grupont.fw.util.tools.Console.log("[" + i++ +"]" + " >> " + r);
		return r;
	}
	
//	public static void main(String[] args) {
//		Tripa tripa = new Tripa("abcdefg");
//		br.com.grupont.fw.util.tools.Console.log(tripa.get(2));
//		br.com.grupont.fw.util.tools.Console.log(tripa.get(2));
//	}
	
	public String check(int x){
		String r = s.substring(0,x);
		ULog.debug(">> " + r);
		return r;
	}
	
	public void print() {
		ULog.debug(s);
	}
	
	public Integer getInt(int x) {
		return UInteger.toInt(get(x));
	}

	public Data getData(String format) {
		try {
			SimpleDateFormat f = new SimpleDateFormat(format);
			String s = get(format.length());
			Date parse = f.parse(s);
			return new Data(parse);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public boolean isEmpty() {
		return UString.isEmpty(s);
	}

	public Integer getInt(String delimitador) {
		return UInteger.toInt(get(delimitador));
	}
	
	public String get(String delimitador) {
		String x = UString.beforeFirst(s, delimitador);
		s = UString.afterFirst(s, delimitador);
		return x;
	}
}
