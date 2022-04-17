package br.tcc.service;

import br.tcc.model.ImportacaoArquivoErroMensagem;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ImportacaoArquivoErroMensagemSelect;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportacaoArquivoErroMensagemService extends ServiceModelo<ImportacaoArquivoErroMensagem> {

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ImportacaoArquivoErroMensagem> getClasse() {
		return ImportacaoArquivoErroMensagem.class;
	}

	@Override
	public MapSO toMap(ImportacaoArquivoErroMensagem o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected ImportacaoArquivoErroMensagem fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ImportacaoArquivoErroMensagem o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		return o;
	}

	@Override
	public ImportacaoArquivoErroMensagem newO() {
		ImportacaoArquivoErroMensagem o = new ImportacaoArquivoErroMensagem();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(ImportacaoArquivoErroMensagem o) {
		validar2(o);
		o.setMensagem(tratarString(o.getMensagem()));
		if (o.getMensagem() == null) {
			throw new MessageException("O campo Importação Arquivo Erro Mensagem > Mensagem é obrigatório");
		}
		if (UString.length(o.getMensagem()) > 300) {
			throw new MessageException("O campo Importação Arquivo Erro Mensagem > Mensagem aceita no máximo 300 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueMensagem(o);
		}
		validar3(o);
	}

	public void validarUniqueMensagem(ImportacaoArquivoErroMensagem o) {
		ImportacaoArquivoErroMensagemSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.mensagem().eq(o.getMensagem());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("importacaoarquivoerromensagem_mensagem"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.ImportacaoArquivoErroMensagem.idEntidade;
	}

	@Override
	protected void saveObservacoes(ImportacaoArquivoErroMensagem o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ImportacaoArquivoErroMensagem> func) {
		final ImportacaoArquivoErroMensagemSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "mensagem", select.mensagem());
		FiltroConsulta.bool(params, "excluido", select.excluido());
		FiltroConsulta.bool(params, "registroBloqueado", select.registroBloqueado());
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.pagina = 1;
			result.registros = select.count();
			result.paginas = result.registros / 30;
			if (result.registros > result.paginas * 30) {
				result.paginas++;
			}
		} else {
			select.page(pagina);
			result.pagina = pagina;
		}
		select.limit(30);
		Lst<ImportacaoArquivoErroMensagem> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ImportacaoArquivoErroMensagem buscaUnicoObrig(MapSO params) {
		final ImportacaoArquivoErroMensagemSelect<?> select = select();
		String mensagem = params.getString("mensagem");
		if (!UString.isEmpty(mensagem)) select.mensagem().eq(mensagem);
		ImportacaoArquivoErroMensagem o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (mensagem != null) {
			s += "&& mensagem = '" + mensagem + "'";
		}
		s = "Não foi encontrado um ImportacaoArquivoErroMensagem com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	@Override
	protected ImportacaoArquivoErroMensagem setOld(ImportacaoArquivoErroMensagem o) {
		ImportacaoArquivoErroMensagem old = newO();
		old.setMensagem(o.getMensagem());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(ImportacaoArquivoErroMensagem o) {
		ImportacaoArquivoErroMensagem old = o.getOld();
		if (!UString.equals(o.getMensagem(), old.getMensagem())) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		ImportacaoArquivoErroMensagem old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivoErroMensagem.mensagem, old.getMensagem(), o.getMensagem());
	}

	@Override
	protected void registrarAuditoriaDelete(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public ImportacaoArquivoErroMensagemSelect<?> select(Boolean excluido) {
		ImportacaoArquivoErroMensagemSelect<?> o = new ImportacaoArquivoErroMensagemSelect<ImportacaoArquivoErroMensagemSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public ImportacaoArquivoErroMensagemSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(ImportacaoArquivoErroMensagem o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ImportacaoArquivoErroMensagem o) {
		if (o == null) return null;
		return o.getMensagem();
	}

	@Override
	public ListString getTemplateImportacao() {
		return null;
	}

	static {
		CONSTRAINTS_MESSAGES.put("importacaoarquivoerromensagem_mensagem", "O campo Mensagem não pode se repetir. Já existe um registro com este valor.");
	}

	public static final int LINHA_REPETIDA_ID = 1;
	public static final String LINHA_REPETIDA_TEXT = "Linha repetida";

	public ImportacaoArquivoErroMensagem get(String msg) {
		String busca = UString.toCampoBusca(msg);
		ImportacaoArquivoErroMensagem o = select().busca().eq(busca).unique();
		if (o == null) {
			o = newO();
			o.setMensagem(msg);
			o.setBusca(busca);
			o = insertSemAuditoria(o);
		}
		return o;
	}

	public void cargaInicial() {
		get(LINHA_REPETIDA_TEXT);
	}

}
