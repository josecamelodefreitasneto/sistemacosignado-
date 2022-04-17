/* front-constructor */
import React from 'react';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import ImportacaoArquivoStatusCampos from '../../cruds/importacaoArquivoStatus/ImportacaoArquivoStatusCampos';
import LayoutApp from '../../../fc/components/LayoutApp';
import ObservacaoEdit from '../../cruds/observacao/ObservacaoEdit';
import ObservacaoView from '../../../fc/components/observacao/ObservacaoView';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class ImportacaoArquivoStatusEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("ImportacaoArquivoStatus", ImportacaoArquivoStatusCampos.getInstance());
	}
	getTabs() {
		return (
			<Tabs
			tabPosition={"top"}
			activeKey={this.state.abaSelecionada}
			defaultActiveKey={"Geral"}
			onChange={s => this.setAbaSelecionada(s)}>
				<TabPane key={"Geral"} tab={"Geral"}>
					{this.abaGeral()}
				</TabPane>
				<TabPane key={"Observações"} tab={"Observações"}>
					{this.abaObservacoes()}
				</TabPane>
			</Tabs>
		);
	}
	abaGeral() {
		if (!UString.equals(this.state.abaSelecionada, "Geral")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				{this.grupo_geral_0(cps)}
			</Row>
		);
	}
	inputNome() {
		return <FormItemInput bind={this.getCampos().nome} lg={24}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.nome.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputNome()}
					</Row>
				</Card>
			</Col>
		);
	}
	abaObservacoes() {
		if (!UString.equals(this.state.abaSelecionada, "Observações")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				<ObservacaoView campos={cps}/>
			</Row>
		);
	}
	getTitleImpl() {
		return "Importação Arquivo Status";
	}
	getModal() {
		const cps = this.getCampos();
		if (cps.observacoes.isTrue()) {
			return (
				<ObservacaoEdit
				vinculo={cps.observacoes}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		throw new Error("???");
	}
	getCampos() {
		return super.getCampos();
	}
	isAuditada() {
		return false;
	}
	possuiPermissaoParaVer() {
		return true;
	}
	possuiPermissaoParaIncluir() {
		return false;
	}
	possuiPermissaoParaExcluir() {
		return false;
	}
	init3() {}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}

ImportacaoArquivoStatusEditAbstract.defaultProps = FormEdit.defaultProps;
