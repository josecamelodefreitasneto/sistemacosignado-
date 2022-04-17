/* tcc-java */
import React from 'react';
import CepCampos from '../../cruds/cep/CepCampos';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import LayoutApp from '../../../fc/components/LayoutApp';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class CepEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("Cep", CepCampos.getInstance());
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
	inputNumero() {
		return <FormItemInput bind={this.getCampos().numero} lg={4}/>;
	}
	inputUf() {
		return <FormItemInput bind={this.getCampos().uf} lg={5}/>;
	}
	inputCidade() {
		return <FormItemInput bind={this.getCampos().cidade} lg={5}/>;
	}
	inputBairro() {
		return <FormItemInput bind={this.getCampos().bairro} lg={5}/>;
	}
	inputLogradouro() {
		return <FormItemInput bind={this.getCampos().logradouro} lg={5}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.numero.isVisible() && !cps.uf.isVisible() && !cps.cidade.isVisible() && !cps.bairro.isVisible() && !cps.logradouro.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputNumero()}
						{this.inputUf()}
						{this.inputCidade()}
						{this.inputBairro()}
						{this.inputLogradouro()}
					</Row>
				</Card>
			</Col>
		);
	}
	getTitleImpl() {
		return "Cep";
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

CepEditAbstract.defaultProps = FormEdit.defaultProps;
