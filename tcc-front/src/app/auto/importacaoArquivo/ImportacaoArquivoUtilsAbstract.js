/* front-constructor */
import ArquivoUtils from '../../cruds/arquivo/ArquivoUtils';
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import EntityCampos from '../../../fc/components/EntityCampos';
import IdText from '../../misc/utils/IdText';
import ImportacaoArquivo from '../../cruds/importacaoArquivo/ImportacaoArquivo';
import ImportacaoArquivoErroUtils from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroUtils';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UEntity from '../../../fc/components/UEntity';
import UString from '../../misc/utils/UString';

export default class ImportacaoArquivoUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoUtils", this);
	}
	init() {}
	equalsAtualizarRegistrosExistentes(a, b) {
		return UBoolean.eq(a.getAtualizarRegistrosExistentes(), b.getAtualizarRegistrosExistentes());
	}
	equalsDelimitador(a, b) {
		return UString.equals(a.getDelimitador(), b.getDelimitador());
	}
	equalsEntidade(a, b) {
		return UEntity.equalsId(a.getEntidade(), b.getEntidade());
	}
	equalsArquivo(a, b) {
		return ArquivoUtils.getInstance().equals(a.getArquivo(), b.getArquivo());
	}
	equalsArquivoDeErros(a, b) {
		return ArquivoUtils.getInstance().equals(a.getArquivoDeErros(), b.getArquivoDeErros());
	}
	equalsErros(a, b) {
		return ImportacaoArquivoErroUtils.getInstance().equalsList(a.getErros(), b.getErros());
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
		if (!this.equalsAtualizarRegistrosExistentes(a, b)) return false;
		if (!this.equalsDelimitador(a, b)) return false;
		if (!this.equalsArquivo(a, b)) return false;
		if (!this.equalsEntidade(a, b)) return false;
		if (!this.equalsObservacoes(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsAtualizarRegistrosExistentes(a, b)) {
			list.add(new CampoAlterado().setCampo("Atualizar Registros Existentes").setDe(UString.toString(a.getAtualizarRegistrosExistentes())).setPara(UString.toString(b.getAtualizarRegistrosExistentes())));
		}
		if (!this.equalsDelimitador(a, b)) {
			list.add(new CampoAlterado().setCampo("Delimitador").setDe(a.getDelimitador()).setPara(b.getDelimitador()));
		}
		if (!this.equalsEntidade(a, b)) {
			list.add(new CampoAlterado().setCampo("Entidade").setDe(UString.toString(a.getEntidade())).setPara(UString.toString(b.getEntidade())));
		}
		if (!this.equalsArquivo(a, b)) {
			list.add(new CampoAlterado().setCampo("Arquivo"));
		}
		if (!this.equalsArquivoDeErros(a, b)) {
			list.add(new CampoAlterado().setCampo("Arquivo de Erros"));
		}
		if (!this.equalsErros(a, b)) {
			list.add(new CampoAlterado().setCampo("Erros"));
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
		let o = new ImportacaoArquivo();
		o.setId(json.id);
		if (UCommons.notEmpty(json.arquivo)) {
			o.setArquivo(ArquivoUtils.getInstance().fromJson(json.arquivo));
		}
		if (UCommons.notEmpty(json.arquivoDeErros)) {
			o.setArquivoDeErros(ArquivoUtils.getInstance().fromJson(json.arquivoDeErros));
		}
		if (UCommons.notEmpty(json.atualizarRegistrosExistentes)) {
			o.setAtualizarRegistrosExistentes(json.atualizarRegistrosExistentes);
		}
		if (UCommons.notEmpty(json.delimitador)) {
			o.setDelimitador(json.delimitador);
		}
		if (UCommons.notEmpty(json.entidade)) {
			o.setEntidade(new IdText(json.entidade.id, json.entidade.text));
		}
		if (UCommons.notEmpty(json.erros)) {
			o.setErros(ImportacaoArquivoErroUtils.getInstance().fromJsonList(json.erros));
		}
		if (UCommons.notEmpty(json.processadosComErro)) {
			o.setProcessadosComErro(json.processadosComErro);
		}
		if (UCommons.notEmpty(json.processadosComSucesso)) {
			o.setProcessadosComSucesso(json.processadosComSucesso);
		}
		if (UCommons.notEmpty(json.status)) {
			o.setStatus(new IdText(json.status.id, json.status.text));
		}
		if (UCommons.notEmpty(json.totalDeLinhas)) {
			o.setTotalDeLinhas(json.totalDeLinhas);
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
		para.setArquivo(ArquivoUtils.getInstance().clonar(de.getArquivo()));
		para.setArquivoDeErros(ArquivoUtils.getInstance().clonar(de.getArquivoDeErros()));
		para.setAtualizarRegistrosExistentes(de.getAtualizarRegistrosExistentes());
		para.setDelimitador(de.getDelimitador());
		para.setEntidade(de.getEntidade());
		para.setErros(de.getErros());
		para.setProcessadosComErro(de.getProcessadosComErro());
		para.setProcessadosComSucesso(de.getProcessadosComSucesso());
		para.setStatus(de.getStatus());
		para.setTotalDeLinhas(de.getTotalDeLinhas());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
		para.setErros(ImportacaoArquivoErroUtils.getInstance().clonarList(de.getErros()));
		para.setObservacoes(ObservacaoUtils.getInstance().clonarList(de.getObservacoes()));
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new ImportacaoArquivo();
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
		let o = new ImportacaoArquivo();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		o.setErros([]);
		return o;
	}
}
