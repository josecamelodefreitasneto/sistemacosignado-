package br.tcc.outros;

import java.math.BigDecimal;
import java.util.Calendar;

import gm.utils.comum.UBoolean;
import gm.utils.date.Data;

public class UCrud {

	public static String formatBoolean(final Boolean o) {
		return UBoolean.format(o);
	}
	public static String formatInteger(final Integer o) {
		return o == null ? "" : ""+o;
	}
	public static String formatDecimal(final BigDecimal o) {
		return o == null ? "" : ""+o;
	}
	public static String formatDate(final Calendar o) {
		return o == null ? "" : new Data(o).format_dd_mm_yyyy();
	}
	public static String formatDateTime(final Calendar o) {
		return o == null ? "" : new Data(o).format_dd_mm_yyyy_hh_mm();
	}
	
}
