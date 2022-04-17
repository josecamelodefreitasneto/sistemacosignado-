/* react */
import NumericJs from './NumericJs';

export default class Numeric15 extends NumericJs {

	constructor(o) {
		super(o, 15);
	}

	dividido(o) {
		return new Numeric15(super.dividido(o).toDouble());
	}

	vezes(o) {
		return new Numeric15(super.vezes(o).toDouble());
	}

	mais(o) {
		return new Numeric15(super.mais(o).toDouble());
	}

	menos(o) {
		return new Numeric15(super.menos(o).toDouble());
	}

	pow(o) {
		return new Numeric15(super.pow(o).toDouble());
	}

}
