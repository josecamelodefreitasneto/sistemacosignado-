/* tcc-java */
import React from 'react';
import BarraSuperior from '../../infra/components/BarraSuperior';
import ClienteFormConsulta from '../cliente/ClienteFormConsulta';
import FramePrincipalAbstract from '../../auto/outros/FramePrincipalAbstract';

export default class FramePrincipal extends FramePrincipalAbstract {

	getBarraSuperior() {
		return <BarraSuperior/>;
	}

	getHome() {
		return <ClienteFormConsulta/>;
	}
	setCollapsed = o => this.setState({collapsed:o});

}

FramePrincipal.defaultProps = FramePrincipalAbstract.defaultProps;
