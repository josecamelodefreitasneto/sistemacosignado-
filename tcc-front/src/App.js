/* tcc-java */
import React from 'react';
import './App.css';
import 'antd/dist/antd.css';
import Config from './app/custom/Config';
import FramePrincipal from './app/cruds/outros/FramePrincipal';
import LayoutApp from './fc/components/LayoutApp';
import Servico from './projeto/Servico';
import Session from './app/estado/Session';
import StartPrototypes from './app/misc/utils/StartPrototypes';
import Style from './app/misc/utils/Style';
import SuperComponent from './app/misc/components/SuperComponent';

export default class App extends SuperComponent {

	static montado = false;

	constructor(props) {
		super(props);
		setTimeout(() => {
			StartPrototypes.exec();
			Config.start();
			Style.modelo = LayoutApp.EMPTY;
			App.montado = true;
			Session.getInstance();
			Servico.addExecutarQuandoVazio(() => this.forceUpdate());
		});
	}

	render() {
		if (App.montado) {
			return <FramePrincipal/>;
		} else {
			return null;
		}
	}

}

App.defaultProps = SuperComponent.defaultProps;
