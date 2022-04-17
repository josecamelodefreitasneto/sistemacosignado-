/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UBoolean from '../../misc/utils/UBoolean';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class TelefoneColsAbstract {

	DDD;
	NUMERO;
	NOME;
	WHATSAPP;
	RECADO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.DDD = new Coluna(45, "DDD", o => o.getDdd(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getDdd(), b.getDdd())).setGrupo(false).setId("Telefone-Cols-ddd");
		this.NUMERO = new Coluna(18, "Número", o => o.getNumero(), TextAlign.left).setSort((a, b) => UString.compare(a.getNumero(), b.getNumero())).setGrupo(false).setId("Telefone-Cols-numero");
		this.NOME = new Coluna(56, "Como vai aparecer", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("Telefone-Cols-nome");
		this.WHATSAPP = new Coluna(100, "WhatsApp", o => o.getWhatsapp(), TextAlign.center).setSort((a, b) => UBoolean.compare(a.getWhatsapp(), b.getWhatsapp())).setGrupo(false).setId("Telefone-Cols-whatsapp");
		this.RECADO = new Coluna(100, "Recado", o => o.getRecado(), TextAlign.center).setSort((a, b) => UBoolean.compare(a.getRecado(), b.getRecado())).setGrupo(false).setId("Telefone-Cols-recado");
		this.list = [this.DDD, this.NUMERO, this.NOME, this.WHATSAPP, this.RECADO];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("TelefoneCols", this);
	}
}
