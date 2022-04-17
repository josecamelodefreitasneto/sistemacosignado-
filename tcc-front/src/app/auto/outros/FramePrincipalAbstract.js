/* tcc-java */
import React from 'react';
import AtendenteFormConsulta from '../../cruds/atendente/AtendenteFormConsulta';
import ClienteFormConsulta from '../../cruds/cliente/ClienteFormConsulta';
import ClienteRubricaFormConsulta from '../../cruds/clienteRubrica/ClienteRubricaFormConsulta';
import FramePrincipalBase from '../../../fc/components/FramePrincipalBase';
import ImportacaoArquivoFormConsulta from '../../cruds/importacaoArquivo/ImportacaoArquivoFormConsulta';
import Session from '../../estado/Session';
import {CustomerServiceOutlined} from '@ant-design/icons';
import {LineChartOutlined} from '@ant-design/icons';
import {Route} from 'react-router-dom';
import {Switch} from 'react-router-dom';
import {TeamOutlined} from '@ant-design/icons';

export default class FramePrincipalAbstract extends FramePrincipalBase {

	getRotas() {
		return (
			<Switch>
				<Route exact={true} path={"/"} render={() => this.getHome()}/>
				{this.getRotaCliente()}
				{this.getRotaClienteRubrica()}
				{this.getRotaAtendente()}
				{this.getRotaImportacaoArquivo()}
				<Route path={"*"} render={() => this.get404()}/>
			</Switch>
		);
	}

	getItens() {
		return (
			[
				this.getCliente(),
				this.getClienteRubrica(),
				this.getAtendente(),
				this.getImportacaoArquivo()
			]
		);
	}

	getCliente() {
		if (Session.canRead("Cliente")) {
			return this.getItem("Cliente", "Cliente", <TeamOutlined/>);
		} else {
			return null;
		}
	}

	getRotaCliente() {
		return <Route exact={true} path={"/Cliente"} render={() => <ClienteFormConsulta/>}/>;
	}

	getClienteRubrica() {
		if (Session.canRead("ClienteRubrica")) {
			return this.getItem("Cliente Rubrica", "ClienteRubrica", <TeamOutlined/>);
		} else {
			return null;
		}
	}

	getRotaClienteRubrica() {
		return <Route exact={true} path={"/ClienteRubrica"} render={() => <ClienteRubricaFormConsulta/>}/>;
	}

	getAtendente() {
		if (Session.canRead("Atendente")) {
			return this.getItem("Atendente", "Atendente", <CustomerServiceOutlined/>);
		} else {
			return null;
		}
	}

	getRotaAtendente() {
		return <Route exact={true} path={"/Atendente"} render={() => <AtendenteFormConsulta/>}/>;
	}

	getImportacaoArquivo() {
		if (Session.canRead("ImportacaoArquivo")) {
			return this.getItem("Importação Arquivo", "ImportacaoArquivo", <LineChartOutlined/>);
		} else {
			return null;
		}
	}

	getRotaImportacaoArquivo() {
		return <Route exact={true} path={"/ImportacaoArquivo"} render={() => <ImportacaoArquivoFormConsulta/>}/>;
	}
	setCollapsed = o => this.setState({collapsed:o});
}

FramePrincipalAbstract.defaultProps = FramePrincipalBase.defaultProps;
