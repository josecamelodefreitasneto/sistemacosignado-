/* react */
import BindingBotao from './BindingBotao';

export default class BindingCheckBotao extends BindingBotao {

	binding;

	constructor(binding) {
		super();
		this.binding = binding;
	}

	getLabel() {
		return this.binding.getLabel();
	}

	onPressImpl() {
		this.binding.change();
	}

	isBranco() {
		return this.isFalse();
	}

	isTrue() {
		return this.binding.isTrue();
	}
	isFalse() {
		return this.binding.isFalse();
	}

}
