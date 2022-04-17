/* react */
import UNumbers from '../UNumbers';
import UString from '../UString';
import ValidadorDeMatricula from './ValidadorDeMatricula';

export default class ValidadorDeMatriculaBancoDoBrasil extends ValidadorDeMatricula {

	formatParcial(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.length(s) < 2) {
			return s;
		}
		s = UString.maxLength(s, 15);
		let digito = "-" + UString.right(s, 1);
		s = UString.ignoreRight(s, 1);
		s = UNumbers.separarMilhares(s);
		return s + digito;
	}

	isValid(original) {

		if (UString.isEmpty(original)) {
			return false;
		}

		original = UString.replaceWhile(original, ".", "");
		original = original.replace("-", "");

		let s = UString.mantemSomenteNumeros(original);

		if (!UString.equals(original, s)) {
			return false;
		}

		return true;

	}

}
