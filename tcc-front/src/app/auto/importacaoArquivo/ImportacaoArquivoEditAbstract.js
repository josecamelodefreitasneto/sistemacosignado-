/* front-constructor */
import React from 'react';
import ArquivoEdit from '../../cruds/arquivo/ArquivoEdit';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemInput from '../../../antd/form/FormItemInput';
import FormItemSelect from '../../../antd/form/FormItemSelect';
import GroupCard from '../../../fc/components/GroupCard';
import ImportacaoArquivoCampos from '../../cruds/importacaoArquivo/ImportacaoArquivoCampos';
import ImportacaoArquivoErroEdit from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroEdit';
import ImportacaoArquivoErrosCols from '../../cruds/importacaoArquivo/ImportacaoArquivoErrosCols';
import LayoutApp from '../../../fc/components/LayoutApp';
import ObservacaoEdit from '../../cruds/observacao/ObservacaoEdit';
import ObservacaoView from '../../../fc/components/observacao/ObservacaoView';
import RadioBoolean from '../../../antd/form/RadioBoolean';
import Tabela from '../../../fc/components/tabela/Tabela';
import UString from '../../misc/utils/UString';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class ImportacaoArquivoEditAbstract extends FormEdit {

	errosEditCols;
	errosGrupoCols;
	init2() {
		this.errosEditCols = ImportacaoArquivoErrosCols.getInstance().list;
		this.errosGrupoCols = ImportacaoArquivoErrosCols.getInstance().grupos;
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("ImportacaoArquivo", ImportacaoArquivoCampos.getInstance());
	}
	getTabs() {
		return (
			<Tabs
			tabPosition={"top"}
			activeKey={this.state.abaSelecionada}
			defaultActiveKey={"Geral"}
			onChange={s => this.setAbaSelecionada(s)}>
				<TabPane key={"Geral"} tab={"Geral"}>
					{this.abaGeral()}
				</TabPane>
				<TabPane key={"Observações"} tab={"Observações"}>
					{this.abaObservacoes()}
				</TabPane>
			</Tabs>
		);
	}
	abaGeral() {
		if (!UString.equals(this.state.abaSelecionada, "Geral")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				{this.grupo_geral_0(cps)}
				{this.grupo_geral_dadosDeProgresso(cps)}
				{this.grupo_geral_erros(cps)}
			</Row>
		);
	}
	inputArquivo() {
		return this.inputVinculado(this.getCampos().arquivo, 10);
	}
	inputDelimitador() {
		return <FormItemInput bind={this.getCampos().delimitador} lg={4}/>;
	}
	inputAtualizarRegistrosExistentes() {
		return <RadioBoolean bind={this.getCampos().atualizarRegistrosExistentes} lg={10}/>;
	}
	grupo_geral_0(cps) {
		if (!cps.arquivo.isVisible() && !cps.delimitador.isVisible() && !cps.atualizarRegistrosExistentes.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						{this.inputArquivo()}
						{this.inputDelimitador()}
						{this.inputAtualizarRegistrosExistentes()}
					</Row>
				</Card>
			</Col>
		);
	}
	inputEntidade() {
		return <FormItemSelect bind={this.getCampos().entidade} lg={4}/>;
	}
	inputStatus() {
		return <FormItemInput bind={this.getCampos().status} lg={4}/>;
	}
	inputTotalDeLinhas() {
		return <FormItemInput bind={this.getCampos().totalDeLinhas} lg={4}/>;
	}
	inputProcessadosComSucesso() {
		return <FormItemInput bind={this.getCampos().processadosComSucesso} lg={4}/>;
	}
	inputProcessadosComErro() {
		return <FormItemInput bind={this.getCampos().processadosComErro} lg={4}/>;
	}
	inputArquivoDeErros() {
		return this.inputVinculado(this.getCampos().arquivoDeErros, 4);
	}
	grupo_geral_dadosDeProgresso(cps) {
		if (!cps.entidade.isVisible() && !cps.status.isVisible() && !cps.totalDeLinhas.isVisible() && !cps.processadosComSucesso.isVisible() && !cps.processadosComErro.isVisible() && !cps.arquivoDeErros.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Dados de Progresso"}>
					<Row gutter={24}>
						{this.inputEntidade()}
						{this.inputStatus()}
						{this.inputTotalDeLinhas()}
						{this.inputProcessadosComSucesso()}
						{this.inputProcessadosComErro()}
						{this.inputArquivoDeErros()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	grupo_geral_erros(cps) {
		if (!cps.erros.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Erros"}>
					<Row gutter={24}>
						<Tabela
						bind={cps.erros}
						colunas={this.errosEditCols}
						colunasGrupo={this.errosGrupoCols}
						formKeyDown={this}
						/>
					</Row>
				</GroupCard>
			</Col>
		);
	}
	abaObservacoes() {
		if (!UString.equals(this.state.abaSelecionada, "Observações")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				<ObservacaoView campos={cps}/>
			</Row>
		);
	}
	getTitleImpl() {
		return "Importação Arquivo";
	}
	getModal() {
		const cps = this.getCampos();
		if (cps.observacoes.isTrue()) {
			return (
				<ObservacaoEdit
				vinculo={cps.observacoes}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.arquivo.isTrue()) {
			return (
				<ArquivoEdit
				tamanhoMaximoEmBytes={5242880}
				mensagemTamanho={"O tamanho máximo não deve exceder 5.242.880 bytes"}
				mensagemExtensoes={"São aceitos quaisquer tipos de arquivo"}
				vinculo={cps.arquivo}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.arquivoDeErros.isTrue()) {
			return (
				<ArquivoEdit
				tamanhoMaximoEmBytes={5242880}
				mensagemTamanho={"O tamanho máximo não deve exceder 5.242.880 bytes"}
				extensoes={["csv"]}
				mensagemExtensoes={"São aceitos os arquivos do tipo csv"}
				vinculo={cps.arquivoDeErros}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.erros.isTrue()) {
			return (
				<ImportacaoArquivoErroEdit
				vinculo={cps.erros}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		throw new Error("???");
	}
	getCampos() {
		return super.getCampos();
	}
	isAuditada() {
		return true;
	}
	init3() {}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}

ImportacaoArquivoEditAbstract.defaultProps = FormEdit.defaultProps;
