/* tcc-java */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import Telefone from '../../cruds/telefone/Telefone';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class TelefoneUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("TelefoneUtils", this);
	}
	init() {}
	equalsDdd(a, b) {
		return UInteger.equals(a.getDdd(), b.getDdd());
	}
	equalsRecado(a, b) {
		return UBoolean.eq(a.getRecado(), b.getRecado());
	}
	equalsWhatsapp(a, b) {
		return UBoolean.eq(a.getWhatsapp(), b.getWhatsapp());
	}
	equalsNumero(a, b) {
		return UString.equals(a.getNumero(), b.getNumero());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsDdd(a, b)) return false;
		if (!this.equalsRecado(a, b)) return false;
		if (!this.equalsWhatsapp(a, b)) return false;
		if (!this.equalsNumero(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsDdd(a, b)) {
			list.add(new CampoAlterado().setCampo("DDD").setDe(UString.toString(a.getDdd())).setPara(UString.toString(b.getDdd())));
		}
		if (!this.equalsRecado(a, b)) {
			list.add(new CampoAlterado().setCampo("Recado").setDe(UString.toString(a.getRecado())).setPara(UString.toString(b.getRecado())));
		}
		if (!this.equalsWhatsapp(a, b)) {
			list.add(new CampoAlterado().setCampo("WhatsApp").setDe(UString.toString(a.getWhatsapp())).setPara(UString.toString(b.getWhatsapp())));
		}
		if (!this.equalsNumero(a, b)) {
			list.add(new CampoAlterado().setCampo("Número").setDe(a.getNumero()).setPara(b.getNumero()));
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
		let o = new Telefone();
		o.setId(json.id);
		if (UCommons.notEmpty(json.ddd)) {
			o.setDdd(json.ddd);
		}
		if (UCommons.notEmpty(json.nome)) {
			o.setNome(json.nome);
		}
		if (UCommons.notEmpty(json.numero)) {
			o.setNumero(json.numero);
		}
		if (UCommons.notEmpty(json.recado)) {
			o.setRecado(json.recado);
		}
		if (UCommons.notEmpty(json.whatsapp)) {
			o.setWhatsapp(json.whatsapp);
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
		para.setDdd(de.getDdd());
		para.setNome(de.getNome());
		para.setNumero(de.getNumero());
		para.setRecado(de.getRecado());
		para.setWhatsapp(de.getWhatsapp());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Telefone();
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
		let o = new Telefone();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
