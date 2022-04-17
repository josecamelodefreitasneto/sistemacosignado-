/* tcc-java */
import ClienteCamposAbstract from '../../auto/cliente/ClienteCamposAbstract';
import Sessao from '../../../projeto/Sessao';
import {message} from 'antd';

export default class ClienteCampos extends ClienteCamposAbstract {

	emiteMensagemSucessoCalculo() {
		message.success("Calculado com Sucesso");
	}

	calcularClick() {
		if (this.houveMudancas()) {
			this.save(null, () => this.emiteMensagemSucessoCalculo());
		} else {
			this.emiteMensagemSucessoCalculo();
		}
	}

	setCampos2(o) {
		if (!this.permissao.insert()) {
			this.listBindings.forEach(item => item.setDisabled(true));
			this.telefonePrincipal.setDisabled(false);
			this.telefoneSecundario.setDisabled(false);
			this.email.setDisabled(false);
			this.valorDeSimulacao.setDisabled(false);
			this.tipoDeSimulacao.setDisabled(false);
			this.dia.setDisabled(false);
			this.cep.setDisabled(false);
			this.complemento.setDisabled(false);
		}
	}

	static getInstance() {
		return Sessao.getInstance("ClienteCampos", () => new ClienteCampos(), o => o.init());
	}

}
