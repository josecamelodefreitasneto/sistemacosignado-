/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class ArquivoColsAbstract {

	NOME;
	URI;
	TYPE;
	TAMANHO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(1000, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("Arquivo-Cols-nome");
		this.URI = new Coluna(-2, "Uri", o => o.getUri(), TextAlign.left).setSort((a, b) => UString.compare(a.getUri(), b.getUri())).setGrupo(false).setId("Arquivo-Cols-uri");
		this.TYPE = new Coluna(100, "Type", o => o.getType(), TextAlign.left).setSort((a, b) => UString.compare(a.getType(), b.getType())).setGrupo(false).setId("Arquivo-Cols-type");
		this.TAMANHO = new Coluna(90, "Tamanho", o => o.getTamanho(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getTamanho(), b.getTamanho())).setGrupo(false).setId("Arquivo-Cols-tamanho");
		this.list = [this.NOME, this.URI, this.TYPE, this.TAMANHO];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ArquivoCols", this);
	}
}
