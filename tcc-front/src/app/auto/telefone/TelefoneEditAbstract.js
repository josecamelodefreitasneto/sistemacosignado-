/* tcc-java */
import React from 'react';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import LayoutApp from '../../../fc/components/LayoutApp';
import RadioBoolean from '../../../antd/form/RadioBoolean';
import TelefoneCampos from '../../cruds/telefone/TelefoneCampos';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class TelefoneEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("Telefone", TelefoneCampos.getInstance());
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
	inputDdd() {
		return <FormItemInput bind={this.getCampos().ddd} lg={2}/>;
	}
	inputNumero() {
		return <FormItemInput bind={this.getCampos().numero} lg={5}/>;
	}
	inputNome() {
		return <FormItemInput bind={this.getCampos().nome} lg={5}/>;
	}
	inputWhatsapp() {
		return <RadioBoolean bind={this.getCampos().whatsapp} lg={6}/>;
	}
	inputRecado() {
		return <RadioBoolean bind={this.getCampos().recado} lg={6}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.ddd.isVisible() && !cps.numero.isVisible() && !cps.nome.isVisible() && !cps.whatsapp.isVisible() && !cps.recado.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputDdd()}
						{this.inputNumero()}
						{this.inputNome()}
						{this.inputWhatsapp()}
						{this.inputRecado()}
					</Row>
				</Card>
			</Col>
		);
	}
	getTitleImpl() {
		return "Telefone";
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

TelefoneEditAbstract.defaultProps = FormEdit.defaultProps;
