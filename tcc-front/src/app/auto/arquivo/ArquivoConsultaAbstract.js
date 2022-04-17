/* front-constructor */
import ArquivoUtils from '../../cruds/arquivo/ArquivoUtils';
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ArquivoConsultaAbstract extends Consulta {

	dados;
	nome;
	tamanho;
	type;
	uri;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Arquivo";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ArquivoUtils.getInstance().fromJsonList(array));
			},
			"arquivo/consulta",
			() => this.getTo()
		);
		this.nome = this.newString("Nome", 500);
		this.tamanho = this.newInteger("Tamanho");
		this.type = this.newString("Type", 50);
		this.uri = this.newString("Uri", 2147483647);
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
		Sessao.checkInstance("ArquivoConsulta", this);
	}
}
