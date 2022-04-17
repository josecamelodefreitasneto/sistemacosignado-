/* tcc-java */
import React from 'react';
import ClienteSimulacaoCampos from '../../cruds/clienteSimulacao/ClienteSimulacaoCampos';
import ClienteSimulacaoCols from '../../cruds/clienteSimulacao/ClienteSimulacaoCols';
import ClienteSimulacaoConsulta from '../../cruds/clienteSimulacao/ClienteSimulacaoConsulta';
import ClienteSimulacaoEdit from '../../cruds/clienteSimulacao/ClienteSimulacaoEdit';
import ClienteSimulacaoUtils from '../../cruds/clienteSimulacao/ClienteSimulacaoUtils';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ClienteSimulacaoFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Cliente Simulação";
		this.componentDidMount4();
		this.normalCols = ClienteSimulacaoCols.getInstance().list;
		this.grupoCols = ClienteSimulacaoCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ClienteSimulacaoConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.cliente} bind2={campos.parcelas}/>
				<FormItemConsulta bind1={campos.contratado} bind2={campos.valor}/>
				<FormItemConsulta bind1={campos.indice}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ClienteSimulacaoUtils.getInstance().novo());
	}
	edit(id) {
		ClienteSimulacaoCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ClienteSimulacaoCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ClienteSimulacaoEdit somenteUpdate={true} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	getTable() {
		const cps = ClienteSimulacaoConsulta.getInstance();
		return (
			<Tabela
			bind={cps.dados}
			colunas={this.normalCols}
			colunasGrupo={this.grupoCols}
			onClick={o => this.edit(o.getId())}
			formKeyDown={this}
			/>
		);
	}
	getEntidade() {
		return "ClienteSimulacao";
	}
	getEntidadePath() {
		return "cliente-simulacao";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ClienteSimulacaoFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
