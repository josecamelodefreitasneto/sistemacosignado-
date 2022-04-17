/* react */
import IdText from './IdText';
import UCommons from './UCommons';
import UString from './UString';

export default class UCartaoDeCredito {

	static formatParcialSemBandeira(n) {
		return n;
	}

	static formatParcial(n, bandeira) {
		n = UString.mantemSomenteNumeros(n);
		if (UCommons.equals(bandeira, UCartaoDeCredito.master)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.visa)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.discover)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.amex)) {
			return UCartaoDeCredito.format(n, 4, 6, 5);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.diners)) {
			return UCartaoDeCredito.format(n, 4, 6, 4);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.enRoute)) {
			return UCartaoDeCredito.format(n, 4, 7, 4);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.jcb)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.voyager)) {
			return UCartaoDeCredito.formatVoyager(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.elo)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.hiper)) {
			return UCartaoDeCredito.format4x4(n);
		}
		if (UCommons.equals(bandeira, UCartaoDeCredito.aura)) {
			return UCartaoDeCredito.format4x4(n);
		}
		return n;
	}

	static formatVoyager(n) {

		if (n.length > 15) {
			n = n.substring(0, 15);
		}

		let digito;

		if (UCommons.eqeqeq(n.length, 15)) {
			digito = " " + UString.right(n, 1);
			n = UString.ignoreRight(n, 1);
		} else {
			digito = "";
		}

		return UCartaoDeCredito.format(n, 5, 4, 5) + digito;
	}

	static format(n, a, b, c) {

		if (n.length <= a) {
			return n;
		}
		let s = n.substring(0, a) + " ";
		n = n.substring(a);

		if (n.length <= b) {
			return s + n;
		}

		s += n.substring(0, b) + " ";
		n = n.substring(b);

		if (n.length <= c) {
			return s + n;
		}

		s += n.substring(0, c);

		return s;

	}

	static format4x4(n) {
		if (n.length > 16) {
			n = n.substring(0, 16);
		}
		let s = "";
		while (UString.length(n) > 4) {
			s += n.substring(0, 4) + " ";
			n = n.substring(4);
		}
		return s + n;
	}

	static valido(s, bandeira) {
		if (UString.isEmpty(s)) {
			return false;
		}
		s = UString.mantemSomenteNumeros(s);
		if (!UString.lengthIs(s, 16)) {
			return false;
		}
		return true;
	}

}
UCartaoDeCredito.master   = new IdText(1, "MasterCard");
UCartaoDeCredito.visa     = new IdText(2, "Visa");
UCartaoDeCredito.amex     = new IdText(3, "American Express");
UCartaoDeCredito.diners   = new IdText(4, "Diners Club");
UCartaoDeCredito.discover = new IdText(5, "Discover");
UCartaoDeCredito.enRoute  = new IdText(6, "enRoute");
UCartaoDeCredito.jcb      = new IdText(7, "JCB");
UCartaoDeCredito.voyager  = new IdText(8, "Voyager");
UCartaoDeCredito.elo      = new IdText(9, "Elo");
UCartaoDeCredito.hiper    = new IdText(10, "HiperCard");
UCartaoDeCredito.aura     = new IdText(11, "Aura");
UCartaoDeCredito.BANDEIRAS = [
	IdText.VAZIO, UCartaoDeCredito.master, UCartaoDeCredito.visa, UCartaoDeCredito.amex, UCartaoDeCredito.diners, UCartaoDeCredito.discover, UCartaoDeCredito.enRoute, UCartaoDeCredito.jcb, UCartaoDeCredito.voyager, UCartaoDeCredito.elo, UCartaoDeCredito.hiper, UCartaoDeCredito.aura
];
