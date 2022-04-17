/* react-web */
import AbstractStorage from './AbstractStorage';

export default class SessionStorage extends AbstractStorage {

	constructor() {
		super(sessionStorage);
	}

}
