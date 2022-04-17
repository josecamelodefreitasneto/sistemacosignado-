/* front-constructor */
import React from 'react';
import ArquivoCampos from '../../cruds/arquivo/ArquivoCampos';
import ArquivoCols from '../../cruds/arquivo/ArquivoCols';
import ArquivoConsulta from '../../cruds/arquivo/ArquivoConsulta';
import ArquivoEdit from '../../cruds/arquivo/ArquivoEdit';
import ArquivoUtils from '../../cruds/arquivo/ArquivoUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ArquivoFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Arquivo";
		this.componentDidMount4();
		this.normalCols = ArquivoCols.getInstance().list;
		this.grupoCols = ArquivoCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ArquivoConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.nome} bind2={campos.type}/>
				<FormItemConsulta bind1={campos.tamanho} bind2={campos.uri}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ArquivoUtils.getInstance().novo());
	}
	edit(id) {
		ArquivoCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ArquivoCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ArquivoEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ArquivoCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ArquivoConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ArquivoConsulta.getInstance();
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
		return "Arquivo";
	}
	getEntidadePath() {
		return "arquivo";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ArquivoFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
