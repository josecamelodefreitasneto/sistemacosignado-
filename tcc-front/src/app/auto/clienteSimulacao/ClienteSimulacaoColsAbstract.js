/* tcc-java */
import React from 'react';
import Botao from '../../../antd/Botao';
import BotaoSize from '../../../antd/BotaoSize';
import Coluna from '../../../fc/components/tabela/Coluna';
import Console from '../../misc/utils/Console';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UBoolean from '../../misc/utils/UBoolean';
import UDouble from '../../misc/utils/UDouble';
import UIdText from '../../misc/utils/UIdText';
import UInteger from '../../misc/utils/UInteger';
import UMoney from '../../misc/utils/UMoney';
import UString from '../../misc/utils/UString';

export default class ClienteSimulacaoColsAbstract {

	CLIENTE;
	PARCELAS;
	INDICE;
	VALOR;
	CONTRATAR;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.CLIENTE = new Coluna(300, "Cliente", o => o.getCliente(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getCliente(), b.getCliente())).setGrupo(false).setId("ClienteSimulacao-Cols-cliente");
		this.PARCELAS = new Coluna(45, "Parcelas", o => o.getParcelas(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getParcelas(), b.getParcelas())).setGrupo(false).setId("ClienteSimulacao-Cols-parcelas");
		this.INDICE = new Coluna(100, "Indice", o => o.getIndice(), TextAlign.right).setSort((a, b) => UString.compare(a.getIndice(), b.getIndice())).setGrupo(false).setId("ClienteSimulacao-Cols-indice");
		this.VALOR = new Coluna(45, "Valor", o => UMoney.format(o.getValor()), TextAlign.right).setSort((a, b) => UDouble.compare(a.getValor(), b.getValor())).setGrupo(false).setId("ClienteSimulacao-Cols-valor");
		this.CONTRATAR = new Coluna(45, "Contratar", o => o.getContratar(), TextAlign.center).setSort((a, b) => UBoolean.compare(a.getContratar(), b.getContratar())).setGrupo(false).setId("ClienteSimulacao-Cols-contratar");
		this.CONTRATAR.setRenderItem(o => {
			if (UBoolean.isTrue(o)) {
				return (
					<Botao
					title={"Contratar"}
					size={BotaoSize.small}
					onClick={() => {
						Console.log("Click", "Contratar");
					}}
					/>
				);
			} else {
				return null;
			}
		});
		this.list = [this.CLIENTE, this.PARCELAS, this.INDICE, this.VALOR, this.CONTRATAR];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteSimulacaoCols", this);
	}
}
