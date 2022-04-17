/* react */
import UCommons from './UCommons';
import UConstantes from './UConstantes';
import UString from './UString';

export default class UCpf {

	static main(args) {
		console.log(UCpf.isValid("37104694840"));
	}

	static formatParcial(cpf) {
		cpf = UString.mantemSomenteNumeros(cpf);
		if (cpf.length >= 11) {
			return UCpf.format(cpf);
		} else if (cpf.length < 4) {
			return cpf;
		}
		let s = cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + "-";
		cpf = cpf.substring(3);
		if (cpf.length > 2) {
			cpf = cpf.substring(0, 2);
		}
		s += cpf;
		return s;

	}

	static format(cpf) {
		cpf = UString.mantemSomenteNumeros(cpf);
		if (cpf.length > 11) {
			cpf = cpf.substring(0, 11);
		} else while (cpf.length < 11) {
			cpf = "0" + cpf;
		}
		cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		return cpf;
	}

	static isValid(cpf) {
		if (UString.isEmpty(cpf)) {
			return false;
		}
		cpf = UString.mantemSomenteNumeros(cpf);
		if (!UString.lengthIs(cpf, 11)) {
			return false;
		}
		const numDig = cpf.substring(0, 9);
		if (!UCpf.calcDigVerif(numDig).equals(cpf.substring(9, 11))) {
			return false;
		}
		const numeros = UConstantes.numeros;

		const x = cpf;
		if (numeros.some(s => UString.isEmpty(UString.replaceWhile(x, s, "")))) {
			return false;
		}
		return true;
	}

	static calcDigVerif(num) {

		let soma = 0;
		let peso = 10;
		for (let i = 0; i < num.length; i++) {
			soma += parseInt(num.substring(i, i + 1)) * peso--;
		}

		let resto = soma % 11;
		let primDig = 0;
		let segDig = 0;

		if (UCommons.equals(resto, 0) || UCommons.equals(resto, 1)) {
			primDig = 0;
		} else {
			primDig = parseInt(11 - resto);
		}
		soma = 0;
		peso = 11;
		for (let i = 0; i < num.length; i++) {
			soma += parseInt(num.substring(i, i + 1)) * peso--;
		}
		soma += primDig * 2;

		resto = soma % 11;
		if (UCommons.equals(resto, 0) || UCommons.equals(resto, 1)) {
			segDig = 0;
		} else {
			segDig = parseInt(11 - resto);
		}
		return "" + primDig + segDig;
	}

	static isComplete(cpf) {
		return UString.lengthIs(UString.mantemSomenteNumeros(cpf), 11);
	}

}
