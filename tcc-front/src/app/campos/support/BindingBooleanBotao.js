/* react */
import BindingBotao from './BindingBotao';

export default class BindingBooleanBotao extends BindingBotao {

	bind;
	value = false;

	constructor(bindParam, valueParam, lblParam) {
		super(lblParam, null);
		this.bind = bindParam;
		this.value = valueParam;
		this.bind.addObserver(this);
	}

	onPressImpl() {
		this.bind.set(this.value);
	}

	isBranco() {
		return !this.bind.eq(this.value);
	}

	notify(o) {
		this.notifyObservers();
	}

}
