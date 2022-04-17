package gm.utils.date;

import gm.utils.number.UInteger;
import gm.utils.string.UString;

public class UAnoMes {

	public static int compare_mmmm_yyyy(final String a, final String b) {
		if (UString.isEmpty(a)) {
			if (UString.isEmpty(b)) {
				return 0;
			} else {
				return -1;
			}
		} else if (UString.isEmpty(b)) {
			return 1;
		}

		final int inta = UAnoMes.mmmm_yyyy_toInt(a);
		final int intb = UAnoMes.mmmm_yyyy_toInt(b);

		return UInteger.compare(inta, intb);

	}

	public static int mmmm_yyyy_toInt(final String s) {
		final Data data = Data.unformat("[mmmm]/[yyyy]", s);
		return data.getAno() * 100 + data.getMes();
	}
	public static int idAnoMes(final int ano, final int mes) {
		return ano * 100 + mes;
	}
	public static int indexAnoMes(final int ano, final int mes) {
		return (ano - 1900) * 12 + mes;
	}
	public static int getAno(final int anoMes) {
		return UInteger.toInt(anoMes / 100);
	}
	public static int getMes(final int anoMes) {
		return anoMes - UAnoMes.getAno(anoMes) * 100;
	}

	public static int menosMeses(final int anoMes, final int meses) {
		int ano = UAnoMes.getAno(anoMes);
		int mes = UAnoMes.getMes(anoMes);
		mes -= meses;
		if (mes < 1) {
			ano--;
			mes += 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

	public static int maisMeses(final int anoMes, final int meses) {
		int ano = UAnoMes.getAno(anoMes);
		int mes = UAnoMes.getMes(anoMes);
		mes += meses;
		if (mes > 12) {
			ano++;
			mes -= 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

}
