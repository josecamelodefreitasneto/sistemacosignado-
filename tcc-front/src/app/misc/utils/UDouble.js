/* react */
import UCommons from './UCommons';

export default class UDouble {

	static toDouble(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		} else {
			return parseFloat(o);
		}
	}

	static isEmptyOrZero(o) {
		return UCommons.isEmpty(o) || UCommons.equals(o, 0) || o === 0;
	}

	static equals(a, b) {
		return UDouble.compare(a, b) === 0;
	}
	static compare(a, b) {
		if (UCommons.isEmpty(a)) {
			if (UCommons.isEmpty(b)) {
				return 0;
			} else {
				return -1;
			}
		} else if (UCommons.isEmpty(b)) {
			return 1;
		} else if (a === b || a - b === 0) {
			return 0;
		} else if (a > b) {
			return 1;
		} else if (b > a) {
			return -1;
		} else {
			throw new Error("???");
		}
	}

}
