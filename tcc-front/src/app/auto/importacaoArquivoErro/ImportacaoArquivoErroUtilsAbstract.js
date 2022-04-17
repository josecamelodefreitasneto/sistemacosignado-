/* front-constructor */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import IdText from '../../misc/utils/IdText';
import ImportacaoArquivoErro from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErro';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';

export default class ImportacaoArquivoErroUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoErroUtils", this);
	}
	init() {}
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
		if (!this.equalsObservacoes(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
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
		let o = new ImportacaoArquivoErro();
		o.setId(json.id);
		if (UCommons.notEmpty(json.erro)) {
			o.setErro(new IdText(json.erro.id, json.erro.text));
		}
		if (UCommons.notEmpty(json.importacaoArquivo)) {
			o.setImportacaoArquivo(new IdText(json.importacaoArquivo.id, json.importacaoArquivo.text));
		}
		if (UCommons.notEmpty(json.linha)) {
			o.setLinha(json.linha);
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
		para.setErro(de.getErro());
		para.setImportacaoArquivo(de.getImportacaoArquivo());
		para.setLinha(de.getLinha());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
		para.setObservacoes(ObservacaoUtils.getInstance().clonarList(de.getObservacoes()));
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new ImportacaoArquivoErro();
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
		let o = new ImportacaoArquivoErro();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
}
