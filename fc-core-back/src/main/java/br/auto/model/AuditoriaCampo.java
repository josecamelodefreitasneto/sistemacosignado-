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

@Getter @Setter @Entity @Table(name="auditoriacampo")
public class AuditoriaCampo extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="auditoriaentidade", nullable=false)
	private AuditoriaEntidade auditoriaEntidade;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="campo", nullable=false)
	private Campo campo;

	@Column(name="de", nullable=true, length=8000)
	private String de;

	@Column(name="para", nullable=true, length=8000)
	private String para;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public AuditoriaCampo getOld() {
		return (AuditoriaCampo) super.getOld();
	}
}
