/* tcc-java */
import BancoConstantes from '../banco/BancoConstantes';
import Binding from '../../campos/support/Binding';
import ClienteConsulta from '../../cruds/cliente/ClienteConsulta';
import ClienteRubrica from '../../cruds/clienteRubrica/ClienteRubrica';
import ClienteRubricaCampos from '../../cruds/clienteRubrica/ClienteRubricaCampos';
import ClienteRubricaUtils from '../../cruds/clienteRubrica/ClienteRubricaUtils';
import ClienteSimulacao from '../../cruds/clienteSimulacao/ClienteSimulacao';
import ClienteSimulacaoCampos from '../../cruds/clienteSimulacao/ClienteSimulacaoCampos';
import ClienteSimulacaoUtils from '../../cruds/clienteSimulacao/ClienteSimulacaoUtils';
import ClienteStatusConstantes from '../clienteStatus/ClienteStatusConstantes';
import ClienteTipoConstantes from '../clienteTipo/ClienteTipoConstantes';
import ClienteTipoSimulacaoConstantes from '../clienteTipoSimulacao/ClienteTipoSimulacaoConstantes';
import ClienteUtils from '../../cruds/cliente/ClienteUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import IndiceConstantes from '../indice/IndiceConstantes';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import OrgaoConstantes from '../orgao/OrgaoConstantes';
import Post from '../../../projeto/Post';
import Sessao from '../../../projeto/Sessao';
import Telefone from '../../cruds/telefone/Telefone';
import TelefoneCampos from '../../cruds/telefone/TelefoneCampos';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteCamposAbstract extends EntityCampos {

	nome;
	cpf;
	dataDeNascimento;
	status;
	atendenteResponsavel;
	tipo;
	matricula;
	orgao;
	banco;
	agencia;
	numeroDaConta;
	telefonePrincipal;
	telefoneSecundario;
	email;
	cep;
	uf;
	cidade;
	bairro;
	logradouro;
	complemento;
	rendaBruta;
	rendaLiquida;
	margem;
	tipoDeSimulacao;
	valorDeSimulacao;
	dia;
	calcular;
	rubricas;
	simulacoes;
	original;
	to;
	botaoCalcular;
	getEntidade() {
		return "Cliente";
	}
	getEntidadePath() {
		return "cliente";
	}
	initImpl() {
		this.nome = this.newNomeProprio("Nome", true, "Geral");
		this.cpf = this.newCpf("CPF", true, "Geral");
		this.dataDeNascimento = this.newData("Data de Nascimento", false, "Geral");
		this.status = this.newList("Status", ClienteStatusConstantes.getList(), true, "Geral").setDisabled(true);
		this.atendenteResponsavel = this.newFk("Atendente Responsável","atendente", false, "Geral");
		this.tipo = this.newList("Tipo", ClienteTipoConstantes.getList(), true, "Geral");
		this.matricula = this.newString("Matrícula", 50, false, "Geral");
		this.orgao = this.newList("Orgão", OrgaoConstantes.getList(), false, "Geral");
		this.banco = this.newList("Banco", BancoConstantes.getList(), false, "Geral");
		this.agencia = this.newString("Agência", 50, false, "Geral");
		this.numeroDaConta = this.newString("Número da Conta", 50, false, "Geral");
		this.telefonePrincipal = this.newVinculado("Telefone Principal", false, "Contatos");
		this.telefoneSecundario = this.newVinculado("Telefone Secundário", false, "Contatos");
		this.email = this.newEmail("E-mail", false, "Contatos");
		this.cep = this.newFk("Cep","cep", false, "Contatos");
		this.uf = this.newString("UF", 50, false, "Contatos").setDisabled(true);
		this.cidade = this.newString("Cidade", 50, false, "Contatos").setDisabled(true);
		this.bairro = this.newString("Bairro", 50, false, "Contatos").setDisabled(true);
		this.logradouro = this.newString("Logradouro", 50, false, "Contatos").setDisabled(true);
		this.complemento = this.newString("Complemento", 50, false, "Contatos");
		this.rendaBruta = this.newMoney("Renda Bruta", 7, true, false, "Financeiro").setDisabled(true);
		this.rendaLiquida = this.newMoney("Renda Liquida", 7, true, false, "Financeiro").setDisabled(true);
		this.margem = this.newMoney("Margem", 7, true, false, "Financeiro").setDisabled(true);
		this.tipoDeSimulacao = this.newList("Tipo de Simulação", ClienteTipoSimulacaoConstantes.getList(), false, "Financeiro");
		this.valorDeSimulacao = this.newMoney("Valor de Simulação", 7, true, false, "Financeiro");
		this.dia = this.newList("Dia", IndiceConstantes.getList(), false, "Financeiro");
		this.calcular = this.newBoolean("Calcular", false, "Financeiro").setDisabled(true);
		this.rubricas = this.newSubList(
			"Rubricas", "rubricas"
			, (de, para) => ClienteRubricaUtils.getInstance().merge(de, para)
			, obj => {
				let array = obj;
				this.original.setRubricas([]);
				array.forEach(json => this.original.getRubricas().add(ClienteRubricaUtils.getInstance().fromJson(json)));
				this.setRubricas();
				if (UCommons.notEmpty(this.original.getOriginal())) {
					this.original.getOriginal().setRubricas(ClienteRubricaUtils.getInstance().clonarList(this.original.getRubricas()));
				}
			}
			, false, "Financeiro", false
		);
		this.simulacoes = this.newSubList(
			"Simulacões", "simulacoes"
			, (de, para) => ClienteSimulacaoUtils.getInstance().merge(de, para)
			, obj => {
				let array = obj;
				this.original.setSimulacoes([]);
				array.forEach(json => this.original.getSimulacoes().add(ClienteSimulacaoUtils.getInstance().fromJson(json)));
				this.setSimulacoes();
				if (UCommons.notEmpty(this.original.getOriginal())) {
					this.original.getOriginal().setSimulacoes(ClienteSimulacaoUtils.getInstance().clonarList(this.original.getSimulacoes()));
				}
			}
			, false, "Financeiro", false
		);
		this.telefonePrincipal.addFunctionObserver(() => {
			if (!this.disabledObservers && this.telefonePrincipal.isTrue()) {
				let o = this.to.getTelefonePrincipal();
				if (UCommons.isEmpty(o)) {
					o = new Telefone();
				}
				TelefoneCampos.getInstance().setCampos(o);
			}
		});
		this.telefonePrincipal.setOnConfirm(() => {
			this.to.setTelefonePrincipal(TelefoneCampos.getInstance().getTo());
			this.telefonePrincipal.setText(TelefoneCampos.getText(this.to.getTelefonePrincipal()));
		});
		this.telefonePrincipal.setOnClear(() => this.to.setTelefonePrincipal(null));
		this.telefoneSecundario.addFunctionObserver(() => {
			if (!this.disabledObservers && this.telefoneSecundario.isTrue()) {
				let o = this.to.getTelefoneSecundario();
				if (UCommons.isEmpty(o)) {
					o = new Telefone();
				}
				TelefoneCampos.getInstance().setCampos(o);
			}
		});
		this.telefoneSecundario.setOnConfirm(() => {
			this.to.setTelefoneSecundario(TelefoneCampos.getInstance().getTo());
			this.telefoneSecundario.setText(TelefoneCampos.getText(this.to.getTelefoneSecundario()));
		});
		this.telefoneSecundario.setOnClear(() => this.to.setTelefoneSecundario(null));
		this.observacoes = this.createSubList(
			"Observacoes", "observacoes"
			, (de, para) => ObservacaoUtils.getInstance().merge(de, para)
			, obj => {
				let array = obj;
				this.original.setObservacoes([]);
				array.forEach(json => this.original.getObservacoes().add(ObservacaoUtils.getInstance().fromJson(json)));
				this.setObservacoes();
				if (UCommons.notEmpty(this.original.getOriginal())) {
					this.original.getOriginal().setObservacoes(ObservacaoUtils.getInstance().clonarList(this.original.getObservacoes()));
				}
			}
		);
		this.observacoes.setOnConfirm(() => {
			this.observacoes.add(ObservacaoCampos.getInstance().getTo());
			this.refreshObservacoesHouveMudancas();
		});
		this.observacoes.setOnClear(() => {
			this.observacoes.remove(ObservacaoCampos.getInstance().getTo());
			this.refreshObservacoesHouveMudancas();
		});
		this.rubricas.setOnConfirm(() => {
			this.rubricas.add(ClienteRubricaCampos.getInstance().getTo());
			this.refreshRubricasHouveMudancas();
		});
		this.rubricas.setOnClear(() => {
			this.rubricas.remove(ClienteRubricaCampos.getInstance().getTo());
			this.refreshRubricasHouveMudancas();
		});
		this.simulacoes.setOnConfirm(() => {
			this.simulacoes.add(ClienteSimulacaoCampos.getInstance().getTo());
			this.refreshSimulacoesHouveMudancas();
		});
		this.simulacoes.setOnClear(() => {
			this.simulacoes.remove(ClienteSimulacaoCampos.getInstance().getTo());
			this.refreshSimulacoesHouveMudancas();
		});
		this.cep.addFunctionObserver(() => {
			if (this.cep.isVirgin()) {
				return;
			}
			if (this.cep.isEmpty()) {
				this.bairro.clear();
				this.cidade.clear();
				this.logradouro.clear();
				this.uf.clear();
			} else {
				new Post(this.getEntidadePath() + "/cep-lookups", this.cep.getId(), res => {
					let result = res.body;
					this.bairro.set(result.bairro);
					this.cidade.set(result.cidade);
					this.logradouro.set(result.logradouro);
					this.uf.set(result.uf);
				}).run();
			}
		});
		this.botaoCalcular = this.newBotaoFront("Calcular", () => this.calcularClick());
		this.init2();
		this.construido = true;
	}
	setCampos(o) {
		if (UCommons.isEmpty(o)) {
			throw new Error("o === null");
		}
		this.checkInstance();
		Binding.notificacoesDesligadasInc();
		this.disabledObservers = true;
		this.original = o;
		this.to = ClienteUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.agencia.set(this.to.getAgencia());
		this.atendenteResponsavel.setUnique(this.to.getAtendenteResponsavel());
		this.bairro.set(this.to.getBairro());
		this.banco.set(this.to.getBanco());
		this.calcular.set(this.to.getCalcular());
		this.cep.setUnique(this.to.getCep());
		this.cidade.set(this.to.getCidade());
		this.complemento.set(this.to.getComplemento());
		this.cpf.set(this.to.getCpf());
		this.dataDeNascimento.set(this.to.getDataDeNascimento());
		this.dia.set(this.to.getDia());
		this.email.set(this.to.getEmail());
		this.logradouro.set(this.to.getLogradouro());
		this.margem.setDouble(this.to.getMargem());
		this.matricula.set(this.to.getMatricula());
		this.nome.set(this.to.getNome());
		this.numeroDaConta.set(this.to.getNumeroDaConta());
		this.orgao.set(this.to.getOrgao());
		this.rendaBruta.setDouble(this.to.getRendaBruta());
		this.rendaLiquida.setDouble(this.to.getRendaLiquida());
		this.status.set(this.to.getStatus());
		this.telefonePrincipal.setText(TelefoneCampos.getText(o.getTelefonePrincipal()));
		this.telefoneSecundario.setText(TelefoneCampos.getText(o.getTelefoneSecundario()));
		this.tipo.set(this.to.getTipo());
		this.tipoDeSimulacao.set(this.to.getTipoDeSimulacao());
		this.uf.set(this.to.getUf());
		this.valorDeSimulacao.setDouble(this.to.getValorDeSimulacao());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.agencia.setStartValue(this.agencia.get());
		this.atendenteResponsavel.setStartValue(this.atendenteResponsavel.get());
		this.bairro.setStartValue(this.bairro.get());
		this.banco.setStartValue(this.banco.get());
		this.calcular.setStartValue(this.calcular.get());
		this.cep.setStartValue(this.cep.get());
		this.cidade.setStartValue(this.cidade.get());
		this.complemento.setStartValue(this.complemento.get());
		this.cpf.setStartValue(this.cpf.get());
		this.dataDeNascimento.setStartValue(this.dataDeNascimento.get());
		this.dia.setStartValue(this.dia.get());
		this.email.setStartValue(this.email.get());
		this.logradouro.setStartValue(this.logradouro.get());
		this.margem.setStartValue(this.margem.get());
		this.matricula.setStartValue(this.matricula.get());
		this.nome.setStartValue(this.nome.get());
		this.numeroDaConta.setStartValue(this.numeroDaConta.get());
		this.orgao.setStartValue(this.orgao.get());
		this.rendaBruta.setStartValue(this.rendaBruta.get());
		this.rendaLiquida.setStartValue(this.rendaLiquida.get());
		this.rubricas.setStartValue(this.rubricas.get());
		this.simulacoes.setStartValue(this.simulacoes.get());
		this.status.setStartValue(this.status.get());
		this.telefonePrincipal.setStartValue(this.telefonePrincipal.get());
		this.telefoneSecundario.setStartValue(this.telefoneSecundario.get());
		this.tipo.setStartValue(this.tipo.get());
		this.tipoDeSimulacao.setStartValue(this.tipoDeSimulacao.get());
		this.uf.setStartValue(this.uf.get());
		this.valorDeSimulacao.setStartValue(this.valorDeSimulacao.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		this.setRubricas();
		this.setSimulacoes();
		this.setObservacoes();
		let readOnly = this.isReadOnly();
		this.agencia.setDisabled(readOnly);
		this.atendenteResponsavel.setDisabled(readOnly);
		this.banco.setDisabled(readOnly);
		this.cep.setDisabled(readOnly);
		this.complemento.setDisabled(readOnly);
		this.cpf.setDisabled(readOnly);
		this.dataDeNascimento.setDisabled(readOnly);
		this.dia.setDisabled(readOnly);
		this.email.setDisabled(readOnly);
		this.matricula.setDisabled(readOnly);
		this.nome.setDisabled(readOnly);
		this.numeroDaConta.setDisabled(readOnly);
		this.orgao.setDisabled(readOnly);
		this.rubricas.setDisabled(readOnly);
		this.simulacoes.setDisabled(readOnly);
		this.telefonePrincipal.setDisabled(readOnly);
		this.telefoneSecundario.setDisabled(readOnly);
		this.tipo.setDisabled(readOnly);
		this.tipoDeSimulacao.setDisabled(readOnly);
		this.valorDeSimulacao.setDisabled(readOnly);
		if (readOnly) {
			if (UCommons.notEmpty(this.to.getTelefonePrincipal())) {
				this.to.getTelefonePrincipal().setRegistroBloqueado(true);
			}
			if (UCommons.notEmpty(this.to.getTelefoneSecundario())) {
				this.to.getTelefoneSecundario().setRegistroBloqueado(true);
			}
		}
		this.setCampos2(o);
		this.reiniciar();
	}
	setRubricas() {
		if (UCommons.isEmpty(this.original.getRubricas())) {
			this.rubricas.clearItens();
		} else {
			this.rubricas.clearItens2();
			let readOnly = this.isReadOnly();
			this.original.getRubricas().forEach(item => {
				let o = ClienteRubricaUtils.getInstance().clonar(item);
				if (readOnly) o.setRegistroBloqueado(true);
				this.rubricas.add(o);
			});
		}
	}
	setSimulacoes() {
		if (UCommons.isEmpty(this.original.getSimulacoes())) {
			this.simulacoes.clearItens();
		} else {
			this.simulacoes.clearItens2();
			let readOnly = this.isReadOnly();
			this.original.getSimulacoes().forEach(item => {
				let o = ClienteSimulacaoUtils.getInstance().clonar(item);
				if (readOnly) o.setRegistroBloqueado(true);
				this.simulacoes.add(o);
			});
		}
	}
	setObservacoes() {
		if (UCommons.isEmpty(this.original.getObservacoes())) {
			this.observacoes.clearItens();
		} else {
			this.observacoes.clearItens2();
			let readOnly = this.isReadOnly();
			this.original.getObservacoes().forEach(item => {
				let o = ObservacaoUtils.getInstance().clonar(item);
				if (readOnly) o.setRegistroBloqueado(true);
				this.observacoes.add(o);
			});
		}
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setAgencia(this.agencia.get());
		this.to.setAtendenteResponsavel(this.atendenteResponsavel.get());
		this.to.setBairro(this.bairro.get());
		this.to.setBanco(this.banco.get());
		this.to.setCep(this.cep.get());
		this.to.setCidade(this.cidade.get());
		this.to.setComplemento(this.complemento.get());
		this.to.setCpf(this.cpf.get());
		this.to.setDataDeNascimento(this.dataDeNascimento.get());
		this.to.setDia(this.dia.get());
		this.to.setEmail(this.email.get());
		this.to.setLogradouro(this.logradouro.get());
		this.to.setMargem(this.margem.getDouble());
		this.to.setMatricula(this.matricula.get());
		this.to.setNome(this.nome.get());
		this.to.setNumeroDaConta(this.numeroDaConta.get());
		this.to.setOrgao(this.orgao.get());
		this.to.setRendaBruta(this.rendaBruta.getDouble());
		this.to.setRendaLiquida(this.rendaLiquida.getDouble());
		this.to.setStatus(this.status.get());
		this.to.setTipo(this.tipo.get());
		this.to.setTipoDeSimulacao(this.tipoDeSimulacao.get());
		this.to.setUf(this.uf.get());
		this.to.setValorDeSimulacao(this.valorDeSimulacao.getDouble());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		this.to.setRubricas(this.rubricas.getItens());
		this.to.setSimulacoes(this.simulacoes.getItens());
		this.to.setObservacoes(this.observacoes.getItens());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ClienteUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ClienteConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ClienteUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	refreshObservacoesHouveMudancas() {
		this.to.setObservacoesHouveMudancas(!ObservacaoUtils.getInstance().equalsList(this.observacoes.getItens(), this.original.getObservacoes()));
		if (this.to.getObservacoesHouveMudancas()) {
			this.observacoes.getItens().forEach(o => {
				let ori = this.original.getObservacoes().byId(o.getId());
				o.setHouveMudancas(!ObservacaoUtils.getInstance().equals(o, ori));
			});
		}
	}
	refreshRubricasHouveMudancas() {
		this.to.setRubricasHouveMudancas(!ClienteRubricaUtils.getInstance().equalsList(this.rubricas.getItens(), this.original.getRubricas()));
		if (this.to.getRubricasHouveMudancas()) {
			this.rubricas.getItens().forEach(o => {
				let ori = this.original.getRubricas().byId(o.getId());
				o.setHouveMudancas(!ClienteRubricaUtils.getInstance().equals(o, ori));
			});
		}
	}
	refreshSimulacoesHouveMudancas() {
		this.to.setSimulacoesHouveMudancas(!ClienteSimulacaoUtils.getInstance().equalsList(this.simulacoes.getItens(), this.original.getSimulacoes()));
		if (this.to.getSimulacoesHouveMudancas()) {
			this.simulacoes.getItens().forEach(o => {
				let ori = this.original.getSimulacoes().byId(o.getId());
				o.setHouveMudancas(!ClienteSimulacaoUtils.getInstance().equals(o, ori));
			});
		}
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getNome();
	}
	observacoesEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.observacoes.setTrue("edit-observacoes-Cliente");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ClienteUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ClienteUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	rubricasNovo() {
		let o = new ClienteRubrica();
		o.setId(--EntityCampos.novos);
		this.rubricasEdit(o);
	}
	rubricasEdit(o) {
		let cps = ClienteRubricaCampos.getInstance();
		cps.cliente.setVisible(false);
		cps.setCampos(o);
		this.rubricas.setTrue("edit");
	}
	simulacoesNovo() {
		let o = new ClienteSimulacao();
		o.setId(--EntityCampos.novos);
		this.simulacoesEdit(o);
	}
	simulacoesEdit(o) {
		let cps = ClienteSimulacaoCampos.getInstance();
		cps.cliente.setVisible(false);
		cps.setCampos(o);
		this.simulacoes.setTrue("edit");
	}
	banco_001BancoDoBrasilSABb() {
		return this.banco.equals(BancoConstantes._001_BANCO_DO_BRASIL_S_A_BB);
	}
	banco_341BancoItauSAItau() {
		return this.banco.equals(BancoConstantes._341_BANCO_ITAU_S_A_ITAU);
	}
	banco_033BancoSantanderBrasilSASantander() {
		return this.banco.equals(BancoConstantes._033_BANCO_SANTANDER_BRASIL_S_A_SANTANDER);
	}
	banco_356BancoRealSAAntigoReal() {
		return this.banco.equals(BancoConstantes._356_BANCO_REAL_S_A_ANTIGO_REAL);
	}
	banco_652ItauUnibancoHoldingSAItauUnibanco() {
		return this.banco.equals(BancoConstantes._652_ITAU_UNIBANCO_HOLDING_S_A_ITAU_UNIBANCO);
	}
	banco_237BancoBradescoSABradesco() {
		return this.banco.equals(BancoConstantes._237_BANCO_BRADESCO_S_A_BRADESCO);
	}
	banco_745BancoCitibankSACitibank() {
		return this.banco.equals(BancoConstantes._745_BANCO_CITIBANK_S_A_CITIBANK);
	}
	banco_399HsbcBankBrasilSABancoMultiploHsbc() {
		return this.banco.equals(BancoConstantes._399_HSBC_BANK_BRASIL_S_A_BANCO_MULTIPLO_HSBC);
	}
	banco_104CaixaEconomicaFederalCaixa() {
		return this.banco.equals(BancoConstantes._104_CAIXA_ECONOMICA_FEDERAL_CAIXA);
	}
	banco_389BancoMercantilDoBrasilSAMercantil() {
		return this.banco.equals(BancoConstantes._389_BANCO_MERCANTIL_DO_BRASIL_S_A_MERCANTIL);
	}
	banco_453BancoRuralSARural() {
		return this.banco.equals(BancoConstantes._453_BANCO_RURAL_S_A_RURAL);
	}
	banco_422BancoSafraSASafra() {
		return this.banco.equals(BancoConstantes._422_BANCO_SAFRA_S_A_SAFRA);
	}
	banco_633BancoRendimentoSA() {
		return this.banco.equals(BancoConstantes._633_BANCO_RENDIMENTO_S_A);
	}
	banco_246BancoAbcBrasilSA() {
		return this.banco.equals(BancoConstantes._246_BANCO_ABC_BRASIL_S_A);
	}
	banco_025BancoAlfaSA() {
		return this.banco.equals(BancoConstantes._025_BANCO_ALFA_S_A);
	}
	banco_641BancoAlvoradaSA() {
		return this.banco.equals(BancoConstantes._641_BANCO_ALVORADA_S_A);
	}
	banco_029BancoBanerjSA() {
		return this.banco.equals(BancoConstantes._029_BANCO_BANERJ_S_A);
	}
	banco_038BancoBanestadoSA() {
		return this.banco.equals(BancoConstantes._038_BANCO_BANESTADO_S_A);
	}
	banco_0BancoBankparSA() {
		return this.banco.equals(BancoConstantes._0_BANCO_BANKPAR_S_A);
	}
	banco_740BancoBarclaysSA() {
		return this.banco.equals(BancoConstantes._740_BANCO_BARCLAYS_S_A);
	}
	banco_107BancoBbmSA() {
		return this.banco.equals(BancoConstantes._107_BANCO_BBM_S_A);
	}
	banco_031BancoBegSA() {
		return this.banco.equals(BancoConstantes._031_BANCO_BEG_S_A);
	}
	banco_096BancoBmFDeServicosDeLiquidacaoECustodiaSA() {
		return this.banco.equals(BancoConstantes._096_BANCO_BM_F_DE_SERVICOS_DE_LIQUIDACAO_E_CUSTODIA_S_A);
	}
	banco_318BancoBmgSA() {
		return this.banco.equals(BancoConstantes._318_BANCO_BMG_S_A);
	}
	banco_752BancoBnpParibasBrasilSA() {
		return this.banco.equals(BancoConstantes._752_BANCO_BNP_PARIBAS_BRASIL_S_A);
	}
	banco_248BancoBoavistaInteratlanticoSA() {
		return this.banco.equals(BancoConstantes._248_BANCO_BOAVISTA_INTERATLANTICO_S_A);
	}
	banco_036BancoBradescoBbiSA() {
		return this.banco.equals(BancoConstantes._036_BANCO_BRADESCO_BBI_S_A);
	}
	banco_204BancoBradescoCartoesSA() {
		return this.banco.equals(BancoConstantes._204_BANCO_BRADESCO_CARTOES_S_A);
	}
	banco_225BancoBrascanSA() {
		return this.banco.equals(BancoConstantes._225_BANCO_BRASCAN_S_A);
	}
	banco_044BancoBvaSA() {
		return this.banco.equals(BancoConstantes._044_BANCO_BVA_S_A);
	}
	banco_263BancoCaciqueSA() {
		return this.banco.equals(BancoConstantes._263_BANCO_CACIQUE_S_A);
	}
	banco_473BancoCaixaGeralBrasilSA() {
		return this.banco.equals(BancoConstantes._473_BANCO_CAIXA_GERAL_BRASIL_S_A);
	}
	banco_222BancoCalyonBrasilSA() {
		return this.banco.equals(BancoConstantes._222_BANCO_CALYON_BRASIL_S_A);
	}
	banco_040BancoCargillSA() {
		return this.banco.equals(BancoConstantes._040_BANCO_CARGILL_S_A);
	}
	bancoM08BancoCiticardSA() {
		return this.banco.equals(BancoConstantes.M_08_BANCO_CITICARD_S_A);
	}
	bancoM19BancoCnhCapitalSA() {
		return this.banco.equals(BancoConstantes.M_19_BANCO_CNH_CAPITAL_S_A);
	}
	banco_215BancoComercialEDeInvestimentoSudamerisSA() {
		return this.banco.equals(BancoConstantes._215_BANCO_COMERCIAL_E_DE_INVESTIMENTO_SUDAMERIS_S_A);
	}
	banco_756BancoCooperativoDoBrasilSABrasilSA() {
		return this.banco.equals(BancoConstantes._756_BANCO_COOPERATIVO_DO_BRASIL_S_A_BRASIL_S_A);
	}
	banco_748BancoCooperativoSicrediSA() {
		return this.banco.equals(BancoConstantes._748_BANCO_COOPERATIVO_SICREDI_S_A);
	}
	banco_505BancoCreditSuisseBrasilSA() {
		return this.banco.equals(BancoConstantes._505_BANCO_CREDIT_SUISSE_BRASIL_S_A);
	}
	banco_229BancoCruzeiroDoSulSA() {
		return this.banco.equals(BancoConstantes._229_BANCO_CRUZEIRO_DO_SUL_S_A);
	}
	banco_003BancoDaAmazoniaSA() {
		return this.banco.equals(BancoConstantes._003_BANCO_DA_AMAZONIA_S_A);
	}
	banco_0833BancoDaChinaBrasilSA() {
		return this.banco.equals(BancoConstantes._0833_BANCO_DA_CHINA_BRASIL_S_A);
	}
	banco_707BancoDaycovalSA() {
		return this.banco.equals(BancoConstantes._707_BANCO_DAYCOVAL_S_A);
	}
	bancoM06BancoDeLageLandenBrasilSA() {
		return this.banco.equals(BancoConstantes.M_06_BANCO_DE_LAGE_LANDEN_BRASIL_S_A);
	}
	banco_024BancoDePernambucoSABandepe() {
		return this.banco.equals(BancoConstantes._024_BANCO_DE_PERNAMBUCO_S_A_BANDEPE);
	}
	banco_456BancoDeTokyoMitsubishiUfjBrasilSA() {
		return this.banco.equals(BancoConstantes._456_BANCO_DE_TOKYO_MITSUBISHI_UFJ_BRASIL_S_A);
	}
	banco_214BancoDibensSA() {
		return this.banco.equals(BancoConstantes._214_BANCO_DIBENS_S_A);
	}
	banco_047BancoDoEstadoDeSergipeSA() {
		return this.banco.equals(BancoConstantes._047_BANCO_DO_ESTADO_DE_SERGIPE_S_A);
	}
	banco_037BancoDoEstadoDoParaSA() {
		return this.banco.equals(BancoConstantes._037_BANCO_DO_ESTADO_DO_PARA_S_A);
	}
	banco_041BancoDoEstadoDoRioGrandeDoSulSA() {
		return this.banco.equals(BancoConstantes._041_BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL_S_A);
	}
	banco_004BancoDoNordesteDoBrasilSA() {
		return this.banco.equals(BancoConstantes._004_BANCO_DO_NORDESTE_DO_BRASIL_S_A);
	}
	banco_265BancoFatorSA() {
		return this.banco.equals(BancoConstantes._265_BANCO_FATOR_S_A);
	}
	bancoM03BancoFiatSA() {
		return this.banco.equals(BancoConstantes.M_03_BANCO_FIAT_S_A);
	}
	banco_224BancoFibraSA() {
		return this.banco.equals(BancoConstantes._224_BANCO_FIBRA_S_A);
	}
	banco_626BancoFicsaSA() {
		return this.banco.equals(BancoConstantes._626_BANCO_FICSA_S_A);
	}
	banco_394BancoFinasaBmcSA() {
		return this.banco.equals(BancoConstantes._394_BANCO_FINASA_BMC_S_A);
	}
	bancoM18BancoFordSA() {
		return this.banco.equals(BancoConstantes.M_18_BANCO_FORD_S_A);
	}
	banco_233BancoGeCapitalSA() {
		return this.banco.equals(BancoConstantes._233_BANCO_GE_CAPITAL_S_A);
	}
	banco_734BancoGerdauSA() {
		return this.banco.equals(BancoConstantes._734_BANCO_GERDAU_S_A);
	}
	bancoM07BancoGmacSA() {
		return this.banco.equals(BancoConstantes.M_07_BANCO_GMAC_S_A);
	}
	banco_612BancoGuanabaraSA() {
		return this.banco.equals(BancoConstantes._612_BANCO_GUANABARA_S_A);
	}
	bancoM22BancoHondaSA() {
		return this.banco.equals(BancoConstantes.M_22_BANCO_HONDA_S_A);
	}
	banco_063BancoIbiSABancoMultiplo() {
		return this.banco.equals(BancoConstantes._063_BANCO_IBI_S_A_BANCO_MULTIPLO);
	}
	bancoM11BancoIbmSA() {
		return this.banco.equals(BancoConstantes.M_11_BANCO_IBM_S_A);
	}
	banco_604BancoIndustrialDoBrasilSA() {
		return this.banco.equals(BancoConstantes._604_BANCO_INDUSTRIAL_DO_BRASIL_S_A);
	}
	banco_320BancoIndustrialEComercialSA() {
		return this.banco.equals(BancoConstantes._320_BANCO_INDUSTRIAL_E_COMERCIAL_S_A);
	}
	banco_653BancoIndusvalSA() {
		return this.banco.equals(BancoConstantes._653_BANCO_INDUSVAL_S_A);
	}
	banco_630BancoIntercapSA() {
		return this.banco.equals(BancoConstantes._630_BANCO_INTERCAP_S_A);
	}
	banco_249BancoInvestcredUnibancoSA() {
		return this.banco.equals(BancoConstantes._249_BANCO_INVESTCRED_UNIBANCO_S_A);
	}
	banco_184BancoItauBbaSA() {
		return this.banco.equals(BancoConstantes._184_BANCO_ITAU_BBA_S_A);
	}
	banco_479BancoItauBankSA() {
		return this.banco.equals(BancoConstantes._479_BANCO_ITAU_BANK_S_A);
	}
	bancoM09BancoItaucredFinanciamentosSA() {
		return this.banco.equals(BancoConstantes.M_09_BANCO_ITAUCRED_FINANCIAMENTOS_S_A);
	}
	banco_376BancoJPMorganSA() {
		return this.banco.equals(BancoConstantes._376_BANCO_J_P_MORGAN_S_A);
	}
	banco_074BancoJSafraSA() {
		return this.banco.equals(BancoConstantes._074_BANCO_J_SAFRA_S_A);
	}
	banco_217BancoJohnDeereSA() {
		return this.banco.equals(BancoConstantes._217_BANCO_JOHN_DEERE_S_A);
	}
	banco_65BancoLemonSA() {
		return this.banco.equals(BancoConstantes._65_BANCO_LEMON_S_A);
	}
	banco_600BancoLusoBrasileiroSA() {
		return this.banco.equals(BancoConstantes._600_BANCO_LUSO_BRASILEIRO_S_A);
	}
	banco_755BancoMerrillLynchDeInvestimentosSA() {
		return this.banco.equals(BancoConstantes._755_BANCO_MERRILL_LYNCH_DE_INVESTIMENTOS_S_A);
	}
	banco_746BancoModalSA() {
		return this.banco.equals(BancoConstantes._746_BANCO_MODAL_S_A);
	}
	banco_151BancoNossaCaixaSA() {
		return this.banco.equals(BancoConstantes._151_BANCO_NOSSA_CAIXA_S_A);
	}
	banco_45BancoOpportunitySA() {
		return this.banco.equals(BancoConstantes._45_BANCO_OPPORTUNITY_S_A);
	}
	banco_623BancoPanamericanoSA() {
		return this.banco.equals(BancoConstantes._623_BANCO_PANAMERICANO_S_A);
	}
	banco_611BancoPaulistaSA() {
		return this.banco.equals(BancoConstantes._611_BANCO_PAULISTA_S_A);
	}
	banco_643BancoPineSA() {
		return this.banco.equals(BancoConstantes._643_BANCO_PINE_S_A);
	}
	banco_638BancoProsperSA() {
		return this.banco.equals(BancoConstantes._638_BANCO_PROSPER_S_A);
	}
	banco_747BancoRabobankInternationalBrasilSA() {
		return this.banco.equals(BancoConstantes._747_BANCO_RABOBANK_INTERNATIONAL_BRASIL_S_A);
	}
	bancoM16BancoRodobensSA() {
		return this.banco.equals(BancoConstantes.M_16_BANCO_RODOBENS_S_A);
	}
	banco_072BancoRuralMaisSA() {
		return this.banco.equals(BancoConstantes._072_BANCO_RURAL_MAIS_S_A);
	}
	banco_250BancoSchahinSA() {
		return this.banco.equals(BancoConstantes._250_BANCO_SCHAHIN_S_A);
	}
	banco_749BancoSimplesSA() {
		return this.banco.equals(BancoConstantes._749_BANCO_SIMPLES_S_A);
	}
	banco_366BancoSocieteGeneraleBrasilSA() {
		return this.banco.equals(BancoConstantes._366_BANCO_SOCIETE_GENERALE_BRASIL_S_A);
	}
	banco_637BancoSofisaSA() {
		return this.banco.equals(BancoConstantes._637_BANCO_SOFISA_S_A);
	}
	banco_464BancoSumitomoMitsuiBrasileiroSA() {
		return this.banco.equals(BancoConstantes._464_BANCO_SUMITOMO_MITSUI_BRASILEIRO_S_A);
	}
	banco_0825BancoTopazioSA() {
		return this.banco.equals(BancoConstantes._0825_BANCO_TOPAZIO_S_A);
	}
	bancoM20BancoToyotaDoBrasilSA() {
		return this.banco.equals(BancoConstantes.M_20_BANCO_TOYOTA_DO_BRASIL_S_A);
	}
	banco_634BancoTrianguloSA() {
		return this.banco.equals(BancoConstantes._634_BANCO_TRIANGULO_S_A);
	}
	banco_208BancoUbsPactualSA() {
		return this.banco.equals(BancoConstantes._208_BANCO_UBS_PACTUAL_S_A);
	}
	bancoM14BancoVolkswagenSA() {
		return this.banco.equals(BancoConstantes.M_14_BANCO_VOLKSWAGEN_S_A);
	}
	banco_655BancoVotorantimSA() {
		return this.banco.equals(BancoConstantes._655_BANCO_VOTORANTIM_S_A);
	}
	banco_610BancoVrSA() {
		return this.banco.equals(BancoConstantes._610_BANCO_VR_S_A);
	}
	banco_370BancoWestLBDoBrasilSA() {
		return this.banco.equals(BancoConstantes._370_BANCO_WEST_L_B_DO_BRASIL_S_A);
	}
	banco_021BanestesSABancoDoEstadoDoEspiritoSantoBanestes() {
		return this.banco.equals(BancoConstantes._021_BANESTES_S_A_BANCO_DO_ESTADO_DO_ESPIRITO_SANTO_BANESTES);
	}
	banco_719BanifBancoInternacionalDoFunchalBrasilSA() {
		return this.banco.equals(BancoConstantes._719_BANIF_BANCO_INTERNACIONAL_DO_FUNCHAL_BRASIL_S_A);
	}
	banco_073BbBancoPopularDoBrasilSA() {
		return this.banco.equals(BancoConstantes._073_BB_BANCO_POPULAR_DO_BRASIL_S_A);
	}
	banco_078BesInvestimentoDoBrasilSABancoDeInvestimento() {
		return this.banco.equals(BancoConstantes._078_BES_INVESTIMENTO_DO_BRASIL_S_A_BANCO_DE_INVESTIMENTO);
	}
	banco_069BpnBrasilBancoMultiploSA() {
		return this.banco.equals(BancoConstantes._069_BPN_BRASIL_BANCO_MULTIPLO_S_A);
	}
	banco_070BancoDeBrasiliaSABrb() {
		return this.banco.equals(BancoConstantes._070_BANCO_DE_BRASILIA_S_A_BRB);
	}
	banco_477CitibankNA() {
		return this.banco.equals(BancoConstantes._477_CITIBANK_N_A);
	}
	banco_0817ConcordiaBancoSA() {
		return this.banco.equals(BancoConstantes._0817_CONCORDIA_BANCO_S_A);
	}
	banco_487DeutscheBankSABancoAlemao() {
		return this.banco.equals(BancoConstantes._487_DEUTSCHE_BANK_S_A_BANCO_ALEMAO);
	}
	banco_751DresdnerBankBrasilSABancoMultiplo() {
		return this.banco.equals(BancoConstantes._751_DRESDNER_BANK_BRASIL_S_A_BANCO_MULTIPLO);
	}
	banco_062HipercardBancoMultiploSA() {
		return this.banco.equals(BancoConstantes._062_HIPERCARD_BANCO_MULTIPLO_S_A);
	}
	banco_492IngBankNV() {
		return this.banco.equals(BancoConstantes._492_ING_BANK_N_V);
	}
	banco_488JPMorganChaseBank() {
		return this.banco.equals(BancoConstantes._488_J_P_MORGAN_CHASE_BANK);
	}
	banco_409UniaoDeBancosBrasileirosSAUnibanco() {
		return this.banco.equals(BancoConstantes._409_UNIAO_DE_BANCOS_BRASILEIROS_S_A_UNIBANCO);
	}
	banco_230UnicardBancoMultiploSA() {
		return this.banco.equals(BancoConstantes._230_UNICARD_BANCO_MULTIPLO_S_A);
	}
	diaDia01() {
		return this.dia.equals(IndiceConstantes.DIA_01);
	}
	diaDia02() {
		return this.dia.equals(IndiceConstantes.DIA_02);
	}
	diaDia03() {
		return this.dia.equals(IndiceConstantes.DIA_03);
	}
	diaDia04() {
		return this.dia.equals(IndiceConstantes.DIA_04);
	}
	diaDia05() {
		return this.dia.equals(IndiceConstantes.DIA_05);
	}
	diaDia06() {
		return this.dia.equals(IndiceConstantes.DIA_06);
	}
	diaDia07() {
		return this.dia.equals(IndiceConstantes.DIA_07);
	}
	diaDia08() {
		return this.dia.equals(IndiceConstantes.DIA_08);
	}
	diaDia09() {
		return this.dia.equals(IndiceConstantes.DIA_09);
	}
	diaDia10() {
		return this.dia.equals(IndiceConstantes.DIA_10);
	}
	diaDia11() {
		return this.dia.equals(IndiceConstantes.DIA_11);
	}
	diaDia12() {
		return this.dia.equals(IndiceConstantes.DIA_12);
	}
	diaDia13() {
		return this.dia.equals(IndiceConstantes.DIA_13);
	}
	diaDia14() {
		return this.dia.equals(IndiceConstantes.DIA_14);
	}
	diaDia15() {
		return this.dia.equals(IndiceConstantes.DIA_15);
	}
	diaDia16() {
		return this.dia.equals(IndiceConstantes.DIA_16);
	}
	diaDia17() {
		return this.dia.equals(IndiceConstantes.DIA_17);
	}
	diaDia18() {
		return this.dia.equals(IndiceConstantes.DIA_18);
	}
	diaDia19() {
		return this.dia.equals(IndiceConstantes.DIA_19);
	}
	diaDia20() {
		return this.dia.equals(IndiceConstantes.DIA_20);
	}
	diaDia21() {
		return this.dia.equals(IndiceConstantes.DIA_21);
	}
	diaDia22() {
		return this.dia.equals(IndiceConstantes.DIA_22);
	}
	diaDia23() {
		return this.dia.equals(IndiceConstantes.DIA_23);
	}
	diaDia24() {
		return this.dia.equals(IndiceConstantes.DIA_24);
	}
	diaDia25() {
		return this.dia.equals(IndiceConstantes.DIA_25);
	}
	diaDia26() {
		return this.dia.equals(IndiceConstantes.DIA_26);
	}
	diaDia27() {
		return this.dia.equals(IndiceConstantes.DIA_27);
	}
	diaDia28() {
		return this.dia.equals(IndiceConstantes.DIA_28);
	}
	diaDia29() {
		return this.dia.equals(IndiceConstantes.DIA_29);
	}
	diaDia30() {
		return this.dia.equals(IndiceConstantes.DIA_30);
	}
	diaDia31() {
		return this.dia.equals(IndiceConstantes.DIA_31);
	}
	orgao_26201ColegioPedroIi() {
		return this.orgao.equals(OrgaoConstantes._26201_COLEGIO_PEDRO_II);
	}
	orgao_21000ComandoDaAeronautica() {
		return this.orgao.equals(OrgaoConstantes._21000_COMANDO_DA_AERONAUTICA);
	}
	orgao_45205FundInstBrasilGeogEEstatistica() {
		return this.orgao.equals(OrgaoConstantes._45205_FUND_INST_BRASIL_GEOG_E_ESTATISTICA);
	}
	orgao_36205FundacaoNacionalDeSaude() {
		return this.orgao.equals(OrgaoConstantes._36205_FUNDACAO_NACIONAL_DE_SAUDE);
	}
	orgao_26277FundacaoUnivFederalDeOuroPreto() {
		return this.orgao.equals(OrgaoConstantes._26277_FUNDACAO_UNIV_FEDERAL_DE_OURO_PRETO);
	}
	orgao_26274FundacaoUnivFederalDeUberlandia() {
		return this.orgao.equals(OrgaoConstantes._26274_FUNDACAO_UNIV_FEDERAL_DE_UBERLANDIA);
	}
	orgao_40803GovernoDoExTerritorioDeRondonia() {
		return this.orgao.equals(OrgaoConstantes._40803_GOVERNO_DO_EX_TERRITORIO_DE_RONDONIA);
	}
	orgao_40804GovernoDoExTerritorioDeRoraima() {
		return this.orgao.equals(OrgaoConstantes._40804_GOVERNO_DO_EX_TERRITORIO_DE_RORAIMA);
	}
	orgao_40801GovernoDoExTerritorioDoAmapa() {
		return this.orgao.equals(OrgaoConstantes._40801_GOVERNO_DO_EX_TERRITORIO_DO_AMAPA);
	}
	orgao_40701InstBrMeioAmbRecNatRenovaveis() {
		return this.orgao.equals(OrgaoConstantes._40701_INST_BR_MEIO_AMB_REC_NAT_RENOVAVEIS);
	}
	orgao_26403InstitutoFederalDoAmazonas() {
		return this.orgao.equals(OrgaoConstantes._26403_INSTITUTO_FEDERAL_DO_AMAZONAS);
	}
	orgao_30204InstitutoNacDaPropriedadeIndustrial() {
		return this.orgao.equals(OrgaoConstantes._30204_INSTITUTO_NAC_DA_PROPRIEDADE_INDUSTRIAL);
	}
	orgao_57202InstitutoNacionalDeSeguroSocial() {
		return this.orgao.equals(OrgaoConstantes._57202_INSTITUTO_NACIONAL_DE_SEGURO_SOCIAL);
	}
	orgao_40108MinisterioDaCienciaETecnologia() {
		return this.orgao.equals(OrgaoConstantes._40108_MINISTERIO_DA_CIENCIA_E_TECNOLOGIA);
	}
	orgao_17000MinisterioDaFazenda() {
		return this.orgao.equals(OrgaoConstantes._17000_MINISTERIO_DA_FAZENDA);
	}
	orgao_25000MinisterioDaSaude() {
		return this.orgao.equals(OrgaoConstantes._25000_MINISTERIO_DA_SAUDE);
	}
	orgao_49000MinisterioDosTransportes() {
		return this.orgao.equals(OrgaoConstantes._49000_MINISTERIO_DOS_TRANSPORTES);
	}
	orgao_40805Orgao40805() {
		return this.orgao.equals(OrgaoConstantes._40805_ORGAO_40805);
	}
	orgao_26269UniversidadeDoRioDeJaneiro() {
		return this.orgao.equals(OrgaoConstantes._26269_UNIVERSIDADE_DO_RIO_DE_JANEIRO);
	}
	orgao_26239UniversidadeFederalDoPara() {
		return this.orgao.equals(OrgaoConstantes._26239_UNIVERSIDADE_FEDERAL_DO_PARA);
	}
	orgao_26248UniversidadeFederalRuralDePernambuco() {
		return this.orgao.equals(OrgaoConstantes._26248_UNIVERSIDADE_FEDERAL_RURAL_DE_PERNAMBUCO);
	}
	orgao_13000MinistDaAgriculturaPecuariaEAbast() {
		return this.orgao.equals(OrgaoConstantes._13000_MINIST_DA_AGRICULTURA_PECUARIA_E_ABAST);
	}
	orgao_15000MinisterioDaEducacao() {
		return this.orgao.equals(OrgaoConstantes._15000_MINISTERIO_DA_EDUCACAO);
	}
	orgao_16000ComandoDoExercito() {
		return this.orgao.equals(OrgaoConstantes._16000_COMANDO_DO_EXERCITO);
	}
	statusEmAtendimento() {
		return this.status.equals(ClienteStatusConstantes.EM_ATENDIMENTO);
	}
	statusEmprestimoRealizado() {
		return this.status.equals(ClienteStatusConstantes.EMPRESTIMO_REALIZADO);
	}
	statusNaoTemInteresse() {
		return this.status.equals(ClienteStatusConstantes.NAO_TEM_INTERESSE);
	}
	tipoServidor() {
		return this.tipo.equals(ClienteTipoConstantes.SERVIDOR);
	}
	tipoPensionista() {
		return this.tipo.equals(ClienteTipoConstantes.PENSIONISTA);
	}
	tipoDeSimulacaoPeloValorDaParcela() {
		return this.tipoDeSimulacao.equals(ClienteTipoSimulacaoConstantes.PELO_VALOR_DA_PARCELA);
	}
	tipoDeSimulacaoPeloValorDoEmprestimo() {
		return this.tipoDeSimulacao.equals(ClienteTipoSimulacaoConstantes.PELO_VALOR_DO_EMPRESTIMO);
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteCampos", this);
	}
}
