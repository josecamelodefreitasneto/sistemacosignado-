/* front-constructor */
import React from 'react';
import Color from '../../app/misc/consts/fixeds/Color';
import EsqueciSenhaEmailView from './deslogado/EsqueciSenhaEmailView';
import LoginView from './deslogado/LoginView';
import MudarSenhaEdit from '../../app/cruds/mudarSenha/MudarSenhaEdit';
import Session from '../../app/estado/Session';
import Style from '../../app/misc/utils/Style';
import SuperComponent from '../../app/misc/components/SuperComponent';
import {Fragment} from 'react';
import {Layout} from 'antd';
import {Link} from 'react-router-dom';
import {MenuFoldOutlined} from '@ant-design/icons';
import {MenuUnfoldOutlined} from '@ant-design/icons';
import {Menu} from 'antd';
import {Route} from 'react-router-dom';
import {Switch} from 'react-router-dom';
const MenuItem = Menu.Item;
const LayoutSider = Layout.Sider;
const LayoutContent = Layout.Content;

export default class FramePrincipalBase extends SuperComponent {
	constructor(props){
		super(props);
		this.state.collapsed = false;
	}

	render() {
		return (
			<Fragment>
				{this.getBarraSuperior()}
				{this.getBody()}
			</Fragment>
		);
	}

	getBody() {
		if (Session.getInstance().isLogado()) {
			return this.getAreaLogado();
		} else {
			return this.getAreaDeslogado();
		}
	}

	getAreaLogado() {
		return (
			<Fragment>
				{this.getModal()}
				<div style={Style.create().widthPercent(100).heightPercent(100).get()}>
					<Layout style={Style.create().widthPercent(100).heightPercent(100).get()}>
						{this.menuLateral()}
						{this.areaCentral()}
					</Layout>
				</div>
			</Fragment>
		);
	}

	getModal() {
		if (Session.getInstance().mudarSenha.isTrue()) {
			return <MudarSenhaEdit/>;
		} else {
			return this.getModalImpl();
		}
	}

	getModalImpl() {
		return null;
	}

	areaCentral() {
		return (
			<Layout className={"site-layout"}>
				<LayoutContent className={"site-layout-background"} style={FramePrincipalBase.STYLE_CONTENT}>
					{this.getRotas()}
				</LayoutContent>
			</Layout>
		);
	}

	menuLateral() {
		return <LayoutSider trigger={null} collapsible={true} collapsed={this.state.collapsed}>
			<Menu theme={"dark"} mode={"inline"} defaultSelectedKeys={["1"]}>
				<MenuItem onClick={() => this.toggle()} key={"0"}>
					{this.getBotao()}
		 	</MenuItem>
				{this.getItens()}
			</Menu>
		</LayoutSider>;
	}

	getItem(title, key, icon) {
		return (
			<MenuItem key={key}>
				<Link to={"/" + key}>
					{icon}
					<span>{title}</span>
				</Link>
			</MenuItem>
		);
	}

	getBotao() {
		if (this.state.collapsed) {
			return <MenuUnfoldOutlined onClick={() => this.toggle()} style={Style.create().color(Color.white).get()}/>;
		} else {
			return <MenuFoldOutlined onClick={() => this.toggle()} style={Style.create().color(Color.white).get()}/>;
		}
	}

	toggle() {
		this.setCollapsed(!this.state.collapsed);
	}

	getAreaDeslogado() {
		return (
			<Switch>
				<Route exact={true} path={"/EsqueciMinhaSenha"} render={() => <EsqueciSenhaEmailView/>}/>
				<Route path={"*"} render={() => <LoginView/>}/>
			</Switch>
		);
	}

	get404() {
		let s = "404 - Página não encontrada!";
		return <span>{s}</span>;
	}

	componentDidMount() {
		this.observar(Session.getInstance().token);
		this.observar(Session.getInstance().mudarSenha);
	}
	setCollapsed = o => this.setState({collapsed:o});

}
FramePrincipalBase.STYLE_CONTENT = Style.create().marginTop(24).marginRight(16).padding(24).heightPercent(100).get();

FramePrincipalBase.defaultProps = SuperComponent.defaultProps;
