/* react */
import UConstantes from './UConstantes';
import UString from './UString';

export default class UContaBancaria {

	static CARACTERES_VALIDOS = UConstantes.numeros.concat(["x"]);

	static formatParcial(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = s.toLowerCase();
		s = UString.mantemSomenteOsSeguintesCaracteres(s, UContaBancaria.CARACTERES_VALIDOS);

		if (UString.isEmpty(s)) {
			return "";
		}

		let digito;

		if (s.contains("x")) {
			s = UString.beforeFirst(s, "x");
			digito = "x";
		} else {
			digito = UString.right(s, 1);
			s = UString.ignoreRight(s, 1);
		}

		s = UString.mantemSomenteNumeros(s);

		if (UString.isEmpty(s)) {
			return digito;
		}

		if (UString.length(s) < 4) {
			return s + "-" + digito;
		}

		let result = UString.right(s, 3) + "-" + digito;
		s = UString.ignoreRight(s, 3);
		while (UString.length(s) > 3) {
			result = UString.right(s, 3) + "." + result;
			s = UString.ignoreRight(s, 3);
		}

		if (!UString.isEmpty(s)) {
			result = s + "." + result;
		}

		return result;

	}

}
