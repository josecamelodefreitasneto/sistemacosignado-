/* react */
import UCommons from './UCommons';
import UString from './UString';

export default class UInteger {

	static isPositivo(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else {
			return o > 0;
		}
	}

	static isNegativo(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else {
			return o < 0;
		}
	}

	static isInt(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		}
		if (Number.isInteger(o)) {
			return true;
		}
		if (UString.isString(o)) {
			const s = o;
			return UString.mantemSomenteNumeros(s).equals(s);
		}
		return false;
	}

	static isEmptyOrZero(o) {
		return UCommons.isEmpty(o) || UCommons.equals(o, 0);
	}

	static between(value, min, max) {
		return value >= min && value <= max;
	}
	static compareToInt(a, b) {
		return UInteger.compare(UInteger.toInt(a), UInteger.toInt(b));
	}
	static compareCast(a, b) {
		return UInteger.compare(UCommons.cast(a), UCommons.cast(b));
	}
	static compareCastReverse(a, b) {
		return UInteger.compare(UCommons.cast(a), UCommons.cast(b)) * -1;
	}
	static compare(a, b) {
		if (UCommons.equals(a, b)) {
			return 0;
		} else if (UCommons.isEmpty(a)) {
			return -1;
		} else if (UCommons.isEmpty(b)) {
			return 1;
		} else if (a < b) {
			return -1;
		} else if (b < a) {
			return 1;
		} else {
			return 0;
		}
	}

	static format00(value, casas) {
		if (UCommons.isEmpty(value)) {
			return "";
		}
		let s = ""+value;
		while (s.length < casas) {
			s = "0" + s;
		}
		return s;
	}

	static equals(a, b) {
		return UCommons.eqeqeq(UInteger.compare(a, b), 0);
	}

	static main(args) {
		console.log(UInteger.separarMilhares("1234567"));
	}

	static separarMilhares(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.isEmpty(s)) {
			return "";
		}
		let split = UString.split(s, "");
		s = "";
		while (!split.isEmpty()) {
			s = split.pop() + s;
			if (!split.isEmpty()) {
				s = split.pop() + s;
				if (!split.isEmpty()) {
					s = split.pop() + s;
					if (!split.isEmpty()) {
						s = "." + s;
					}
				}
			}
		}
		return s;
	}
	static format(value) {
		if (UInteger.isEmptyOrZero(value)) {
			return "";
		}
		return UInteger.separarMilhares(""+value);
	}

	static toIntDef(o, def) {
		if (UInteger.isInt(o)) {
			return UInteger.toInt(o);
		}
		try {
			o = parseInt(o);
			if (UInteger.isInt(o)) {
				return UInteger.toInt(o);
			} else {
				return def;
			}
		} catch (e) {
			return def;
		}

	}

	static toInt(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		} else {
			return parseInt(o);
		}
	}
}
