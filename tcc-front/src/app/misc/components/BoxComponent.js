/* react */
import UCommons from '../utils/UCommons';

export default class BoxComponent {
	comp;
	v;

	constructor(comp, startValue) {
		this.comp = comp;
		this.v = startValue;
	}

	set(value) {
		this.v = value;
		if (UCommons.notEmpty(this.comp)) {
			this.comp.forceUpdate();
		}
	}

	get() {
		return this.v;
	}

}
