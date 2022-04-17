/* react */
import BindingObserver from './BindingObserver';
import IconeStatus from '../../misc/consts/enums/IconeStatus';
import UCommons from '../../misc/utils/UCommons';
import UInteger from '../../misc/utils/UInteger';
import UNative from '../../misc/utils/UNative';
import UString from '../../misc/utils/UString';

export default class Binding extends BindingObserver {

	static notificacoesDesligadas = 0;
	static notificacoesDesligadasInc() {
		Binding.notificacoesDesligadas++;
	}
	static notificacoesDesligadasDec() {
		Binding.notificacoesDesligadas--;
	}

	static enfileirarNotificacoes = false;
	static notificacoesEnfileiradas = [];

	getInvalidMessageFunction;

	val;
	startValue;
	label;
	placeHolder;
	virgin = true;
	enabled = true;
	visible = true;
	observersDisabled = false;
	replaceRenderBody = false;
	renderBody;
	renderObservers = [];
	observers = [];
	changeObservers = [];
	functionsObservers = [];
	functionsTObservers = [];
	atributes = new Map();
	controller;
	help;

	constructor(label) {
		super();
		this.label = label;
		this.val = this.getStartValue();
	}

	setLabel(value) {
		this.label = value;
		this.notifyObservers();
	}

	get() {
		return this.val;
	}
	getToService() {
		if (this.isValid()) {
			return this.getToServiceImpl();
		} else {
			return null;
		}
	}

	getToServiceImpl() {
		return this.get();
	}

	restart() {
		this.set(this.getStartValue());
		this.setVirgin(true);
		return true;
	}

	setCast(value) {
		return this.set(value);
	}

	set(value) {
		if (!this.ativo()) {
			return false;
		}
		value = this.beforeSet(value);
		if (this.eq(value)) {
			this.afterCancelSet();
			return false;
		} else {
			const changed = this.isChanged();
			this.virgin = false;
			this.val = value;
			this.afterSet();
			this.notifyObservers();
			if (UCommons.neq(changed, this.isChanged())) {
				this.changeObservers.forEach(o => o.notify(this));
			}
			return true;
		}
	}

	static dispararNotificacoesEnfieiradas() {
		while (!Binding.notificacoesEnfileiradas.isEmpty()) {
			Binding.notificacoesEnfileiradas.remove(0).notifyObservers();
		}
		Binding.enfileirarNotificacoes = false;
	}

	notifyObservers() {
		if (Binding.notificacoesDesligadas > 0) {
			return;
		}
		if (this.observersDisabled) {
			return;
		}
		if (Binding.enfileirarNotificacoes) {
			Binding.notificacoesEnfileiradas.addIfNotContains(this);
			return;
		}
		this.observers.concat([]).forEach(o => {
			o.notify0(this);
			o.notify(this);
		});
		this.renderObservers.concat([]).forEach(o => {
			if (UCommons.notEmpty(o)) {
				o.forceUpdate();
			}
		});
		this.functionsObservers.concat([]).forEach(o => o());
		this.functionsTObservers.concat([]).forEach(o => o(this));
	}

	notify(o) {}

	notify0(o) {
		const s = this.getInvalidMessagePrivate0();
		if (!UString.equals(s, this.ultimaInvalidMessage)) {
			this.ultimaInvalidMessage = s;
			this.notifyObservers();
		}
	}

	isValid() {
		return UString.isEmpty(this.getInvalidMessage());
	}

	setVisible(value) {
		if (value) {
			value = this.isVisiblePrivate();
		}
		if (UCommons.neq(this.visible, value)) {
			this.visible = value;
			this.notifyObservers();
		}
	}
	setEnabled(value) {
		if (value) {
			value = this.isEnabledPrivate();
		}
		if (UCommons.neq(this.enabled, value)) {
			this.enabled = value;
			this.notifyObservers();
		}
		return this;
	}
	setDisabled(value) {
		return this.setEnabled(!value);
	}

	isEnabled() {
		return this.enabled;
	}
	isDisabled() {
		return !this.enabled;
	}
	isVisible() {
		return this.visible;
	}

	isVisiblePrivate() {
		return true;
	}
	isEnabledPrivate() {
		return true;
	}

	eq(o) {
		if (UCommons.isEmpty(o) || this.isEmpty()) {
			return UCommons.equals(this.get(), o);
		}

		const idA = UNative.getAtributo(o, "idComponent");
		if (UCommons.notEmpty(idA)) {
			const idB = UNative.getAtributo(this.get(), "idComponent");
			return UCommons.eqeqeq(idA, idB);
		}

		return UCommons.equals(this.get(), o);

	}

	ativo() {
		return true;
	}

	getStatus() {
		if (this.isEmpty()) {
			return IconeStatus.warning;
		} else if (this.isValid()) {
			return IconeStatus.ok;
		} else {
			return IconeStatus.error;
		}
	}
	isPassword() {
		return false;
	}

	mostrarPassword = false;

