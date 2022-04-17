/* @author Gamarra - francisco.gamarra@gmail.com */
/* front-constructor */
import EntityMock from './EntityMock';
import GetText from '../../app/misc/utils/GetText';
import IdText from '../../app/misc/utils/IdText';
import UCommons from '../../app/misc/utils/UCommons';
import UString from '../../app/misc/utils/UString';

export default class GenericMock extends EntityMock {

	static map = new Map();

	static restart() {
		GenericMock.map.clear();
	}

	static get(entidade) {
		let o = GenericMock.map.get(entidade);
		if (UCommons.isEmpty(o)) {
			o = new GenericMock(entidade);
			GenericMock.map.set(entidade, o);
		}
		return o;
	}

	list = [];

	constructor(entidade) {
		super();
		for (let i = 0; i < 10; i++) {
			this.list.add(new IdText(i+1, entidade + " " + i));
		}
	}

	byId(id) {
		return this.list.byId(id);
	}

	save(obj) {
		let o = obj;
		if (UCommons.isEmpty(o.id)) {
			o.id = this.list.length;
			this.list.push(o);
			return o;
		} else {
			let to = this.byId(o.id);
			to.text = o.text;
			return to;
		}
	}

	remove(obj) {
		let o = obj;
		if (UCommons.notEmpty(o.id)) {
			this.removeId(o.id);
		}
	}

	removeId(id) {
		this.list.removeObject(this.list.byId(id));
	}

	getListGrid(params) {
		return this.list;
	}

	search(params) {
		let p = params;
		return this.list.filter(o => UString.like(GenericMock.getText(o), p.text)).map(o => new IdText(o.id, GenericMock.getText(o)));
	}

	byIdAs(id) {
		let o = this.byId(id);
		if (UCommons.isEmpty(o)) {
			return null;
		} else {
			return new IdText(id, GenericMock.getText(o));
		}
	}

	static getText(o) {
		return GetText.get(o);
	}

}
