/* front-constructor */
import React from 'react';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import ImportacaoArquivoCampos from '../../cruds/importacaoArquivo/ImportacaoArquivoCampos';
import ImportacaoArquivoCols from '../../cruds/importacaoArquivo/ImportacaoArquivoCols';
import ImportacaoArquivoConsulta from '../../cruds/importacaoArquivo/ImportacaoArquivoConsulta';
import ImportacaoArquivoEdit from '../../cruds/importacaoArquivo/ImportacaoArquivoEdit';
import ImportacaoArquivoUtils from '../../cruds/importacaoArquivo/ImportacaoArquivoUtils';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ImportacaoArquivoFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Importação Arquivo";
		this.componentDidMount4();
		this.normalCols = ImportacaoArquivoCols.getInstance().list;
		this.grupoCols = ImportacaoArquivoCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ImportacaoArquivoConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.arquivo} bind2={campos.processadosComErro}/>
				<FormItemConsulta bind1={campos.arquivoDeErros} bind2={campos.processadosComSucesso}/>
				<FormItemConsulta bind1={campos.atualizarRegistrosExistentes} bind2={campos.status}/>
				<FormItemConsulta bind1={campos.delimitador} bind2={campos.totalDeLinhas}/>
				<FormItemConsulta bind1={campos.entidade}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ImportacaoArquivoUtils.getInstance().novo());
	}
	edit(id) {
		ImportacaoArquivoCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ImportacaoArquivoCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ImportacaoArquivoEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ImportacaoArquivoCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ImportacaoArquivoConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ImportacaoArquivoConsulta.getInstance();
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
		return "ImportacaoArquivo";
	}
	getEntidadePath() {
		return "importacao-arquivo";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ImportacaoArquivoFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
