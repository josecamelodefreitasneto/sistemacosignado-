/* react */
import BindingList from './BindingList';
import UInteger from '../../misc/utils/UInteger';

export default class BindingFk extends BindingList {
	service;
	chamandoServico = false;

	constructor(label, service) {
		super(label);
		this.service = service;
	}

	chamada = 0;

	notify(o) {
		super.notify(o);
		if (this.isDisabled()) {
			return;
		}
		this.chamandoServico = true;
		const cham = ++this.chamada;
		setTimeout(() => {
			if (UInteger.equals(this.chamada, cham)) {
				this.service.exec(this.input.get(), res => {
					const list = res.body;
					this.setItens(list);
					this.chamandoServico = false;
					this.notifyObservers();
				});
			}
		}, 250);
	}

	getSugestoes() {
		if (this.chamandoServico) {
			return [];
		} else {
			return this.getItens().map(o => {
				return {value: o};
			});
		}
	}

	setDisabled(value) {
		super.setDisabled(value);
		return this;
	}

}
