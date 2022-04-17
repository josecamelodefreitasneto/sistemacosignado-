/* react */
import BindingString from './BindingString';
import Numeric15 from '../../misc/utils/numbers/Numeric15';
import NumericJs from '../../misc/utils/numbers/NumericJs';
import UCommons from '../../misc/utils/UCommons';
import UMoney from '../../misc/utils/UMoney';
import UString from '../../misc/utils/UString';

export default class BindingMoney extends BindingString {

	inteiros = 0;
	nullIfZeroWhenDisabled = false;

	constructor(label, inteirosP) {
		super(label, UMoney.descobreMaxLength(inteirosP));
		this.inteiros = inteirosP;
	}

	formatParcial(s) {
		return UMoney.formatParcial(s, false, this.inteiros);
	}

	getDouble() {
		if (this.isEmpty()) return null;
		let s = this.get().replace(".", "").replace(",", ".");
		return parseFloat(s);
	}

	setNullIfZeroWhenDisabled(value) {
		this.nullIfZeroWhenDisabled = value;
		return this;
	}

	beforeSet(s) {
		s = super.beforeSet(s);
		if (UString.isEmpty(s)) {
			return null;
		}
		if (this.nullIfZeroWhenDisabled) {
			let x = UString.replaceWhile(s, "0", "");
			x = UString.replaceWhile(x, ".", "");
			if (UString.isEmpty(x)) {
				return null;
			}
		}
		return s;
	}

	toJsNumeric(casas) {
		return new NumericJs(this.getDouble(), casas);
	}
	toNumeric15() {
		return new Numeric15(this.getDouble());
	}

	setDouble(d) {
		if (UCommons.isEmpty(d)) {
			return this.set(null);
		} else {
			const s = ""+d;
			return this.set(s.replace(".", ","));
		}
	}

	setNumeric(n) {
		if (UCommons.isEmpty(n)) {
			return this.set(null);
		} else {
			return this.set(n.asString());
		}
	}

	isNumeric() {
		return true;
	}

	setDisabled(o) {
		super.setDisabled(o);
		return this;
	}

}
