/* front-constructor */
import React from 'react';
import CommonStyles from '../../misc/styles/CommonStyles';
import FcBotao from '../../../fc/components/FcBotao';
import ImportacaoArquivoCamposAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoCamposAbstract';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoCampos extends ImportacaoArquivoCamposAbstract {

	init2() {
		this.arquivoDeErros.setRenderBody(() => <FcBotao style={CommonStyles.W100P} title={"Gerar"} onClick={() => this.gerarArquivoDeErros()}/>);
	}

	setCampos2(o) {
		if (this.statusProcessado()) {
			this.arquivoDeErros.setVisible(true);
			if (UCommons.isEmpty(o.getArquivoDeErros())) {
				this.arquivoDeErros.setReplaceRenderBody(true);
			} else {
				this.arquivoDeErros.setReplaceRenderBody(false);
			}
		} else {
			this.arquivoDeErros.setVisible(false);
		}
	}
	gerarArquivoDeErros() {
		this.callComando("gerar-arquivo-de-erros");
	}

	isReadOnly() {
		return super.isReadOnly() || this.id.get() > 0;
	}

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoCampos", () => new ImportacaoArquivoCampos(), o => o.init());
	}

	processar() {
		this.callComando("processar");
	}

}
