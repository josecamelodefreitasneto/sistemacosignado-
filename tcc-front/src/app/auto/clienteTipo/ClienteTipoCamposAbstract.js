/* tcc-java */
import Binding from '../../campos/support/Binding';
import ClienteTipoConsulta from '../../cruds/clienteTipo/ClienteTipoConsulta';
import ClienteTipoUtils from '../../cruds/clienteTipo/ClienteTipoUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteTipoCamposAbstract extends EntityCampos {

	nome;
	original;
	to;
	getEntidade() {
		return "ClienteTipo";
	}
	getEntidadePath() {
		return "cliente-tipo";
	}
	initImpl() {
		this.nome = this.newString("Nome", 11, true, "Geral");
		this.init2();
		this.construido = true;
	}
	setCampos(o) {
		if (UCommons.isEmpty(o)) {
			throw new Error("o === null");
		}
		this.checkInstance();
		Binding.notificacoesDesligadasInc();
		this.disabledObservers = true;
		this.original = o;
		this.to = ClienteTipoUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.nome.set(this.to.getNome());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.nome.setStartValue(this.nome.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.nome.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setNome(this.nome.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ClienteTipoUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ClienteTipoConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ClienteTipoUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getNome();
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ClienteTipoUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ClienteTipoUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteTipoCampos", this);
	}
}
