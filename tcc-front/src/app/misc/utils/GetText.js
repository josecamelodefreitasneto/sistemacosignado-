/* react */
import UBoolean from './UBoolean';
import UCommons from './UCommons';
import UNative from './UNative';
import UString from './UString';

export default class GetText {

	static getAttr(o, attr) {

		const pm = UString.primeiraMaiuscula(attr);

		const getText = UNative.getAtributo(o, "get" + pm);
		if (UCommons.notEmpty(getText)) {
			const x = o;
			x.getText = getText;
			return x.getText();
		}

		const text = UNative.getAtributo(o, attr);

		if (UCommons.neq(text, undefined)) {
			return text;
		}

		return null;

	}

	static get(o) {

		if (UCommons.isEmpty(o)) {
			return null;
		}
		if (UString.equals(typeof(o), "string")) {
			return o;
		}
		if (UString.equals(typeof(o), "boolean")) {
			return UBoolean.isTrue(o) ? "Sim" : "Não";
		}

		if (UString.equals(typeof(o), "number")) {
			return UString.toString(o);
		}

		let s = GetText.getAttr(o, "text");
		if (UString.notEmpty(s)) return s;

		s = GetText.getAttr(o, "nome");
		if (UString.notEmpty(s)) return s;

		s = GetText.getAttr(o, "descricao");
		if (UString.notEmpty(s)) return s;

		s = GetText.getAttr(o, "codigo");
		if (UString.notEmpty(s)) return s;

		s = GetText.getAttr(o, "numero");
		if (UString.notEmpty(s)) return s;

		return null;

	}
}
