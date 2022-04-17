/* react */
import Box from './Box';
import UCommons from './UCommons';
import UString from './UString';

export default class UMap {

	static reduce(map, func, initial) {
		const box = new Box(initial);
		map.forEach((v, k) => box.set(func(k, v, box.get())));
		return box.get();
	}

	static asString(map, separador) {
		if (map.size === 0) {
			return "";
		} else {
			return UMap.reduce(map, (k, v, atual) => atual + separador + k + "="+v, "").substring(separador.length);
		}
	}

	static containsValue(map, value) {
		if (map.size === 0) {
			return false;
		} else {
			let box = new Box(false);
			if (UString.isString(value)) {
				let ss = value;
				map.forEach((v,k) => {
					let s = v;
					if (UString.equals(s, ss)) {
						box.set(true);
					}
				});
			} else {
				map.forEach((v,k) => {
					if (UCommons.equals(k, value)) {
						box.set(true);
					}
				});
			}
			return box.get();
		}
	}
}
