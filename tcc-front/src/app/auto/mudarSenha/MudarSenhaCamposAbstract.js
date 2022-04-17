/* front-constructor */
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import MudarSenhaConsulta from '../../cruds/mudarSenha/MudarSenhaConsulta';
import MudarSenhaUtils from '../../cruds/mudarSenha/MudarSenhaUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class MudarSenhaCamposAbstract extends EntityCampos {

	senhaAtual;
	novaSenha;
	confirmarSenha;
	original;
	to;
	getEntidade() {
		return "MudarSenha";
	}
	getEntidadePath() {
		return "mudar-senha";
	}
	initImpl() {
		this.senhaAtual = this.newSenha("Senha Atual", 50, true, "Geral");
		this.novaSenha = this.newSenha("Nova Senha", 50, true, "Geral");
		this.confirmarSenha = this.newSenha("Confirmar Senha", 50, true, "Geral");
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
		this.to = MudarSenhaUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.confirmarSenha.set(this.to.getConfirmarSenha());
		this.novaSenha.set(this.to.getNovaSenha());
		this.senhaAtual.set(this.to.getSenhaAtual());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.confirmarSenha.setStartValue(this.confirmarSenha.get());
		this.novaSenha.setStartValue(this.novaSenha.get());
		this.senhaAtual.setStartValue(this.senhaAtual.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.confirmarSenha.setDisabled(readOnly);
		this.novaSenha.setDisabled(readOnly);
		this.senhaAtual.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setConfirmarSenha(this.confirmarSenha.get());
		this.to.setNovaSenha(this.novaSenha.get());
		this.to.setSenhaAtual(this.senhaAtual.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = MudarSenhaUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = MudarSenhaConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				MudarSenhaUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getConfirmarSenha();
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !MudarSenhaUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return MudarSenhaUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("MudarSenhaCampos", this);
	}
}
