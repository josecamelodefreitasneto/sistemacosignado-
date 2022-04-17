/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UDouble from '../../misc/utils/UDouble';
import UIdText from '../../misc/utils/UIdText';
import UMoney from '../../misc/utils/UMoney';

export default class ClienteRubricaColsAbstract {

	CLIENTE;
	TIPO;
	RUBRICA;
	VALOR;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.CLIENTE = new Coluna(300, "Cliente", o => o.getCliente(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getCliente(), b.getCliente())).setGrupo(false).setId("ClienteRubrica-Cols-cliente");
		this.TIPO = new Coluna(300, "Tipo", o => o.getTipo(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getTipo(), b.getTipo())).setGrupo(false).setId("ClienteRubrica-Cols-tipo");
		this.RUBRICA = new Coluna(300, "Rubrica", o => o.getRubrica(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getRubrica(), b.getRubrica())).setGrupo(false).setId("ClienteRubrica-Cols-rubrica");
		this.VALOR = new Coluna(75, "Valor", o => UMoney.format(o.getValor()), TextAlign.right).setSort((a, b) => UDouble.compare(a.getValor(), b.getValor())).setGrupo(false).setId("ClienteRubrica-Cols-valor");
		this.list = [this.CLIENTE, this.TIPO, this.RUBRICA, this.VALOR];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteRubricaCols", this);
	}
}
