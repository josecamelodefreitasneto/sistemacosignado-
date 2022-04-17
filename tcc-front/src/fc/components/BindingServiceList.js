/* front-constructor */
import BindingVinculado from '../../app/campos/support/BindingVinculado';
import Post from '../../projeto/Post';
import UArray from '../../app/misc/utils/UArray';
import UCommons from '../../app/misc/utils/UCommons';
import UString from '../../app/misc/utils/UString';

export default class BindingServiceList extends BindingVinculado {

	static count = 0;
	itens;
	mergeFunction;
	carregando = false;
	uri;
	carragarCallBack;
	mensagemErro;

	constructor(titleP,mergeFunctionP, carragarCallBackP, uriP) {
		super();
		this.setLabel(titleP);
		this.carragarCallBack = carragarCallBackP;
		this.mergeFunction = mergeFunctionP;
		this.uri = uriP;
	}
	addItens(list) {
		this.itens = [];
		if (UArray.notEmpty(list)) {
			list.forEach(o => this.itens.add(o));
		}
		if (!this.carregando) {
			this.notifyObservers();
		}
	}
	add(o) {
		if (!this.carregado()) {
			this.itens = [];
		}
		if (UCommons.isEmpty(o.getId())) {
			BindingServiceList.count--;
			o.setId(BindingServiceList.count);
			this.itens.add(o);
		} else {
			const obj = this.itens.byId(o.getId());
			if (UCommons.isEmpty(obj)) {
				this.itens.add(o);
			} else {
				this.mergeFunction(o, obj);
			}
		}
		this.notifyObservers();
	}
	remove(o) {
		o = this.itens.byId(o.getId());
		this.itens.removeObject(o);
		this.notifyObservers();
	}
	clearItens() {
		this.itens = null;
		this.mensagemErro = null;
		this.notifyObservers();
	}
	clearItens2() {
		this.itens = [];
	}
	start() {
		this.clearItens2();
		this.notifyObservers();
	}
	getItens() {
		return this.itens;
	}
	carregado() {
		return UCommons.notEmpty(this.itens);
	}
	carregar() {
		if (this.carregado() || this.carregando || UString.notEmpty(this.mensagemErro)) return;
		if (this.preparadoParaBusca()) {
			this.carregando = true;
			new Post(this.uri, this.getParametros(), res => {
				this.carragarCallBack(res.body);
				this.carregando = false;
				this.notifyObservers();
			}).setOnError(message => {
				this.mensagemErro = message;
				this.carregando = false;
				this.notifyObservers();
			}).run();
		}
	}

	getMensagemErro() {
		return this.mensagemErro;
	}
}
