/* react */
import IdText from './IdText';
import UNative from './UNative';
import UString from './UString';

export default class UCommons {

	static notEmptyId(o) {
		return !UCommons.isEmptyId(o);
	}
	static isEmptyId(o) {

		if (UCommons.isEmpty(o)) {
			return true;
		}

		if (UCommons.eq(o, 0)) {
			return true;
		}

		if (o instanceof IdText) {
			const x = o;
			return UCommons.eq(x.id, 0);
		}

		return false;

	}

	static notEmpty(o) {
		return !UCommons.isEmpty(o);
	}
	static isEmpty(o) {
		return o === null || o === undefined || o === "";
	}
	static notEquals(a, b) {
		return !UCommons.equals(a, b);
	}

	static inEquals = false;

	static equals(a, b) {

		if (UCommons.inEquals) {
			return false;
		}

		UCommons.inEquals = true;

		try {

			if (UCommons.eq(a,b)) {
				return true;
			}

			if (UCommons.isEmpty(a)) {
				return UCommons.isEmpty(b);
			} else if (UCommons.isEmpty(b)) {
				return false;
			}

			if (a instanceof IdText) {
				const x = a;
				a = x.id;
			}

			if (b instanceof IdText) {
				const x = b;
				b = x.id;
			}
			if (UCommons.eq(a,b)) {
				return true;
			}
			if (UCommons.getClassName(a) !== UCommons.getClassName(b) ) {
				return false;
			}

			const sa = UString.toString(a);
			const sb = UString.toString(b);

			if (UNative.inJava) {
				return sa.equals(sb);
			} else {
				return UCommons.eq(sa,sb);
			}

		} finally {
			UCommons.inEquals = false;
		}

	}
	static eqeqeq(a, b) {
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		return a === b;
	}
	static neq(a, b) {
		return !UCommons.eqeqeq(a, b);
	}
	static eq(a, b) {
		return UCommons.eqeqeq(a, b) && UString.equals(typeof(a), typeof(b));
	}

	static coalesce(a, b) {
		return UCommons.isEmpty(a) ? b : a;
	}

	static cast(o) {
		return o;
	}

	static isBoolean(o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else {
			return UString.equals(typeof(o), "boolean");
		}
	}

	static isLink(s) {
		return s.startsWith("www.") || s.startsWith("http") || s.endsWith(".com.br");
	}

	static getClassName(o) {
		if (UNative.inJava) {
			return o.getClass().name;
		} else {
			const js = o;
			return js.__proto__.constructor.name;
		}
	}

	static simpleCompare(a, b) {
		if (UCommons.equals(a, b)) {
			return 0;
		} else if (UCommons.isEmpty(a)) {
			return -1;
		} else if (UCommons.isEmpty(b)) {
			return 1;
		} else {
			return null;
		}
	}

	static instanceOf(o, classe) {
		const className = UCommons.getClassName(o);
		return UCommons.equals(className, classe.name);
	}

}
