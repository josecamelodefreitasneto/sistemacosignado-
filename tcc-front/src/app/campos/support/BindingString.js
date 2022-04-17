/* react */
import Binding from './Binding';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class BindingString extends Binding {

	maxLength = 0;
	asStringFunc;
	somenteNumeros = false;

	constructor(label, maxLength) {
		super(label);
		this.maxLength = maxLength;
		this.showTeclado = true;
	}

	getMaxLength() {
		return this.maxLength;
	}
	setMaxLength(value) {
		this.maxLength = value;
	}

	beforeSet(s) {
		if (UString.isEmpty(s)) {
			s = "";
		}
		if (this.somenteNumeros) {
			s = UString.mantemSomenteNumeros(s);
		} else {
			s = UString.ltrim(s);
			s = UString.replaceWhile(s, "  ", " ");
			s = UString.replaceWhile(s, ",,", ",");
			s = UString.replaceWhile(s, "..", ".");
		}
		s = this.formatParcial(s);
		const max = this.getMaxLength();
		if (max > 0) {
			s = UString.maxLength(s, max);
		}
		return s;
	}

	formatParcial(s) {
		return s;
	}

	isEmpty() {
		return UString.isEmpty(this.get());
	}

	equals(s) {
		return UString.equals(this.get(), s);
	}

	setAsStringFunc(value) {
		this.asStringFunc = value;
		return this;
	}

	asString() {
		if (UCommons.isEmpty(this.asStringFunc)) {
			return super.asString();
		} else {
			return this.asStringFunc(this.get());
		}
	}

	setNotNull(value) {
		super.setNotNull(value);
		return this;
	}

	castFromString(s) {
		return s;
	}

	setStartValue(o) {
		super.setStartValue(o);
		this.set(o);
		return this;
	}

	setDisabled(o) {
		super.setDisabled(o);
		return this;
	}

	withPlaceHolder(value) {
		this.setPlaceHolder(value);
		return this;
	}

	eq(o) {
		return UString.equals(this.get(), o);
	}

	isNumeric() {
		return this.somenteNumeros;
	}

	setSomenteNumeros(value) {
		this.somenteNumeros = value;
		return this;
	}

}
