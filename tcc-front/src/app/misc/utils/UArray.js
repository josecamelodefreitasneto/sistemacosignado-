/* react */
import UCommons from './UCommons';
import UInteger from './UInteger';
import UNative from './UNative';
import UString from './UString';

export default class UArray {

	static exists(array,func) {
		if (UArray.isEmpty(array)) return false;
		return !UArray.isEmpty(array.filter(o => func(o)));
	}

	static contains(array, value) {
		if (array.indexOf(value) > -1) {
			return true;
		}
		if (UCommons.isEmpty(value)) {
			return array.exists(o => UCommons.isEmpty(o));
		}
		if (UString.isString(value)) {
			const v = value;
			return array.exists(o => UString.equals(v, o));
		}
		return array.exists(o => UCommons.eqeqeq(value, o));
	}

	static size(array) {
		if (UCommons.isEmpty(array)) {
			return 0;
		} else if (!Array.isArray(array)) {
			return 0;
		} else {
			return array.length;
		}
	}

	static equalsSize(a, b) {
		return UInteger.equals(UArray.size(a), UArray.size(b));
	}

	static equalsSizeCast(a, b) {
		return UArray.equalsSize(UCommons.cast(a), UCommons.cast(b));
	}

	static getLast(array) {
		return array.get(array.size()-1);
	}

	static byId(list, id) {
		return list.filter(o => UArray.confirmaId(o, id)).get(0);
	}

	static unique(list, predicate) {
		const itens = list.filter(predicate);
		if (itens.isEmpty()) {
			return null;
		} else if (itens.length > 1) {
			throw new Error("O filtro retornou + de 1 resultado");
		} else {
			return itens.get(0);
		}
	}

	static uniqueObrig(list, predicate) {
		const o = UArray.unique(list, predicate);
		if (UCommons.isEmpty(o)) {
			throw new Error("O filtro não retornou resultados");
		}
		return o;
	}

	static confirmaId(o, id) {
		const x = UNative.getAtributo(o, "id");
		return UInteger.equals(x, id);
	}

	static pushNotEmpty(array, o) {
		if (UCommons.isEmpty(o)) {
			return false;
		} else {
			array.push(o);
			return true;
		}
	}

	static isEmpty(array) {
		return UCommons.eqeqeq(UArray.size(array), 0);
	}

	static notEmpty(array) {
		return !UArray.isEmpty(array);
	}

	static add(array, o, index) {
		if (UCommons.eqeqeq(index, undefined)) {
			array.push(o);
		} else {
			array.splice(index, 0, o);
		}
		return array;
	}

	static addIfNotContains(array, o) {
		if (UArray.contains(array, o)) {
			return false;
		} else {
			return UArray.pushNotEmpty(array, o);
		}
	}

	static sortByKey(array, key, comparator) {
		array.sort((a, b) => {
			let i = UCommons.simpleCompare(a, b);
			if (UCommons.neq(i, null)) {
				return i;
			}
			a = UNative.getAtributo(a, key);
			b = UNative.getAtributo(b, key);
			i = UCommons.simpleCompare(a, b);
			if (i !== null) {
				return i;
			}
			return comparator(a, b);
		});
		return array;
	}

	static sortByIntegerKey(array, key) {
		return UArray.sortByKey(array, key, UInteger.compareCast);
	}

	static sortByIntegerKeyReverse(array, key) {
		return UArray.sortByKey(array, key, UInteger.compareCastReverse);
	}

	static sortByIdReverse(array) {
		return UArray.sortByIntegerKeyReverse(array, "id");
	}

	static copy(array) {
		return array.concat([]);
	}

	static equals(a, b, comparator) {
		if (!UArray.equalsSize(a, b)) {
			return false;
		} else if (UArray.isEmpty(a)) {
			return UArray.isEmpty(b);
		} else if (UArray.isEmpty(b)) {
			return false;
		} else {
			if (UCommons.isEmpty(comparator) || !UString.equals(typeof(comparator), "function") ) {
				comparator = UCommons.equals;
			}
			for (let i = 0; i < a.length; i++) {
				if (!comparator(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	static compareArrays(a, b, comparator) {
		return UArray.equals(a, b, comparator);
	}

	static getSafe(array, index) {
		if (UArray.isEmpty(array)) {
			return null;
		} else if (UArray.size(array) < index+1) {
			return null;
		} else {
			return array.get(index);
		}
	}

	static remove(a, index) {
		const o = a.get(index);
		a.splice(index, 1);
		return o;
	}

	static removeObject(a, o) {
		const index = a.indexOf(o);
		if (index === -1) {
			return false;
		} else {
			a.remove(index);
			return true;
		}
	}

	static clear(a) {
		while (a.length > 0) {
			a.pop();
		}
	}

	static removeLast(a) {
		return a.pop();
	}

	static removeFirst(a) {
		return a.shift();
	}

	static foreach(array, func) {
		if (!UArray.isEmpty(array)) {
			array.forEach(o => func(o));
		}
	}

	static distinct(array, func) {
		const list = [];
		array.forEach(o => list.addIfNotContains(func(o)));
		return list;
	}

	static removeIf(list, predicate) {
		let filter = list.filter(predicate);
		while (!filter.isEmpty()) {
			let o = filter.remove(0);
			list.removeObject(o);
		}
	}

}
