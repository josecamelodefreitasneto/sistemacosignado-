/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class EsqueciSenhaAbstract extends EntityFront {

	original;
	confirmarSenha = null;
	novaSenha = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getConfirmarSenha();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.confirmarSenha)) {
			s += "\"confirmarSenha\":\""+this.confirmarSenha+"\",";
		}
		if (UCommons.notEmpty(this.novaSenha)) {
			s += "\"novaSenha\":\""+this.novaSenha+"\",";
		}
		s += "\"excluido\":"+this.excluido+",";
		s += "}";
		return s;
	}
	getOriginal() {
		return this.original;
	}
	setOriginal(value) {
		this.original = value;
	}
	getConfirmarSenha() {
		return this.confirmarSenha;
	}
	setConfirmarSenha(value) {
		this.confirmarSenha = value;
	}
	getNovaSenha() {
		return this.novaSenha;
	}
	setNovaSenha(value) {
		this.novaSenha = value;
	}
	getExcluido() {
		return this.excluido;
	}
	setExcluido(value) {
		this.excluido = value;
	}
	getRegistroBloqueado() {
		return this.registroBloqueado;
	}
	setRegistroBloqueado(value) {
		this.registroBloqueado = value;
	}
}
