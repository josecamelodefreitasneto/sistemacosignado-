/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class CepAbstract extends EntityFront {

	original;
	bairro = null;
	cidade = null;
	logradouro = null;
	numero = null;
	uf = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getNumero();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.bairro)) {
			s += "\"bairro\":\""+this.bairro+"\",";
		}
		if (UCommons.notEmpty(this.cidade)) {
			s += "\"cidade\":\""+this.cidade+"\",";
		}
		if (UCommons.notEmpty(this.logradouro)) {
			s += "\"logradouro\":\""+this.logradouro+"\",";
		}
		if (UCommons.notEmpty(this.numero)) {
			s += "\"numero\":\""+this.numero+"\",";
		}
		if (UCommons.notEmpty(this.uf)) {
			s += "\"uf\":\""+this.uf+"\",";
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
	getBairro() {
		return this.bairro;
	}
	setBairro(value) {
		this.bairro = value;
	}
	getCidade() {
		return this.cidade;
	}
	setCidade(value) {
		this.cidade = value;
	}
	getLogradouro() {
		return this.logradouro;
	}
	setLogradouro(value) {
		this.logradouro = value;
	}
	getNumero() {
		return this.numero;
	}
	setNumero(value) {
		this.numero = value;
	}
	getUf() {
		return this.uf;
	}
	setUf(value) {
		this.uf = value;
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
