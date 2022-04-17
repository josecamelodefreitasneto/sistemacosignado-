package br.auto.service;

import br.auto.model.ImportacaoArquivoErroMensagem;
import br.auto.select.ImportacaoArquivoErroMensagemSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaCampoService;
import br.impl.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoErroMensagemServiceAbstract extends ServiceModelo<ImportacaoArquivoErroMensagem> {

	@Autowired
	protected ObservacaoService observacaoService;

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ImportacaoArquivoErroMensagem> getClasse() {
		return ImportacaoArquivoErroMensagem.class;
	}
	@Override
	public MapSO toMap(final ImportacaoArquivoErroMensagem o, final boolean listas) {
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
	protected final void validar(final ImportacaoArquivoErroMensagem o) {
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
	public void validarUniqueMensagem(final ImportacaoArquivoErroMensagem o) {
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
	protected void saveObservacoes(final ImportacaoArquivoErroMensagem o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, ImportacaoArquivoErroMensagem> func) {
		final ImportacaoArquivoErroMensagemSelect<?> select = select();
		Integer pagina = params.getInt("pagina");
		String busca = params.getString("busca");
		if (!UString.isEmpty(busca)) select.busca().like(UString.toCampoBusca(busca));
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.registros = select.count();
		} else {
			select.page(pagina);
		}
		select.limit(30);
		Lst<ImportacaoArquivoErroMensagem> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected ImportacaoArquivoErroMensagem buscaUnicoObrig(final MapSO params) {
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
	protected ImportacaoArquivoErroMensagem setOld(final ImportacaoArquivoErroMensagem o) {
		ImportacaoArquivoErroMensagem old = newO();
		old.setMensagem(o.getMensagem());
		o.setOld(old);
		return o;
	}
	@Override
	protected boolean houveMudancas(final ImportacaoArquivoErroMensagem o) {
		ImportacaoArquivoErroMensagem old = o.getOld();
		if (!UString.equals(o.getMensagem(), old.getMensagem())) return true;
		return false;
	}
	@Override
	protected void registrarAuditoriaInsert(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUpdate(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		ImportacaoArquivoErroMensagem old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivoErroMensagem.mensagem, old.getMensagem(), o.getMensagem());
	}
	@Override
	protected void registrarAuditoriaDelete(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUndelete(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaBloqueio(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaDesbloqueio(final ImportacaoArquivoErroMensagem o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}
	public ImportacaoArquivoErroMensagemSelect<?> select(final Boolean excluido) {
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
	protected void setBusca(final ImportacaoArquivoErroMensagem o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final ImportacaoArquivoErroMensagem o) {
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
}
