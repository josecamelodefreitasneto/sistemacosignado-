/* front-constructor */
import Arquivo from '../../cruds/arquivo/Arquivo';
import ArquivoCampos from '../../cruds/arquivo/ArquivoCampos';
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import ImportacaoArquivoConsulta from '../../cruds/importacaoArquivo/ImportacaoArquivoConsulta';
import ImportacaoArquivoErro from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErro';
import ImportacaoArquivoErroCampos from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroCampos';
import ImportacaoArquivoErroUtils from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroUtils';
import ImportacaoArquivoStatusConstantes from '../importacaoArquivoStatus/ImportacaoArquivoStatusConstantes';
import ImportacaoArquivoUtils from '../../cruds/importacaoArquivo/ImportacaoArquivoUtils';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoCamposAbstract extends EntityCampos {

	arquivo;
	delimitador;
	atualizarRegistrosExistentes;
	entidade;
	status;
	totalDeLinhas;
	processadosComSucesso;
	processadosComErro;
	arquivoDeErros;
	erros;
	original;
	to;
	getEntidade() {
		return "ImportacaoArquivo";
	}
	getEntidadePath() {
		return "importacao-arquivo";
	}
	initImpl() {
		this.arquivo = this.newVinculado("Arquivo", true, "Geral");
		this.delimitador = this.newString("Delimitador", 3, true, "Geral");
		this.atualizarRegistrosExistentes = this.newBoolean("Atualizar Registros Existentes", true, "Geral");
		this.entidade = this.newFk("Entidade","entidade", true, "Geral").setDisabled(true);
		this.status = this.newList("Status", ImportacaoArquivoStatusConstantes.getList(), true, "Geral").setDisabled(true);
		this.totalDeLinhas = this.newInteger("Total de Linhas", 99999, true, "Geral").setDisabled(true);
		this.processadosComSucesso = this.newInteger("Processados com Sucesso", 99999, true, "Geral").setDisabled(true);
		this.processadosComErro = this.newInteger("Processados com Erro", 99999, true, "Geral").setDisabled(true);
		this.arquivoDeErros = this.newVinculado("Arquivo de Erros", false, "Geral").setDisabled(true);
		this.erros = this.newSubList(
			"Erros", "erros"
			, (de, para) => ImportacaoArquivoErroUtils.getInstance().merge(de, para)
			, obj => {
				let array = obj;
				this.original.setErros([]);
				array.forEach(json => this.original.getErros().add(ImportacaoArquivoErroUtils.getInstance().fromJson(json)));
				this.setErros();
				if (UCommons.notEmpty(this.original.getOriginal())) {
					this.original.getOriginal().setErros(ImportacaoArquivoErroUtils.getInstance().clonarList(this.original.getErros()));
				}
			}
			, false, "Geral", true
		);
		this.arquivo.addFunctionObserver(() => {
			if (!this.disabledObservers && this.arquivo.isTrue()) {
				let o = this.to.getArquivo();
				if (UCommons.isEmpty(o)) {
					o = new Arquivo();
				}
				ArquivoCampos.getInstance().setCampos(o);
			}
		});
		this.arquivo.setOnConfirm(() => {
			this.to.setArquivo(ArquivoCampos.getInstance().getTo());
			this.arquivo.setText(ArquivoCampos.getText(this.to.getArquivo()));
		});
		this.arquivo.setOnClear(() => this.to.setArquivo(null));
		this.arquivoDeErros.addFunctionObserver(() => {
			if (!this.disabledObservers && this.arquivoDeErros.isTrue()) {
				let o = this.to.getArquivoDeErros();
				if (UCommons.isEmpty(o)) {
					o = new Arquivo();
				}
				ArquivoCampos.getInstance().setCampos(o);
			}
		});
		this.arquivoDeErros.setOnConfirm(() => {
			this.to.setArquivoDeErros(ArquivoCampos.getInstance().getTo());
			this.arquivoDeErros.setText(ArquivoCampos.getText(this.to.getArquivoDeErros()));
		});
		this.arquivoDeErros.setOnClear(() => this.to.setArquivoDeErros(null));
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
		this.erros.setOnConfirm(() => {
			this.erros.add(ImportacaoArquivoErroCampos.getInstance().getTo());
			this.refreshErrosHouveMudancas();
		});
		this.erros.setOnClear(() => {
			this.erros.remove(ImportacaoArquivoErroCampos.getInstance().getTo());
			this.refreshErrosHouveMudancas();
		});
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
		this.to = ImportacaoArquivoUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.arquivo.setText(ArquivoCampos.getText(o.getArquivo()));
		this.arquivoDeErros.setText(ArquivoCampos.getText(o.getArquivoDeErros()));
		this.atualizarRegistrosExistentes.set(this.to.getAtualizarRegistrosExistentes());
		this.delimitador.set(this.to.getDelimitador());
		this.entidade.setUnique(this.to.getEntidade());
		this.processadosComErro.set(this.to.getProcessadosComErro());
		this.processadosComSucesso.set(this.to.getProcessadosComSucesso());
		this.status.set(this.to.getStatus());
		this.totalDeLinhas.set(this.to.getTotalDeLinhas());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.arquivo.setStartValue(this.arquivo.get());
		this.arquivoDeErros.setStartValue(this.arquivoDeErros.get());
		this.atualizarRegistrosExistentes.setStartValue(this.atualizarRegistrosExistentes.get());
		this.delimitador.setStartValue(this.delimitador.get());
		this.entidade.setStartValue(this.entidade.get());
		this.erros.setStartValue(this.erros.get());
		this.processadosComErro.setStartValue(this.processadosComErro.get());
		this.processadosComSucesso.setStartValue(this.processadosComSucesso.get());
		this.status.setStartValue(this.status.get());
		this.totalDeLinhas.setStartValue(this.totalDeLinhas.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		this.setErros();
		this.setObservacoes();
		let readOnly = this.isReadOnly();
		this.arquivo.setDisabled(readOnly);
		this.atualizarRegistrosExistentes.setDisabled(readOnly);
		this.delimitador.setDisabled(readOnly);
		if (readOnly) {
			if (UCommons.notEmpty(this.to.getArquivo())) {
				this.to.getArquivo().setRegistroBloqueado(true);
			}
			if (UCommons.notEmpty(this.to.getArquivoDeErros())) {
				this.to.getArquivoDeErros().setRegistroBloqueado(true);
			}
		}
		this.setCampos2(o);
		this.reiniciar();
	}
	setErros() {
		if (UCommons.isEmpty(this.original.getErros())) {
			this.erros.clearItens();
		} else {
			this.erros.clearItens2();
			let readOnly = this.isReadOnly();
			this.original.getErros().forEach(item => {
				let o = ImportacaoArquivoErroUtils.getInstance().clonar(item);
				if (readOnly) o.setRegistroBloqueado(true);
				this.erros.add(o);
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
		this.to.setAtualizarRegistrosExistentes(this.atualizarRegistrosExistentes.get());
		this.to.setDelimitador(this.delimitador.get());
		this.to.setEntidade(this.entidade.get());
		this.to.setProcessadosComErro(this.processadosComErro.get());
		this.to.setProcessadosComSucesso(this.processadosComSucesso.get());
		this.to.setStatus(this.status.get());
		this.to.setTotalDeLinhas(this.totalDeLinhas.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		this.to.setErros(this.erros.getItens());
		this.to.setObservacoes(this.observacoes.getItens());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ImportacaoArquivoUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ImportacaoArquivoConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ImportacaoArquivoUtils.getInstance().merge(o, itemGrid);
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
	refreshErrosHouveMudancas() {
		this.to.setErrosHouveMudancas(!ImportacaoArquivoErroUtils.getInstance().equalsList(this.erros.getItens(), this.original.getErros()));
		if (this.to.getErrosHouveMudancas()) {
			this.erros.getItens().forEach(o => {
				let ori = this.original.getErros().byId(o.getId());
				o.setHouveMudancas(!ImportacaoArquivoErroUtils.getInstance().equals(o, ori));
			});
		}
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getDelimitador();
	}
	observacoesEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.observacoes.setTrue("edit-observacoes-ImportacaoArquivo");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ImportacaoArquivoUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ImportacaoArquivoUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	errosNovo() {
		let o = new ImportacaoArquivoErro();
		o.setId(--EntityCampos.novos);
		this.errosEdit(o);
	}
	errosEdit(o) {
		let cps = ImportacaoArquivoErroCampos.getInstance();
		cps.importacaoArquivo.setVisible(false);
		cps.setCampos(o);
		this.erros.setTrue("edit");
	}
	statusAguardandoProcessamento() {
		return this.status.equals(ImportacaoArquivoStatusConstantes.AGUARDANDO_PROCESSAMENTO);
	}
	statusEmAnalise() {
		return this.status.equals(ImportacaoArquivoStatusConstantes.EM_ANALISE);
	}
	statusEmProcessamento() {
		return this.status.equals(ImportacaoArquivoStatusConstantes.EM_PROCESSAMENTO);
	}
	statusProcessado() {
		return this.status.equals(ImportacaoArquivoStatusConstantes.PROCESSADO);
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoCampos", this);
	}
}
