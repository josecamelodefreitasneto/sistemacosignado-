/* tcc-java */
import React from 'react';
import Afters from '../../../antd/form/Afters';
import BotaoSize from '../../../antd/BotaoSize';
import BotaoType from '../../../antd/BotaoType';
import ClienteCampos from '../../cruds/cliente/ClienteCampos';
import ClienteRubricaEdit from '../../cruds/clienteRubrica/ClienteRubricaEdit';
import ClienteRubricasCols from '../../cruds/cliente/ClienteRubricasCols';
import ClienteSimulacaoEdit from '../../cruds/clienteSimulacao/ClienteSimulacaoEdit';
import ClienteSimulacoesCols from '../../cruds/cliente/ClienteSimulacoesCols';
import FcBotao from '../../../fc/components/FcBotao';
import FormEdit from '../../../fc/components/FormEdit';
import FormItemButton from '../../../antd/form/FormItemButton';
import FormItemInput from '../../../antd/form/FormItemInput';
import FormItemSelect from '../../../antd/form/FormItemSelect';
import GroupCard from '../../../fc/components/GroupCard';
import ObservacaoEdit from '../../cruds/observacao/ObservacaoEdit';
import ObservacaoView from '../../../fc/components/observacao/ObservacaoView';
import Session from '../../estado/Session';
import Tabela from '../../../fc/components/tabela/Tabela';
import TelefoneEdit from '../../cruds/telefone/TelefoneEdit';
import UString from '../../misc/utils/UString';
import {Col} from 'antd';
import {Row} from 'antd';
import {Tabs} from 'antd';
const TabPane = Tabs.TabPane;

export default class ClienteEditAbstract extends FormEdit {

