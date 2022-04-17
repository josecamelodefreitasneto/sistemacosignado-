/* tcc-java */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import Cep from '../../cruds/cep/Cep';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class CepUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("CepUtils", this);
	}
	init() {}
	equalsBairro(a, b) {
		return UString.equals(a.getBairro(), b.getBairro());
	}
	equalsCidade(a, b) {
		return UString.equals(a.getCidade(), b.getCidade());
	}
	equalsLogradouro(a, b) {
		return UString.equals(a.getLogradouro(), b.getLogradouro());
	}
	equalsNumero(a, b) {
		return UString.equals(a.getNumero(), b.getNumero());
	}
	equalsUf(a, b) {
		return UString.equals(a.getUf(), b.getUf());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsBairro(a, b)) return false;
		if (!this.equalsCidade(a, b)) return false;
		if (!this.equalsLogradouro(a, b)) return false;
		if (!this.equalsNumero(a, b)) return false;
		if (!this.equalsUf(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsBairro(a, b)) {
			list.add(new CampoAlterado().setCampo("Bairro").setDe(a.getBairro()).setPara(b.getBairro()));
		}
		if (!this.equalsCidade(a, b)) {
			list.add(new CampoAlterado().setCampo("Cidade").setDe(a.getCidade()).setPara(b.getCidade()));
		}
		if (!this.equalsLogradouro(a, b)) {
			list.add(new CampoAlterado().setCampo("Logradouro").setDe(a.getLogradouro()).setPara(b.getLogradouro()));
		}
		if (!this.equalsNumero(a, b)) {
			list.add(new CampoAlterado().setCampo("Número").setDe(a.getNumero()).setPara(b.getNumero()));
		}
		if (!this.equalsUf(a, b)) {
			list.add(new CampoAlterado().setCampo("UF").setDe(a.getUf()).setPara(b.getUf()));
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
		let o = new Cep();
		o.setId(json.id);
		if (UCommons.notEmpty(json.bairro)) {
			o.setBairro(json.bairro);
		}
		if (UCommons.notEmpty(json.cidade)) {
			o.setCidade(json.cidade);
		}
		if (UCommons.notEmpty(json.logradouro)) {
			o.setLogradouro(json.logradouro);
		}
		if (UCommons.notEmpty(json.numero)) {
			o.setNumero(json.numero);
		}
		if (UCommons.notEmpty(json.uf)) {
			o.setUf(json.uf);
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
		para.setBairro(de.getBairro());
		para.setCidade(de.getCidade());
		para.setLogradouro(de.getLogradouro());
		para.setNumero(de.getNumero());
		para.setUf(de.getUf());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Cep();
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
		let o = new Cep();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
