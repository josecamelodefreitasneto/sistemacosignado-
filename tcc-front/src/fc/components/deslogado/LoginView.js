/* front-constructor */
import React from 'react';
import EsqueciSenhaCodigoView from './EsqueciSenhaCodigoView';
import EsqueciSenhaEdit from '../../../app/cruds/esqueciSenha/EsqueciSenhaEdit';
import EsqueciSenhaEmailView from './EsqueciSenhaEmailView';
import FcBotao from '../FcBotao';
import FormItemCheck from '../../../antd/form/FormItemCheck';
import FormItemInput from '../../../antd/form/FormItemInput';
import FormKeyDownObserver from '../FormKeyDownObserver';
import LayoutApp from '../LayoutApp';
import ResourcesFc from '../../../resources/ResourcesFc';
import Session from '../../../app/estado/Session';
import Style from '../../../app/misc/utils/Style';
import {Card} from 'antd';
import {Row} from 'antd';

export default class LoginView extends FormKeyDownObserver {

	session;

	render() {
		this.session = Session.getInstance();
		return (
			<div style={LoginView.FORM_STYLE}>
				<Card size={"small"} style={LoginView.CARD_STYLE} className={"card-table"}>
					<Row gutter={10}>
						<div style={LoginView.ALIGN_ITEMS_CENTER}>
							<img src={ResourcesFc.user} alt={ResourcesFc.user} style={LoginView.IMAGE_STYLE}/>
						</div>
					</Row>
					<Row gutter={10}>
						<div style={LoginView.ALIGN_ITEMS_CENTER}>
							<span style={LoginView.TEXT_STYLE.get()}>Por favor, autentique-se!</span>
						</div>
					</Row>
					<Row gutter={10}>
						<FormItemInput bind={this.session.login} lg={24}/>
					</Row>
					<Row gutter={10}>
						<FormItemInput bind={this.session.senha} lg={24}/>
					</Row>
					<Row gutter={10}>
						<FormItemCheck bind={this.session.lembrarme} lg={24}/>
					</Row>
					<Row gutter={10}>
						<div style={LoginView.ALIGN_ITEMS_CENTER}>
							<FcBotao title={"Efetuar Login"} onClick={() => this.session.efetuaLogin()}/>
						</div>
					</Row>
					<Row gutter={10}>
						<div style={LoginView.ALIGN_ITEMS_CENTER}>
							<FcBotao title={"Esqueci Minha Senha"} onClick={() => this.session.modalEsqueciSenhaEmail.set(true)}/>
						</div>
					</Row>
				</Card>
				{this.modais()}
			</div>
		);
	}

	modais() {
		if (this.session.modalEsqueciSenhaEmail.isTrue()) {
			return <EsqueciSenhaEmailView/>;
		}
		if (this.session.modalEsqueciSenhaCodigo.isTrue()) {
			return <EsqueciSenhaCodigoView/>;
		}
		if (this.session.modalEsqueciSenhaCadastrar.isTrue()) {
			return <EsqueciSenhaEdit/>;
		}
		return null;
	}

	onKeyDown0(e) {
		if (e.enter()) {
			this.session.efetuaLogin();
		}
	}

	componentDidMount1() {
		this.session = Session.getInstance();
		this.observar(this.session.modalEsqueciSenhaEmail);
		this.observar(this.session.modalEsqueciSenhaCodigo);
		this.observar(this.session.modalEsqueciSenhaCadastrar);
	}

}
LoginView.TEXT_STYLE = Style.create();
LoginView.IMAGE_STYLE = Style.create().height(100).get();
LoginView.FORM_STYLE = Style.create().width(500).margin("auto").padding(30).get();
LoginView.ALIGN_ITEMS_CENTER = Style.create().textAlignCenter().alignItemsCenter().widthPercent(100).paddingBottom(20).get();
LoginView.CARD_STYLE = LayoutApp.createStyle().marginTop(15).padding(20).get();

LoginView.defaultProps = FormKeyDownObserver.defaultProps;
