/* react */
import Caracteres from '../consts/Caracteres';
import Console from './Console';
import StringBox from './StringBox';
import UCommons from './UCommons';
import UConstantes from './UConstantes';
import UNative from './UNative';

export default class UString {

	static notEmpty(s) {
		return !UString.isEmpty(s);
	}
	static isEmpty(s) {
		try {
			return UString.isEmptyReal(s) || UCommons.eqeqeq(s.trim().length, 0);
		} catch (e) {
			Console.log("UString.isEmpty", s);
			return UString.isEmptyReal(s) || UCommons.eqeqeq(s.trim().length, 0);
		}
	}

	static isEmptyReal(s) {
		if (UCommons.eqeqeq(s, null)) {
			return true;
		} else if (UCommons.eqeqeq(s, undefined)) {
			return true;
		} else {
			return false;
		}
	}

	static replaceWhile(s, a, b) {
		if (UString.isEmpty(s) || UString.isEmpty(a)) {
			return s;
		}
		while (s.contains(a)) {
			s = s.replace(a, b);
		}
		return s;
	}

	static replace(s, a, b) {
		if (UString.isEmpty(s)) {
			return null;
		}
		if (UString.isEmpty(a)) {
			return s;
		}
		if (UString.isEmpty(b)) {
			b = "";
		}
		s = "|"+s+"|";
		s = UString.split(s, a).join(b);
		s = s.substring(1);
		s = UString.ignoreRight(s, 1);
		return s;
	}

	static toLowerCase(s) {
		if (UString.isEmpty(s)) {
			return s;
		} else {
			return s.toLowerCase();
		}
	}

	/*existe uma falha no js que esta criando um novo object*/
	static safe(s) {
		return s + "";
	}

	static equalsIgnoreCase(a, b) {
		return UString.equals(UString.toLowerCase(a), UString.toLowerCase(b));
	}

	static equals(a, b) {
		if (UString.isEmpty(a)) {
			return UString.isEmpty(b);
		} else if (UString.isEmpty(b)) {
			return false;
		} else {
			a = UString.safe(a);
			b = UString.safe(b);
			if (UCommons.eqeqeq(a, b)) {
				return true;
			}
			return false;
		}
	}

	static contains(s, x) {
		if (UString.isEmptyReal(s) || UString.isEmptyReal(x)) {
			return false;
		} else {
			return s.indexOf(x) > -1;
		}
	}

	static trimPlus(s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = s.replace("\t", " ");
		s = s.trim();
		while (UString.contains(s, "  ")) {
			s = s.replace("  ", " ");
		}
		return s;
	}

	static preparaParaBusca(s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.trimPlus(s);

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.removeAcentos(s);
		s = s.toLowerCase();

		return s;

	}

	static removeAcentos(s) {
		if (UString.isEmpty(s)) return s;
		s = UString.replace(s, "ç", "c");
		return s;
	}

	static beforeFirst(s, substring) {
		const i = s.indexOf(substring);
		if (UCommons.eqeqeq(i, -1)) return null;
		return s.substring(0, i);
	}
	static afterFirst(s, substring) {
		let i = s.indexOf(substring);
		if (UCommons.eqeqeq(i, -1)) return null;
		i += substring.length;
		return s.substring(i);
	}

	static highlights(original, substring) {

		const list = [];

		if (UString.isEmpty(original)) {
			return list;
		}

		if (UString.notEmpty(substring)) {
			let s = UString.preparaParaBusca(original);
			const s2 = UString.preparaParaBusca(substring);
			while (!UString.isEmpty(s) && s.contains(s2)) {
				let before = UString.beforeFirst(s, s2);
				if (!UString.isEmpty(before)) {
					before = original.substring(0, before.length);
					list.push({text: before, highlight: false});
					original = original.substring(before.length);
				}
				list.push({text: original.substring(0, substring.length), highlight: true});
				s = UString.afterFirst(s, s2);
				original = original.substring(substring.length);
			}
		}

		list.push({text: original, highlight: false});

		return list;
	}

