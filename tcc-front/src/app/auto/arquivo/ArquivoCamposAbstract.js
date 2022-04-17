/* front-constructor */
import ArquivoConsulta from '../../cruds/arquivo/ArquivoConsulta';
import ArquivoUtils from '../../cruds/arquivo/ArquivoUtils';
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ArquivoCamposAbstract extends EntityCampos {

	nome;
	uri;
	type;
	tamanho;
	original;
	to;
	getEntidade() {
		return "Arquivo";
	}
	getEntidadePath() {
		return "arquivo";
	}
	initImpl() {
		this.nome = this.newString("Nome", 500, true, "Geral");
		this.uri = this.newString("Uri", 2147483647, false, "Geral");
		this.type = this.newString("Type", 50, false, "Geral");
		this.tamanho = this.newInteger("Tamanho", 99999, true, "Geral").setDisabled(true);
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
		this.to = ArquivoUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.nome.set(this.to.getNome());
		this.tamanho.set(this.to.getTamanho());
		this.type.set(this.to.getType());
		this.uri.set(this.to.getUri());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.nome.setStartValue(this.nome.get());
		this.tamanho.setStartValue(this.tamanho.get());
		this.type.setStartValue(this.type.get());
		this.uri.setStartValue(this.uri.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.nome.setDisabled(readOnly);
		this.type.setDisabled(readOnly);
		this.uri.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setNome(this.nome.get());
		this.to.setTamanho(this.tamanho.get());
		this.to.setType(this.type.get());
		this.to.setUri(this.uri.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ArquivoUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ArquivoConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ArquivoUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getNome();
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ArquivoUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ArquivoUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ArquivoCampos", this);
	}
}
