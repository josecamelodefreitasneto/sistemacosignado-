/* react */
import Binding from './Binding';
import BindingString from './BindingString';
import Console from '../../misc/utils/Console';
import StringBox from '../../misc/utils/StringBox';
import UCommons from '../../misc/utils/UCommons';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class BindingList extends Binding {

	input;
	limparInput = true;

	itens;

	constructor(label) {
		super(label);
		this.input = new BindingString(label, 250);
		this.input.addObserver(this);
		this.input.controller = this;
	}

	setItens(itensParams) {
		if (UCommons.isEmpty(itensParams)) {
			this.itens = [];
		} else {
			this.itens = itensParams.filter(o => UCommons.notEmpty(o));
			if (UCommons.notEmpty(this.itens)) {
				const itensComIdEmBranco = this.itens.filter(o => UString.isEmpty(o.text) && o.id > 0);
				if (itensComIdEmBranco.length > 0) {
					const box = new StringBox("");
					itensComIdEmBranco.forEach(o => box.add(", " + o.id));
					const s = box.get().substring(1);
					throw new Error("Não se pode adicionar itens com text em branco: " + this.getLabel() + " - ids:" + s);
				}
			}
		}
		return this;
	}

	getItens() {
		return this.itens;
	}

	isEmpty() {
		return UCommons.isEmpty(this.get()) || UCommons.eqeqeq(this.get(), 0);
	}

	getSugestoes() {
		const s = this.input.get();
		const list = [];
		if (UCommons.notEmpty(this.itens)) {
			if (UString.isEmpty(s) || UString.equals(s, this.asString())) {
				this.itens.forEach(obj => list.push({value: obj}));
			} else {
				this.itens.forEach(obj => {
					const highlights = UString.highlights(obj.text, s);
					if (highlights.some(o => o.highlight)) {
						list.push({value: obj, highlights: highlights});
					}
				});
			}
		}
		return list;
	}

	setId(id) {
		return this.set(this.getItens().byId(id));
	}

	setStartValue(o) {
		super.setStartValue(o);
		this.afterSet();
		return this;
	}

	selectSugestion(o) {
		this.set(o.value);
	}

	beforeSet(value) {
		if (UCommons.isEmpty(value)) {
			return null;
		} else {
			value = this.beforeSet2(value);
			return this.getItens().byId(value.id);
		}
	}

	beforeSet2(value) {
		return value;
	}

	afterSet() {
		if (this.isEmpty()) {
			if (this.limparInput) {
				this.input.set(null);
			}
		} else {
			if (!this.getItens().contains(this.get())) {
				Console.log("BindingList", this.getLabel());
				Console.log("BindingList", this.getItens());
				Console.log("BindingList", this.get());
				throw new Error("O Item setado nao consta na lista de itens do campo: " + this.getLabel());
			}
			this.input.set(this.asString());
		}
		this.afterSet2();
	}

	afterSet2() {}

	eq(o) {
		return UCommons.eqeqeq(this.get(), o);
	}

	equals(id) {
		if (this.isEmpty()) {
			return false;
		} else {
			return UInteger.equals(this.getId(), id);
		}
	}

	notify(o) {
		if (!UString.equals(this.input.get(), this.asString())) {
			this.limparInput = UCommons.neq(o, this.input);
			this.set(null);
			this.limparInput = true;
		}
	}

	setUnique(o) {
		this.setItens([o]);
		this.set(o);
	}

	castFromString(s) {
		/* TODO Auto-g=65enerated method stub*/
		return null;
	}

	getId() {
		if (this.isEmpty()) {
			return null;
		}
		let o = this.get();
		if (UCommons.isEmpty(o)) {
			return null;
		} else {
			return o.id;
		}
	}

	getText() {
		if (this.isEmpty()) {
			return null;
		}
		let o = this.get();
		if (UCommons.isEmpty(o)) {
			return null;
		} else {
			return o.text;
		}
	}

	selectFirst() {
		this.set(this.getItens().get(0));
	}

	notNullAndIsEmptyInvalidMessage() {
		if (this.input.isEmpty()) {
			return null;
		} else {
			return "Valor inválido!";
		}
	}

	setDisabled(value) {
		super.setDisabled(value);
		this.input.setDisabled(value);
		return this;
	}

	withPlaceHolder(value) {
		this.setPlaceHolder(value);
		return this;
	}

	setNotNull(value) {
		super.setNotNull(value);
		return this;
	}

	asString() {
		if (this.isEmpty()) {
			return "";
		} else {
			const o = this.get();
			return o.text;
		}
	}

	getSafeId() {
		return this.isEmpty() ? 0 : this.getId();
	}

	maior(value) {
		return this.getSafeId() > value;
	}
	menor(value) {
		return this.getSafeId() < value;
	}
	maiorOuIgual(value) {
		return this.getSafeId() >= value;
	}
	menorOuIgual(value) {
		return this.getSafeId() <= value;
	}
}
