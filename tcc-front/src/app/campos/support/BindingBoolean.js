/* react */
import Binding from './Binding';
import BindingBooleanBotao from './BindingBooleanBotao';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class BindingBoolean extends Binding {

	labelSim = "Sim";
	labelNao = "Não";

	constructor() {
		super("");
	}

	static novo(labelP) {
		let o = new BindingBoolean();
		o.setLabel(labelP);
		return o;
	}

	setLabels(labelSimParam, labelNaoParam) {
		this.labelSim = labelSimParam;
		this.labelNao = labelNaoParam;
		return this;
	}

	isFalse() {
		return UBoolean.isFalse(this.get());
	}

	isTrue() {
		return UBoolean.isTrue(this.get());
	}

	change() {
		this.set(!this.isTrue());
	}

	botaoSim;
	getBotaoSim() {
		if (UCommons.isEmpty(this.botaoSim)) {
			this.botaoSim = new BindingBooleanBotao(this, true, this.labelSim);
			this.botaoSim.focusController = this.focusController;
		}
		return this.botaoSim;
	}

	botaoNao;
	getBotaoNao() {
		if (UCommons.isEmpty(this.botaoNao)) {
			this.botaoNao = new BindingBooleanBotao(this, false, this.labelNao);
			this.botaoNao.focusController = this.focusController;
		}
		return this.botaoNao;
	}

	setStartValue(value) {
		super.setStartValue(value);
		this.set(value);
		return this;
	}

	castFromString(s) {
		if (UString.isEmpty(s)) return null;
		if (UBoolean.isTrue(s)) return true;
		if (UBoolean.isFalse(s)) return false;
		return null;
	}

	setDisabled(value) {
		super.setDisabled(value);
		return this;
	}

}