	showPassword() {
		return this.mostrarPassword;
	}
	showBotaoShowHidePassword() {
		return true;
	}

	changeShowPassword() {
		this.mostrarPassword = !this.mostrarPassword;
		this.notifyObservers();
	}
	isNumeric() {
		return false;
	}

	setPlaceHolder(value) {
		this.placeHolder = value;
	}
	getPlaceHolder() {
		return this.placeHolder;
	}

	asString() {
		return UString.toString(this.get());
	}

	afterSet() {}
	afterCancelSet() {}

	beforeSet(value) {
		return value;
	}

	ultimaInvalidMessage;

	getInvalidMessage() {
		this.ultimaInvalidMessage = this.getInvalidMessagePrivate0();
		return this.ultimaInvalidMessage;
	}
	getInvalidMessagePrivate0() {
		if (UCommons.notEmpty(this.controller)) {
			return this.controller.getInvalidMessage();
		}
		if (this.isDisabled() && !this.erroMesmoSeDisabled()) {
			return null;
		} else if (!this.isVisible()) {
			return null;
		} else if (this.isDisabled()) {
			return null;
		} else if (this.isEmpty()) {
			if (this.notNull()) {
				return this.getMensagemObrigatorio();
			} else {
				return this.notNullAndIsEmptyInvalidMessage();
			}
		} else {
			if (UCommons.notEmpty(this.getInvalidMessageFunction)) {
				let s = this.getInvalidMessageFunction();
				if (UString.notEmpty(s)) {
					return s;
				}
			}
			return this.getInvalidMessagePrivate();
		}
	}

	notNullAndIsEmptyInvalidMessage() {
		return null;
	}

	getMensagemObrigatorio() {
		return "Obrigatório!";
	}

	getInvalidMessagePrivate() {
		return null;
	}

	getLabel() {
		return this.label;
	}

	getStartValue() {
		return this.startValue;
	}
	commit() {
		if (this.isChanged()) {
			this.setStartValue(this.get());
			this.changeObservers.forEach(o => o.notify(this));
		}
	}
	setStartValue(o) {
		o = this.beforeSet(o);
		this.startValue = o;
		this.virgin = true;
		return this;
	}

	obrigatorio = true;

	notNull() {
		return this.obrigatorio;
	}
	allowNull() {
		return !this.notNull();
	}

	setNotNull(value) {
		this.obrigatorio = value;
		return this;
	}

	isEmpty() {
		return UCommons.isEmpty(this.get());
	}
	isNotEmpty() {
		return !this.isEmpty();
	}

	erroMesmoSeDisabled() {
		return true;
	}

	addRenderObserver(o) {
		if (!this.renderObservers.contains(o)) {
			this.renderObservers.push(o);
		}
	}

	addObserver(o) {
		if (this.observers.contains(o)) {
			return false;
		} else if (UCommons.eqeqeq(o, this)) {
			return false;
		} else {
			this.observers.push(o);
			return true;
		}
	}
	addChangeObserver(o) {
		if (this.changeObservers.contains(o)) {
			return false;
		} else {
			this.changeObservers.push(o);
			return true;
		}
	}
	addFunctionObserver(funcao) {
		if (!this.functionsObservers.contains(funcao)) {
			this.functionsObservers.push(funcao);
		}
	}
	addFunctionTObserver(funcao) {
		if (!this.functionsTObservers.contains(funcao)) {
			this.functionsTObservers.push(funcao);
		}
	}
	isChanged() {
		return !this.eq(this.getStartValue());
	}

	isVirgin() {
		return this.virgin;
	}

	setVirgin(value) {
		this.virgin = value;
		this.notifyObservers();
	}

	setString(s) {
		return this.set(this.castFromString(s));
	}

	sendKey(key) {
		let s = this.asString();
		if (UString.equals(key, "<")) {
			if (!UString.isEmpty(s)) {
				this.setString(UString.ignoreRight(s, 1));
			}
			return;
		} else if (this.isNumeric()) {
			if (UInteger.isInt(key)) {
				s = UString.mantemSomenteNumeros(s) + key;
				this.setString(s);
			}
			return;
		} else {
			this.setString(s + key);
		}
	}
	setAtribute(key, value) {
		this.atributes.set(key, value);
	}
	getAtribute(key) {
		return this.atributes.get(key);
	}
	removeRenderObserver(o) {
		this.renderObservers.removeObject(o);
	}
	setHelp(value) {
		this.help = value;
		this.notifyObservers();
	}
	getHelp() {
		return this.help;
	}
	setObserversDisabled(value) {
		this.observersDisabled = value;
	}
	clear() {
		this.set(null);
	}

	getReplaceRenderBody() {
		return this.replaceRenderBody;
	}
	setReplaceRenderBody(value) {
		this.replaceRenderBody = value;
	}
	getRenderBody() {
		return this.renderBody();
	}
	setRenderBody(value) {
		this.renderBody = value;
	}
}
