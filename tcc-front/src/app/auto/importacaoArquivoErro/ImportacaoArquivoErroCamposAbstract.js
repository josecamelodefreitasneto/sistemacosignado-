/* front-constructor */
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import ImportacaoArquivoErroConsulta from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroConsulta';
import ImportacaoArquivoErroUtils from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroUtils';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ImportacaoArquivoErroCamposAbstract extends EntityCampos {

	importacaoArquivo;
	linha;
	erro;
	original;
	to;
	getEntidade() {
		return "ImportacaoArquivoErro";
	}
	getEntidadePath() {
		return "importacao-arquivo-erro";
	}
	initImpl() {
		this.importacaoArquivo = this.newFk("Importação Arquivo","importacao-arquivo", true, "Geral").setDisabled(true);
		this.linha = this.newInteger("Linha", 99999, true, "Geral").setDisabled(true);
		this.erro = this.newFk("Erro","importacao-arquivo-erro-mensagem", true, "Geral").setDisabled(true);
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
		this.to = ImportacaoArquivoErroUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.erro.setUnique(this.to.getErro());
		this.importacaoArquivo.setUnique(this.to.getImportacaoArquivo());
		this.linha.set(this.to.getLinha());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.erro.setStartValue(this.erro.get());
		this.importacaoArquivo.setStartValue(this.importacaoArquivo.get());
		this.linha.setStartValue(this.linha.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		this.setObservacoes();
		this.setCampos2(o);
		this.reiniciar();
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
		this.to.setErro(this.erro.get());
		this.to.setImportacaoArquivo(this.importacaoArquivo.get());
		this.to.setLinha(this.linha.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		this.to.setObservacoes(this.observacoes.getItens());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ImportacaoArquivoErroUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ImportacaoArquivoErroConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ImportacaoArquivoErroUtils.getInstance().merge(o, itemGrid);
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
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return UString.toString(o.getErro());
	}
	observacoesEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.observacoes.setTrue("edit-observacoes-ImportacaoArquivoErro");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ImportacaoArquivoErroUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ImportacaoArquivoErroUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoErroCampos", this);
	}
}
