/* front-constructor */
import React from 'react';
import ConfiguracaoProjeto from '../../../fc/outros/ConfiguracaoProjeto';
import FcBotao from '../../../fc/components/FcBotao';
import GroupCard from '../../../fc/components/GroupCard';
import IdText from '../../misc/utils/IdText';
import ImportacaoArquivoEditAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoEditAbstract';
import ImportacaoArquivoUtils from './ImportacaoArquivoUtils';
import Style from '../../misc/utils/Style';
import {Col} from 'antd';
import {Fragment} from 'react';
import {Row} from 'antd';

export default class ImportacaoArquivoForm extends ImportacaoArquivoEditAbstract {

	getTabs() {
		return (
			<Fragment>
				<Col lg={24} md={24} sm={24}>
					<GroupCard title={"Instruções"}>
						<Row gutter={24}>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>O Template será gerado com todas as colunas da entidade.</p>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>Apesar disso, as colunas que não são obrigatórias não precisam constar no registro csv.</p>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>Para saber sobre quais colunas são obrigatórias, entre em um registro da entidade e tente salvá-lo!</p>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>Caso a opção 'Atualizar Registros Existentes' seja marcada como 'Sim' o sistema irá tentar encontrar os registros com as chaves importadas. Caso encontre irá tentar atualiza-lo.</p>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>Caso a opção 'Atualizar Registros Existentes' seja marcada como 'Não' o sistema irá tentar encontrar os registros com as chaves importadas. Caso encontre ocorrerá um erro de importação.</p>
							<p style={ImportacaoArquivoForm.MARGIN_LEFT_P}>O resultado da importação poderá ser acompanhado através do menu 'Importações'</p>
						</Row>
					</GroupCard>
				</Col>
				{this.grupo_geral_0(this.getCampos())}
			</Fragment>
		);
	}

	getTitleImpl() {
		return "Fazer Upload de Arquivo - " + this.getCampos().entidade.asString();
	}

	componentDidMount3() {
		let o = ImportacaoArquivoUtils.getInstance().novo();
		o.setEntidade(new IdText(this.props.idEntidade, this.props.nomeEntidade));
		this.getCampos().setCampos(o);
	}

	ehModal() {
		return true;
	}

	getBotoesCustomizados() {
		return (
			<a href={ConfiguracaoProjeto.urlBase + this.props.pathEntidade + "/download-template-importacao"} download={"template.csv"}>
				<FcBotao title={"Download Template"}/>
			</a>
		);
	}

	afterSave() {
		this.close();
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}
ImportacaoArquivoForm.MARGIN_LEFT_P = Style.create().marginLeft(30).get();

ImportacaoArquivoForm.defaultProps = {
	...ImportacaoArquivoEditAbstract.defaultProps,
	idEntidade: 0
}
