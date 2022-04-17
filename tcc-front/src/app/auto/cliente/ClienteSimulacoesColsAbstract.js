/* tcc-java */
import React from 'react';
import Botao from '../../../antd/Botao';
import BotaoSize from '../../../antd/BotaoSize';
import ClienteSimulacaoCols from '../../cruds/clienteSimulacao/ClienteSimulacaoCols';
import Coluna from '../../../fc/components/tabela/Coluna';
import Console from '../../misc/utils/Console';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UBoolean from '../../misc/utils/UBoolean';
import UDouble from '../../misc/utils/UDouble';
import UInteger from '../../misc/utils/UInteger';
import UMoney from '../../misc/utils/UMoney';
import UString from '../../misc/utils/UString';

export default class ClienteSimulacoesColsAbstract {

	PARCELAS;
	INDICE;
	VALOR;
	CONTRATAR;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.PARCELAS = new Coluna(45, "Parcelas", o => o.getParcelas(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getParcelas(), b.getParcelas())).setGrupo(false).setId("Cliente-SimulacoesCols-parcelas");
		this.INDICE = new Coluna(100, "Indice", o => o.getIndice(), TextAlign.right).setSort((a, b) => UString.compare(a.getIndice(), b.getIndice())).setGrupo(false).setId("Cliente-SimulacoesCols-indice");
		this.VALOR = new Coluna(45, "Valor", o => UMoney.format(o.getValor()), TextAlign.right).setSort((a, b) => UDouble.compare(a.getValor(), b.getValor())).setGrupo(false).setId("Cliente-SimulacoesCols-valor");
		this.CONTRATAR = new Coluna(45, "Contratar", o => o.getContratar(), TextAlign.center).setSort((a, b) => UBoolean.compare(a.getContratar(), b.getContratar())).setGrupo(false).setId("Cliente-SimulacoesCols-contratar");
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
		this.list = [this.PARCELAS, this.INDICE, this.VALOR, this.CONTRATAR];
		this.grupos = [];
		let principal = ClienteSimulacaoCols.getInstance();
		this.PARCELAS.renderItem = principal.PARCELAS.renderItem;
		this.INDICE.renderItem = principal.INDICE.renderItem;
		this.VALOR.renderItem = principal.VALOR.renderItem;
		this.CONTRATAR.renderItem = principal.CONTRATAR.renderItem;
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteSimulacoesCols", this);
	}
}
