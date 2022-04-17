package br.tcc.service;

import br.tcc.model.Cep;
import br.tcc.model.Cliente;
import br.tcc.model.ClienteRubrica;
import br.tcc.model.ClienteSimulacao;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ClienteSelect;
import br.tcc.service.AtendenteService;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.BancoService;
import br.tcc.service.CepService;
import br.tcc.service.ClienteRubricaService;
import br.tcc.service.ClienteSimulacaoService;
import br.tcc.service.ClienteStatusService;
import br.tcc.service.ClienteTipoService;
import br.tcc.service.ClienteTipoSimulacaoService;
import br.tcc.service.IndiceService;
import br.tcc.service.ObservacaoService;
import br.tcc.service.OrgaoService;
import br.tcc.service.TelefoneService;
import gm.utils.comum.Lst;
import gm.utils.comum.UCpf;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gm.utils.number.Numeric2;

@Component
public class ClienteService extends ServiceModelo<Cliente> {

	@Autowired AtendenteService atendenteService;

	@Autowired BancoService bancoService;

	@Autowired CepService cepService;

	@Autowired IndiceService indiceService;

	@Autowired OrgaoService orgaoService;

	@Autowired ClienteStatusService clienteStatusService;

	@Autowired TelefoneService telefoneService;

	@Autowired ClienteTipoService clienteTipoService;

	@Autowired ClienteTipoSimulacaoService clienteTipoSimulacaoService;

	@Autowired ClienteRubricaService clienteRubricaService;

	@Autowired ClienteSimulacaoService clienteSimulacaoService;

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Cliente> getClasse() {
		return Cliente.class;
	}

	@Override
	public MapSO toMap(Cliente o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getAgencia() != null) {
			map.put("agencia", o.getAgencia());
		}
		if (o.getAtendenteResponsavel() != null) {
			map.put("atendenteResponsavel", atendenteService.toIdText(o.getAtendenteResponsavel()));
		}
		if (o.getBairro() != null) {
			map.put("bairro", o.getBairro());
		}
		if (o.getBanco() != null) {
			map.put("banco", bancoService.toIdText(o.getBanco()));
		}
		if (o.getCep() != null) {
			map.put("cep", cepService.toIdText(o.getCep()));
		}
		if (o.getCidade() != null) {
			map.put("cidade", o.getCidade());
		}
		if (o.getComplemento() != null) {
			map.put("complemento", o.getComplemento());
		}
		if (o.getCpf() != null) {
			map.put("cpf", UString.toString(o.getCpf()));
		}
		if (o.getDataDeNascimento() != null) {
			map.put("dataDeNascimento", new Data(o.getDataDeNascimento()).format("[dd]/[mm]/[yyyy]"));
		}
		if (o.getDia() != null) {
			map.put("dia", indiceService.toIdText(o.getDia()));
		}
		if (o.getEmail() != null) {
			map.put("email", UString.toString(o.getEmail()));
		}
		if (o.getLogradouro() != null) {
			map.put("logradouro", o.getLogradouro());
		}
		if (o.getMargem() != null) {
			map.put("margem", UString.toString(o.getMargem()));
		}
		if (o.getMatricula() != null) {
			map.put("matricula", o.getMatricula());
		}
		if (o.getNome() != null) {
			map.put("nome", UString.toString(o.getNome()));
		}
		if (o.getNumeroDaConta() != null) {
			map.put("numeroDaConta", o.getNumeroDaConta());
		}
		if (o.getOrgao() != null) {
			map.put("orgao", orgaoService.toIdText(o.getOrgao()));
		}
		if (o.getRendaBruta() != null) {
			map.put("rendaBruta", UString.toString(o.getRendaBruta()));
		}
		if (o.getRendaLiquida() != null) {
			map.put("rendaLiquida", UString.toString(o.getRendaLiquida()));
		}
		if (o.getStatus() != null) {
			map.put("status", clienteStatusService.toIdText(o.getStatus()));
		}
		if (o.getTelefonePrincipal() != null) {
			map.put("telefonePrincipal", telefoneService.toMap(o.getTelefonePrincipal(), listas));
		}
		if (o.getTelefoneSecundario() != null) {
			map.put("telefoneSecundario", telefoneService.toMap(o.getTelefoneSecundario(), listas));
		}
		if (o.getTipo() != null) {
			map.put("tipo", clienteTipoService.toIdText(o.getTipo()));
		}
		if (o.getTipoDeSimulacao() != null) {
			map.put("tipoDeSimulacao", clienteTipoSimulacaoService.toIdText(o.getTipoDeSimulacao()));
		}
		if (o.getUf() != null) {
			map.put("uf", o.getUf());
		}
		if (o.getValorDeSimulacao() != null) {
			map.put("valorDeSimulacao", UString.toString(o.getValorDeSimulacao()));
		}
		if (listas) {
			map.put("rubricas", clienteRubricaService.toMapList(getRubricas(o)));
			map.put("simulacoes", clienteSimulacaoService.toMapList(getSimulacoes(o)));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		map.put("calcular", podeCalcular(o));
		return map;
	}

