/* react */
import UCommons from './UCommons';
import UInteger from './UInteger';
import UString from './UString';

export default class UIdText {
	static compareText(a, b) {
		if (UCommons.eqeqeq(a, b)) return 0;
		if (UCommons.isEmpty(a)) return -1;
		if (UCommons.isEmpty(b)) return 1;
		return UString.compare(a.text, b.text);
	}
	static isId(o, id) {
		if (UCommons.isEmpty(o)) return UCommons.isEmpty(id);
		return UInteger.equals(o.id, id);
	}
}
