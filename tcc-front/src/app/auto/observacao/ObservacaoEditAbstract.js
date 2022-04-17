/* front-constructor */
import React from 'react';
import ArquivoEdit from '../../cruds/arquivo/ArquivoEdit';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import LayoutApp from '../../../fc/components/LayoutApp';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class ObservacaoEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("Observacao", ObservacaoCampos.getInstance());
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
	inputTexto() {
		return <FormItemInput bind={this.getCampos().texto} lg={12}/>;
	}
	inputAnexo() {
		return this.inputVinculado(this.getCampos().anexo, 6);
	}
	grupo_geral_0(cps) {
		if (!cps.texto.isVisible() && !cps.anexo.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputTexto()}
						{this.inputAnexo()}
					</Row>
				</Card>
			</Col>
		);
	}
	getTitleImpl() {
		return "Observação";
	}
	getModal() {
		const cps = this.getCampos();
		if (cps.anexo.isTrue()) {
			return (
				<ArquivoEdit
				tamanhoMaximoEmBytes={5242880}
				mensagemTamanho={"O tamanho máximo não deve exceder 5.242.880 bytes"}
				mensagemExtensoes={"São aceitos quaisquer tipos de arquivo"}
				vinculo={cps.anexo}
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
	init3() {}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}

ObservacaoEditAbstract.defaultProps = FormEdit.defaultProps;
