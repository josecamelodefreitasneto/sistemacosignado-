/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class AtendenteAbstract extends EntityFront {

	original;
	email = null;
	nome = null;
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
		if (UCommons.notEmpty(this.email)) {
			s += "\"email\":\""+this.email+"\",";
		}
		if (UCommons.notEmpty(this.nome)) {
			s += "\"nome\":\""+this.nome+"\",";
		}
		if (this.getObservacoes() !== null) {
			s += "\"observacoes\":[";
			s += this.getObservacoes().reduce((ss, o) => ss + o.asString() + ",", "");
			s += "],";
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
	getEmail() {
		return this.email;
	}
	setEmail(value) {
		this.email = value;
	}
	getNome() {
		return this.nome;
	}
	setNome(value) {
		this.nome = value;
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
