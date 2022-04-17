package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "importacaoarquivoerromensagem")
public class ImportacaoArquivoErroMensagem extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "mensagem", nullable = false, length = 300)
	private String mensagem;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public ImportacaoArquivoErroMensagem getOld() {
		return (ImportacaoArquivoErroMensagem) super.getOld();
	}
}