	private Lst<ClienteRubrica> getRubricas(Cliente o) {
		return clienteRubricaService.select().cliente().eq(o).list();
	}

	private Lst<ClienteSimulacao> getSimulacoes(Cliente o) {
		return clienteSimulacaoService.select().cliente().eq(o).list();
	}

	protected boolean podeCalcular(Cliente o) {
		return true;
	}

	@Override
	protected Cliente fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Cliente o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setAgencia(mp.getString("agencia"));
		o.setAtendenteResponsavel(find(atendenteService, mp, "atendenteResponsavel"));
		o.setBanco(find(bancoService, mp, "banco"));
		o.setCep(find(cepService, mp, "cep"));
		o.setComplemento(mp.getString("complemento"));
		o.setCpf(mp.getString("cpf"));
		o.setDataDeNascimento(Data.getCalendar(Data.unformat("[dd]/[mm]/[yyyy]", mp.get("dataDeNascimento"))));
		o.setDia(findId(indiceService, mp, "dia"));
		o.setEmail(mp.getString("email"));
		o.setMatricula(mp.getString("matricula"));
		o.setNome(mp.getString("nome"));
		o.setNumeroDaConta(mp.getString("numeroDaConta"));
		o.setOrgao(find(orgaoService, mp, "orgao"));
		MapSO telefonePrincipal = mp.get("telefonePrincipal");
		if (telefonePrincipal != null) {
			o.setTelefonePrincipal(telefoneService.save(telefonePrincipal));
		}
		MapSO telefoneSecundario = mp.get("telefoneSecundario");
		if (telefoneSecundario != null) {
			o.setTelefoneSecundario(telefoneService.save(telefoneSecundario));
		}
		o.setTipo(findId(clienteTipoService, mp, "tipo"));
		o.setTipoDeSimulacao(findId(clienteTipoSimulacaoService, mp, "tipoDeSimulacao"));
		o.setValorDeSimulacao(mp.getBigDecimal("valorDeSimulacao"));
		return o;
	}

	@Override
	public Cliente newO() {
		Cliente o = new Cliente();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Cliente o) {
		o.setAgencia(tratarString(o.getAgencia()));
		if (UString.length(o.getAgencia()) > 50) {
			throw new MessageException("O campo Cliente > Agência aceita no máximo 50 caracteres");
		}
		o.setComplemento(tratarString(o.getComplemento()));
		if (UString.length(o.getComplemento()) > 50) {
			throw new MessageException("O campo Cliente > Complemento aceita no máximo 50 caracteres");
		}
		o.setCpf(tratarCpf(o.getCpf()));
		if (o.getCpf() == null) {
			throw new MessageException("O campo Cliente > CPF é obrigatório");
		}
		if (UString.length(o.getCpf()) > 14) {
			throw new MessageException("O campo Cliente > CPF aceita no máximo 14 caracteres");
		}
		o.setEmail(tratarEmail(o.getEmail()));
		if (UString.length(o.getEmail()) > 50) {
			throw new MessageException("O campo Cliente > E-mail aceita no máximo 50 caracteres");
		}
		o.setMatricula(tratarString(o.getMatricula()));
		if (UString.length(o.getMatricula()) > 50) {
			throw new MessageException("O campo Cliente > Matrícula aceita no máximo 50 caracteres");
		}
		o.setNome(tratarNomeProprio(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Cliente > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Cliente > Nome aceita no máximo 50 caracteres");
		}
		o.setNumeroDaConta(tratarString(o.getNumeroDaConta()));
		if (UString.length(o.getNumeroDaConta()) > 50) {
			throw new MessageException("O campo Cliente > Número da Conta aceita no máximo 50 caracteres");
		}
		if (o.getTipo() == null) {
			throw new MessageException("O campo Cliente > Tipo é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueCpf(o);
		}
		validar2(o);
		if (o.getStatus() == null) {
			throw new MessageException("O campo Cliente > Status é obrigatório");
		}
		o.setMargem(getMargem(o));
		validar3(o);
	}

	public void validarUniqueCpf(Cliente o) {
		ClienteSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.cpf().eq(o.getCpf());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("cliente_cpf"));
		}
	}


	@Override
	public int getIdEntidade() {
		return IDS.Cliente.idEntidade;
	}

	@Override
	protected void saveListas(Cliente o, final MapSO map) {
		List<MapSO> list;
		final int id = o.getId();
		list = map.get("rubricas");
		if (list != null) {
			for (MapSO item : list) {
				item.put("cliente", id);
				if (item.isTrue("excluido")) {
					if (!item.isEmpty("id")) {
						clienteRubricaService.delete(item.id());
					}
				} else {
					clienteRubricaService.save(item);
				}
			}
		}
		list = map.get("simulacoes");
		if (list != null) {
			for (MapSO item : list) {
				item.put("cliente", id);
				if (item.isTrue("excluido")) {
					if (!item.isEmpty("id")) {
						clienteSimulacaoService.delete(item.id());
					}
				} else {
					clienteSimulacaoService.save(item);
				}
			}
		}
	}

	@Override
	protected void saveObservacoes(Cliente o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Cliente> func) {
		final ClienteSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "agencia", select.agencia());
		FiltroConsulta.fk(params, "atendenteResponsavel", select.atendenteResponsavel());
		FiltroConsulta.string(params, "bairro", select.cep().bairro());
		FiltroConsulta.fk(params, "banco", select.banco());
		FiltroConsulta.fk(params, "cep", select.cep());
		FiltroConsulta.string(params, "cidade", select.cep().cidade());
		FiltroConsulta.string(params, "complemento", select.complemento());
		FiltroConsulta.cpf(params, "cpf", select.cpf());
		FiltroConsulta.date(params, "dataDeNascimento", select.dataDeNascimento());
		FiltroConsulta.fk(params, "dia", select.dia());
		FiltroConsulta.email(params, "email", select.email());
		FiltroConsulta.string(params, "logradouro", select.cep().logradouro());
		FiltroConsulta.money(params, "margem", select.margem());
		FiltroConsulta.string(params, "matricula", select.matricula());
		FiltroConsulta.nomeProprio(params, "nome", select.nome());
		FiltroConsulta.string(params, "numeroDaConta", select.numeroDaConta());
		FiltroConsulta.fk(params, "orgao", select.orgao());
		FiltroConsulta.money(params, "rendaBruta", select.rendaBruta());
		FiltroConsulta.money(params, "rendaLiquida", select.rendaLiquida());
		FiltroConsulta.fk(params, "status", select.status());
		FiltroConsulta.fk(params, "telefonePrincipal", select.telefonePrincipal());
		FiltroConsulta.fk(params, "telefoneSecundario", select.telefoneSecundario());
		FiltroConsulta.fk(params, "tipo", select.tipo());
		FiltroConsulta.fk(params, "tipoDeSimulacao", select.tipoDeSimulacao());
		FiltroConsulta.string(params, "uf", select.cep().uf());
		FiltroConsulta.money(params, "valorDeSimulacao", select.valorDeSimulacao());
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
		Lst<Cliente> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Cliente buscaUnicoObrig(MapSO params) {
		final ClienteSelect<?> select = select();
		String agencia = params.getString("agencia");
		if (!UString.isEmpty(agencia)) select.agencia().eq(agencia);
		Integer atendenteResponsavel = getId(params, "atendenteResponsavel");
		if (atendenteResponsavel != null) {
			select.atendenteResponsavel().id().eq(atendenteResponsavel);
		}
		Integer banco = getId(params, "banco");
		if (banco != null) {
			select.banco().id().eq(banco);
		}
		Integer cep = getId(params, "cep");
		if (cep != null) {
			select.cep().id().eq(cep);
		}
		String complemento = params.getString("complemento");
		if (!UString.isEmpty(complemento)) select.complemento().eq(complemento);
		String cpf = params.getString("cpf");
		if (!UString.isEmpty(cpf)) {
			cpf = UCpf.format(cpf);
			if (!UCpf.isValid(cpf)) {
				throw new MessageException("CPF Inválido!");
			} else {
				select.cpf().eq(cpf);
			}
		}
		Calendar dataDeNascimento = params.get("dataDeNascimento");
		if (dataDeNascimento != null) select.dataDeNascimento().eq(dataDeNascimento);
		Integer dia = getId(params, "dia");
		if (dia != null) {
			select.dia().eq(dia);
		}
		String email = params.getString("email");
		if (!UString.isEmpty(email)) select.email().eq(email);
		BigDecimal margem = params.get("margem");
		if (margem != null) select.margem().eq(margem);
		String matricula = params.getString("matricula");
		if (!UString.isEmpty(matricula)) select.matricula().eq(matricula);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String numeroDaConta = params.getString("numeroDaConta");
		if (!UString.isEmpty(numeroDaConta)) select.numeroDaConta().eq(numeroDaConta);
		Integer orgao = getId(params, "orgao");
		if (orgao != null) {
			select.orgao().id().eq(orgao);
		}
		BigDecimal rendaBruta = params.get("rendaBruta");
		if (rendaBruta != null) select.rendaBruta().eq(rendaBruta);
		BigDecimal rendaLiquida = params.get("rendaLiquida");
		if (rendaLiquida != null) select.rendaLiquida().eq(rendaLiquida);
		Integer status = getId(params, "status");
		if (status != null) {
			select.status().eq(status);
		}
		Integer telefonePrincipal = getId(params, "telefonePrincipal");
		if (telefonePrincipal != null) {
			select.telefonePrincipal().id().eq(telefonePrincipal);
		}
		Integer telefoneSecundario = getId(params, "telefoneSecundario");
		if (telefoneSecundario != null) {
			select.telefoneSecundario().id().eq(telefoneSecundario);
		}
		Integer tipo = getId(params, "tipo");
		if (tipo != null) {
			select.tipo().eq(tipo);
		}
		Integer tipoDeSimulacao = getId(params, "tipoDeSimulacao");
		if (tipoDeSimulacao != null) {
			select.tipoDeSimulacao().eq(tipoDeSimulacao);
		}
		BigDecimal valorDeSimulacao = params.get("valorDeSimulacao");
		if (valorDeSimulacao != null) select.valorDeSimulacao().eq(valorDeSimulacao);
		Cliente o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (agencia != null) {
			s += "&& agencia = '" + agencia + "'";
		}
		if (atendenteResponsavel != null) {
			s += "&& atendenteResponsavel = '" + atendenteResponsavel + "'";
		}
		if (banco != null) {
			s += "&& banco = '" + banco + "'";
		}
		if (cep != null) {
			s += "&& cep = '" + cep + "'";
		}
		if (complemento != null) {
			s += "&& complemento = '" + complemento + "'";
		}
		if (cpf != null) {
			s += "&& cpf = '" + cpf + "'";
		}
		if (dataDeNascimento != null) {
			s += "&& dataDeNascimento = '" + dataDeNascimento + "'";
		}
		if (dia != null) {
			s += "&& dia = '" + dia + "'";
		}
		if (email != null) {
			s += "&& email = '" + email + "'";
		}
		if (margem != null) {
			s += "&& margem = '" + margem + "'";
		}
		if (matricula != null) {
			s += "&& matricula = '" + matricula + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (numeroDaConta != null) {
			s += "&& numeroDaConta = '" + numeroDaConta + "'";
		}
		if (orgao != null) {
			s += "&& orgao = '" + orgao + "'";
		}
		if (rendaBruta != null) {
			s += "&& rendaBruta = '" + rendaBruta + "'";
		}
		if (rendaLiquida != null) {
			s += "&& rendaLiquida = '" + rendaLiquida + "'";
		}
		if (status != null) {
			s += "&& status = '" + status + "'";
		}
		if (telefonePrincipal != null) {
			s += "&& telefonePrincipal = '" + telefonePrincipal + "'";
		}
		if (telefoneSecundario != null) {
			s += "&& telefoneSecundario = '" + telefoneSecundario + "'";
		}
		if (tipo != null) {
			s += "&& tipo = '" + tipo + "'";
		}
		if (tipoDeSimulacao != null) {
			s += "&& tipoDeSimulacao = '" + tipoDeSimulacao + "'";
		}
		if (valorDeSimulacao != null) {
			s += "&& valorDeSimulacao = '" + valorDeSimulacao + "'";
		}
		s = "Não foi encontrado um Cliente com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	public MapSO cepLookups(int id) {
		Cep o = cepService.find(id);
		MapSO mp = new MapSO();
		mp.add("bairro", o.getBairro());
		mp.add("cidade", o.getCidade());
		mp.add("logradouro", o.getLogradouro());
		mp.add("uf", o.getUf());
		return mp;
	}

	@Override
	protected Cliente setOld(Cliente o) {
		Cliente old = newO();
		old.setAgencia(o.getAgencia());
		old.setAtendenteResponsavel(o.getAtendenteResponsavel());
		old.setBanco(o.getBanco());
		old.setCep(o.getCep());
		old.setComplemento(o.getComplemento());
		old.setCpf(o.getCpf());
		old.setDataDeNascimento(o.getDataDeNascimento());
		old.setDia(o.getDia());
		old.setEmail(o.getEmail());
		old.setMargem(o.getMargem());
		old.setMatricula(o.getMatricula());
		old.setNome(o.getNome());
		old.setNumeroDaConta(o.getNumeroDaConta());
		old.setOrgao(o.getOrgao());
		old.setRendaBruta(o.getRendaBruta());
		old.setRendaLiquida(o.getRendaLiquida());
		old.setStatus(o.getStatus());
		old.setTelefonePrincipal(o.getTelefonePrincipal());
		old.setTelefoneSecundario(o.getTelefoneSecundario());
		old.setTipo(o.getTipo());
		old.setTipoDeSimulacao(o.getTipoDeSimulacao());
		old.setValorDeSimulacao(o.getValorDeSimulacao());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(Cliente o) {
		Cliente old = o.getOld();
		if (!UString.equals(o.getAgencia(), old.getAgencia())) {
			return true;
		}
		if (o.getAtendenteResponsavel() != old.getAtendenteResponsavel()) {
			return true;
		}
		if (o.getBanco() != old.getBanco()) {
			return true;
		}
		if (o.getCep() != old.getCep()) {
			return true;
		}
		if (!UString.equals(o.getComplemento(), old.getComplemento())) {
			return true;
		}
		if (!UString.equals(o.getCpf(), old.getCpf())) {
			return true;
		}
		if (o.getDataDeNascimento() != old.getDataDeNascimento()) {
			return true;
		}
		if (o.getDia() != old.getDia()) {
			return true;
		}
		if (!UString.equals(o.getEmail(), old.getEmail())) {
			return true;
		}
		if (o.getMargem() != old.getMargem()) {
			return true;
		}
		if (!UString.equals(o.getMatricula(), old.getMatricula())) {
			return true;
		}
		if (!UString.equals(o.getNome(), old.getNome())) {
			return true;
		}
		if (!UString.equals(o.getNumeroDaConta(), old.getNumeroDaConta())) {
			return true;
		}
		if (o.getOrgao() != old.getOrgao()) {
			return true;
		}
		if (o.getRendaBruta() != old.getRendaBruta()) {
			return true;
		}
		if (o.getRendaLiquida() != old.getRendaLiquida()) {
			return true;
		}
		if (o.getStatus() != old.getStatus()) {
			return true;
		}
		if (o.getTelefonePrincipal() != old.getTelefonePrincipal()) {
			return true;
		}
		if (o.getTelefoneSecundario() != old.getTelefoneSecundario()) {
			return true;
		}
		if (o.getTipo() != old.getTipo()) {
			return true;
		}
		if (o.getTipoDeSimulacao() != old.getTipoDeSimulacao()) {
			return true;
		}
		if (o.getValorDeSimulacao() != old.getValorDeSimulacao()) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(Cliente o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(Cliente o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Cliente old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.agencia, old.getAgencia(), o.getAgencia());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.atendenteResponsavel, old.getAtendenteResponsavel(), o.getAtendenteResponsavel());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.banco, old.getBanco(), o.getBanco());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.cep, old.getCep(), o.getCep());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.complemento, old.getComplemento(), o.getComplemento());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.cpf, old.getCpf(), o.getCpf());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.dataDeNascimento, old.getDataDeNascimento(), o.getDataDeNascimento());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.dia, old.getDia(), o.getDia());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.email, old.getEmail(), o.getEmail());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.margem, old.getMargem(), o.getMargem(), 2);
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.matricula, old.getMatricula(), o.getMatricula());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.nome, old.getNome(), o.getNome());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.numeroDaConta, old.getNumeroDaConta(), o.getNumeroDaConta());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.orgao, old.getOrgao(), o.getOrgao());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.rendaBruta, old.getRendaBruta(), o.getRendaBruta(), 2);
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.rendaLiquida, old.getRendaLiquida(), o.getRendaLiquida(), 2);
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.status, old.getStatus(), o.getStatus());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.telefonePrincipal, old.getTelefonePrincipal(), o.getTelefonePrincipal());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.telefoneSecundario, old.getTelefoneSecundario(), o.getTelefoneSecundario());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.tipo, old.getTipo(), o.getTipo());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.tipoDeSimulacao, old.getTipoDeSimulacao(), o.getTipoDeSimulacao());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Cliente.valorDeSimulacao, old.getValorDeSimulacao(), o.getValorDeSimulacao(), 2);
	}

	@Override
	protected void registrarAuditoriaDelete(Cliente o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(Cliente o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(Cliente o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(Cliente o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public static boolean statusEmAtendimento(Cliente o) {
		return o.getStatus() == ClienteStatusService.EM_ATENDIMENTO;
	}

	public boolean statusEmAtendimento(int id) {
		return statusEmAtendimento(find(id));
	}

	public static boolean statusEmprestimoRealizado(Cliente o) {
		return o.getStatus() == ClienteStatusService.EMPRESTIMO_REALIZADO;
	}

	public boolean statusEmprestimoRealizado(int id) {
		return statusEmprestimoRealizado(find(id));
	}

	public static boolean statusNaoTemInteresse(Cliente o) {
		return o.getStatus() == ClienteStatusService.NAO_TEM_INTERESSE;
	}

	public boolean statusNaoTemInteresse(int id) {
		return statusNaoTemInteresse(find(id));
	}

	public static boolean tipoServidor(Cliente o) {
		return o.getTipo() == ClienteTipoService.SERVIDOR;
	}

	public boolean tipoServidor(int id) {
		return tipoServidor(find(id));
	}

	public static boolean tipoPensionista(Cliente o) {
		return o.getTipo() == ClienteTipoService.PENSIONISTA;
	}

	public boolean tipoPensionista(int id) {
		return tipoPensionista(find(id));
	}

	public static boolean tipoDeSimulacaoPeloValorDaParcela(Cliente o) {
		return o.getTipoDeSimulacao() == ClienteTipoSimulacaoService.PELO_VALOR_DA_PARCELA;
	}

	public boolean tipoDeSimulacaoPeloValorDaParcela(int id) {
		return tipoDeSimulacaoPeloValorDaParcela(find(id));
	}

	public static boolean tipoDeSimulacaoPeloValorDoEmprestimo(Cliente o) {
		return o.getTipoDeSimulacao() == ClienteTipoSimulacaoService.PELO_VALOR_DO_EMPRESTIMO;
	}

	public boolean tipoDeSimulacaoPeloValorDoEmprestimo(int id) {
		return tipoDeSimulacaoPeloValorDoEmprestimo(find(id));
	}

	public ClienteSelect<?> select(Boolean excluido) {
		ClienteSelect<?> o = new ClienteSelect<ClienteSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.nome().asc();
		return o;
	}

	public ClienteSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Cliente o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Cliente o) {
		if (o == null) return null;
		String s = "";
		s += " - " + o.getCpf();
		s += " - " + o.getNome();
		return s.substring(3);
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Cliente");
		list.add("nome;cpf;dataDeNascimento;atendenteResponsavel;tipo;matricula;orgao;banco;agencia;numeroDaConta;telefonePrincipal.ddd;telefonePrincipal.numero;telefonePrincipal.whatsapp;telefonePrincipal.recado;telefoneSecundario.ddd;telefoneSecundario.numero;telefoneSecundario.whatsapp;telefoneSecundario.recado;email;cep;complemento;tipoDeSimulacao;valorDeSimulacao;dia;calcular");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("cliente_cpf", "O campo CPF não pode se repetir. Já existe um registro com este valor.");
	}


	@Override
	protected void beforeInsert(Cliente o) {
		o.setStatus(ClienteStatusService.EM_ATENDIMENTO);
	}

	@Override
	protected void afterUpdate(Cliente o) {
		clienteSimulacaoService.calcularParcelas(o);
	}

	protected BigDecimal getMargem(Cliente o) {
		Numeric2 remuneracao = new Numeric2(o.getRendaBruta());
		if (remuneracao.isZero()) {
			return null;
		} else {
			Numeric2 valor = remuneracao.vezes(30).dividido(100);
			if (valor.menor(0)) {
				return null;
			} else {
				return valor.getValor();
			}
		}
	}

	@Override
	protected void validar3(Cliente o) {
		if (o.getTipoDeSimulacao() == null) {
			return;
		}
		if (tipoDeSimulacaoPeloValorDaParcela(o)) {
			Numeric2 margem = new Numeric2(o.getMargem());
			Numeric2 simulacao = new Numeric2(o.getValorDeSimulacao());
			if (simulacao.maior(margem)) {
				throw new MessageException("Valor de Simulação dever ser menor ou igual à Margem");
			}
		}
	}

}
