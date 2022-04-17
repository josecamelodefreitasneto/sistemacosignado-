package gm.utils.jpa.nativeQuery;

import java.util.Date;

import gm.utils.comum.UBoolean;
import gm.utils.date.Data;
import gm.utils.number.UDouble;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.string.UString;

public class UQueryRow {
	
	private Object[] array;
	
	UQueryRow(Object[] array) {
		this.array = array;
	}
	public Date getDate(int i) {
		Data data = Data.to(array[i]);
		if (data == null) {
			return null;
		} else {
			return data.getDate();
		}
	}
	public String getStringUpper(int i) {
		String s = getString(i);
		if (UString.isEmpty(s)) {
			return null;
		} else {
			return s.toUpperCase();
		}
	}
	public String getString(int i) {
		return UString.toString(array[i]);
	}
	public Double getDouble(int i) {
		return UDouble.toDouble(array[i]);
	}
	public Boolean getBoolean(int i) {
		return UBoolean.toBoolean(array[i]);
	}
	public boolean isTrue(int i) {
		return UBoolean.isTrue(array[i]);
	}
	public Integer getInt(int i) {
		return UInteger.toInt(array[i]);
	}
	public Long getLong(int i) {
		return ULong.toLong(array[i]);
	}
	public int length() {
		return array.length;
	}
	public Character getCharacter(int i) {
		String s = getString(i);
		if (UString.isEmpty(s)) {
			return null;
		} else {
			return s.charAt(0);
		}
	}
}
