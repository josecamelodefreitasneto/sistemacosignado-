/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import ClienteUtils from '../../cruds/cliente/ClienteUtils';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteConsultaAbstract extends Consulta {

	dados;
	agencia;
	atendenteResponsavel;
	bairro;
	banco;
	cep;
	cidade;
	complemento;
	cpf;
	dataDeNascimento;
	dia;
	email;
	logradouro;
	margem;
	matricula;
	nome;
	numeroDaConta;
	orgao;
	rendaBruta;
	rendaLiquida;
	status;
	telefonePrincipal;
	telefoneSecundario;
	tipo;
	tipoDeSimulacao;
	uf;
	valorDeSimulacao;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Cliente";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ClienteUtils.getInstance().fromJsonList(array));
			},
			"cliente/consulta",
			() => this.getTo()
		);
		this.agencia = this.newString("Agência", 50);
		this.atendenteResponsavel = this.newFk("Atendente Responsável");
		this.bairro = this.newString("Bairro", 100);
		this.banco = this.newFk("Banco");
		this.cep = this.newFk("Cep");
		this.cidade = this.newString("Cidade", 100);
		this.complemento = this.newString("Complemento", 50);
		this.cpf = this.newCpf("CPF");
		this.dataDeNascimento = this.newData("Data de Nascimento");
		this.dia = this.newFk("Dia");
		this.email = this.newEmail("E-mail");
		this.logradouro = this.newString("Logradouro", 100);
		this.margem = this.newMoney("Margem", 7, true);
		this.matricula = this.newString("Matrícula", 50);
		this.nome = this.newNomeProprio("Nome");
		this.numeroDaConta = this.newString("Número da Conta", 50);
		this.orgao = this.newFk("Orgão");
		this.rendaBruta = this.newMoney("Renda Bruta", 7, true);
		this.rendaLiquida = this.newMoney("Renda Liquida", 7, true);
		this.status = this.newFk("Status");
		this.telefonePrincipal = this.newFk("Telefone Principal");
		this.telefoneSecundario = this.newFk("Telefone Secundário");
		this.tipo = this.newFk("Tipo");
		this.tipoDeSimulacao = this.newFk("Tipo de Simulação");
		this.uf = this.newString("UF", 100);
		this.valorDeSimulacao = this.newMoney("Valor de Simulação", 7, true);
		this.excluido = this.newBoolean("Excluido");
		this.registroBloqueado = this.newBoolean("Registro Bloqueado");
	}
	consultar() {
		this.dados.clearItens();
		this.dados.carregar();
	}
	getTo() {
		this.checkInstance();
		const o = {};
		o.busca = this.getBusca().get();
		return o;
	}
	getDataSource() {
		if (UCommons.isEmpty(this.dados)) {
			return [];
		} else {
			return this.dados.getItens();
		}
	}
	checkInstance() {
		Sessao.checkInstance("ClienteConsulta", this);
	}
}
