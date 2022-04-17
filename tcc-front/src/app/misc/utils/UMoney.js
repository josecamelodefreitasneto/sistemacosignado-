/* react */
import UCommons from './UCommons';
import UConstantes from './UConstantes';
import UInteger from './UInteger';
import UString from './UString';

export default class UMoney {

	static toDouble(s) {

		s = UMoney.formatParcial(s, false, 15);

		if (UString.isEmpty(s)) {
			return 0;
		}

		s = UString.mantemSomenteNumeros(s);
		const x = parseInt(s);
		return x / 100.;

	}

	static format(value) {
		if (UCommons.isEmpty(value)) {
			return "";
		} else {
			return UMoney.formatParcial(UString.toString(value).replace(".", ","), false, 30);
		}
	}

	static formatParcial(s, r$, maxLengthInteiros) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.mantemSomenteOsSeguintesCaracteres(s, UMoney.CHARS);

		if (UString.isEmpty(s)) {
			return "";
		}

		let centavos = "";

		let contemVirgula = s.contains(",");

		if (contemVirgula) {
			centavos = UString.mantemSomenteNumeros(UString.afterFirst(s, ","));
			s = UString.beforeFirst(s, ",");
		}

		while (s.startsWith("00")) {
			s = s.substring(1);
		}

		if (UInteger.isEmptyOrZero(maxLengthInteiros)) {
			maxLengthInteiros = 9;
		}

		s = UString.maxLength(s, maxLengthInteiros);

		if (UString.length(centavos) > 2) {
			centavos = centavos.substring(0, 2);
		}

		if (UString.isEmpty(s)) {
			if (!contemVirgula) {
				return "";
			}
			if (UString.isEmpty(centavos)) {
				return "0,";
			}
			return "0," + centavos;
		}

		s = UInteger.separarMilhares(s);

		if (contemVirgula) {
			s += ",";
			if (!UString.isEmpty(centavos)) {
				s += centavos;
			}
		}

		if (r$) {
			s = "R$ " + s;
		}

		return s;

	}

	static main(args) {
		console.log(UMoney.formatParcial(""));
		console.log(UMoney.formatParcial("0"));
		console.log(UMoney.formatParcial("1,"));
		console.log(UMoney.formatParcial("12"));
		console.log(UMoney.formatParcial("123"));
		console.log(UMoney.formatParcial("1234"));
		console.log(UMoney.formatParcial("12345"));
		console.log(UMoney.formatParcial("123456"));
		console.log(UMoney.formatParcial("1234567"));
		console.log(UMoney.formatParcial("12345678"));
		console.log(UMoney.formatParcial("123456789"));
		console.log(UMoney.formatParcial("1234567890,00"));
		console.log(UMoney.formatParcial("12345678901,0"));
		console.log(UMoney.formatParcial("123456789012"));
		console.log(UMoney.formatParcial("1234567890123"));
		console.log(UMoney.formatParcial("12345678901234"));
		console.log(UMoney.formatParcial("123456789012345"));
	}

	static isValid(s) {
		const x = UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.caracteresNumericos);
		if (!UString.lengthIs(s, x.length)) {
			return false;
		}
		return true;
	}

	static descobreMaxLength(inteirosP) {
		let s = UString.repete("9", inteirosP);
		s = UMoney.formatParcial(s, false, inteirosP*2);
		return UString.length(s) + 3;
	}

}
UMoney.CHARS = UConstantes.numeros.concat(",");
