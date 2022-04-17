/* front-constructor */
import React from 'react';
import FormConsulta from '../../../fc/components/FormConsulta';
import FormItemConsulta from '../../../fc/components/FormItemConsulta';
import Tabela from '../../../fc/components/tabela/Tabela';
import UsuarioCampos from '../../cruds/usuario/UsuarioCampos';
import UsuarioCols from '../../cruds/usuario/UsuarioCols';
import UsuarioConsulta from '../../cruds/usuario/UsuarioConsulta';
import UsuarioEdit from '../../cruds/usuario/UsuarioEdit';
import UsuarioUtils from '../../cruds/usuario/UsuarioUtils';
import {Fragment} from 'react';

export default class UsuarioFormConsultaAbstract extends FormConsulta {

	normalCols;
	grupoCols;
	componentDidMount3() {
		this.titulo = "Usuário";
		this.componentDidMount4();
		this.normalCols = UsuarioCols.getInstance().list;
		this.grupoCols = UsuarioCols.getInstance().grupos;
	}
	componentDidMount4() {}
	getFiltros() {
		return UsuarioConsulta.getInstance();
	}
	getRenderFiltros() {
		const campos = this.getFiltros();
		return (
			<Fragment>
				<FormItemConsulta bind1={campos.login} bind2={campos.nome}/>
				<FormItemConsulta bind1={campos.excluido} bind2={campos.registroBloqueado}/>
			</Fragment>
		);
	}
	novo() {
		this.setEdit(UsuarioUtils.getInstance().novo());
	}
	edit(id) {
		UsuarioCampos.getInstance().edit(id, o => this.setEdit(o));
	}
	setEdit(o) {
		UsuarioCampos.getInstance().setCampos(o);
		this.setShowEdit(true);
	}
	renderEdit() {
		return <UsuarioEdit somenteUpdate={true} onClose={() => this.setShowEdit(false)} isModal={true}/>;
	}
	getTable() {
		const cps = UsuarioConsulta.getInstance();
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
		return "Usuario";
	}
	getEntidadePath() {
		return "usuario";
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});
}

UsuarioFormConsultaAbstract.defaultProps = FormConsulta.defaultProps;