	rubricasEditCols;
	rubricasGrupoCols;
	simulacoesEditCols;
	simulacoesGrupoCols;
	init2() {
		this.rubricasEditCols = ClienteRubricasCols.getInstance().list;
		this.rubricasGrupoCols = ClienteRubricasCols.getInstance().grupos;
		this.simulacoesEditCols = ClienteSimulacoesCols.getInstance().list;
		this.simulacoesGrupoCols = ClienteSimulacoesCols.getInstance().grupos;
		this.init3();
	}
	constructor(props) {
		super(props);
		this.init("Cliente", ClienteCampos.getInstance());
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
				<TabPane key={"Contatos"} tab={"Contatos"}>
					{this.abaContatos()}
				</TabPane>
				<TabPane key={"Financeiro"} tab={"Financeiro"}>
					{this.abaFinanceiro()}
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
				{this.grupo_geral_dadosPessoais(cps)}
				{this.grupo_geral_situacao(cps)}
				{this.grupo_geral_dadosFuncionais(cps)}
				{this.grupo_geral_dadosBancarios(cps)}
			</Row>
		);
	}
	inputNome() {
		return <FormItemInput bind={this.getCampos().nome} lg={14}/>;
	}
	inputCpf() {
		return <FormItemInput bind={this.getCampos().cpf} lg={6}/>;
	}
	inputDataDeNascimento() {
		return this.inputData(this.getCampos().dataDeNascimento, 4);
	}
	grupo_geral_dadosPessoais(cps) {
		if (!cps.nome.isVisible() && !cps.cpf.isVisible() && !cps.dataDeNascimento.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Dados Pessoais"}>
					<Row gutter={24}>
						{this.inputNome()}
						{this.inputCpf()}
						{this.inputDataDeNascimento()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	inputStatus() {
		return <FormItemInput bind={this.getCampos().status} lg={12}/>;
	}
	inputAtendenteResponsavel() {
		return <FormItemSelect bind={this.getCampos().atendenteResponsavel} lg={12}/>;
	}
	grupo_geral_situacao(cps) {
		if (!cps.status.isVisible() && !cps.atendenteResponsavel.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Situação"}>
					<Row gutter={24}>
						{this.inputStatus()}
						{this.inputAtendenteResponsavel()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	inputTipo() {
		return <FormItemSelect bind={this.getCampos().tipo} lg={6}/>;
	}
	inputMatricula() {
		return <FormItemInput bind={this.getCampos().matricula} lg={6}/>;
	}
	inputOrgao() {
		return <FormItemSelect bind={this.getCampos().orgao} lg={12}/>;
	}
	grupo_geral_dadosFuncionais(cps) {
		if (!cps.tipo.isVisible() && !cps.matricula.isVisible() && !cps.orgao.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Dados Funcionais"}>
					<Row gutter={24}>
						{this.inputTipo()}
						{this.inputMatricula()}
						{this.inputOrgao()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	inputBanco() {
		return <FormItemSelect bind={this.getCampos().banco} lg={8}/>;
	}
	inputAgencia() {
		return <FormItemInput bind={this.getCampos().agencia} lg={8}/>;
	}
	inputNumeroDaConta() {
		return <FormItemInput bind={this.getCampos().numeroDaConta} lg={8}/>;
	}
	grupo_geral_dadosBancarios(cps) {
		if (!cps.banco.isVisible() && !cps.agencia.isVisible() && !cps.numeroDaConta.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Dados Bancários"}>
					<Row gutter={24}>
						{this.inputBanco()}
						{this.inputAgencia()}
						{this.inputNumeroDaConta()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	abaContatos() {
		if (!UString.equals(this.state.abaSelecionada, "Contatos")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				{this.grupo_contatos_contatos(cps)}
				{this.grupo_contatos_endereco(cps)}
			</Row>
		);
	}
	inputTelefonePrincipal() {
		return this.inputVinculado(this.getCampos().telefonePrincipal, 8);
	}
	inputTelefoneSecundario() {
		return this.inputVinculado(this.getCampos().telefoneSecundario, 8);
	}
	inputEmail() {
		return <FormItemInput after={Afters.getEmail()} bind={this.getCampos().email} lg={8}/>;
	}
	grupo_contatos_contatos(cps) {
		if (!cps.telefonePrincipal.isVisible() && !cps.telefoneSecundario.isVisible() && !cps.email.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Contatos"}>
					<Row gutter={24}>
						{this.inputTelefonePrincipal()}
						{this.inputTelefoneSecundario()}
						{this.inputEmail()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	inputCep() {
		return <FormItemSelect bind={this.getCampos().cep} lg={6}/>;
	}
	inputUf() {
		return <FormItemInput bind={this.getCampos().uf} lg={6}/>;
	}
	inputCidade() {
		return <FormItemInput bind={this.getCampos().cidade} lg={6}/>;
	}
	inputBairro() {
		return <FormItemInput bind={this.getCampos().bairro} lg={6}/>;
	}
	inputLogradouro() {
		return <FormItemInput bind={this.getCampos().logradouro} lg={12}/>;
	}
	inputComplemento() {
		return <FormItemInput bind={this.getCampos().complemento} lg={12}/>;
	}
	grupo_contatos_endereco(cps) {
		if (!cps.cep.isVisible() && !cps.uf.isVisible() && !cps.cidade.isVisible() && !cps.bairro.isVisible() && !cps.logradouro.isVisible() && !cps.complemento.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Endereço"}>
					<Row gutter={24}>
						{this.inputCep()}
						{this.inputUf()}
						{this.inputCidade()}
						{this.inputBairro()}
					</Row>
					<Row gutter={24}>
						{this.inputLogradouro()}
						{this.inputComplemento()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	abaFinanceiro() {
		if (!UString.equals(this.state.abaSelecionada, "Financeiro")) {
			return null;
		}
		const cps = this.getCampos();
		return (
			<Row gutter={24}>
				{this.grupo_financeiro_rubricas(cps)}
				{this.grupo_financeiro_simulacao(cps)}
				{this.grupo_financeiro_simulacoes(cps)}
			</Row>
		);
	}
	botaoNovoRubricas() {
		if (Session.canInsert("ClienteRubrica")) {
			return <FcBotao title={"+ Novo"} type={BotaoType.normal} size={BotaoSize.small} onClick={() => this.getCampos().rubricasNovo()}/>;
		} else {
			return null;
		}
	}
	onDeleteRubricas() {
		if (Session.canDelete("ClienteRubrica")) {
			return o => {
				o.setExcluido(true);
				this.getCampos().rubricas.notifyObservers();
			};
		} else {
			return null;
		}
	}
	grupo_financeiro_rubricas(cps) {
		if (!cps.rubricas.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Rubricas"} extra={this.botaoNovoRubricas()}>
					<Row gutter={24}>
						<Tabela
						bind={cps.rubricas}
						onClick={o => cps.rubricasEdit(o)}
						onDelete={this.onDeleteRubricas()}
						colunas={this.rubricasEditCols}
						colunasGrupo={this.rubricasGrupoCols}
						formKeyDown={this}
						/>
					</Row>
				</GroupCard>
			</Col>
		);
	}
	inputRendaBruta() {
		return <FormItemInput bind={this.getCampos().rendaBruta} lg={3}/>;
	}
	inputRendaLiquida() {
		return <FormItemInput bind={this.getCampos().rendaLiquida} lg={3}/>;
	}
	inputMargem() {
		return <FormItemInput bind={this.getCampos().margem} lg={3}/>;
	}
	inputTipoDeSimulacao() {
		return <FormItemSelect bind={this.getCampos().tipoDeSimulacao} lg={6}/>;
	}
	inputValorDeSimulacao() {
		return <FormItemInput bind={this.getCampos().valorDeSimulacao} lg={3}/>;
	}
	inputDia() {
		return <FormItemSelect bind={this.getCampos().dia} lg={3}/>;
	}
	botaoCalcular() {
		if (this.getCampos().calcular.isTrue() && !this.getCampos().houveMudancas()) {
			return <FormItemButton bind={this.getCampos().botaoCalcular}/>;
		} else {
			return null;
		}
	}
	grupo_financeiro_simulacao(cps) {
		if (!cps.rendaBruta.isVisible() && !cps.rendaLiquida.isVisible() && !cps.margem.isVisible() && !cps.tipoDeSimulacao.isVisible() && !cps.valorDeSimulacao.isVisible() && !cps.dia.isVisible() && !cps.calcular.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Simulação"}>
					<Row gutter={24}>
						{this.inputRendaBruta()}
						{this.inputRendaLiquida()}
						{this.inputMargem()}
						{this.inputTipoDeSimulacao()}
						{this.inputValorDeSimulacao()}
						{this.inputDia()}
						{this.botaoCalcular()}
					</Row>
				</GroupCard>
			</Col>
		);
	}
	onDeleteSimulacoes() {
		if (Session.canDelete("ClienteSimulacao")) {
			return o => {
				o.setExcluido(true);
				this.getCampos().simulacoes.notifyObservers();
			};
		} else {
			return null;
		}
	}
	grupo_financeiro_simulacoes(cps) {
		if (!cps.simulacoes.isVisible()) {
			return null;
		}
		return (
			<Col lg={24} md={24} sm={24}>
				<GroupCard title={"Simulacões"}>
					<Row gutter={24}>
						<Tabela
						bind={cps.simulacoes}
						onClick={o => cps.simulacoesEdit(o)}
						onDelete={this.onDeleteSimulacoes()}
						colunas={this.simulacoesEditCols}
						colunasGrupo={this.simulacoesGrupoCols}
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
		return "Cliente";
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
		if (cps.telefonePrincipal.isTrue()) {
			return (
				<TelefoneEdit
				vinculo={cps.telefonePrincipal}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.telefoneSecundario.isTrue()) {
			return (
				<TelefoneEdit
				vinculo={cps.telefoneSecundario}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.rubricas.isTrue()) {
			return (
				<ClienteRubricaEdit
				vinculo={cps.rubricas}
				somenteUpdate={false}
				isModal={true}
				/>
			);
		}
		if (cps.simulacoes.isTrue()) {
			return (
				<ClienteSimulacaoEdit
				vinculo={cps.simulacoes}
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

ClienteEditAbstract.defaultProps = FormEdit.defaultProps;
