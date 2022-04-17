/* front-constructor */
import ArquivoUtils from '../../cruds/arquivo/ArquivoUtils';
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import Observacao from '../../cruds/observacao/Observacao';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ObservacaoUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ObservacaoUtils", this);
	}
	init() {}
	equalsTexto(a, b) {
		return UString.equals(a.getTexto(), b.getTexto());
	}
	equalsAnexo(a, b) {
		return ArquivoUtils.getInstance().equals(a.getAnexo(), b.getAnexo());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsTexto(a, b)) return false;
		if (!this.equalsAnexo(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsTexto(a, b)) {
			list.add(new CampoAlterado().setCampo("Texto").setDe(a.getTexto()).setPara(b.getTexto()));
		}
		if (!this.equalsAnexo(a, b)) {
			list.add(new CampoAlterado().setCampo("Anexo"));
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
		let o = new Observacao();
		o.setId(json.id);
		if (UCommons.notEmpty(json.anexo)) {
			o.setAnexo(ArquivoUtils.getInstance().fromJson(json.anexo));
		}
		if (UCommons.notEmpty(json.texto)) {
			o.setTexto(json.texto);
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
		para.setAnexo(ArquivoUtils.getInstance().clonar(de.getAnexo()));
		para.setTexto(de.getTexto());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Observacao();
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
		let o = new Observacao();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
