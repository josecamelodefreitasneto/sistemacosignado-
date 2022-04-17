/* react */
import BindingString from './BindingString';
import UEmail from '../../misc/utils/UEmail';

export default class BindingEmailConfirmacao extends BindingString {

	email;

	constructor(email, label) {
		super(label, 60);
		this.email = email;
		this.setNotNull(email.notNull());
	}

	getInvalidMessagePrivate() {
		return this.eq(this.email.get()) ? null : "Não corresponde";
	}

	formatParcial(s) {
		return UEmail.formatParcial(s);
	}

}
