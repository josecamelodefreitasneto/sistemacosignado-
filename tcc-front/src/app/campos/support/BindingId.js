/* react */
import BindingInteger from './BindingInteger';
import UCommons from '../../misc/utils/UCommons';

export default class BindingId extends BindingInteger {

	constructor(label) {
		super(label, 999999999);
	}

	beforeSet(x) {
		if (!this.isEmpty() && UCommons.notEmpty(x) && !this.eq(x)) {
			throw new Error("Mudança de Id: de " + this.get() + " para " + x);
		}
		return super.beforeSet(x);
	}

}
