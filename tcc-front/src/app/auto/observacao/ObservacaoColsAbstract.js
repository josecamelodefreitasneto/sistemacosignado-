/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class ObservacaoColsAbstract {

	TEXTO;
	ANEXO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.TEXTO = new Coluna(1000, "Texto", o => o.getTexto(), TextAlign.left).setSort((a, b) => UString.compare(a.getTexto(), b.getTexto())).setGrupo(false).setId("Observacao-Cols-texto");
		this.ANEXO = new Coluna(300, "Anexo", o => o.getAnexo(), TextAlign.left).setSort((a, b) => 0).setGrupo(false).setId("Observacao-Cols-anexo");
		this.list = [this.TEXTO, this.ANEXO];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ObservacaoCols", this);
	}
}
