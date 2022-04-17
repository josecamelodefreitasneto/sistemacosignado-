/* react */
import UConstantes from './UConstantes';
import UInteger from './UInteger';
import UString from './UString';

export default class UNumbers {

	static separarMilhares(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.length(s) < 4) {
			return s;
		}
		let result = "";
		while (UString.length(s) > 3) {
			result = "." + UString.right(s, 3) + result;
			s = UString.ignoreRight(s, 3);
		}
		return s + result;
	}

	static round(value, decimais) {

		let s = "" + value;
		if (!s.contains(".")) {
			s += ".0";
		}

		let ints = UString.beforeFirst(s, ".");
		let decs = UString.afterFirst(s, ".");

		if (UString.length(decs) <= decimais) {
			return value;
		}

		const itens = UString.split(decs, "").map(o => parseInt(o));

		while (itens.size() > decimais) {
			const x = itens.removeLast();
			if (x >= 5) {
				let y = itens.removeLast();
				y++;
				itens.push(y);
			}
		}

		if (itens.exists(o => o > 9)) {
			let intValue = parseInt(ints);

			while (itens.exists(o => o > 9)) {
				if (itens.get(0) > 9) {
					let x = itens.remove(0);
					x -= 10;
					intValue++;
					itens.add(x, 0);
				}
				for (let i = 1; i < itens.length; i++) {
					if (itens.get(i) > 9) {
						const x = itens.remove(i) - 10;
						const y = itens.remove(i-1) + 1;
						itens.add(y, i-1);
						itens.add(x, i);
					}
				}
			}

			ints = "" + intValue;

		}

		decs = itens.join("");

		s = ints + "." + decs;

		value = parseFloat(s);

		return value;
	}
	static formatDouble(value, decimais) {
		value = UNumbers.round(value, decimais);
		let s = "" + value;
		if (!s.contains(".")) {
			s += ".0";
		}
		const ints = UString.beforeFirst(s, ".");
		return UNumbers.formatString(s, UString.length(ints), decimais);
	}
	static formatString(s, inteiros, decimais) {
		s = UNumbers.formatParcial(s, inteiros, decimais);
		if (UString.isEmpty(s)) return "";
		while (!UString.lengthIs(s, inteiros+decimais+1)) {
			s += "0";
		}
		return s;
	}

	static formatParcial(s, inteiros, decimais) {

		if (inteiros < 1 || decimais < 1) {
			throw new Error("Invalid");
		}

		s = UString.mantemSomenteOsSeguintesCaracteres(s, UNumbers.CHARS);
		if (UString.isEmpty(s)) return "";

		if (s.contains(",")) {
			if (s.contains(".")) {
				if (s.indexOf(",") < s.indexOf(".")) {
					s = s.replace(",", "");
				} else {
					s = s.replace(".", "");
					s = s.replace(",", ".");
				}
			} else {
				s = s.replace(",", ".");
			}
		}

		let left;
		let right;

		if (UInteger.isInt(s)) {
			if (UString.length(s) <= inteiros) {
				return s;
			}
			left = s.substring(0, inteiros);
			right = s.substring(inteiros);
		} else {
			left = UString.beforeFirst(s, ".");
			right = UString.mantemSomenteNumeros(UString.afterFirst(s, "."));
			if (UString.isEmpty(left)) {
				left = "0";
			} else {
				while (UString.length(left) > inteiros) {
					right = UString.right(left, 1) + right;
					left = UString.ignoreRight(left, 1);
				}
			}
		}

		right = UString.maxLength(right, decimais);
		return left + "." + right;
	}

}
UNumbers.CHARS = UConstantes.numeros.concat(",").concat(".");
