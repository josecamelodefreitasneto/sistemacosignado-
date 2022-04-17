/* tcc-java */
import React from 'react';
import ClienteSimulacaoCampos from '../../cruds/clienteSimulacao/ClienteSimulacaoCampos';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemButton from '../../../antd/form/FormItemButton';
import FormItemInput from '../../../antd/form/FormItemInput';
import FormItemSelect from '../../../antd/form/FormItemSelect';
import LayoutApp from '../../../fc/components/LayoutApp';
import RadioBoolean from '../../../antd/form/RadioBoolean';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class ClienteSimulacaoEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("ClienteSimulacao", ClienteSimulacaoCampos.getInstance());
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
	inputCliente() {
		return <FormItemSelect bind={this.getCampos().cliente} lg={4}/>;
	}
	inputParcelas() {
		return <FormItemInput bind={this.getCampos().parcelas} lg={4}/>;
	}
	inputIndice() {
		return <FormItemInput bind={this.getCampos().indice} lg={4}/>;
	}
	inputValor() {
		return <FormItemInput bind={this.getCampos().valor} lg={4}/>;
	}
	botaoContratar() {
		if (this.getCampos().contratar.isTrue() && !this.getCampos().houveMudancas()) {
			return <FormItemButton bind={this.getCampos().botaoContratar}/>;
		} else {
			return null;
		}
	}
	inputContratado() {
		return <RadioBoolean bind={this.getCampos().contratado} lg={4}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.cliente.isVisible() && !cps.parcelas.isVisible() && !cps.indice.isVisible() && !cps.valor.isVisible() && !cps.contratar.isVisible() && !cps.contratado.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputCliente()}
						{this.inputParcelas()}
						{this.inputIndice()}
						{this.inputValor()}
						{this.botaoContratar()}
						{this.inputContratado()}
					</Row>
				</Card>
			</Col>
		);
	}
	getTitleImpl() {
		return "Cliente Simulação";
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

ClienteSimulacaoEditAbstract.defaultProps = FormEdit.defaultProps;
