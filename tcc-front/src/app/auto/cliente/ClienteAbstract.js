/* tcc-java */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteAbstract extends EntityFront {

	original;
	agencia = null;
	atendenteResponsavel = null;
	bairro = null;
	banco = null;
	calcular = false;
	cep = null;
	cidade = null;
	complemento = null;
	cpf = null;
	dataDeNascimento = null;
	dia = null;
	email = null;
	logradouro = null;
	margem = null;
	matricula = null;
	nome = null;
	numeroDaConta = null;
	orgao = null;
	rendaBruta = null;
	rendaLiquida = null;
	rubricas = null;
	rubricasHouveMudancas = false;
	simulacoes = null;
	simulacoesHouveMudancas = false;
	status = null;
	telefonePrincipal = null;
	telefoneSecundario = null;
	tipo = null;
	tipoDeSimulacao = null;
	uf = null;
	valorDeSimulacao = null;
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
		if (UCommons.notEmpty(this.agencia)) {
			s += "\"agencia\":\""+this.agencia+"\",";
		}
		if (UCommons.notEmpty(this.atendenteResponsavel)) {
			s += "\"atendenteResponsavel\":"+this.atendenteResponsavel.id+",";
		}
		if (UCommons.notEmpty(this.banco)) {
			s += "\"banco\":"+this.banco.id+",";
		}
		if (UCommons.notEmpty(this.cep)) {
			s += "\"cep\":"+this.cep.id+",";
		}
		if (UCommons.notEmpty(this.complemento)) {
			s += "\"complemento\":\""+this.complemento+"\",";
		}
		if (UCommons.notEmpty(this.cpf)) {
			s += "\"cpf\":\""+this.cpf+"\",";
		}
		if (UCommons.notEmpty(this.dataDeNascimento)) {
			s += "\"dataDeNascimento\":\""+this.dataDeNascimento+"\",";
		}
		if (UCommons.notEmpty(this.dia)) {
			s += "\"dia\":"+this.dia.id+",";
		}
		if (UCommons.notEmpty(this.email)) {
			s += "\"email\":\""+this.email+"\",";
		}
		if (UCommons.notEmpty(this.matricula)) {
			s += "\"matricula\":\""+this.matricula+"\",";
		}
		if (UCommons.notEmpty(this.nome)) {
			s += "\"nome\":\""+this.nome+"\",";
		}
		if (UCommons.notEmpty(this.numeroDaConta)) {
			s += "\"numeroDaConta\":\""+this.numeroDaConta+"\",";
		}
		if (UCommons.notEmpty(this.orgao)) {
			s += "\"orgao\":"+this.orgao.id+",";
		}
		if (UCommons.notEmpty(this.telefonePrincipal)) {
			s += "\"telefonePrincipal\":"+this.telefonePrincipal.asString()+",";
		}
		if (UCommons.notEmpty(this.telefoneSecundario)) {
			s += "\"telefoneSecundario\":"+this.telefoneSecundario.asString()+",";
		}
		if (UCommons.notEmpty(this.tipo)) {
			s += "\"tipo\":"+this.tipo.id+",";
		}
		if (UCommons.notEmpty(this.tipoDeSimulacao)) {
			s += "\"tipoDeSimulacao\":"+this.tipoDeSimulacao.id+",";
		}
		if (UCommons.notEmpty(this.valorDeSimulacao)) {
			s += "\"valorDeSimulacao\":\""+this.valorDeSimulacao+"\",";
		}
		if (this.rubricas !== null) {
			s += "\"rubricas\":[";
			s += this.rubricas.reduce((ss, o) => ss + o.asString() + ",", "");
			s += "],";
		}
		if (this.simulacoes !== null) {
			s += "\"simulacoes\":[";
			s += this.simulacoes.reduce((ss, o) => ss + o.asString() + ",", "");
			s += "],";
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
	getAgencia() {
		return this.agencia;
	}
	setAgencia(value) {
		this.agencia = value;
	}
	getAtendenteResponsavel() {
		return this.atendenteResponsavel;
	}
	setAtendenteResponsavel(value) {
		this.atendenteResponsavel = value;
	}
	getBairro() {
		return this.bairro;
	}
	setBairro(value) {
		this.bairro = value;
	}
	getBanco() {
		return this.banco;
	}
	setBanco(value) {
		this.banco = value;
	}
	getCalcular() {
		return this.calcular;
	}
	setCalcular(value) {
		this.calcular = value;
	}
	getCep() {
		return this.cep;
	}
	setCep(value) {
		this.cep = value;
	}
	getCidade() {
		return this.cidade;
	}
	setCidade(value) {
		this.cidade = value;
	}
	getComplemento() {
		return this.complemento;
	}
	setComplemento(value) {
		this.complemento = value;
	}
	getCpf() {
		return this.cpf;
	}
	setCpf(value) {
		this.cpf = value;
	}
	getDataDeNascimento() {
		return this.dataDeNascimento;
	}
	setDataDeNascimento(value) {
		this.dataDeNascimento = value;
	}
	getDia() {
		return this.dia;
	}
	setDia(value) {
		this.dia = value;
	}
	getEmail() {
		return this.email;
	}
	setEmail(value) {
		this.email = value;
	}
	getLogradouro() {
		return this.logradouro;
	}
	setLogradouro(value) {
		this.logradouro = value;
	}
	getMargem() {
		return this.margem;
	}
	setMargem(value) {
		this.margem = value;
	}
	getMatricula() {
		return this.matricula;
	}
	setMatricula(value) {
		this.matricula = value;
	}
	getNome() {
		return this.nome;
	}
	setNome(value) {
		this.nome = value;
	}
	getNumeroDaConta() {
		return this.numeroDaConta;
	}
	setNumeroDaConta(value) {
		this.numeroDaConta = value;
	}
	getOrgao() {
		return this.orgao;
	}
	setOrgao(value) {
		this.orgao = value;
	}
	getRendaBruta() {
		return this.rendaBruta;
	}
	setRendaBruta(value) {
		this.rendaBruta = value;
	}
	getRendaLiquida() {
		return this.rendaLiquida;
	}
	setRendaLiquida(value) {
		this.rendaLiquida = value;
	}
	getRubricas() {
		return this.rubricas;
	}
	setRubricas(value) {
		this.rubricas = value;
	}
	getRubricasHouveMudancas() {
		return this.rubricasHouveMudancas;
	}
	setRubricasHouveMudancas(value) {
		this.rubricasHouveMudancas = value;
	}
	getSimulacoes() {
		return this.simulacoes;
	}
	setSimulacoes(value) {
		this.simulacoes = value;
	}
	getSimulacoesHouveMudancas() {
		return this.simulacoesHouveMudancas;
	}
	setSimulacoesHouveMudancas(value) {
		this.simulacoesHouveMudancas = value;
	}
	getStatus() {
		return this.status;
	}
	setStatus(value) {
		this.status = value;
	}
	getTelefonePrincipal() {
		return this.telefonePrincipal;
	}
	setTelefonePrincipal(value) {
		this.telefonePrincipal = value;
	}
	getTelefoneSecundario() {
		return this.telefoneSecundario;
	}
	setTelefoneSecundario(value) {
		this.telefoneSecundario = value;
	}
	getTipo() {
		return this.tipo;
	}
	setTipo(value) {
		this.tipo = value;
	}
	getTipoDeSimulacao() {
		return this.tipoDeSimulacao;
	}
	setTipoDeSimulacao(value) {
		this.tipoDeSimulacao = value;
	}
	getUf() {
		return this.uf;
	}
	setUf(value) {
		this.uf = value;
	}
	getValorDeSimulacao() {
		return this.valorDeSimulacao;
	}
	setValorDeSimulacao(value) {
		this.valorDeSimulacao = value;
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
