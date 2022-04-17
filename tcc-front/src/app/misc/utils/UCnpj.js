/* react */
import UString from './UString';

export default class UCnpj {

	static formatParcial(cnpj) {
		cnpj = UString.mantemSomenteNumeros(cnpj);
		if (cnpj.length >= 14) {
			return UCnpj.format(cnpj);
		} else if (cnpj.length < 3) {
			return cnpj;
		}
		let s = cnpj.substring(0, 2) + ".";
		cnpj = cnpj.substring(2);

		if (cnpj.length < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + ".";
		cnpj = cnpj.substring(3);

		if (cnpj.length < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + "/";
		cnpj = cnpj.substring(3);

		if (cnpj.length < 5) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 4) + "-";
		cnpj = cnpj.substring(4);

		if (cnpj.length > 2) {
			cnpj = cnpj.substring(0, 2);
		}
		s += cnpj;
		return s;

	}

	static garante14caracteres(cnpj) {
		cnpj = UString.mantemSomenteNumeros(cnpj);
		if (UString.length(cnpj) > 14) {
			cnpj = UString.maxLength(cnpj, 14);
		} else {
			while (!UString.lengthIs(cnpj, 14)) {
				cnpj = "0" + cnpj;
			}
		}
		return cnpj;
	}

	static format(cnpj) {
		cnpj = UCnpj.garante14caracteres(cnpj);
		cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8,12) + "-" + cnpj.substring(12);
		return cnpj;
	}

	static isComplete(cnpj) {
		return UString.lengthIs(UString.mantemSomenteNumeros(cnpj), 14);
	}

	static calcularDigito(str) {
		let soma = 0;
		for (let indice = str.length - 1; indice >= 0; indice--) {
			const digito = parseInt(str.substring(indice, indice + 1));
			soma += digito * UCnpj.peso.get(UCnpj.peso.length - str.length + indice);
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}
	static isValid(cnpj) {
		cnpj = UCnpj.garante14caracteres(cnpj);
		const digito1 = UCnpj.calcularDigito(cnpj.substring(0, 12));
		const digito2 = UCnpj.calcularDigito(cnpj.substring(0, 12) + digito1);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

}
UCnpj.peso = [
		6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
];
