/* react */
import BindingObserver from './BindingObserver';
import Console from '../../misc/utils/Console';
import UCommons from '../../misc/utils/UCommons';

export default class BindingBotao extends BindingObserver {

	branco = false;
	label;
	onClick;
	onClickDisabled;
	getLabelFunc;

	constructor(label, onClick) {
		super();
		this.label = label;
		this.onClick = onClick;
		this.refreshDisabled();
	}

	notifyObservers() {
		this.enabledRenderObservers.forEach(o => o.forceUpdate());
	}

	isEnabled() {
		return !this.isDisabled();
	}

	isDisabled() {
		return this.disabled;
	}

	refreshDisabled() {
		this.setDisabled(this.calcDisabled());
	}

	enabledRenderObservers = [];

	addEnabledRenderObserver(o) {
		if (!this.enabledRenderObservers.contains(o)) {
			this.enabledRenderObservers.push(o);
		}
	}
	removeEnabledRenderObserver(o) {
		this.enabledRenderObservers.removeObject(o);
	}

	notify(o) {}

	calcDisabled() {
		return false;
	}

	getLabel() {
		if (UCommons.isEmpty(this.getLabelFunc)) {
			return this.label;
		} else {
			return this.getLabelFunc();
		}
	}
	onPress() {
		if (this.isDisabled()) {
			this.onPressDisabled();
			return false;
		} else {
			this.focus();
			this.onPressImpl();
			return true;
		}
	}
	onPressImpl() {
		if (UCommons.isEmpty(this.onClick)) {
			Console.log("BindingBotao.onPressImpl", "onClick is null: " + this.getLabel());
		} else {
			this.onClick();
		}
	}
	onPressDisabled() {
		if (UCommons.notEmpty(this.onClickDisabled)) {
			this.onClickDisabled();
		}
	}

	getIcone() {
		return null;
	}

	getImage() {
		return null;
	}
	isBranco() {
		return this.branco;
	}
	setBranco(valueParam) {
		this.branco = valueParam;
		this.notifyObservers();
		return this;
	}

	setGetLabelFunc(getLabelFunction) {
		this.getLabelFunc = getLabelFunction;
		return this;
	}

	isVisible() {
		return this.visible;
	}

	visible = true;
	setVisible(valueParam) {
		if (UCommons.neq(this.visible, valueParam)) {
			this.visible = valueParam;
			this.notifyObservers();
		}
		return this;
	}

	disabled = false;
	setDisabled(valueParam) {
		if (UCommons.neq(this.disabled, valueParam)) {
			this.disabled = valueParam;
			this.notifyObservers();
		}
	}

}
