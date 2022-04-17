/* front-constructor */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import ImportacaoArquivoErroUtils from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoErroConsultaAbstract extends Consulta {

	dados;
	erro;
	importacaoArquivo;
	linha;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "ImportacaoArquivoErro";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ImportacaoArquivoErroUtils.getInstance().fromJsonList(array));
			},
			"importacao-arquivo-erro/consulta",
			() => this.getTo()
		);
		this.erro = this.newFk("Erro");
		this.importacaoArquivo = this.newFk("Importação Arquivo");
		this.linha = this.newInteger("Linha");
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
		Sessao.checkInstance("ImportacaoArquivoErroConsulta", this);
	}
}
