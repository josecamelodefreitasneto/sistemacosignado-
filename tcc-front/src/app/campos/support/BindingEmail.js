/* react */
import BindingEmailConfirmacao from './BindingEmailConfirmacao';
import BindingString from './BindingString';
import UEmail from '../../misc/utils/UEmail';

export default class BindingEmail extends BindingString {

	confirmacao = new BindingEmailConfirmacao(this, "Confirmar e-mail");

	constructor(label) {
		super(label, 60);
		this.addObserver(this.confirmacao);
	}

	getInvalidMessagePrivate() {
		if (UEmail.isValid(this.get())) {
			return null;
		} else {
			return "E-mail inválido";
		}
	}

	formatParcial(s) {
		return UEmail.formatParcial(s);
	}

	notify(o) {
		this.notifyObservers();
	}

	addObserver(o) {
		return super.addObserver(o) | this.confirmacao.addObserver(o);
	}

	addRenderObserver(o) {
		super.addRenderObserver(o);
		this.confirmacao.addRenderObserver(o);
	}

	addChangeObserver(o) {
		return super.addChangeObserver(o) | this.confirmacao.addChangeObserver(o);
	}

	addFunctionObserver(funcao) {
		super.addFunctionObserver(funcao);
		this.confirmacao.addFunctionObserver(funcao);
	}

	setDisabled(o) {
		super.setDisabled(o);
		return this;
	}

}
