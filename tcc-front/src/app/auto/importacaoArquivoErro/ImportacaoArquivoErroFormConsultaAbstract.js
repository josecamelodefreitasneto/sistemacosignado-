/* front-constructor */
import React from 'react';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import ImportacaoArquivoErroCampos from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroCampos';
import ImportacaoArquivoErroCols from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroCols';
import ImportacaoArquivoErroConsulta from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroConsulta';
import ImportacaoArquivoErroEdit from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroEdit';
import ImportacaoArquivoErroUtils from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroUtils';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ImportacaoArquivoErroFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Importação Arquivo Erro";
		this.componentDidMount4();
		this.normalCols = ImportacaoArquivoErroCols.getInstance().list;
		this.grupoCols = ImportacaoArquivoErroCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ImportacaoArquivoErroConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.erro} bind2={campos.linha}/>
				<FormItemConsulta bind1={campos.importacaoArquivo}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ImportacaoArquivoErroUtils.getInstance().novo());
	}
	edit(id) {
		ImportacaoArquivoErroCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ImportacaoArquivoErroCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ImportacaoArquivoErroEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ImportacaoArquivoErroCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ImportacaoArquivoErroConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ImportacaoArquivoErroConsulta.getInstance();
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
		return "ImportacaoArquivoErro";
	}
	getEntidadePath() {
		return "importacao-arquivo-erro";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ImportacaoArquivoErroFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
