/* front-constructor */
import React from 'react';
import BotaoType from '../../../antd/BotaoType';
import Color from '../../../app/misc/consts/fixeds/Color';
import Coluna from '../tabela/Coluna';
import FcBotao from '../FcBotao';
import FormEditButtons from '../FormEditButtons';
import FormGenerico from '../FormGenerico';
import LayoutApp from '../LayoutApp';
import Loading from '../../../antd/Loading';
import ModalCamposAlterados from '../ModalCamposAlterados';
import Tabela from '../tabela/Tabela';
import TextAlign from '../../../app/misc/consts/enums/TextAlign';
import UInteger from '../../../app/misc/utils/UInteger';
import UString from '../../../app/misc/utils/UString';
import {Fragment} from 'react';

export default class ModalAuditoria extends FormGenerico {

	rd() {
		if (this.props.campos.alditoria.carregado()) {
			return this.render0();
		} else if (UString.notEmpty(this.props.campos.alditoria.getMensagemErro())) {
			const s = "Ocorreu um erro no servico: " + this.props.campos.alditoria.getMensagemErro();
			return <span style={ModalAuditoria.STYLE_ERROR.get()}>{s}</span>;
		} else {
			setTimeout(() => this.props.campos.alditoria.carregar(), 250);
			return <Loading/>;
		}
	}

	render0() {
		return (
			<Tabela
			bind={this.props.campos.alditoria}
			colunas={ModalAuditoria.COLUNAS}
			onClick={o => this.edit(o)}
			funcGetEditIcon={o => {
				if (UInteger.equals(o.idTipo, 2)) {
					return Tabela.ICONE_SEARCH;
				} else {
					return Tabela.ICONE_VAZIO;
				}
			}}
			/>
		);
	}

	edit(o) {
		if (UInteger.equals(o.idTipo, 2)) {
			this.props.campos.detalharAuditoria(o);
		}
	}

	getTitle() {
		return "Auditoria";
	}

	getBody() {
		return (
			<Fragment>
				{this.rd()}
				{this.getModal()}
			</Fragment>
		);
	}

	getModal() {
		if (this.props.campos.alditoria.isTrue()) {
			return <ModalCamposAlterados valores={this.props.campos.auditoriaItem.alteracoes} onClose={() => this.props.campos.alditoria.set(false)}/>;
		} else {
			return null;
		}
	}

	getFooter() {
		return (
			<div style={ModalAuditoria.STYLE_FOOTER}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.normal} onClick={() => this.close()} title={"Sair (Esc)"}/>
			</div>
		);
	}

	ehModal() {
		return true;
	}

	close() {
		this.props.campos.modalAuditoria.set(false);
	}

	componentDidMount2() {
		this.observar(this.props.campos.alditoria);
	}
	setWidthForm = o => this.setState({widthForm:o});
}
ModalAuditoria.STYLE_FOOTER = LayoutApp.createStyle().widthPercent(100).padding(10).paddingRight(20).get();
ModalAuditoria.TIPO = new Coluna(15, "Ação", o => o.tipo, TextAlign.center).setId("AuditoriaView-Cols-tipo");
ModalAuditoria.USUARIO = new Coluna(34, "Usuário", o => o.usuario, TextAlign.center).setId("AuditoriaView-Cols-usuario");
ModalAuditoria.DATA = new Coluna(15, "Data/Hora", o => o.data, TextAlign.center).setId("AuditoriaView-Cols-data");
ModalAuditoria.TEMPO = new Coluna(15, "Tempo", o => o.tempo, TextAlign.center).setId("AuditoriaView-Cols-tempo");
ModalAuditoria.COLUNAS = [ModalAuditoria.TIPO, ModalAuditoria.USUARIO, ModalAuditoria.DATA, ModalAuditoria.TEMPO];
ModalAuditoria.STYLE_ERROR = LayoutApp.createStyle().margin(10).bold(true).color(Color.red);

ModalAuditoria.defaultProps = FormGenerico.defaultProps;
