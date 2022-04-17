/* tcc-java */
import ClienteRubricaCols from '../../cruds/clienteRubrica/ClienteRubricaCols';
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UDouble from '../../misc/utils/UDouble';
import UIdText from '../../misc/utils/UIdText';
import UMoney from '../../misc/utils/UMoney';

export default class ClienteRubricasColsAbstract {

	TIPO;
	RUBRICA;
	VALOR;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.TIPO = new Coluna(300, "Tipo", o => o.getTipo(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getTipo(), b.getTipo())).setGrupo(false).setId("Cliente-RubricasCols-tipo");
		this.RUBRICA = new Coluna(300, "Rubrica", o => o.getRubrica(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getRubrica(), b.getRubrica())).setGrupo(false).setId("Cliente-RubricasCols-rubrica");
		this.VALOR = new Coluna(75, "Valor", o => UMoney.format(o.getValor()), TextAlign.right).setSort((a, b) => UDouble.compare(a.getValor(), b.getValor())).setGrupo(false).setId("Cliente-RubricasCols-valor");
		this.list = [this.TIPO, this.RUBRICA, this.VALOR];
		this.grupos = [];
		let principal = ClienteRubricaCols.getInstance();
		this.TIPO.renderItem = principal.TIPO.renderItem;
		this.RUBRICA.renderItem = principal.RUBRICA.renderItem;
		this.VALOR.renderItem = principal.VALOR.renderItem;
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteRubricasCols", this);
	}
}
