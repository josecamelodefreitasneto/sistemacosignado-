/* react-web */
import UInteger from '../../app/misc/utils/UInteger';
import UString from '../../app/misc/utils/UString';

export default class AbstractStorage {

	storage;

	constructor(storage) {
		this.storage = storage;
	}
	set(key, value) {
		const s = UString.toString(value);
		if (UString.isEmpty(s)) {
			this.remove(key);
		} else {
			this.storage.setItem(key, s);
		}
	}
	get(key) {
		return this.storage.getItem(key);
	}
	remove(key) {
		this.storage.removeItem(key);
	}
	clear() {
		this.storage.clear();
	}
	key(index) {
		return this.storage.key(index);
	}
	getInt(key) {
		return UInteger.toInt(this.get(key));
	}
	getObject(key, classe) {
		return JSON.parse(this.get(key));
	}
}
