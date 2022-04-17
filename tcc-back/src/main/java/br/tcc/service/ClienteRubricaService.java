package br.tcc.service;

import br.tcc.model.Cliente;
import br.tcc.model.ClienteRubrica;
import br.tcc.model.Rubrica;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ClienteRubricaSelect;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.ClienteService;
import br.tcc.service.ObservacaoService;
import br.tcc.service.RubricaService;
import br.tcc.service.RubricaTipoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.utils.number.Numeric2;

@Component
public class ClienteRubricaService extends ServiceModelo<ClienteRubrica> {

	@Autowired ClienteService clienteService;

	@Autowired RubricaService rubricaService;

	@Autowired RubricaTipoService rubricaTipoService;

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ClienteRubrica> getClasse() {
		return ClienteRubrica.class;
	}

	@Override
	public MapSO toMap(ClienteRubrica o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getCliente() != null) {
			map.put("cliente", clienteService.toIdText(o.getCliente()));
		}
		if (o.getRubrica() != null) {
			map.put("rubrica", rubricaService.toIdText(o.getRubrica()));
		}
		if (o.getTipo() != null) {
			map.put("tipo", rubricaTipoService.toIdText(o.getTipo()));
		}
		if (o.getValor() != null) {
			map.put("valor", UString.toString(o.getValor()));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected ClienteRubrica fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ClienteRubrica o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setCliente(find(clienteService, mp, "cliente"));
		o.setRubrica(find(rubricaService, mp, "rubrica"));
		o.setValor(mp.getBigDecimal("valor"));
		return o;
	}

	@Override
	public ClienteRubrica newO() {
		ClienteRubrica o = new ClienteRubrica();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(ClienteRubrica o) {
		if (o.getCliente() == null) {
			throw new MessageException("O campo Cliente Rubrica > Cliente é obrigatório");
		}
		if (o.getRubrica() == null) {
			throw new MessageException("O campo Cliente Rubrica > Rubrica é obrigatório");
		}
		if (o.getValor() == null) {
			throw new MessageException("O campo Cliente Rubrica > Valor é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueRubricaCliente(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueRubricaCliente(ClienteRubrica o) {
		ClienteRubricaSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.rubrica().eq(o.getRubrica());
		select.cliente().eq(o.getCliente());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("clienterubrica_rubrica_cliente"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.ClienteRubrica.idEntidade;
	}

	@Override
	protected void saveObservacoes(ClienteRubrica o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ClienteRubrica> func) {
		final ClienteRubricaSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "cliente", select.cliente());
		FiltroConsulta.fk(params, "rubrica", select.rubrica());
		FiltroConsulta.fk(params, "tipo", select.rubrica().tipo());
		FiltroConsulta.money(params, "valor", select.valor());
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
		Lst<ClienteRubrica> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ClienteRubrica buscaUnicoObrig(MapSO params) {
		final ClienteRubricaSelect<?> select = select();
		Integer cliente = getId(params, "cliente");
		if (cliente != null) {
			select.cliente().id().eq(cliente);
		}
		Integer rubrica = getId(params, "rubrica");
		if (rubrica != null) {
			select.rubrica().id().eq(rubrica);
		}
		BigDecimal valor = params.get("valor");
		if (valor != null) select.valor().eq(valor);
		ClienteRubrica o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (cliente != null) {
			s += "&& cliente = '" + cliente + "'";
		}
		if (rubrica != null) {
			s += "&& rubrica = '" + rubrica + "'";
		}
		if (valor != null) {
			s += "&& valor = '" + valor + "'";
		}
		s = "Não foi encontrado um ClienteRubrica com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	public MapSO rubricaLookups(int id) {
		Rubrica o = rubricaService.find(id);
		MapSO mp = new MapSO();
		mp.add("tipo", rubricaTipoService.toIdText(o.getTipo()));
		return mp;
	}

	@Override
	protected ClienteRubrica setOld(ClienteRubrica o) {
		ClienteRubrica old = newO();
		old.setCliente(o.getCliente());
		old.setRubrica(o.getRubrica());
		old.setValor(o.getValor());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(ClienteRubrica o) {
		ClienteRubrica old = o.getOld();
		if (o.getCliente() != old.getCliente()) {
			return true;
		}
		if (o.getRubrica() != old.getRubrica()) {
			return true;
		}
		if (o.getValor() != old.getValor()) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(ClienteRubrica o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(ClienteRubrica o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		ClienteRubrica old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.ClienteRubrica.cliente, old.getCliente(), o.getCliente());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.ClienteRubrica.rubrica, old.getRubrica(), o.getRubrica());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.ClienteRubrica.valor, old.getValor(), o.getValor(), 2);
	}

	@Override
	protected void registrarAuditoriaDelete(ClienteRubrica o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(ClienteRubrica o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(ClienteRubrica o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(ClienteRubrica o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public static boolean tipoRemuneracao(ClienteRubrica o) {
		return o.getTipo() == RubricaTipoService.REMUNERACAO;
	}

	public boolean tipoRemuneracao(int id) {
		return tipoRemuneracao(find(id));
	}

	public static boolean tipoDesconto(ClienteRubrica o) {
		return o.getTipo() == RubricaTipoService.DESCONTO;
	}

	public boolean tipoDesconto(int id) {
		return tipoDesconto(find(id));
	}

	public ClienteRubricaSelect<?> select(Boolean excluido) {
		ClienteRubricaSelect<?> o = new ClienteRubricaSelect<ClienteRubricaSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public ClienteRubricaSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(ClienteRubrica o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ClienteRubrica o) {
		if (o == null) return null;
		return clienteService.getText(o.getCliente());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ClienteRubrica");
		list.add("cliente;rubrica;valor");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("clienterubrica_rubrica_cliente", "A combinação de campos Rubrica + Cliente não pode se repetir. Já existe um registro com esta combinação.");
	}


	@Override
	protected void afterInsert(ClienteRubrica o) {
		ajustarRemuneracao(o, null, o.getValor());
	}

	@Override
	protected void afterUpdate(ClienteRubrica o) {
		ajustarRemuneracao(o, o.getOld().getValor(), o.getValor());
	}

	@Override
	protected void afterDelete(ClienteRubrica o) {
		ajustarRemuneracao(o, o.getValor(), null);
	}

	private void ajustarRemuneracao(ClienteRubrica o, BigDecimal velho, BigDecimal novo) {

		Cliente cliente = clienteService.find(o.getCliente().getId());
		Numeric2 valor = new Numeric2(cliente.getRendaLiquida());
		if (tipoRemuneracao(o)) {
			valor.add(novo);
			valor.menosIgual(velho);
		} else {
			valor.menosIgual(novo);
			valor.add(velho);
		}
		cliente.setRendaLiquida(valor.getValor());

		if (tipoRemuneracao(o)) {
			valor = new Numeric2(cliente.getRendaBruta());
			valor.add(novo);
			valor.menosIgual(velho);
			cliente.setRendaBruta(valor.getValor());
		}

		clienteService.save(cliente);
	}

}
