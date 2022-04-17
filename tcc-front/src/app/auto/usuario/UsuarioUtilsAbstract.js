/* front-constructor */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';
import Usuario from '../../cruds/usuario/Usuario';

export default class UsuarioUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("UsuarioUtils", this);
	}
	init() {}
	equalsLogin(a, b) {
		return UString.equals(a.getLogin(), b.getLogin());
	}
	equalsNome(a, b) {
		return UString.equals(a.getNome(), b.getNome());
	}
	equalsSenha(a, b) {
		return UString.equals(a.getSenha(), b.getSenha());
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
		if (!this.equalsLogin(a, b)) return false;
		if (!this.equalsNome(a, b)) return false;
		if (!this.equalsSenha(a, b)) return false;
		if (!this.equalsObservacoes(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsLogin(a, b)) {
			list.add(new CampoAlterado().setCampo("Login").setDe(a.getLogin()).setPara(b.getLogin()));
		}
		if (!this.equalsNome(a, b)) {
			list.add(new CampoAlterado().setCampo("Nome").setDe(a.getNome()).setPara(b.getNome()));
		}
		if (!this.equalsSenha(a, b)) {
			list.add(new CampoAlterado().setCampo("Senha").setDe(a.getSenha()).setPara(b.getSenha()));
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
		let o = new Usuario();
		o.setId(json.id);
		if (UCommons.notEmpty(json.login)) {
			o.setLogin(json.login);
		}
		if (UCommons.notEmpty(json.nome)) {
			o.setNome(json.nome);
		}
		if (UCommons.notEmpty(json.senha)) {
			o.setSenha(json.senha);
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
		para.setLogin(de.getLogin());
		para.setNome(de.getNome());
		para.setSenha(de.getSenha());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
		para.setObservacoes(ObservacaoUtils.getInstance().clonarList(de.getObservacoes()));
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Usuario();
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
		let o = new Usuario();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
