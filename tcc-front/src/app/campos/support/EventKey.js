/* react */
import UString from '../../misc/utils/UString';

export default class EventKey {
	e;
	constructor(e)  {
		this.e = e;
	}
	enter() {
		return UString.equals(this.e.key, "Enter");
	}
	shift() {
		return this.e.shiftKey;
	}
	ctrl() {
		return this.e.ctrlKey;
	}
	esc() {
		return UString.equals(this.e.key, "Escape");
	}
	del() {
		return UString.equals(this.e.key, "Delete");
	}
	arrowDown() {
		return UString.equals(this.e.key, "ArrowDown");
	}
	arrowUp() {
		return UString.equals(this.e.key, "ArrowUp");
	}
	getKey() {
		return this.e.key;
	}
}
