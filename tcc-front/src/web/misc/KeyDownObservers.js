/* react-web */
import EventKey from '../../app/campos/support/EventKey';
import UCommons from '../../app/misc/utils/UCommons';
import UString from '../../app/misc/utils/UString';

document.addEventListener("keydown", event => KeyDownObservers.exec(event));
export default class KeyDownObservers {

	static list = [];

	static add(observer) {
		if (!KeyDownObservers.list.contains(observer)) {
			KeyDownObservers.list.add(observer);
		}
	}

	static removeByClass(o) {
		const className = UCommons.getClassName(o);
		KeyDownObservers.list.filter(item => UString.equals(UCommons.getClassName(item), className)).forEach(item => KeyDownObservers.remove(item));
	}

	static remove(observer) {
		KeyDownObservers.list.removeObject(observer);
	}

	static exec(event) {
		const e = new EventKey(event);
		KeyDownObservers.list.forEach(o => o.onKeyDown(e));
	}

}
