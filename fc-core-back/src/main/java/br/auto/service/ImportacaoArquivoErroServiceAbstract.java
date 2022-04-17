package br.auto.service;

import br.auto.model.ImportacaoArquivoErro;
import br.auto.select.ImportacaoArquivoErroSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaCampoService;
import br.impl.service.ImportacaoArquivoErroMensagemService;
import br.impl.service.ImportacaoArquivoService;
import br.impl.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoErroServiceAbstract extends ServiceModelo<ImportacaoArquivoErro> {

	@Autowired
	protected ImportacaoArquivoErroMensagemService importacaoArquivoErroMensagemService;

	@Autowired
	protected ImportacaoArquivoService importacaoArquivoService;

	@Autowired
	protected ObservacaoService observacaoService;

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ImportacaoArquivoErro> getClasse() {
		return ImportacaoArquivoErro.class;
	}
	@Override
	public MapSO toMap(final ImportacaoArquivoErro o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getErro() != null) {
			map.put("erro", importacaoArquivoErroMensagemService.toIdText(o.getErro()));
		}
		if (o.getImportacaoArquivo() != null) {
			map.put("importacaoArquivo", importacaoArquivoService.toIdText(o.getImportacaoArquivo()));
		}
		if (o.getLinha() != null) {
			map.put("linha", o.getLinha());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected ImportacaoArquivoErro fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ImportacaoArquivoErro o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		return o;
	}
	@Override
	public ImportacaoArquivoErro newO() {
		ImportacaoArquivoErro o = new ImportacaoArquivoErro();
		o.setLinha(0);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final ImportacaoArquivoErro o) {
		validar2(o);
		if (o.getErro() == null) {
			throw new MessageException("O campo Importação Arquivo Erro > Erro é obrigatório");
		}
		if (o.getImportacaoArquivo() == null) {
			throw new MessageException("O campo Importação Arquivo Erro > Importação Arquivo é obrigatório");
		}
		if (o.getLinha() == null) {
			throw new MessageException("O campo Importação Arquivo Erro > Linha é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueLinhaImportacaoArquivo(o);
		}
		validar3(o);
	}
	public void validarUniqueLinhaImportacaoArquivo(final ImportacaoArquivoErro o) {
		ImportacaoArquivoErroSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.linha().eq(o.getLinha());
		select.importacaoArquivo().eq(o.getImportacaoArquivo());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("importacaoarquivoerro_linha_importacaoarquivo"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.ImportacaoArquivoErro.idEntidade;
	}
	@Override
	protected void saveObservacoes(final ImportacaoArquivoErro o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	public ResultadoConsulta consulta(final MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, ImportacaoArquivoErro> func) {
		final ImportacaoArquivoErroSelect<?> select = select();
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
		Lst<ImportacaoArquivoErro> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected ImportacaoArquivoErro buscaUnicoObrig(final MapSO params) {
		final ImportacaoArquivoErroSelect<?> select = select();
		Integer erro = getId(params, "erro");
		if (erro != null) {
			select.erro().id().eq(erro);
		}
		Integer importacaoArquivo = getId(params, "importacaoArquivo");
		if (importacaoArquivo != null) {
			select.importacaoArquivo().id().eq(importacaoArquivo);
		}
		Integer linha = params.get("linha");
		if (linha != null) select.linha().eq(linha);
		ImportacaoArquivoErro o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (erro != null) {
			s += "&& erro = '" + erro + "'";
		}
		if (importacaoArquivo != null) {
			s += "&& importacaoArquivo = '" + importacaoArquivo + "'";
		}
		if (linha != null) {
			s += "&& linha = '" + linha + "'";
		}
		s = "Não foi encontrado um ImportacaoArquivoErro com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return true;
	}
	@Override
	protected ImportacaoArquivoErro setOld(final ImportacaoArquivoErro o) {
		ImportacaoArquivoErro old = newO();
		old.setErro(o.getErro());
		old.setImportacaoArquivo(o.getImportacaoArquivo());
		old.setLinha(o.getLinha());
		o.setOld(old);
		return o;
	}
	@Override
	protected boolean houveMudancas(final ImportacaoArquivoErro o) {
		ImportacaoArquivoErro old = o.getOld();
		if (o.getErro() != old.getErro()) return true;
		if (o.getImportacaoArquivo() != old.getImportacaoArquivo()) return true;
		if (o.getLinha() != old.getLinha()) return true;
		return false;
	}
	@Override
	protected void registrarAuditoriaInsert(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUpdate(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		ImportacaoArquivoErro old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivoErro.erro, old.getErro(), o.getErro());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivoErro.importacaoArquivo, old.getImportacaoArquivo(), o.getImportacaoArquivo());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivoErro.linha, old.getLinha(), o.getLinha());
	}
	@Override
	protected void registrarAuditoriaDelete(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUndelete(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaBloqueio(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaDesbloqueio(final ImportacaoArquivoErro o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}
	public ImportacaoArquivoErroSelect<?> select(final Boolean excluido) {
		ImportacaoArquivoErroSelect<?> o = new ImportacaoArquivoErroSelect<ImportacaoArquivoErroSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ImportacaoArquivoErroSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final ImportacaoArquivoErro o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final ImportacaoArquivoErro o) {
		if (o == null) return null;
		return importacaoArquivoErroMensagemService.getText(o.getErro());
	}
	@Override
	public ListString getTemplateImportacao() {
		return null;
	}

	static {
		CONSTRAINTS_MESSAGES.put("importacaoarquivoerro_linha_importacaoarquivo", "A combinação de campos Linha + Importação Arquivo não pode se repetir. Já existe um registro com esta combinação.");
	}
}
