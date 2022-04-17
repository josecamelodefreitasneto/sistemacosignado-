/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ClienteSimulacaoAbstract extends EntityFront {

	original;
	cliente = null;
	contratado = false;
	contratar = false;
	indice = null;
	parcelas = null;
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
	getContratado() {
		return this.contratado;
	}
	setContratado(value) {
		this.contratado = value;
	}
	getContratar() {
		return this.contratar;
	}
	setContratar(value) {
		this.contratar = value;
	}
	getIndice() {
		return this.indice;
	}
	setIndice(value) {
		this.indice = value;
	}
	getParcelas() {
		return this.parcelas;
	}
	setParcelas(value) {
		this.parcelas = value;
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
