package gm.utils.comum;

import gm.utils.exception.UException;
import gm.utils.string.UString;

public class UCnpj {

	private static final int[] pesoCNPJ = {
			6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
	};
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1; indice >= 0; indice--) {
			int digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}
	public static boolean isValid(String cnpj) {
		if (UString.isEmpty(cnpj)) return false;
		cnpj = garante14caracteres(cnpj);
		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}
	
	private static int mod(int dividendo, int divisor) {
		return (int) Math.round(dividendo - (Math.floor((double) dividendo / divisor) * divisor));
	}
	
	public static String format(Object cnpj) {
		return format(UString.toString(cnpj));
	}
	
	private static String garante14caracteres(String cnpj) {
		cnpj = UString.mantemSomenteNumeros(cnpj);
		while (cnpj.length() != 14) {
			cnpj = "0" + cnpj;
		}
		return cnpj;
	}
	
	public static String format(String cnpj) {
		if (!isValid(cnpj)) {
			throw UException.business("Este cnpj n"+UConstantes.a_til+"o pode ser formatado pois "+UConstantes.e_agudo+" invalido!");
		}
		cnpj = garante14caracteres(cnpj);
		cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8,12) + "-" + cnpj.substring(12);
		return cnpj;
	}

	public static String aleatorio() {
		int n = 9;
		int n1 = Aleatorio.get(1, n);
		int n2 = Aleatorio.get(1, n);
		int n3 = Aleatorio.get(1, n);
		int n4 = Aleatorio.get(1, n);
		int n5 = Aleatorio.get(1, n);
		int n6 = Aleatorio.get(1, n);
		int n7 = Aleatorio.get(1, n);
		int n8 = Aleatorio.get(1, n);
		int n9 = 0;
		int n10 = 0;
		int n11 = 0;
		int n12 = 1;
		int d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4 + n1 * 5;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4 + n2 * 5 + n1 * 6;

		d2 = 11 - (mod(d2, 11));

		if (d2 >= 10)
			d2 = 0;

		return "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "/" + n9 + n10 + n11 + n12 + "-" + d1 + d2;
		
	}
	
	public static void main(String[] args) {
		System.out.println(isValid("58106519000139"));
	}
	
}
