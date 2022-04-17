/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoAbstract extends EntityFront {

	original;
	arquivo = null;
	arquivoDeErros = null;
	atualizarRegistrosExistentes = false;
	delimitador = ";";
	entidade = null;
	erros = null;
	errosHouveMudancas = false;
	processadosComErro = 0;
	processadosComSucesso = 0;
	status = null;
	totalDeLinhas = 0;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getDelimitador();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.arquivo)) {
			s += "\"arquivo\":"+this.arquivo.asString()+",";
		}
		if (UCommons.notEmpty(this.atualizarRegistrosExistentes)) {
			s += "\"atualizarRegistrosExistentes\":\""+this.atualizarRegistrosExistentes+"\",";
		}
		if (UCommons.notEmpty(this.delimitador)) {
			s += "\"delimitador\":\""+this.delimitador+"\",";
		}
		if (UCommons.notEmpty(this.entidade)) {
			s += "\"entidade\":"+this.entidade.id+",";
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
	getArquivo() {
		return this.arquivo;
	}
	setArquivo(value) {
		this.arquivo = value;
	}
	getArquivoDeErros() {
		return this.arquivoDeErros;
	}
	setArquivoDeErros(value) {
		this.arquivoDeErros = value;
	}
	getAtualizarRegistrosExistentes() {
		return this.atualizarRegistrosExistentes;
	}
	setAtualizarRegistrosExistentes(value) {
		this.atualizarRegistrosExistentes = value;
	}
	getDelimitador() {
		return this.delimitador;
	}
	setDelimitador(value) {
		this.delimitador = value;
	}
	getEntidade() {
		return this.entidade;
	}
	setEntidade(value) {
		this.entidade = value;
	}
	getErros() {
		return this.erros;
	}
	setErros(value) {
		this.erros = value;
	}
	getErrosHouveMudancas() {
		return this.errosHouveMudancas;
	}
	setErrosHouveMudancas(value) {
		this.errosHouveMudancas = value;
	}
	getProcessadosComErro() {
		return this.processadosComErro;
	}
	setProcessadosComErro(value) {
		this.processadosComErro = value;
	}
	getProcessadosComSucesso() {
		return this.processadosComSucesso;
	}
	setProcessadosComSucesso(value) {
		this.processadosComSucesso = value;
	}
	getStatus() {
		return this.status;
	}
	setStatus(value) {
		this.status = value;
	}
	getTotalDeLinhas() {
		return this.totalDeLinhas;
	}
	setTotalDeLinhas(value) {
		this.totalDeLinhas = value;
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
