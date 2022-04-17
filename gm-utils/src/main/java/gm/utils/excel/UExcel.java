package gm.utils.excel;

import java.util.Calendar;

import gm.utils.comum.UBoolean;
import gm.utils.date.Data;
import gm.utils.number.UDouble;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.UString;

public class UExcel {
	public static Data getData(Object o, CelulaEstilo coluna) {
		return Data.to(getValue(o, coluna));
	}
	public static String getString(Object o, CelulaEstilo coluna) {
		return UString.toString(getValue(o, coluna));
	}
	public static <T> T getValue(Object o, CelulaEstilo coluna) {
		return ListAtributos.get(o).get(coluna.getField()).get(o);
	}
	public static Calendar getCalendar(Object o, CelulaEstilo coluna) {
		Data data = getData(o, coluna);
		return data == null ? null : data.getCalendar();
	}
	public static Double getDouble(Object o, CelulaEstilo coluna) {
		return UDouble.toDouble(getValue(o, coluna));
	}
	public static String getSimNao(Object o, CelulaEstilo coluna) {
		return UBoolean.format(UBoolean.toBoolean(getValue(o, coluna)));
	}
}
