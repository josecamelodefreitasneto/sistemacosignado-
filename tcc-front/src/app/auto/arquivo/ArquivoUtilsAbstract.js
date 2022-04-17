/* front-constructor */
import Arquivo from '../../cruds/arquivo/Arquivo';
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ArquivoUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ArquivoUtils", this);
	}
	init() {}
	equalsNome(a, b) {
		return UString.equals(a.getNome(), b.getNome());
	}
	equalsType(a, b) {
		return UString.equals(a.getType(), b.getType());
	}
	equalsUri(a, b) {
		return UString.equals(a.getUri(), b.getUri());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsNome(a, b)) return false;
		if (!this.equalsType(a, b)) return false;
		if (!this.equalsUri(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsNome(a, b)) {
			list.add(new CampoAlterado().setCampo("Nome").setDe(a.getNome()).setPara(b.getNome()));
		}
		if (!this.equalsType(a, b)) {
			list.add(new CampoAlterado().setCampo("Type").setDe(a.getType()).setPara(b.getType()));
		}
		if (!this.equalsUri(a, b)) {
			list.add(new CampoAlterado().setCampo("Uri").setDe(a.getUri()).setPara(b.getUri()));
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
		let o = new Arquivo();
		o.setId(json.id);
		if (UCommons.notEmpty(json.nome)) {
			o.setNome(json.nome);
		}
		if (UCommons.notEmpty(json.tamanho)) {
			o.setTamanho(json.tamanho);
		}
		if (UCommons.notEmpty(json.type)) {
			o.setType(json.type);
		}
		if (UCommons.notEmpty(json.uri)) {
			o.setUri(json.uri);
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
		para.setNome(de.getNome());
		para.setTamanho(de.getTamanho());
		para.setType(de.getType());
		para.setUri(de.getUri());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Arquivo();
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
		let o = new Arquivo();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
