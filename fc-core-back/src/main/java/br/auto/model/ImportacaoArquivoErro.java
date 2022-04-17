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

@Getter @Setter @Entity @Table(name="importacaoarquivoerro")
public class ImportacaoArquivoErro extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="importacaoarquivo", nullable=false)
	private ImportacaoArquivo importacaoArquivo;

	@Column(name="linha", nullable=false)
	private Integer linha;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="erro", nullable=false)
	private ImportacaoArquivoErroMensagem erro;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public ImportacaoArquivoErro getOld() {
		return (ImportacaoArquivoErro) super.getOld();
	}
}
