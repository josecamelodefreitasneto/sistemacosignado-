/* react */
import Binding from './Binding';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class BindingInteger extends Binding {

	minimo = 0;
	maximo = 0;

	constructor(label, maximo) {
		super(label);
		this.maximo = maximo;
		this.showTeclado = true;
	}

	getInvalidMessagePrivate() {
		if (UInteger.between(this.get(), this.min(), this.max())) {
			return this.getInvalidMessage2();
		} else {
			return "O valor deve estar entre " + this.min() + " e " + this.max();
		}
	}

	beforeSet(x) {
		let s = UString.toString(x);
		if (UString.isEmpty(s)) {
			return null;
		}
		const negativo = s.startsWith("-");
		s = UString.mantemSomenteNumeros(s);
		if (UString.isEmpty(s)) {
			return null;
		}
		let result = parseInt(UString.maxLength(s, UString.length(UString.toString(this.max()))));
		if (negativo) {
			result = -result;
		}

		if (result < this.min()) {
			return this.min();
		} else if (result > this.max()) {
			return this.max();
		} else {
			return result;
		}

	}

	getInvalidMessage2() {
		return null;
	}

	min() {
		return this.minimo;
	}

	max() {
		return this.maximo;
	}

	castFromString(s) {
		return parseInt(s);
	}

	setDisabled(o) {
		super.setDisabled(o);
		return this;
	}

	setMinimo(value) {
		this.minimo = value;
		return this;
	}

	asString() {
		return UInteger.format(this.get());
	}

	eq(o) {
		return UInteger.equals(o, this.get());
	}

}
