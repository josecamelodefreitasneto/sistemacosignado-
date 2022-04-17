/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import ClienteSimulacaoUtils from '../../cruds/clienteSimulacao/ClienteSimulacaoUtils';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteSimulacaoConsultaAbstract extends Consulta {

	dados;
	cliente;
	contratado;
	indice;
	parcelas;
	valor;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "ClienteSimulacao";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ClienteSimulacaoUtils.getInstance().fromJsonList(array));
			},
			"cliente-simulacao/consulta",
			() => this.getTo()
		);
		this.cliente = this.newFk("Cliente");
		this.contratado = this.newBoolean("Contratado");
		this.indice = this.newDecimal("Indice", 1, 5, true);
		this.parcelas = this.newInteger("Parcelas");
		this.valor = this.newMoney("Valor", 7, true);
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
		Sessao.checkInstance("ClienteSimulacaoConsulta", this);
	}
}
