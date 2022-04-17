/* front-constructor */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import ImportacaoArquivoUtils from '../../cruds/importacaoArquivo/ImportacaoArquivoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoConsultaAbstract extends Consulta {

	dados;
	arquivo;
	arquivoDeErros;
	atualizarRegistrosExistentes;
	delimitador;
	entidade;
	processadosComErro;
	processadosComSucesso;
	status;
	totalDeLinhas;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "ImportacaoArquivo";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ImportacaoArquivoUtils.getInstance().fromJsonList(array));
			},
			"importacao-arquivo/consulta",
			() => this.getTo()
		);
		this.arquivo = this.newFk("Arquivo");
		this.arquivoDeErros = this.newFk("Arquivo de Erros");
		this.atualizarRegistrosExistentes = this.newBoolean("Atualizar Registros Existentes");
		this.delimitador = this.newString("Delimitador", 3);
		this.entidade = this.newFk("Entidade");
		this.processadosComErro = this.newInteger("Processados com Erro");
		this.processadosComSucesso = this.newInteger("Processados com Sucesso");
		this.status = this.newFk("Status");
		this.totalDeLinhas = this.newInteger("Total de Linhas");
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
		Sessao.checkInstance("ImportacaoArquivoConsulta", this);
	}
}