	static mantemSomenteNumeros(s) {
		return UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.numeros);
	}

	static mantemSomenteOsSeguintesCaracteres(s, list) {

		if (UString.isEmpty(s)) {
			return "";
		}

		let s2 = "";

		while (!UString.isEmpty(s)) {
			const x = s.substring(0, 1);
			s = s.substring(1);
			if (list.contains(x)) {
				s2 += x;
			}
		}

		return s2;

	}

	static format00(numero, casas) {

		if (UCommons.isEmpty(casas)) {
			casas = 2;
		}

		let s = "" + numero;

		while (s.length < casas) {
			s = '0' + s;
		}
		return s;
	}
	static afterLast(s, substring) {
		const i = s.lastIndexOf(substring);
		if (UCommons.eqeqeq(i, -1)) {
			return null;
		}
		return s.substring(i + substring.length);
	}
	static beforeLast(s, substring) {
		const i = s.lastIndexOf(substring);
		if (UCommons.eqeqeq(i, -1)) {
			return null;
		}
		return s.substring(0, i);
	}
	static afterFirstObrig(s, substring) {
		const ss = UString.afterFirst(s, substring);
		if (UString.isEmpty(ss)) {
			throw new Error("afterFirst('" + s + "', '" + substring + ") is null");
		}
		return ss;
	}

	static length(s) {
		return UString.isEmpty(s) ? 0 : s.length;
	}

	static lengthIs(s, i) {
		return UCommons.eqeqeq(UString.length(s), i);
	}

	static right(s, count) {
		const len = UString.length(s);
		if (len < count) {
			return s;
		}
		return s.substring(len - count);
	}

	static ocorrencias(s, substring) {
		let i = s.length;
		s = s.replace(substring, "");
		i -= s.length;
		i = i / substring.length;
		return i;
	}

	static ignoreRight(s, count) {
		if (UString.isEmpty(s)) {
			return s;
		}
		const len = UString.length(s);
		if (len < count) {
			return "";
		}
		return s.substring(0, len - count);
	}

	static ltrim(s) {

		if (UString.isEmpty(s)) {
			return s;
		}

		while (s.startsWith(" ") || s.startsWith("\t")) {
			s = s.substring(1);
		}

		return s;
	}
	static repete(s, vezes) {
		let result = "";
		while (vezes > 0) {
			result += s;
			vezes--;
		}
		return result;
	}
	static maxLength(s, max) {
		if (UString.length(s) > max) {
			return s.substring(0, max);
		} else {
			return s;
		}
	}
	static quebraAoMeio(s) {

		s = UString.trimPlus(s);

		if (UString.isEmpty(s)) {
			return ["", ""];
		}

		if (!s.contains(" ")) {
			return [s, ""];
		}

		const box = new StringBox(s);

		Caracteres.letrasMaiusculas.forEach(o => box.value = box.value.replace(o, o+"^^"));
		Caracteres.letrasMinusculas.forEach(o => box.value = box.value.replace(o, o+"^"));

		box.value = box.value.replace("i^", "i");
		box.value = box.value.replace("l^", "l");
		box.value = box.value.replace("f^", "f");
		box.value = box.value.replace("t^", "t");
		box.value = box.value.replace("I^^", "I");

		s = box.value;

		const len = UString.length(s) / 2;

		let left = s.substring(0, len);
		let right = s.substring(len);

		left = UString.replaceWhile(left, "^", "");
		right = UString.replaceWhile(right, "^", "");
		s = UString.replaceWhile(s, "^", "");

		if (left.endsWith(" ") || right.startsWith(" ")) {
			return [left.trim(), right.trim()];
		}

		if (!left.contains(" ") || !right.contains(" ")) {
			left = UString.beforeFirst(s, " ");
			right = UString.afterFirst(s, " ");
			return [left, right];
		}

		left = UString.beforeLast(left, " ");
		right = UString.afterFirst(s, left + " ");

		return [left, right];

	}

	static compare(a, b) {
		if (UString.equals(a, b)) {
			return 0;
		}
		if (UString.isEmpty(a)) {
			return -1;
		}
		if (UString.isEmpty(b)) {
			return 1;
		}
		a = UString.removeAcentos(a);
		b = UString.removeAcentos(b);
		if (UString.equals(a, b)) {
			return 0;
		}
		if (UString.equalsIgnoreCase(a, b)) {
			while (UString.notEmpty(a) && UString.notEmpty(b)) {
				const aa = UString.right(a, 1);
				const bb = UString.right(b, 1);
				a = a.substring(1);
				b = b.substring(1);
				if (UString.equals(aa, bb)) {
					continue;
				}
				if (UConstantes.letrasMinusculas.contains(a)) {
					return -1;
				} else {
					return 1;
				}
			}
			return 0;
		}

		a = a.toLowerCase();
		b = b.toLowerCase();

		if (a.startsWith(b)) {
			return 1;
		}
		if (b.startsWith(a)) {
			return -1;
		}
		while (UString.notEmpty(a) && UString.notEmpty(b)) {
			const aa = a.charCodeAt(0);
			const bb = b.charCodeAt(0);
			if (aa < bb) {
				return -1;
			} else if (aa > bb) {
				return 1;
			}
			a = a.substring(1);
			b = b.substring(1);
		}
		return 0;
	}
	static split(s, separator) {
		if (UString.isEmpty(s)) {
			return [];
		} else {
			return UCommons.cast(s.split(separator));
		}
	}
	static toString(o) {

		const start = Date.now();

		try {
			if (UCommons.isEmpty(o)) {
				return "";
			}

			if (UString.equals(UCommons.getClassName(o), "Map")) {
				const map = o;
				return UString.toString(Object.fromEntries(map));
			}

			if (UString.isObject(o)) {

				const stringify = UNative.getAtributo(o, "stringify");

				if (UCommons.notEmpty(stringify)) {
					return stringify();
				}

				const cache = [];
				const s = JSON.stringify(o, (key, value) => {
					if (UCommons.eqeqeq(value, null)) {
						return null;
					} else if (UString.isObject(value)) {
						if (cache.indexOf(value) > -1) {
							return null;
						} else {
							cache.push(value);
						}
					}
					return value;
				});
				cache.clear();
				return s;
			} else if (UString.isString(o)) {
				return o;
			} else {
				return "" + o;
			}

		} finally {
			const end = Date.now();
			const dif = end - start;
			if (dif > 100) {
				Console.log("UString.toString " + dif, o);
			}
		}

	}

	static isObject(o) {
		return UString.equals(typeof(o), "object");
	}
	static isString(o) {
		return UString.equals(typeof(o), "string");
	}

	static like(a, b) {
		if (UString.isEmpty(b)) {
			return true;
		} else if (UString.isEmpty(a)) {
			return false;
		}
		a = UString.removeAcentos(a).toLowerCase();
		b = UString.removeAcentos(b).toLowerCase();
		return UString.contains(a, b);
	}

	static primeiraMaiuscula(s) {
		s = UString.trimPlus(s);
		if (UString.isEmpty(s)) {
			return null;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	static equivalente(a, b) {
		return UString.equals(UString.preparaParaBusca(a), UString.preparaParaBusca(b));
	}

}
