/* front-constructor */
import React from 'react';
import BindingCamposAlterados from '../outros/BindingCamposAlterados';
import Coluna from './tabela/Coluna';
import FcBotao from './FcBotao';
import FormEditButtons from './FormEditButtons';
import FormGenerico from './FormGenerico';
import LayoutApp from './LayoutApp';
import Tabela from './tabela/Tabela';
import TextAlign from '../../app/misc/consts/enums/TextAlign';
import UString from '../../app/misc/utils/UString';

export default class ModalCamposAlterados extends FormGenerico {

	ehModal() {
		return true;
	}

	getTitle() {
		return "Campos Alterados";
	}

	getWidthModal() {
		return 65;
	}

	getBody() {
		const bind = BindingCamposAlterados.getInstance();
		bind.addItens(this.props.valores);
		return <Tabela bind={bind} colunas={ModalCamposAlterados.colunas} formKeyDown={this}/>;
	}

	getFooter() {
		const style = LayoutApp.createStyle().widthPercent(100).padding(10).get();
		return (
			<div style={style}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} onClick={this.props.onClose} title={"Fechar (Esc)"}/>
			</div>
		);
	}

	close() {
		this.props.onClose();
	}
	setWidthForm = o => this.setState({widthForm:o});
}
ModalCamposAlterados.COL_NOME = new Coluna(35, "Campo", o => o.campo, TextAlign.left).setSort((a, b) => UString.compare(a.campo, a.campo)).setGrupo(false);
ModalCamposAlterados.COL_DE = new Coluna(90, "De", o => o.de, TextAlign.left).setSort((a, b) => UString.compare(a.de, b.de)).setGrupo(false);
ModalCamposAlterados.COL_PARA = new Coluna(90, "Para", o => o.para, TextAlign.left).setSort((a, b) => UString.compare(a.para, b.para)).setGrupo(false);
ModalCamposAlterados.colunas = [ModalCamposAlterados.COL_NOME, ModalCamposAlterados.COL_DE, ModalCamposAlterados.COL_PARA];

ModalCamposAlterados.defaultProps = FormGenerico.defaultProps;
