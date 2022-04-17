/* react */
import UConstantes from './UConstantes';
import UString from './UString';

export default class UEmail {

	static CARACTERES_VALIDOS =
		UConstantes.numeros
		.concat(UConstantes.letrasMinusculas)
		.concat([".","@","-","_"])
	;

	static EXTREMIDADES_VALIDAS =
		UConstantes.numeros
		.concat(UConstantes.letrasMinusculas)
		.concat(["_"])
	;

	static formatParcial(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = s.toLowerCase();
		s = UString.mantemSomenteOsSeguintesCaracteres(s, UEmail.CARACTERES_VALIDOS);

		s = UString.replaceWhile(s, ".-", ".");
		s = UString.replaceWhile(s, "-.", "-");
		s = UString.replaceWhile(s, "..", ".");
		s = UString.replaceWhile(s, "--", "-");

		while (!UString.isEmpty(s) && !UEmail.EXTREMIDADES_VALIDAS.contains(s.substring(0, 1)) ) {
			s = s.substring(1);
		}

		if (UEmail.isValid(s)) {
			return s;
		}

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) > 60) {
			s = s.substring(0, 60);
		}

		if (!s.contains("@")) {
			return s;
		}

		let before = UString.beforeFirst(s, "@");

		while (!UString.isEmpty(before) && !UEmail.EXTREMIDADES_VALIDAS.contains(UString.right(before, 1)) ) {
			before = UString.ignoreRight(before, 1);
		}

		let after = UString.afterFirst(s, "@");
		after = UString.replaceWhile(after, "@", "");

		while (!UString.isEmpty(after) && !UEmail.EXTREMIDADES_VALIDAS.contains(after.substring(0, 1)) ) {
			after = after.substring(1);
		}

		if (UString.isEmpty(before)) {
			return after;
		} else {
			return before + "@" + after;
		}

	}

	static isValid(s) {

		if (UString.isEmpty(s)) {
			return false;
		}

		let semCaracteresInvalidos = UString.mantemSomenteOsSeguintesCaracteres(s, UEmail.CARACTERES_VALIDOS);

		if (!UString.equals(semCaracteresInvalidos, s)) {
			return false;
		}

		if (!s.contains("@")) {
			return false;
		}

		if (s.contains("..") || s.contains("--")) {
			return false;
		}

		if (UString.ocorrencias(s, "@") > 1) {
			return false;
		}

		if (UString.length(s) < 3) {
			return false;
		}

		let first = s.substring(0,1);

		if (!UEmail.EXTREMIDADES_VALIDAS.contains(first)) {
			return false;
		}

		let right = UString.right(s, 1);

		if (!UEmail.EXTREMIDADES_VALIDAS.contains(right)) {
			return false;
		}

		let before = UString.beforeFirst(s, "@");

		right = UString.right(before, 1);

		if (!UEmail.EXTREMIDADES_VALIDAS.contains(right)) {
			return false;
		}

		let after = UString.afterFirst(s, "@");

		first = after.substring(0,1);

		if (!UEmail.EXTREMIDADES_VALIDAS.contains(first)) {
			return false;
		}

		if (after.contains("@")) {
			return false;
		}

		if (UString.length(s) > 60) {
			return false;
		}

		return true;

	}

}
