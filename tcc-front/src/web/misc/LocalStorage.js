/* react-web */
import AbstractStorage from './AbstractStorage';

export default class LocalStorage extends AbstractStorage {

	constructor() {
		super(localStorage);
	}

}
