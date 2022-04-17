/* react */
import React from 'react';
import Console from '../utils/Console';
import SuperComponent from './SuperComponent';
import UArray from '../utils/UArray';
import UString from '../utils/UString';
import {Fragment} from 'react';

export default class Foreach extends SuperComponent {
	list;
	copy;
	vez = 0;
	keys = [];

	render() {
		if (UArray.isEmpty(this.props.array)) {
			return null;
		} else {
			this.keys.clear();
			this.list = [];
			this.copy = this.props.array.copy();
			this.vez = 0;
			this.monta();
			return <Fragment>{this.list}</Fragment>;
		}
	}

	monta () {
		/*
		* atencao!
		* nao substituir por while e nem colocar ||
		* pois o trace é utilizado como chave
		* */

		if (this.vez === 0) return this.next();
		if (this.vez === 1) return this.next();
		if (this.vez === 2) return this.next();
		if (this.vez === 3) return this.next();
		if (this.vez === 4) return this.next();
		if (this.vez === 5) return this.next();
		if (this.vez === 6) return this.next();
		if (this.vez === 7) return this.next();
		if (this.vez === 8) return this.next();
		if (this.vez === 9) return this.next();
		if (this.vez === 10) return this.next();
		if (this.vez === 11) return this.next();
		if (this.vez === 12) return this.next();
		if (this.vez === 13) return this.next();
		if (this.vez === 14) return this.next();
		if (this.vez === 15) return this.next();
		if (this.vez === 16) return this.next();
		if (this.vez === 17) return this.next();
		if (this.vez === 18) return this.next();
		if (this.vez === 19) return this.next();
		throw new Error("Colocar mais itens");
	}

	next() {

		/*
		* atencao!
		* nao substituir por while e nem colocar ||
		* pois o trace é utilizado como chave de identificacao do objeto
		* */

		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;
		if (this.finish()) return true;

		this.vez++;
		return this.monta();

	}

	finish() {
		const o = this.copy.remove(0);
		let key = this.props.getKey(o);
		if (UString.isEmpty(key)) {
			key = UString.toString(o);
		}

		if (this.keys.contains(key)) {
			Console.log("Foreach", "KEY REPETIDA >>>> " + key);
		} else {
			this.keys.push(key);
		}

		this.list.add(<Fragment key={key}>{this.props.func(o)}</Fragment>);
		return this.copy.isEmpty();
	}

}

Foreach.defaultProps = {
	...SuperComponent.defaultProps,
	getKey: o => UString.toString(o)
}
