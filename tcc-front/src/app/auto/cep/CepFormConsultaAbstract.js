/* tcc-java */
import React from 'react';
import CepCampos from '../../cruds/cep/CepCampos';
import CepCols from '../../cruds/cep/CepCols';
import CepConsulta from '../../cruds/cep/CepConsulta';
import CepEdit from '../../cruds/cep/CepEdit';
import CepUtils from '../../cruds/cep/CepUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class CepFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Cep";
		this.componentDidMount4();
		this.normalCols = CepCols.getInstance().list;
		this.grupoCols = CepCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return CepConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.bairro} bind2={campos.numero}/>
				<FormItemConsulta bind1={campos.cidade} bind2={campos.uf}/>
				<FormItemConsulta bind1={campos.logradouro}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(CepUtils.getInstance().novo());
	}
	edit(id) {
		CepCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		CepCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <CepEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(CepCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = CepConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = CepConsulta.getInstance();
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
		return "Cep";
	}
	getEntidadePath() {
		return "cep";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

CepFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
