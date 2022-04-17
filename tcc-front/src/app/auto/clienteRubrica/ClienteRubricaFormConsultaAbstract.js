/* tcc-java */
import React from 'react';
import ClienteRubricaCampos from '../../cruds/clienteRubrica/ClienteRubricaCampos';
import ClienteRubricaCols from '../../cruds/clienteRubrica/ClienteRubricaCols';
import ClienteRubricaConsulta from '../../cruds/clienteRubrica/ClienteRubricaConsulta';
import ClienteRubricaEdit from '../../cruds/clienteRubrica/ClienteRubricaEdit';
import ClienteRubricaUtils from '../../cruds/clienteRubrica/ClienteRubricaUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ClienteRubricaFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Cliente Rubrica";
		this.componentDidMount4();
		this.normalCols = ClienteRubricaCols.getInstance().list;
		this.grupoCols = ClienteRubricaCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ClienteRubricaConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.cliente} bind2={campos.tipo}/>
				<FormItemConsulta bind1={campos.rubrica} bind2={campos.valor}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ClienteRubricaUtils.getInstance().novo());
	}
	edit(id) {
		ClienteRubricaCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ClienteRubricaCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ClienteRubricaEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ClienteRubricaCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ClienteRubricaConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ClienteRubricaConsulta.getInstance();
		return (
			<Tabela
			bind={cps.dados}
			colunas={this.normalCols}
			colunasGrupo={this.grupoCols}
			onClick={o => this.edit(o.getId())}
			onDelete={this.onDeleteFunction()}
			formKeyDown={this}
			/>
		);
	}
	getEntidade() {
		return "ClienteRubrica";
	}
	getEntidadePath() {
		return "cliente-rubrica";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ClienteRubricaFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
