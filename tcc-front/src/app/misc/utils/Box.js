/* react */
import UCommons from './UCommons';

export default class Box {
	value;
	constructor(valueP) {
		this.set(valueP);
	}
	get() {
		return this.value;
	}
	set(valueP) {
		this.value = valueP;
	}
	isNotNull() {
		return UCommons.notEmpty(this.value);
	}
	isNull() {
		return UCommons.isEmpty(this.value);
	}
}
