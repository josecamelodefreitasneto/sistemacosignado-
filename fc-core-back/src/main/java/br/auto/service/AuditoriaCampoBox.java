package br.auto.service;

import lombok.Getter;

@Getter
public class AuditoriaCampoBox {

	private int idCampo;
	private String de;
	private String para;
	public AuditoriaCampoBox(int idCampo, String de, String para) {
		this.idCampo = idCampo;
		this.de = de;
		this.para = para;
	}
	
}