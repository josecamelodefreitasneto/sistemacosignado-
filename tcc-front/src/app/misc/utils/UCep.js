/* react */
import UString from './UString';

export default class UCep {

	static formatParcial(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.mantemSomenteNumeros(s);

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) < 3) {
			return s;
		} else if (UString.length(s) > 8) {
			s = s.substring(0, 8);
		}

		let x = s.substring(0, 2) + ".";
		s = s.substring(2);

		if (UString.length(s) > 3) {
			x += s.substring(0, 3) + "-";
			s = s.substring(3);
		}

		return x + s;

	}

	static main(args) {
		/*System.out.println(s);(formatParcial(""));*/
		/*System.out.println(s);(formatParcial("7"));*/
		/*System.out.println(s);(formatParcial("72"));*/
		/*System.out.println(s);(formatParcial("723"));*/
		/*System.out.println(s);(formatParcial("7231"));*/
		/*System.out.println(s);(formatParcial("72318"));*/
		/*System.out.println(s);(formatParcial("723180"));*/
		/*System.out.println(s);(formatParcial("7231802"));*/
		/*System.out.println(s);(formatParcial("72318024"));*/
		/*System.out.println(s);(formatParcial("723180240"));*/
	}

	static isValid(s) {
		s = UString.mantemSomenteNumeros(s);
		if (!UString.lengthIs(s, 8)) {
			return false;
		}
		return true;
	}

	static getBairro(s) {
		return "Um Bairro Mock para o cep " + s;
	}

}
