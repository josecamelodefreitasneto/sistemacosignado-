/* react */
import BindingString from './BindingString';
import UString from '../../misc/utils/UString';

export default class BindingSenha extends BindingString {

	numeric = false;

	constructor(label, size, numeric) {
		super(label, size);
		this.numeric = numeric;
		this.setPlaceHolder("Entre com uma Senha");
	}

	formatParcial(s) {

		let x = this.get();
		let result = "";

		while (UString.notEmpty(s) && UString.notEmpty(x)) {
			if (s.startsWith("*")) {
				result += x.substring(0, 1);
			} else {
				result += s.substring(0, 1);
			}
			s = s.substring(1);
			x = x.substring(1);
		}

		s = result + s;
		if (this.isNumeric()) {
			s = UString.mantemSomenteNumeros(s);
		}
		s = UString.maxLength(s, this.getMaxLength());
		return s;

	}

	isNumeric() {
		return this.numeric;
	}

	asString() {
		if (this.showPassword()) {
			return this.get();
		} else {
			return UString.repete("*", UString.length(this.get()));
		}
	}

	isPassword() {
		return true;
	}

}
