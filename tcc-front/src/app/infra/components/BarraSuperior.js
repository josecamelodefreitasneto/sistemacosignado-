/* tcc-java */
import React from 'react';
import Color from '../../misc/consts/fixeds/Color';
import Display from '../../misc/consts/enums/Display';
import Session from '../../estado/Session';
import Style from '../../misc/utils/Style';
import SuperComponent from '../../misc/components/SuperComponent';
import TextAlign from '../../misc/consts/enums/TextAlign';
import VerticalAlign from '../../misc/consts/enums/VerticalAlign';
import {Button} from 'antd';
import {Fragment} from 'react';
import {Popconfirm} from 'antd';

export default class BarraSuperior extends SuperComponent {

	render() {
		return (
			<table style={BarraSuperior.STYLE_BANNER}>
				<thead style={BarraSuperior.STYLE_ROW}>
					<tr style={BarraSuperior.STYLE_ROW}>
						<td style={BarraSuperior.STYLE_LEFT}>
						{}</td>
						<td style={BarraSuperior.STYLE_AREA_TITLE}>
							<span style={BarraSuperior.STYLE_TITLE.get()}>SYSTEM CONSIG</span>
						</td>
						<td style={BarraSuperior.STYLE_RIGHT}>
							{this.tdLogado()}
						</td>
						<td style={BarraSuperior.STYLE_BOTAO_LOGOUT}>
							{this.tdBotaoMudarSenha()}
						</td>
						<td style={BarraSuperior.STYLE_BOTAO_LOGOUT}>
							{this.tdBotaoLogout()}
						</td>
					</tr>
				</thead>
			</table>
		);
	}

	tdBotaoMudarSenha() {
		const session = Session.getInstance();
		if (session.isLogado()) {
			return <Button shape={"circle"} icon={"user"} onClick={() => Session.getInstance().mudarSenha.set(true)}/>;
		} else {
			return null;
		}
	}

	tdBotaoLogout() {

		const session = Session.getInstance();

		if (session.isLogado()) {
			return (
				<Popconfirm
				onConfirm={() => session.efetuaLogout()}
				title={"Sair do Sistema?"}
				okText={"Sim"}
				cancelText={"Não"}
				placement={"bottomRight"}
				><Button shape={"circle"} icon={"logout"}/></Popconfirm>
			);
		} else {
			return null;
		}

	}

	tdLogado() {

		const session = Session.getInstance();

		if (session.isLogado()) {
			return (
				<Fragment>
					<span style={BarraSuperior.STYLE_NOME}>{session.nomeUsuario.get()}</span>
					<span style={BarraSuperior.STYLE_NOME}>{session.login.get()}</span>
				</Fragment>
			);
		} else {
			return null;
		}

	}

	componentDidMount() {
		this.observar(Session.getInstance().token);
	}

}
BarraSuperior.WIDTH_BOTAO_LOGOUT = 1;
BarraSuperior.WIDTH_AREA_LOGO = 30;
BarraSuperior.STYLE_TITLE = Style.create().fontFamily("fantasy").bold(true).fontSize(20).marginLeft(10).marginRight(10).verticalAlign(VerticalAlign.middle);
BarraSuperior.STYLE_ROW = Style.create().paddingTop(10).get();
BarraSuperior.STYLE_NOME = Style.create().bold(true).display(Display.block).fontSize(12).get();
BarraSuperior.STYLE_LEFT = Style.create().widthPercent(BarraSuperior.WIDTH_AREA_LOGO).padding(10).get();
BarraSuperior.STYLE_BANNER = Style.create().widthPercent(100).backgroundColor(Color.blue).get();
BarraSuperior.STYLE_RIGHT = Style.create().widthPercent(BarraSuperior.WIDTH_AREA_LOGO).padding(10).textAlign(TextAlign.right).get();
BarraSuperior.STYLE_BOTAO_LOGOUT = Style.create().widthPercent(BarraSuperior.WIDTH_BOTAO_LOGOUT).padding(10).textAlign(TextAlign.right).get();
BarraSuperior.STYLE_AREA_TITLE = Style.create().widthPercent(100 - (BarraSuperior.WIDTH_AREA_LOGO*2+BarraSuperior.WIDTH_BOTAO_LOGOUT)).textAlignCenter().padding(10).get();

BarraSuperior.defaultProps = SuperComponent.defaultProps;
