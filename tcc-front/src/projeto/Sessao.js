/* react */
import UCommons from '../app/misc/utils/UCommons';

export default class Sessao {

	static map = new Map();

	static clear() {
		Sessao.map.clear();
	}

	static set(key, value) {
		Sessao.map.set(key, value);
	}

	static get(key) {
		return Sessao.map.get(key);
	}

	static checkInstance(key, value) {
		if (UCommons.neq(Sessao.get(key), value)) {
			throw new Error("Utilizando instancia obsoleta: " + key);
		}
	}

	static createInstance(key, creator) {
		let o = Sessao.get(key);
		if (UCommons.isEmpty(o)) {
			o = creator();
			Sessao.set(key, o);
		}
		return o;
	}

	static getInstance(key, creator, init) {
		let o = Sessao.get(key);
		if (UCommons.isEmpty(o)) {
			o = Sessao.createInstance(key, creator);
			init(o);
		}
		return o;
	}

}
