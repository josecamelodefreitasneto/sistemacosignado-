/* react */
import BindingBoolean from './BindingBoolean';
import BindingString from './BindingString';

export default class BindingBooleanFiltro extends BindingString {

	sim = new BindingBoolean("Sim");
	nao = new BindingBoolean("Não");

	constructor(label) {
		super(label, 9);
		this.sim.addObserver(this);
		this.nao.addObserver(this);
	}

	getInvalidMessagePrivate() {
		if (this.sim.isFalse() && this.nao.isFalse()) {
			return "Pelo menos Sim ou Não deve estar preenchido";
		} else {
			return null;
		}
	}

	notify(o) {
		this.notifyObservers();
	}

}
