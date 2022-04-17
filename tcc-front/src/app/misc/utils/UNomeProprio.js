/* react */
import StringBox from './StringBox';
import UConstantes from './UConstantes';
import UString from './UString';

export default class UNomeProprio {

	static CARACTERES_VALIDOS =
		UConstantes.letrasMinusculas.concat(UConstantes.acentuadasMinusculas).concat(["'", " ", "ç"])
	;

	static formatParcial(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.replaceWhile(s, "\t", " ");

		let espaco = s.endsWith(" ");
		s = UString.trimPlus(s);

		while (!UString.isEmpty(s) && s.startsWith("'")) {
			s = s.substring(1);
			s = UString.trimPlus(s);
		}

		if (UString.isEmpty(s)) {
			return "";
		}

		s = s.toLowerCase();
		s = UString.mantemSomenteOsSeguintesCaracteres(s, UNomeProprio.CARACTERES_VALIDOS);

		if (UString.isEmpty(s)) {
			return "";
		}

		const box = new StringBox(s);

		UNomeProprio.CARACTERES_VALIDOS.forEach(o => {
			box.value = UString.replaceWhile(box.value, o+o+o, o+o);
		});

		box.value = UString.replaceWhile(box.value, "''", "'");
		box.value = " " + box.value + " ";

		UConstantes.letrasMinusculas.forEach(o => {
			box.value = UString.replaceWhile(box.value, " " + o, " " + o.toUpperCase());
		});

		s = box.value;

		s = UString.replaceWhile(s, " De ", " de ");
		s = UString.replaceWhile(s, " Do ", " do ");
		s = UString.replaceWhile(s, " Da ", " da ");
		s = UString.replaceWhile(s, " Dos ", " dos ");
		s = UString.replaceWhile(s, " Das ", " das ");

		s = UString.trimPlus(s);

		if (UString.length(s) < 2) {
			return s;
		}

		s = s.substring(0,1).toUpperCase() + s.substring(1);

		if (espaco) {
			s += " ";
		}

		return UString.maxLength(s, 60);

	}

	static isValido(s) {
		s = UString.trimPlus(s);
		if (UString.length(s) < 7) {
			return false;
		}
		if (!s.contains(" ")) {
			return false;
		}
		return true;
	}

}
