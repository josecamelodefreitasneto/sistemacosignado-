/* front-constructor */
import React from 'react';
import EntityCampos from '../../../fc/components/EntityCampos';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoCols from '../../cruds/observacao/ObservacaoCols';
import ObservacaoConsulta from '../../cruds/observacao/ObservacaoConsulta';
import ObservacaoEdit from '../../cruds/observacao/ObservacaoEdit';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Tabela from '../../../fc/components/tabela/Tabela';
import {Fragment} from 'react';

export default class ObservacaoFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Observação";
		this.componentDidMount4();
		this.normalCols = ObservacaoCols.getInstance().list;
		this.grupoCols = ObservacaoCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return ObservacaoConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.anexo} bind2={campos.texto}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(ObservacaoUtils.getInstance().novo());
	}
	edit(id) {
		ObservacaoCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <ObservacaoEdit onDelete={idP => this.delete(idP)} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	delete(idP) {
		if (!this.permissao.delete()) return;
		EntityCampos.excluir(ObservacaoCampos.getInstance().getEntidadePath(), idP, () => {
			let cps = ObservacaoConsulta.getInstance();
			let o = cps.dados.getItens().byId(idP);
			cps.dados.remove(o);
		});
	}
	onDeleteFunction() {
		if (!this.permissao.delete()) return null;
		return o => this.delete(o.getId());
	}
	getTable() {
		const cps = ObservacaoConsulta.getInstance();
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
		return "Observacao";
	}
	getEntidadePath() {
		return "observacao";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

ObservacaoFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
