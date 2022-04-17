/* tcc-java */
import React from 'react';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import TelefoneCampos from '../../cruds/telefone/TelefoneCampos';
import TelefoneCols from '../../cruds/telefone/TelefoneCols';
import TelefoneConsulta from '../../cruds/telefone/TelefoneConsulta';
import TelefoneEdit from '../../cruds/telefone/TelefoneEdit';
import TelefoneUtils from '../../cruds/telefone/TelefoneUtils';
import {Fragment} from 'react';

export default class TelefoneFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Telefone";
		this.componentDidMount4();
		this.normalCols = TelefoneCols.getInstance().list;
		this.grupoCols = TelefoneCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return TelefoneConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.ddd} bind2={campos.recado}/>
				<FormItemConsulta bind1={campos.nome} bind2={campos.whatsapp}/>
				<FormItemConsulta bind1={campos.numero}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(TelefoneUtils.getInstance().novo());
	}
	edit(id) {
		TelefoneCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		TelefoneCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <TelefoneEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(TelefoneCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = TelefoneConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = TelefoneConsulta.getInstance();
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
		return "Telefone";
	}
	getEntidadePath() {
		return "telefone";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

TelefoneFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
