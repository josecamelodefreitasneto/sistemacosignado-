package br.tcc.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.model.Cliente;
import br.tcc.model.ClienteSimulacao;
import br.tcc.model.Indice;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ClienteSimulacaoSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric5;
import gm.utils.string.ListString;
import gm.utils.string.UString;

@Component
public class ClienteSimulacaoService extends ServiceModelo<ClienteSimulacao> {

	@Autowired ClienteService clienteService;

	@Override
	public Class<ClienteSimulacao> getClasse() {
		return ClienteSimulacao.class;
	}

	@Override
	public MapSO toMap(ClienteSimulacao o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getCliente() != null) {
			map.put("cliente", clienteService.toIdText(o.getCliente()));
		}
		if (o.getContratado() != null) {
			map.put("contratado", o.getContratado());
		}
		if (o.getIndice() != null) {
			map.put("indice", new Numeric5(o.getIndice()).toStringPonto());
		}
		if (o.getParcelas() != null) {
			map.put("parcelas", o.getParcelas());
		}
		if (o.getValor() != null) {
			map.put("valor", UString.toString(o.getValor()));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		map.put("contratar", podeContratar(o));
		return map;
	}

	@Override
	protected ClienteSimulacao fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ClienteSimulacao o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setCliente(find(clienteService, mp, "cliente"));
		return o;
	}

	@Override
	public ClienteSimulacao newO() {
		ClienteSimulacao o = new ClienteSimulacao();
		o.setContratado(false);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(ClienteSimulacao o) {
		if (o.getCliente() == null) {
			throw new MessageException("O campo Cliente Simulação > Cliente é obrigatório");
		}
		validar2(o);
		if (o.getContratado() == null) {
			throw new MessageException("O campo Cliente Simulação > Contratado é obrigatório");
		}
		o.setIndice(validaDecimal(o.getIndice(), 1, 5, true));
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueParcelasCliente(o);
		}
		validar3(o);
	}

	public void validarUniqueParcelasCliente(ClienteSimulacao o) {
		if (o.getParcelas() == null) return;
		ClienteSimulacaoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.parcelas().eq(o.getParcelas());
		select.cliente().eq(o.getCliente());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("clientesimulacao_parcelas_cliente"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.ClienteSimulacao.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ClienteSimulacao> func) {
		final ClienteSimulacaoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "cliente", select.cliente());
		FiltroConsulta.bool(params, "contratado", select.contratado());
		FiltroConsulta.decimal(params, "indice", select.indice());
		FiltroConsulta.integer(params, "parcelas", select.parcelas());
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
		Lst<ClienteSimulacao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ClienteSimulacao buscaUnicoObrig(MapSO params) {
		final ClienteSimulacaoSelect<?> select = select();
		Integer cliente = getId(params, "cliente");
		if (cliente != null) {
			select.cliente().id().eq(cliente);
		}
		Boolean contratado = params.get("contratado");
		if (contratado != null) select.contratado().eq(contratado);
		BigDecimal indice = params.get("indice");
		if (indice != null) select.indice().eq(indice);
		Integer parcelas = params.get("parcelas");
		if (parcelas != null) select.parcelas().eq(parcelas);
		BigDecimal valor = params.get("valor");
		if (valor != null) select.valor().eq(valor);
		ClienteSimulacao o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (cliente != null) {
			s += "&& cliente = '" + cliente + "'";
		}
		if (contratado != null) {
			s += "&& contratado = '" + contratado + "'";
		}
		if (indice != null) {
			s += "&& indice = '" + indice + "'";
		}
		if (parcelas != null) {
			s += "&& parcelas = '" + parcelas + "'";
		}
		if (valor != null) {
			s += "&& valor = '" + valor + "'";
		}
		s = "Não foi encontrado um ClienteSimulacao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected ClienteSimulacao setOld(ClienteSimulacao o) {
		ClienteSimulacao old = newO();
		old.setCliente(o.getCliente());
		old.setContratado(o.getContratado());
		old.setIndice(o.getIndice());
		old.setParcelas(o.getParcelas());
		old.setValor(o.getValor());
		o.setOld(old);
		return o;
	}

	public ClienteSimulacaoSelect<?> select(Boolean excluido) {
		ClienteSimulacaoSelect<?> o = new ClienteSimulacaoSelect<ClienteSimulacaoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.parcelas().asc();
		return o;
	}

	public ClienteSimulacaoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(ClienteSimulacao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ClienteSimulacao o) {
		if (o == null) return null;
		return clienteService.getText(o.getCliente());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ClienteSimulacao");
		list.add("cliente;contratar");
		return list;
	}

	@Transactional
	public void contratar(int id) {
		contratarImpl(find(id));
	}


	static {
		CONSTRAINTS_MESSAGES.put("clientesimulacao_parcelas_cliente", "A combinação de campos Parcelas + Cliente não pode se repetir. Já existe um registro com esta combinação.");
	}

	private static final Indice VAZIO = new Indice(0, null, null, null, null, null, null, null, null, null, null, null, null);


	public void calcularParcelas(Cliente o) {
		Indice indice = o.getDia() == null ? VAZIO : IndiceService.map.get(o.getDia());
		Numeric2 simulacao = new Numeric2(o.getValorDeSimulacao());
		add(o, 12, simulacao, indice.getEm12());
		add(o, 15, simulacao, indice.getEm15());
		add(o, 18, simulacao, indice.getEm18());
		add(o, 24, simulacao, indice.getEm24());
		add(o, 30, simulacao, indice.getEm30());
		add(o, 36, simulacao, indice.getEm36());
		add(o, 48, simulacao, indice.getEm48());
		add(o, 60, simulacao, indice.getEm60());
		add(o, 72, simulacao, indice.getEm72());
		add(o, 84, simulacao, indice.getEm84());
		add(o, 96, simulacao, indice.getEm96());
	}

	private void add(Cliente cliente, int parcelas, Numeric2 simulacao, BigDecimal indice) {
		ClienteSimulacao o = select(null).cliente().eq(cliente).parcelas().eq(parcelas).unique();
		if (o == null) {
			o = newO();
			o.setParcelas(parcelas);
			o.setCliente(cliente);
		} else {
			setOld(o);
		}
		o.setExcluido(false);
		o.setIndice(indice);
		if (indice == null || simulacao.isZero() || cliente.getTipoDeSimulacao() == null) {
			o.setValor(null);
		} else {
			if (ClienteService.tipoDeSimulacaoPeloValorDaParcela(cliente)) {
				o.setValor(simulacao.dividido(indice).getValor());
			} else if (ClienteService.tipoDeSimulacaoPeloValorDoEmprestimo(cliente)) {
				o.setValor(simulacao.vezes(indice).getValor());
			} else {
				throw new RuntimeException("???");
			}
		}
		save(o);
	}

	protected boolean podeContratar(ClienteSimulacao o) {
		if (!ClienteService.statusEmAtendimento(o.getCliente())) {
			return false;
		}
		if (o.getValor() == null) {
			return false;
		}
		if (ClienteService.tipoDeSimulacaoPeloValorDoEmprestimo(o.getCliente())) {
			Numeric2 valor = new Numeric2(o.getValor());
			Numeric2 margem = new Numeric2(o.getCliente().getMargem());
			if (valor.maior(margem)) {
				return false;
			}
		}
		return true;
	}

	protected void contratarImpl(ClienteSimulacao o) {
		o.setContratado(true);
		save(o);
		Cliente cliente = clienteService.find(o.getCliente().getId());
		cliente.setStatus(ClienteStatusService.EMPRESTIMO_REALIZADO);
		clienteService.save(cliente);
		clienteService.bloqueiaRegistro(o.getCliente());
	}

}
