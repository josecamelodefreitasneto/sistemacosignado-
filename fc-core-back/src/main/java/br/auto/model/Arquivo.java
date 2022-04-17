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
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="arquivo")
public class Arquivo extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nome", nullable=false, length=500)
	private String nome;

	@Column(name="tamanho", nullable=false)
	private Integer tamanho;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="path", nullable=false)
	private ArquivoPath path;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="extensao", nullable=false)
	private ArquivoExtensao extensao;

	@Column(name="checksum", nullable=false, length=50)
	private String checksum;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Transient
	private transient String uri;

	@Transient
	private transient String type;

	@Override
	public Arquivo getOld() {
		return (Arquivo) super.getOld();
	}
}
