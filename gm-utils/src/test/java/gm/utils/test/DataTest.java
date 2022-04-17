package gm.utils.test;

import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.date.Data;

public class DataTest {

	public DataTest() {
		date(new Data("19102019"), 2019, 10, 19);
		date(new Data("20191019"), 2019, 10, 19);
		date(new Data("2019-10-19"), 2019, 10, 19);
		date(new Data("19/10/2019"), 2019, 10, 19);
		all(new Data(2019, 12, 11, 23, 14, 48), 2019, 12, 11, 23, 14, 48);
		all(new Data("Qua 2019-12-11 23:14:48"), 2019, 12, 11, 23, 14, 48);
		all(new Data("Wed Dec 11 2019 23:56:21 GMT-0300 (Hor"+UConstantes.a_agudo+"rio Padr"+UConstantes.a_til+"o de Bras"+UConstantes.i_agudo+"lia) {}"), 2019, 12, 11, 23, 56, 21);
		
	}

	private void all(Data date, int ano, int mes, int dia, int hora, int minuto, int segundo) {
		date(date, ano, mes, dia);
		time(date, hora, minuto, segundo);
	}

	private void date(Data date, int ano, int mes, int dia) {
		UAssert.eq(date.getAno(), ano, "ano");
		UAssert.eq(date.getMes(), mes, "mes");
		UAssert.eq(date.getDia(), dia, "dia");
	}

	private void time(Data date, int hora, int minuto, int segundo) {
		UAssert.eq(date.getHora(), hora, "hora");
		UAssert.eq(date.getMinuto(), minuto, "minuto");
		UAssert.eq(date.getSegundo(), segundo, "segundo");
	}
	
	public static void main(String[] args) {
		new DataTest();
	}
	
}
