package gm.utils.string;

import gm.utils.exception.UException;


public class Margem {
	
	private int i;
	private String s = "";
	private String separator = "	";
	
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	public int inc() {
		i++;
		calcula();
		return i;
	}
	public int dec() {
		i--;
		if (i < 0) {
			throw UException.runtime("[NEGATIVO]");
		}
		calcula();
		return i;
	}
	private void calcula() {
		s = UString.repete(separator, i);
	}
	
	@Override
	public String toString() {
		return s;
	}
	public int get() {
		return i;
	}
	public void set(int i) {
		this.i = i;
		calcula();
	}

	public void descobre(String s) {
		set(0);
		while (s.startsWith(separator)) {
			s = s.substring(separator.length());
			inc();
		}
	}
	
}
