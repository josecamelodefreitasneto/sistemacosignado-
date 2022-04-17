/* front-constructor */
import React from 'react';
import AlignItens from '../../app/misc/consts/enums/AlignItens';
import CommonStyles from '../../app/misc/styles/CommonStyles';
import FcBotao from './FcBotao';
import FormGenerico from './FormGenerico';
import ImportacaoArquivoForm from '../../app/cruds/importacaoArquivo/ImportacaoArquivoForm';
import InputBind from '../../antd/InputBind';
import LayoutApp from './LayoutApp';
import Permissao from './Permissao';
import Session from '../../app/estado/Session';
import Style from '../../app/misc/utils/Style';
import TextAlign from '../../app/misc/consts/enums/TextAlign';
import UString from '../../app/misc/utils/UString';
import {Col} from 'antd';
import {Fragment} from 'react';
import {Row} from 'antd';

export default class FormConsulta extends FormGenerico {
	constructor(props){
		super(props);
		this.state.showEdit = false;
		this.state.showImportarArquivo = false;
		this.state.maisFiltros = false;
	}
	columns = [];
	titulo;
	widthTotal = 0;
	permissao;

	getTitle() {
		return this.titulo;
	}

	getFooter() {
		return null;
	}

	addColumn(s, key, width, funcRender) {
		this.columns.add({title: s, dataIndex: key, key: key, width: width, render: funcRender});
		this.widthTotal += width;
	}

	componentDidMount2() {
		this.permissao = new Permissao(this.getEntidade());
		this.componentDidMount3();
	}

	getIdEntidade() {
		return Session.getInstance().getPermissao(this.getEntidade()).id;
	}

	getBody() {

		if (!this.permissao.read()) {
			return this.acessoNegado();
		}

		return (
			<div style={FormConsulta.STYLE_CONTEUDO.get()}>
				{this.renderBotoesSuperiores()}
				{this.renderFiltros()}
				{this.renderTable()}
				{this.getModal()}
				{this.getBodyComplemento()}
			</div>
		);
	}

	acessoNegado() {
		return <span>Acesso Negado!</span>;
	}

	getBodyComplemento() {
		return null;
	}

	renderTable() {
		return (
			<div style={FormConsulta.STYLE_DIV_TABLE.get()}>
				{this.getTable()}
			</div>
		);
	}

	renderBotoesSuperiores() {
		return (
			<table style={CommonStyles.W100P.get()}>
				<thead>
					<tr>
						<td style={FormConsulta.TD_BUTTONS_LEFT}>
							<InputBind
								bind={this.getFiltros().getBusca()}
								onKeyPress={event => {
									if (UString.equals("Enter", event.key)) {
										this.getFiltros().consultar();
									}
								}}
								style={FormConsulta.STYLE_INPUT_SEARCH}
							/>
							<FcBotao
								title={"Consultar"}
								onClick={() => this.getFiltros().consultar()}
								style={FormConsulta.STYLE_BOTAO_SEARCH}
							/>
						</td>
						<td style={FormConsulta.TD_BUTTONS_RIGHT}>
							{this.getBotaoNovo()}
						</td>
					</tr>
				</thead>
			</table>
		);
	}

	getBotaoNovo() {
		if (this.permissao.insert()) {
			return (
				<Fragment>
					<FcBotao
						title={"Importar"}
						onClick={() => this.setShowImportarArquivo(true)}
						style={FormConsulta.STYLE_BOTAO_MAIS_FILTROS}
					/>
					<FcBotao
						title={"Novo"}
						onClick={() => this.novo()}
						style={FormConsulta.STYLE_BOTAO_MAIS_FILTROS}
					/>
				</Fragment>
			);
		} else {
			return null;
		}
	}

	getModal() {
		if (this.state.showEdit) {
			return this.renderEdit();
		} else if (this.state.showImportarArquivo) {
			return (
				<ImportacaoArquivoForm
					idEntidade={this.getIdEntidade()}
					pathEntidade={this.getEntidadePath()}
					nomeEntidade={this.getEntidade()}
					onClose={() => this.setShowImportarArquivo(false)}
				/>
			);
		} else {
			return null;
		}
	}

	renderFiltros() {
		if (this.state.maisFiltros) {
			return <div style={LayoutApp.createStyle().padding(20).get()}>{this.getRenderFiltros()}</div>;
		} else {
			return null;
		}
	}

	d(s) {
		return <td className={"ant-table-selection-column"}>{s}</td>;
	}

	r(o) {
		return <tr className={"ant-table-row ant-table-row-level-0"}>{o}</tr>;
	}

	newFiltro(title, bd) {
		return (
			<Row gutter={0}>
				<Col lg={8}>
					<span>{title}</span>
				</Col>
				<Col lg={16}>{bd}</Col>
			</Row>
		);
	}

	cancelar() {
		if (!this.state.showEdit) {
			super.cancelar();
		}
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});

}
FormConsulta.TD_BUTTONS_LEFT = Style.create().alignItems(AlignItens.flexStart).textAlign(TextAlign.left).paddingTop(10).paddingBottom(10).paddingLeft(20).get();
FormConsulta.TD_BUTTONS_RIGHT = Style.create().alignItems(AlignItens.flexEnd).textAlign(TextAlign.right).paddingTop(10).paddingBottom(10).paddingRight(20).get();
FormConsulta.STYLE_DIV_TABLE = LayoutApp.createStyle().widthPercent(100).marginTop(20);
FormConsulta.STYLE_BOTAO_SEARCH = LayoutApp.createStyle().marginLeft(10);
FormConsulta.STYLE_BOTAO_MAIS_FILTROS = LayoutApp.createStyle().marginLeft(10);
FormConsulta.STYLE_INPUT_SEARCH = LayoutApp.createStyle().width(300);
FormConsulta.STYLE_CONTEUDO = LayoutApp.createStyle().textAlign(TextAlign.center);

FormConsulta.defaultProps = FormGenerico.defaultProps;
