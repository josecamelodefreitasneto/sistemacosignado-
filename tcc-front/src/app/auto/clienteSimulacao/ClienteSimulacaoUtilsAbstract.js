/* tcc-java */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import ClienteSimulacao from '../../cruds/clienteSimulacao/ClienteSimulacao';
import EntityCampos from '../../../fc/components/EntityCampos';
import IdText from '../../misc/utils/IdText';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UEntity from '../../../fc/components/UEntity';
import UString from '../../misc/utils/UString';

export default class ClienteSimulacaoUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ClienteSimulacaoUtils", this);
	}
	init() {}
	equalsCliente(a, b) {
		return UEntity.equalsId(a.getCliente(), b.getCliente());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsCliente(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsCliente(a, b)) {
			list.add(new CampoAlterado().setCampo("Cliente").setDe(UString.toString(a.getCliente())).setPara(UString.toString(b.getCliente())));
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
		let o = new ClienteSimulacao();
		o.setId(json.id);
		if (UCommons.notEmpty(json.cliente)) {
			o.setCliente(new IdText(json.cliente.id, json.cliente.text));
		}
		if (UCommons.notEmpty(json.contratado)) {
			o.setContratado(json.contratado);
		}
		if (UCommons.notEmpty(json.contratar)) {
			o.setContratar(json.contratar);
		}
		if (UCommons.notEmpty(json.indice)) {
			o.setIndice(json.indice);
		}
		if (UCommons.notEmpty(json.parcelas)) {
			o.setParcelas(json.parcelas);
		}
		if (UCommons.notEmpty(json.valor)) {
			o.setValor(json.valor);
		}
		o.setExcluido(json.excluido);
		o.setRegistroBloqueado(json.registroBloqueado);
		return o;
	}
	fromJsonList(jsons) {
		if (UCommons.isEmpty(jsons)) return null;
		return jsons.map(o => this.fromJson(o));
	}
	merge(de, para) {
		para.setId(de.getId());
		para.setCliente(de.getCliente());
		para.setContratado(de.getContratado());
		para.setContratar(de.getContratar());
		para.setIndice(de.getIndice());
		para.setParcelas(de.getParcelas());
		para.setValor(de.getValor());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new ClienteSimulacao();
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
		let o = new ClienteSimulacao();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
