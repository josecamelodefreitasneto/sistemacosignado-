/* tcc-java */
import React from 'react';
import AtendenteCampos from '../../cruds/atendente/AtendenteCampos';
import AtendenteCols from '../../cruds/atendente/AtendenteCols';
import AtendenteConsulta from '../../cruds/atendente/AtendenteConsulta';
import AtendenteEdit from '../../cruds/atendente/AtendenteEdit';
import AtendenteUtils from '../../cruds/atendente/AtendenteUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class AtendenteFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Atendente";
		this.componentDidMount4();
		this.normalCols = AtendenteCols.getInstance().list;
		this.grupoCols = AtendenteCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return AtendenteConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.email} bind2={campos.nome}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(AtendenteUtils.getInstance().novo());
	}
	edit(id) {
		AtendenteCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		AtendenteCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <AtendenteEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(AtendenteCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = AtendenteConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = AtendenteConsulta.getInstance();
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
		return "Atendente";
	}
	getEntidadePath() {
		return "atendente";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

AtendenteFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
