/* tcc-java */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import ClienteRubrica from '../../cruds/clienteRubrica/ClienteRubrica';
import EntityCampos from '../../../fc/components/EntityCampos';
import IdText from '../../misc/utils/IdText';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UDouble from '../../misc/utils/UDouble';
import UEntity from '../../../fc/components/UEntity';
import UString from '../../misc/utils/UString';

export default class ClienteRubricaUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ClienteRubricaUtils", this);
	}
	init() {}
	equalsValor(a, b) {
		return UDouble.equals(a.getValor(), b.getValor());
	}
	equalsCliente(a, b) {
		return UEntity.equalsId(a.getCliente(), b.getCliente());
	}
	equalsRubrica(a, b) {
		return UEntity.equalsId(a.getRubrica(), b.getRubrica());
	}
	equalsObservacoes(a, b) {
		return ObservacaoUtils.getInstance().equalsList(a.getObservacoes(), b.getObservacoes());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsValor(a, b)) return false;
		if (!this.equalsCliente(a, b)) return false;
		if (!this.equalsRubrica(a, b)) return false;
		if (!this.equalsObservacoes(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsValor(a, b)) {
			list.add(new CampoAlterado().setCampo("Valor").setDe(UString.toString(a.getValor())).setPara(UString.toString(b.getValor())));
		}
		if (!this.equalsCliente(a, b)) {
			list.add(new CampoAlterado().setCampo("Cliente").setDe(UString.toString(a.getCliente())).setPara(UString.toString(b.getCliente())));
		}
		if (!this.equalsRubrica(a, b)) {
			list.add(new CampoAlterado().setCampo("Rubrica").setDe(UString.toString(a.getRubrica())).setPara(UString.toString(b.getRubrica())));
		}
		if (!this.equalsObservacoes(a, b)) {
			list.add(new CampoAlterado().setCampo("Observações"));
		}
		if (!this.equalsExcluido(a, b)) {
			list.add(new CampoAlterado().setCampo("Excluído"));
		}
		return list;
	}
	equalsList(a, b) {
		return UArray.equals(a, b, (x, y) => this.equals(x, y));
	}
	fromJson(json) {
		if (UCommons.isEmpty(json)) return null;
		let o = new ClienteRubrica();
		o.setId(json.id);
		if (UCommons.notEmpty(json.cliente)) {
			o.setCliente(new IdText(json.cliente.id, json.cliente.text));
		}
		if (UCommons.notEmpty(json.rubrica)) {
			o.setRubrica(new IdText(json.rubrica.id, json.rubrica.text));
		}
		if (UCommons.notEmpty(json.tipo)) {
			o.setTipo(new IdText(json.tipo.id, json.tipo.text));
		}
		if (UCommons.notEmpty(json.valor)) {
			o.setValor(json.valor);
		}
		o.setExcluido(json.excluido);
		o.setRegistroBloqueado(json.registroBloqueado);
		o.setObservacoes(null);
		return o;
	}
	fromJsonList(jsons) {
		if (UCommons.isEmpty(jsons)) return null;
		return jsons.map(o => this.fromJson(o));
	}
	merge(de, para) {
		para.setId(de.getId());
		para.setCliente(de.getCliente());
		para.setRubrica(de.getRubrica());
		para.setTipo(de.getTipo());
		para.setValor(de.getValor());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
		para.setObservacoes(ObservacaoUtils.getInstance().clonarList(de.getObservacoes()));
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new ClienteRubrica();
		o.setOriginal(obj);
		this.merge(obj, o);
		return o;
	}
	clonarList(array) {
		if (UArray.isEmpty(array)) {
			return null;
		} else {
			return array.map(o => this.clonar(o));
		}
	}
	novo() {
		let o = new ClienteRubrica();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
