/* front-constructor */
import BindingBooleanFiltro from '../../app/campos/support/BindingBooleanFiltro';
import BindingCpf from '../../app/campos/support/BindingCpf';
import BindingData from '../../app/campos/support/BindingData';
import BindingDecimal from '../../app/campos/support/BindingDecimal';
import BindingEmail from '../../app/campos/support/BindingEmail';
import BindingInteger from '../../app/campos/support/BindingInteger';
import BindingList from '../../app/campos/support/BindingList';
import BindingMoney from '../../app/campos/support/BindingMoney';
import BindingNomeProprio from '../../app/campos/support/BindingNomeProprio';
import BindingString from '../../app/campos/support/BindingString';
import BindingTelefone from '../../app/campos/support/BindingTelefone';

export default class Consulta {

	list = [];
	busca;
	nomeEntidade;

	init() {
		this.busca = this.newString("busca", 200);
		this.init2();
	}

	getBusca() {
		return this.busca;
	}

	touch() {
		this.list.forEach(campo => campo.setVirgin(false));
	}

	getErros() {
		return this.list.filter(campo => !campo.isValid() && !campo.isVirgin());
	}
	add(campo) {
		this.list.add(campo);
		return campo;
	}
	newString(nomeP, sizeP) {
		return this.add(new BindingString(nomeP, sizeP));
	}
	newCpf(nomeP) {
		return this.add(new BindingCpf(nomeP));
	}
	newEmail(nomeP) {
		return this.add(new BindingEmail(nomeP));
	}
	newTelefone(nomeP) {
		return this.add(new BindingTelefone(nomeP));
	}
	newNomeProprio(nomeP) {
		return this.add(new BindingNomeProprio(nomeP));
	}
	newListFixed(nomeP, itensP) {
		return this.add(new BindingList(nomeP).setItens(itensP));
	}
	newFk(nomeP) {
		return this.add(new BindingList(nomeP));
	}
	newStatus(nomeP) {
		return this.add(new BindingList(nomeP));
	}
	newBoolean(nomeP) {
		return this.add(new BindingBooleanFiltro(nomeP));
	}
	newImagem(nomeP) {
		return this.add(new BindingString(nomeP, 100));
	}
	newData(nomeP) {
		return this.add(new BindingData(nomeP));
	}
	newInteger(nomeP) {
		return this.add(new BindingInteger(nomeP, 99999));
	}
	newDecimal(nomeP, inteiros, decimais, nullIfZeroWhenDisabled) {
		return this.add(new BindingDecimal(nomeP, inteiros, decimais).setNullIfZeroWhenDisabled(nullIfZeroWhenDisabled));
	}
	newMoney(nomeP, inteiros, nullIfZeroWhenDisabled) {
		return this.add(new BindingMoney(nomeP, inteiros).setNullIfZeroWhenDisabled(nullIfZeroWhenDisabled));
	}

}
