/* react */
import UInteger from './UInteger';

export default class IdText {

	constructor(idx, textx, ox, iconx) {
		this.id = idx;
		this.text = textx;
		this.o = ox;
		this.icon = iconx;
		this.key = "" + this.id;
	}

	id;
	text;
	o;
	icon;
	sigla;
	key;

	eq(value) {
		return UInteger.equals(this.id, value);
	}

	getId() {
		return this.id;
	}
	getText() {
		return this.text;
	}

	setSigla(siglaParam){this.sigla = siglaParam; return this;}

	stringify = () => this.id + " - " + this.text;

}
IdText.VAZIO = new IdText(0, "");
