/* front-constructor */
import Arquivo from '../../cruds/arquivo/Arquivo';
import ArquivoCampos from '../../cruds/arquivo/ArquivoCampos';
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import ObservacaoConsulta from '../../cruds/observacao/ObservacaoConsulta';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ObservacaoCamposAbstract extends EntityCampos {

	texto;
	anexo;
	original;
	to;
	getEntidade() {
		return "Observacao";
	}
	getEntidadePath() {
		return "observacao";
	}
	initImpl() {
		this.texto = this.newString("Texto", 500, true, "Geral");
		this.anexo = this.newVinculado("Anexo", false, "Geral");
		this.anexo.addFunctionObserver(() => {
			if (!this.disabledObservers && this.anexo.isTrue()) {
				let o = this.to.getAnexo();
				if (UCommons.isEmpty(o)) {
					o = new Arquivo();
				}
				ArquivoCampos.getInstance().setCampos(o);
			}
		});
		this.anexo.setOnConfirm(() => {
			this.to.setAnexo(ArquivoCampos.getInstance().getTo());
			this.anexo.setText(ArquivoCampos.getText(this.to.getAnexo()));
		});
		this.anexo.setOnClear(() => this.to.setAnexo(null));
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
		this.to = ObservacaoUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.anexo.setText(ArquivoCampos.getText(o.getAnexo()));
		this.texto.set(this.to.getTexto());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.anexo.setStartValue(this.anexo.get());
		this.texto.setStartValue(this.texto.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.anexo.setDisabled(readOnly);
		this.texto.setDisabled(readOnly);
		if (readOnly) {
			if (UCommons.notEmpty(this.to.getAnexo())) {
				this.to.getAnexo().setRegistroBloqueado(true);
			}
		}
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setTexto(this.texto.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ObservacaoUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ObservacaoConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ObservacaoUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getTexto();
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ObservacaoUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ObservacaoUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ObservacaoCampos", this);
	}
}
