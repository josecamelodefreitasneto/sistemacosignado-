/* front-constructor */
import React from 'react';
import EsqueciSenhaCampos from '../../cruds/esqueciSenha/EsqueciSenhaCampos';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import LayoutApp from '../../../fc/components/LayoutApp';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class EsqueciSenhaEditAbstract extends FormEdit {
	init2() {
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("EsqueciSenha", EsqueciSenhaCampos.getInstance());
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
	inputNovaSenha() {
		return <FormItemInput bind={this.getCampos().novaSenha} lg={24}/>;
	}
	inputConfirmarSenha() {
		return <FormItemInput bind={this.getCampos().confirmarSenha} lg={24}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.novaSenha.isVisible() && !cps.confirmarSenha.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputNovaSenha()}
					</Row>
					<Row gutter={24}>
						{this.inputConfirmarSenha()}
					</Row>
				</Card>
			</Col>
		);
	}
	getTitleImpl() {
		return "Esqueci Senha";
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

EsqueciSenhaEditAbstract.defaultProps = FormEdit.defaultProps;
