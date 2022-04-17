package br.tcc.select;

import br.tcc.model.Cliente;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectString;

public class ClienteSelect<ORIGEM> extends SelectBase<ORIGEM, Cliente, ClienteSelect<ORIGEM>> {
	public ClienteSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Cliente.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ClienteSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ClienteSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<ClienteSelect<?>> cpf() {
		return new SelectString<>(this, "cpf");
	}
	public SelectDate<ClienteSelect<?>> dataDeNascimento() {
		return new SelectDate<>(this, "dataDeNascimento");
	}
	public SelectInteger<ClienteSelect<?>> status() {
		return new SelectInteger<>(this, "status");
	}
	public AtendenteSelect<ClienteSelect<?>> atendenteResponsavel() {
		return new AtendenteSelect<>(this, getC(), getPrefixo() + ".atendenteResponsavel" );
	}
	public SelectInteger<ClienteSelect<?>> tipo() {
		return new SelectInteger<>(this, "tipo");
	}
	public SelectString<ClienteSelect<?>> matricula() {
		return new SelectString<>(this, "matricula");
	}
	public OrgaoSelect<ClienteSelect<?>> orgao() {
		return new OrgaoSelect<>(this, getC(), getPrefixo() + ".orgao" );
	}
	public BancoSelect<ClienteSelect<?>> banco() {
		return new BancoSelect<>(this, getC(), getPrefixo() + ".banco" );
	}
	public SelectString<ClienteSelect<?>> agencia() {
		return new SelectString<>(this, "agencia");
	}
	public SelectString<ClienteSelect<?>> numeroDaConta() {
		return new SelectString<>(this, "numeroDaConta");
	}
	public TelefoneSelect<ClienteSelect<?>> telefonePrincipal() {
		return new TelefoneSelect<>(this, getC(), getPrefixo() + ".telefonePrincipal" );
	}
	public TelefoneSelect<ClienteSelect<?>> telefoneSecundario() {
		return new TelefoneSelect<>(this, getC(), getPrefixo() + ".telefoneSecundario" );
	}
	public SelectString<ClienteSelect<?>> email() {
		return new SelectString<>(this, "email");
	}
	public CepSelect<ClienteSelect<?>> cep() {
		return new CepSelect<>(this, getC(), getPrefixo() + ".cep" );
	}
	public SelectString<ClienteSelect<?>> complemento() {
		return new SelectString<>(this, "complemento");
	}
	public SelectNumeric2<ClienteSelect<?>> rendaBruta() {
		return new SelectNumeric2<>(this, "rendaBruta");
	}
	public SelectNumeric2<ClienteSelect<?>> rendaLiquida() {
		return new SelectNumeric2<>(this, "rendaLiquida");
	}
	public SelectNumeric2<ClienteSelect<?>> margem() {
		return new SelectNumeric2<>(this, "margem");
	}
	public SelectInteger<ClienteSelect<?>> tipoDeSimulacao() {
		return new SelectInteger<>(this, "tipoDeSimulacao");
	}
	public SelectNumeric2<ClienteSelect<?>> valorDeSimulacao() {
		return new SelectNumeric2<>(this, "valorDeSimulacao");
	}
	public SelectInteger<ClienteSelect<?>> dia() {
		return new SelectInteger<>(this, "dia");
	}
	public SelectBoolean<ClienteSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ClienteSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ClienteSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ClienteSelect<?> asc() {
		return id().asc();
	}
}
