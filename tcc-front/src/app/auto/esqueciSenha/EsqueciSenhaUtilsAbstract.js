/* front-constructor */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import EsqueciSenha from '../../cruds/esqueciSenha/EsqueciSenha';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class EsqueciSenhaUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("EsqueciSenhaUtils", this);
	}
	init() {}
	equalsConfirmarSenha(a, b) {
		return UString.equals(a.getConfirmarSenha(), b.getConfirmarSenha());
	}
	equalsNovaSenha(a, b) {
		return UString.equals(a.getNovaSenha(), b.getNovaSenha());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsConfirmarSenha(a, b)) return false;
		if (!this.equalsNovaSenha(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsConfirmarSenha(a, b)) {
			list.add(new CampoAlterado().setCampo("Confirmar Senha").setDe(a.getConfirmarSenha()).setPara(b.getConfirmarSenha()));
		}
		if (!this.equalsNovaSenha(a, b)) {
			list.add(new CampoAlterado().setCampo("Nova Senha").setDe(a.getNovaSenha()).setPara(b.getNovaSenha()));
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
		let o = new EsqueciSenha();
		o.setId(json.id);
		if (UCommons.notEmpty(json.confirmarSenha)) {
			o.setConfirmarSenha(json.confirmarSenha);
		}
		if (UCommons.notEmpty(json.novaSenha)) {
			o.setNovaSenha(json.novaSenha);
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
		para.setConfirmarSenha(de.getConfirmarSenha());
		para.setNovaSenha(de.getNovaSenha());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new EsqueciSenha();
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
		let o = new EsqueciSenha();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
