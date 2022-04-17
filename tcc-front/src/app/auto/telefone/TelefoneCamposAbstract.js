/* tcc-java */
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import TelefoneConsulta from '../../cruds/telefone/TelefoneConsulta';
import TelefoneUtils from '../../cruds/telefone/TelefoneUtils';
import UCommons from '../../misc/utils/UCommons';

export default class TelefoneCamposAbstract extends EntityCampos {

	ddd;
	numero;
	nome;
	whatsapp;
	recado;
	original;
	to;
	getEntidade() {
		return "Telefone";
	}
	getEntidadePath() {
		return "telefone";
	}
	initImpl() {
		this.ddd = this.newInteger("DDD", 99, true, "Geral");
		this.numero = this.newString("Número", 9, true, "Geral").setSomenteNumeros(true);
		this.nome = this.newString("Como vai aparecer", 28, true, "Geral").setDisabled(true);
		this.whatsapp = this.newBoolean("WhatsApp", false, "Geral");
		this.recado = this.newBoolean("Recado", false, "Geral");
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
		this.to = TelefoneUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.ddd.set(this.to.getDdd());
		this.nome.set(this.to.getNome());
		this.numero.set(this.to.getNumero());
		this.recado.set(this.to.getRecado());
		this.whatsapp.set(this.to.getWhatsapp());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.ddd.setStartValue(this.ddd.get());
		this.nome.setStartValue(this.nome.get());
		this.numero.setStartValue(this.numero.get());
		this.recado.setStartValue(this.recado.get());
		this.whatsapp.setStartValue(this.whatsapp.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.ddd.setDisabled(readOnly);
		this.numero.setDisabled(readOnly);
		this.recado.setDisabled(readOnly);
		this.whatsapp.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setDdd(this.ddd.get());
		this.to.setNome(this.nome.get());
		this.to.setNumero(this.numero.get());
		this.to.setRecado(this.recado.get());
		this.to.setWhatsapp(this.whatsapp.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = TelefoneUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = TelefoneConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				TelefoneUtils.getInstance().merge(o, itemGrid);
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
		return !TelefoneUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return TelefoneUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("TelefoneCampos", this);
	}
}
