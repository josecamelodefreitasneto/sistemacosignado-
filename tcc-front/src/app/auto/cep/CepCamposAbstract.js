/* tcc-java */
import Binding from '../../campos/support/Binding';
import CepConsulta from '../../cruds/cep/CepConsulta';
import CepUtils from '../../cruds/cep/CepUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class CepCamposAbstract extends EntityCampos {

	numero;
	uf;
	cidade;
	bairro;
	logradouro;
	original;
	to;
	getEntidade() {
		return "Cep";
	}
	getEntidadePath() {
		return "cep";
	}
	initImpl() {
		this.numero = this.newString("Número", 10, true, "Geral");
		this.uf = this.newString("UF", 100, true, "Geral");
		this.cidade = this.newString("Cidade", 100, true, "Geral");
		this.bairro = this.newString("Bairro", 100, true, "Geral");
		this.logradouro = this.newString("Logradouro", 100, true, "Geral");
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
		this.to = CepUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.bairro.set(this.to.getBairro());
		this.cidade.set(this.to.getCidade());
		this.logradouro.set(this.to.getLogradouro());
		this.numero.set(this.to.getNumero());
		this.uf.set(this.to.getUf());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.bairro.setStartValue(this.bairro.get());
		this.cidade.setStartValue(this.cidade.get());
		this.logradouro.setStartValue(this.logradouro.get());
		this.numero.setStartValue(this.numero.get());
		this.uf.setStartValue(this.uf.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.bairro.setDisabled(readOnly);
		this.cidade.setDisabled(readOnly);
		this.logradouro.setDisabled(readOnly);
		this.numero.setDisabled(readOnly);
		this.uf.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setBairro(this.bairro.get());
		this.to.setCidade(this.cidade.get());
		this.to.setLogradouro(this.logradouro.get());
		this.to.setNumero(this.numero.get());
		this.to.setUf(this.uf.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = CepUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = CepConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				CepUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return o.getNumero();
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !CepUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return CepUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("CepCampos", this);
	}
}
