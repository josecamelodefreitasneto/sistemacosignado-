package br.auto.service;

import java.util.ArrayList;
import java.util.List;

import br.impl.outros.ThreadScope;
import br.impl.service.TipoAuditoriaEntidadeService;
import lombok.Getter;

@Getter
public class AuditoriaEntidadeBox {

	private int entidade;
	private int registro;
	private int tipo;
	private List<AuditoriaCampoBox> campos = new ArrayList<>();

	public AuditoriaEntidadeBox(int entidade, int registro, int tipo) {
		this.entidade = entidade;
		this.registro = registro;
		this.tipo = tipo;
		ThreadScope.addAuditoria(this);
	}
	
	public void add(int idCampo, String de, String para) {
		campos.add(new AuditoriaCampoBox(idCampo, de, para));
	}
	
	public static AuditoriaEntidadeBox novoInsert(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.INCLUSAO);
	}
	public static AuditoriaEntidadeBox novoUpdate(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.ALTERACAO);
	}
	public static AuditoriaEntidadeBox novoDelete(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.EXCLUSAO);
	}
	public static AuditoriaEntidadeBox novoUndelete(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.RECUPERACAO);
	}
	public static AuditoriaEntidadeBox novoBloqueio(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.BLOQUEIO);
	}
	public static AuditoriaEntidadeBox novoDesbloqueio(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.DESBLOQUEIO);
	}
	public static AuditoriaEntidadeBox novaExecucao(final int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.EXECUCAO);
	}	
	
}