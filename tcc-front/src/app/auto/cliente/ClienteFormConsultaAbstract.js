/* tcc-java */
import React from 'react';
import ClienteCampos from '../../cruds/cliente/ClienteCampos';
import ClienteCols from '../../cruds/cliente/ClienteCols';
import ClienteConsulta from '../../cruds/cliente/ClienteConsulta';
import ClienteEdit from '../../cruds/cliente/ClienteEdit';
import ClienteUtils from '../../cruds/cliente/ClienteUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ClienteFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Cliente";
		this.componentDidMount4();
		this.normalCols = ClienteCols.getInstance().list;
		this.grupoCols = ClienteCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ClienteConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.agencia} bind2={campos.matricula}/>
				<FormItemConsulta bind1={campos.atendenteResponsavel} bind2={campos.nome}/>
				<FormItemConsulta bind1={campos.bairro} bind2={campos.numeroDaConta}/>
				<FormItemConsulta bind1={campos.banco} bind2={campos.orgao}/>
				<FormItemConsulta bind1={campos.cep} bind2={campos.rendaBruta}/>
				<FormItemConsulta bind1={campos.cidade} bind2={campos.rendaLiquida}/>
				<FormItemConsulta bind1={campos.complemento} bind2={campos.status}/>
				<FormItemConsulta bind1={campos.cpf} bind2={campos.telefonePrincipal}/>
				<FormItemConsulta bind1={campos.dataDeNascimento} bind2={campos.telefoneSecundario}/>
				<FormItemConsulta bind1={campos.dia} bind2={campos.tipo}/>
				<FormItemConsulta bind1={campos.email} bind2={campos.tipoDeSimulacao}/>
				<FormItemConsulta bind1={campos.logradouro} bind2={campos.uf}/>
				<FormItemConsulta bind1={campos.margem} bind2={campos.valorDeSimulacao}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ClienteUtils.getInstance().novo());
	}
	edit(id) {
		ClienteCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ClienteCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ClienteEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ClienteCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ClienteConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ClienteConsulta.getInstance();
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
		return "Cliente";
	}
	getEntidadePath() {
		return "cliente";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ClienteFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
