/* react */
import UString from './UString';

export default class StringBox {
	value;
	constructor(initialValue) {
		this.set(initialValue);
	}
	clear() {
		this.set("");
	}
	set(valueParam) {
		if (UString.isEmpty(valueParam)) {
			this.value = "";
		} else {
			this.value = valueParam;
		}
	}
	add(s) {
		this.value += s;
		return this;
	}
	get() {
		return this.value;
	}
}
