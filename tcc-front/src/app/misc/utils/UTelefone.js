/* react */
import UCommons from './UCommons';
import UString from './UString';

export default class UTelefone {

	static formatParcial(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.maxLength(UString.mantemSomenteNumeros(s), UTelefone.MAX_LENGTH);

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) < 2) {
			return "(" + s;
		}

		let x = "(" + s.substring(0, 2);
		s = s.substring(2);

		if (UString.isEmpty(s)) {
			return x;
		}

		x += ") ";

		s = x + UTelefone.formatNumero(s);
		s = UString.maxLength(s, UTelefone.MAX_LENGTH);

		return s;

	}

	static formatNumero(s) {
		if (UString.isEmpty(s)) {
			return "";
		}
		let x = "";
		if (UString.length(s) > 4) {
			while (UString.length(s) > 4) {
				x = "-" + UString.right(s, 4) + x;
				s = UString.ignoreRight(s, 4);
			}
		}
		s = s + x;
		return s;
	}

	static isValid(s) {
		s = UTelefone.formatParcial(s);
		if (UString.length(s) < 14) {
			return false;
		}
		return true;
	}

	static main(args) {
		console.log(UTelefone.formatComDdi(55,61,"992559810"));
		console.log(UTelefone.formatComDdi(null,61,"992559810"));
		console.log(UTelefone.formatComDdi(null,null,"992559810858"));
		console.log(UTelefone.formatComDdi(null,null,"9810858"));
		console.log(UTelefone.formatComDdi(55,null,"992559810"));
	}

	static formatComDdi(ddi, ddd, numero) {
		let s = "";
		if (!UCommons.isEmpty(ddi)) {
			s = "+" + ddi + " ";
		}
		return s + UTelefone.formatComDdd(ddd, numero);
	}

	static formatComDdd(ddd, numero) {
		let s = "";
		if (!UCommons.isEmpty(ddd)) {
			s += "(" + ddd + ") ";
		}
		s += UTelefone.formatNumero(numero);
		return s.trim();
	}

}
UTelefone.MAX_LENGTH = 20;
