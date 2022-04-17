/* react */
import UCommons from './UCommons';
import UString from './UString';

export default class UBoolean {

	/*nulls/falses/true*/
	static compare(a, b) {
		if (UCommons.eqeqeq(a, b)) return 0;
		if (UCommons.isEmpty(a)) return -1;
		if (UCommons.isEmpty(b)) return 1;
		if (UBoolean.isFalse(a)) return -1;
		if (UBoolean.isTrue(a) === UBoolean.isTrue(b)) return 0;
		return 1;
	}

	static eq(a, b) {
		return UBoolean.compare(a, b) === 0;
	}

	static isTrue(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else if (UString.equals(typeof(o), "boolean")) {
			const b = o;
			return b;
		} else if (UString.equals(typeof(o), "string")) {
			const s = UString.toString(o).toLowerCase();
			return UString.equals(s, "true") || UString.equals(s, "sim") || UString.equals(s, "yes") || UString.equals(s, "1") || UString.equals(s, "y") || UString.equals(s, "verdadeiro");
		} else {
			return false;
		}
	}
	static isFalse(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else if (UString.equals(typeof(o), "boolean")) {
			const b = o;
			return !b;
		} else if (UString.equals(typeof(o), "string")) {
			const s = UString.toString(o);
			return UString.equals(s, "false");
		} else {
			return false;
		}
	}
}
