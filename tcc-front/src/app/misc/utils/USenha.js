/* react */
import UConstantes from './UConstantes';
import UString from './UString';

export default class USenha {

	static formatParcial(s) {

		s = UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.numeros);

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) > 6) {
			s = s.substring(0, 6);
		}

		return s;

	}

	static getInvalidMessage(s) {
		if (!UString.equals(s, UString.mantemSomenteNumeros(s))) {
			return "Senha deve conter apenas caracteres numéricos";
		} else if (!UString.lengthIs(s, 6)) {
			return "Senha deve conter 6 caracteres numéricos";
		} else {
			return null;
		}
	}

	static isValid(s) {
		return UString.lengthIs(USenha.formatParcial(s), 6);
	}

}
