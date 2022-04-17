/* react */
export default class Contador {
	value = 0;
	inc() {
		return ++this.value;
	}
	dec() {
		return --this.value;
	}
	get() {
		return this.value;
	}
}
