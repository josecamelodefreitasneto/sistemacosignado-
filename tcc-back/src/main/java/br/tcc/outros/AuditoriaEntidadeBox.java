package br.tcc.outros;

import java.util.ArrayList;
import java.util.List;

import br.tcc.service.TipoAuditoriaEntidadeService;
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

	public static AuditoriaEntidadeBox novoInsert(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.INCLUSAO);
	}
	public static AuditoriaEntidadeBox novoUpdate(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.ALTERACAO);
	}
	public static AuditoriaEntidadeBox novoDelete(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.EXCLUSAO);
	}
	public static AuditoriaEntidadeBox novoUndelete(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.RECUPERACAO);
	}
	public static AuditoriaEntidadeBox novoBloqueio(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.BLOQUEIO);
	}
	public static AuditoriaEntidadeBox novoDesbloqueio(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.DESBLOQUEIO);
	}
	public static AuditoriaEntidadeBox novaExecucao(int entidade, final int registro) {
		return new AuditoriaEntidadeBox(entidade, registro, TipoAuditoriaEntidadeService.EXECUCAO);
	}

}
