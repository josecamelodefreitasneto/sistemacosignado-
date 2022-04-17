/* front-constructor */
import UString from '../../app/misc/utils/UString';

export default class CampoAlterado {

	id;
	campo;
	de;
	para;

	getId() {
		return this.id;
	}

	setId(value) {
		this.id = value;
	}

	setCampo(s) {
		this.campo = s;
		return this;
	}

	quebrar(s) {
		if (UString.isEmpty(s)) {
			return null;
		}

		if (UString.length(s) > 50) {
			let x = "";
			while (UString.length(s) > 50) {
				x += s.substring(0, 50) + "\n";
				s = s.substring(50);
			}
			s = x + s;
		}

		if (UString.length(s) > 500) {
			s = s.substring(0, 500) + " ...";
		}

		return s;
	}

	setDe(s) {
		this.de = this.quebrar(s);
		return this;
	}

	setPara(s) {
		this.para = this.quebrar(s);
		return this;
	}

	toString() {
		return this.campo + " > " + this.de + " > " + this.para;
	}

}
