/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class UsuarioAbstract extends EntityFront {

	original;
	login = null;
	nome = null;
	senha = null;
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
		if (UCommons.notEmpty(this.login)) {
			s += "\"login\":\""+this.login+"\",";
		}
		if (UCommons.notEmpty(this.nome)) {
			s += "\"nome\":\""+this.nome+"\",";
		}
		if (UCommons.notEmpty(this.senha)) {
			s += "\"senha\":\""+this.senha+"\",";
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
	getLogin() {
		return this.login;
	}
	setLogin(value) {
		this.login = value;
	}
	getNome() {
		return this.nome;
	}
	setNome(value) {
		this.nome = value;
	}
	getSenha() {
		return this.senha;
	}
	setSenha(value) {
		this.senha = value;
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
