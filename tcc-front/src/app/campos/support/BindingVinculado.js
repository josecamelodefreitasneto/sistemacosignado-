/* react */
import BindingBoolean from './BindingBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class BindingVinculado extends BindingBoolean {

	text;
	onConfirm;
	onClear;

	static novo(labelP) {
		let o = new BindingVinculado();
		o.setLabel(labelP);
		return o;
	}

	setText(s) {
		if (UString.equals(s, this.text)) {
			this.set(false);
		} else {
			this.text = s;
			if (!this.set(false)) {
				this.notifyObservers();/*pois mudou o texto*/
			}
		}
	}

	clear() {
		if (UString.isEmpty(this.text)) {
			return;
		}
		this.text = "";
		if (UCommons.notEmpty(this.onClear)) {
			this.onClear();
		}
		this.clear2();
		this.notifyObservers();
	}

	clear2() {}

	confirm() {
		if (UCommons.notEmpty(this.onConfirm)) {
			this.onConfirm();
		}
		this.set(false);
	}

	setCast(value) {
		if (UCommons.isEmpty(value)) {
			this.clear();
			return this.set(false);
		} else {
			return this.set(true);
		}
	}

	asString() {
		return this.text;
	}

	setOnConfirm(value) {
		this.onConfirm = value;
		return this;
	}

	setOnClear(value) {
		this.onClear = value;
		return this;
	}

	isEmpty() {
		return UString.isEmpty(this.asString());
	}

	setTrue(value) {
		this.text = value;
		this.set(true);
	}

	setDisabled(value) {
		super.setDisabled(value);
		return this;
	}

}
