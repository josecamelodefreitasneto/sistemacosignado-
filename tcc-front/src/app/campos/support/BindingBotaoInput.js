/* react */
import BindingBotao from './BindingBotao';

export default class BindingBotaoInput extends BindingBotao {

	bind;

	constructor(bind) {
		super();
		this.bind = bind;
		this.setBranco(true);
		bind.addObserver(this);
	}

	onPressImpl() {
		this.bind.focus();
	}

	getLabel() {
		if (this.bind.isEmpty()) {
			return this.bind.getLabel();
		} else {
			return this.bind.asString();
		}
	}

	notify(o) {
		this.notifyObservers();
	}

}
