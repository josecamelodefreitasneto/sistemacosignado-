/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class TelefoneAbstract extends EntityFront {

	original;
	ddd = 61;
	nome = null;
	numero = null;
	recado = null;
	whatsapp = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getNome();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.ddd)) {
			s += "\"ddd\":"+this.ddd+",";
		}
		if (UCommons.notEmpty(this.numero)) {
			s += "\"numero\":\""+this.numero+"\",";
		}
		if (UCommons.notEmpty(this.recado)) {
			s += "\"recado\":\""+this.recado+"\",";
		}
		if (UCommons.notEmpty(this.whatsapp)) {
			s += "\"whatsapp\":\""+this.whatsapp+"\",";
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
	getDdd() {
		return this.ddd;
	}
	setDdd(value) {
		this.ddd = value;
	}
	getNome() {
		return this.nome;
	}
	setNome(value) {
		this.nome = value;
	}
	getNumero() {
		return this.numero;
	}
	setNumero(value) {
		this.numero = value;
	}
	getRecado() {
		return this.recado;
	}
	setRecado(value) {
		this.recado = value;
	}
	getWhatsapp() {
		return this.whatsapp;
	}
	setWhatsapp(value) {
		this.whatsapp = value;
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
