package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="importacaoarquivo")
public class ImportacaoArquivo extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arquivo", nullable=false)
	private Arquivo arquivo;

	@Column(name="delimitador", nullable=false, length=3)
	private String delimitador;

	@Column(name="atualizarregistrosexistentes", nullable=false)
	private Boolean atualizarRegistrosExistentes;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entidade", nullable=false)
	private Entidade entidade;

	@Column(name="status", nullable=false)
	private Integer status;

	@Column(name="totaldelinhas", nullable=false)
	private Integer totalDeLinhas;

	@Column(name="processadoscomsucesso", nullable=false)
	private Integer processadosComSucesso;

	@Column(name="processadoscomerro", nullable=false)
	private Integer processadosComErro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arquivodeerros", nullable=true)
	private Arquivo arquivoDeErros;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public ImportacaoArquivo getOld() {
		return (ImportacaoArquivo) super.getOld();
	}
}
