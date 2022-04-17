/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ClienteRubricaAbstract extends EntityFront {

	original;
	cliente = null;
	rubrica = null;
	tipo = null;
	valor = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return UString.toString(this.getCliente());
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.cliente)) {
			s += "\"cliente\":"+this.cliente.id+",";
		}
		if (UCommons.notEmpty(this.rubrica)) {
			s += "\"rubrica\":"+this.rubrica.id+",";
		}
		if (UCommons.notEmpty(this.valor)) {
			s += "\"valor\":\""+this.valor+"\",";
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
	getCliente() {
		return this.cliente;
	}
	setCliente(value) {
		this.cliente = value;
	}
	getRubrica() {
		return this.rubrica;
	}
	setRubrica(value) {
		this.rubrica = value;
	}
	getTipo() {
		return this.tipo;
	}
	setTipo(value) {
		this.tipo = value;
	}
	getValor() {
		return this.valor;
	}
	setValor(value) {
		this.valor = value;
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
