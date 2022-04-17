/* react */
import Console from '../Console';
import UCommons from '../UCommons';
import UNative from '../UNative';
import UNumbers from '../UNumbers';
import UString from '../UString';

export default class NumericJs {

	value;
	casas = 0;

	asDouble = () => this.toDouble();

	constructor(o, casas) {
		this.casas = casas;
		this.value = this.convert(o);
	}

	convert(o) {
		if (UCommons.isEmpty(o)) {
			return 0.0;
		}
		const asDoubleFunc = UNative.getAtributo(o, "asDouble");
		if (UCommons.notEmpty(asDoubleFunc)) {
			return asDoubleFunc();
		}
		const x = UNumbers.round(parseFloat(o), this.casas);
		return x;
	}

	toDouble() {
		return this.value;
	}
	getCasas() {
		return this.casas;
	}

	dividido(o) {
		return new NumericJs(this.toDouble() / this.convert(o), this.casas);
	}
	vezes(o) {
		return new NumericJs(this.toDouble() * this.convert(o), this.casas);
	}
	mais(o) {
		return new NumericJs(this.toDouble() + this.convert(o), this.casas);
	}
	menos(o) {
		return new NumericJs(this.toDouble() - this.convert(o), this.casas);
	}
	pow(o) {
		return new NumericJs(Math.pow(this.toDouble(), this.convert(o)), this.casas);
	}
	isZero() {
		return this.toDouble() === 0.0;
	}
	menorOuIgual(o) {
		return this.toDouble() <= this.convert(o);
	}
	maiorOuIgual(o) {
		return this.toDouble() >= this.convert(o);
	}

	asString() {
		let s = UString.toString(this.value);
		if (!s.contains(".")) {
			s += ".0";
		}
		while (UString.length(UString.afterFirst(s, ".")) < this.casas) {
			s += "0";
		}
		while (UString.length(UString.afterFirst(s, ".")) > this.casas) {
			s = UString.ignoreRight(s, 1);
		}
		return s;
	}
	print() {
		Console.log("Numericprint", this.asString());
	}

}
