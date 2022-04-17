/* react */
export default class IntegerBox {
	value = 0;
	constructor(i) {
		this.value = i;
	}
	inc(i) {
		this.value += i;
		return this.value;
	}
	dec(i) {
		this.value -= i;
		return this.value;
	}
	get() {
		return this.value;
	}
}
