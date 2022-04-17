/* react */
import BindingString from './BindingString';
import UString from '../../misc/utils/UString';

export default class BindingCodigo extends BindingString {

	numeric = false;

	constructor(label, size, numeric) {
		super(label, size);
		this.numeric = numeric;
	}

	formatParcial(s) {
		return super.formatParcial(UString.mantemSomenteNumeros(s));
	}

	getInvalidMessagePrivate() {
		const length = UString.length(this.get());
		return length < this.getMaxLength() ? length + " de " + this.getMaxLength() + " Caracteres" : null;
	}

	isNumeric() {
		return this.numeric;
	}

}
