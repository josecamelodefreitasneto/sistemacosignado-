/* tcc-java */
import CampoAlterado from '../../../fc/outros/CampoAlterado';
import Cliente from '../../cruds/cliente/Cliente';
import ClienteRubricaUtils from '../../cruds/clienteRubrica/ClienteRubricaUtils';
import ClienteSimulacaoUtils from '../../cruds/clienteSimulacao/ClienteSimulacaoUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import IdText from '../../misc/utils/IdText';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import TelefoneUtils from '../../cruds/telefone/TelefoneUtils';
import UArray from '../../misc/utils/UArray';
import UBoolean from '../../misc/utils/UBoolean';
import UCommons from '../../misc/utils/UCommons';
import UDouble from '../../misc/utils/UDouble';
import UEntity from '../../../fc/components/UEntity';
import UString from '../../misc/utils/UString';

export default class ClienteUtilsAbstract {
	checkInstance() {
		Sessao.checkInstance("ClienteUtils", this);
	}
	init() {}
	equalsValorDeSimulacao(a, b) {
		return UDouble.equals(a.getValorDeSimulacao(), b.getValorDeSimulacao());
	}
	equalsDataDeNascimento(a, b) {
		return UString.equals(a.getDataDeNascimento(), b.getDataDeNascimento());
	}
	equalsAgencia(a, b) {
		return UString.equals(a.getAgencia(), b.getAgencia());
	}
	equalsComplemento(a, b) {
		return UString.equals(a.getComplemento(), b.getComplemento());
	}
	equalsCpf(a, b) {
		return UString.equals(a.getCpf(), b.getCpf());
	}
	equalsEmail(a, b) {
		return UString.equals(a.getEmail(), b.getEmail());
	}
	equalsMatricula(a, b) {
		return UString.equals(a.getMatricula(), b.getMatricula());
	}
	equalsNome(a, b) {
		return UString.equals(a.getNome(), b.getNome());
	}
	equalsNumeroDaConta(a, b) {
		return UString.equals(a.getNumeroDaConta(), b.getNumeroDaConta());
	}
	equalsAtendenteResponsavel(a, b) {
		return UEntity.equalsId(a.getAtendenteResponsavel(), b.getAtendenteResponsavel());
	}
	equalsBanco(a, b) {
		return UEntity.equalsId(a.getBanco(), b.getBanco());
	}
	equalsCep(a, b) {
		return UEntity.equalsId(a.getCep(), b.getCep());
	}
	equalsTipo(a, b) {
		return UEntity.equalsId(a.getTipo(), b.getTipo());
	}
	equalsTipoDeSimulacao(a, b) {
		return UEntity.equalsId(a.getTipoDeSimulacao(), b.getTipoDeSimulacao());
	}
	equalsDia(a, b) {
		return UEntity.equalsId(a.getDia(), b.getDia());
	}
	equalsOrgao(a, b) {
		return UEntity.equalsId(a.getOrgao(), b.getOrgao());
	}
	equalsTelefonePrincipal(a, b) {
		return TelefoneUtils.getInstance().equals(a.getTelefonePrincipal(), b.getTelefonePrincipal());
	}
	equalsTelefoneSecundario(a, b) {
		return TelefoneUtils.getInstance().equals(a.getTelefoneSecundario(), b.getTelefoneSecundario());
	}
	equalsRubricas(a, b) {
		return ClienteRubricaUtils.getInstance().equalsList(a.getRubricas(), b.getRubricas());
	}
	equalsSimulacoes(a, b) {
		return ClienteSimulacaoUtils.getInstance().equalsList(a.getSimulacoes(), b.getSimulacoes());
	}
	equalsObservacoes(a, b) {
		return ObservacaoUtils.getInstance().equalsList(a.getObservacoes(), b.getObservacoes());
	}
	equalsExcluido(a, b) {
		return UBoolean.eq(a.getExcluido(), b.getExcluido());
	}
	equals(a, b) {
		this.checkInstance();
		if (UCommons.isEmpty(a)) return UCommons.isEmpty(b);
		if (UCommons.isEmpty(b)) return false;
		if (!this.equalsValorDeSimulacao(a, b)) return false;
		if (!this.equalsDataDeNascimento(a, b)) return false;
		if (!this.equalsAgencia(a, b)) return false;
		if (!this.equalsComplemento(a, b)) return false;
		if (!this.equalsCpf(a, b)) return false;
		if (!this.equalsEmail(a, b)) return false;
		if (!this.equalsMatricula(a, b)) return false;
		if (!this.equalsNome(a, b)) return false;
		if (!this.equalsNumeroDaConta(a, b)) return false;
		if (!this.equalsRubricas(a, b)) return false;
		if (!this.equalsSimulacoes(a, b)) return false;
		if (!this.equalsAtendenteResponsavel(a, b)) return false;
		if (!this.equalsBanco(a, b)) return false;
		if (!this.equalsCep(a, b)) return false;
		if (!this.equalsTipo(a, b)) return false;
		if (!this.equalsTipoDeSimulacao(a, b)) return false;
		if (!this.equalsDia(a, b)) return false;
		if (!this.equalsOrgao(a, b)) return false;
		if (!this.equalsTelefonePrincipal(a, b)) return false;
		if (!this.equalsTelefoneSecundario(a, b)) return false;
		if (!this.equalsObservacoes(a, b)) return false;
		if (!this.equalsExcluido(a, b)) return false;
		return true;
	}
	camposAlterados(a, b) {
		let list = [];
		if (!this.equalsValorDeSimulacao(a, b)) {
			list.add(new CampoAlterado().setCampo("Valor de Simulação").setDe(UString.toString(a.getValorDeSimulacao())).setPara(UString.toString(b.getValorDeSimulacao())));
		}
		if (!this.equalsDataDeNascimento(a, b)) {
			list.add(new CampoAlterado().setCampo("Data de Nascimento").setDe(UString.toString(a.getDataDeNascimento())).setPara(UString.toString(b.getDataDeNascimento())));
		}
		if (!this.equalsAgencia(a, b)) {
			list.add(new CampoAlterado().setCampo("Agência").setDe(a.getAgencia()).setPara(b.getAgencia()));
		}
		if (!this.equalsComplemento(a, b)) {
			list.add(new CampoAlterado().setCampo("Complemento").setDe(a.getComplemento()).setPara(b.getComplemento()));
		}
		if (!this.equalsCpf(a, b)) {
			list.add(new CampoAlterado().setCampo("CPF").setDe(a.getCpf()).setPara(b.getCpf()));
		}
		if (!this.equalsEmail(a, b)) {
			list.add(new CampoAlterado().setCampo("E-mail").setDe(a.getEmail()).setPara(b.getEmail()));
		}
		if (!this.equalsMatricula(a, b)) {
			list.add(new CampoAlterado().setCampo("Matrícula").setDe(a.getMatricula()).setPara(b.getMatricula()));
		}
		if (!this.equalsNome(a, b)) {
			list.add(new CampoAlterado().setCampo("Nome").setDe(a.getNome()).setPara(b.getNome()));
		}
		if (!this.equalsNumeroDaConta(a, b)) {
			list.add(new CampoAlterado().setCampo("Número da Conta").setDe(a.getNumeroDaConta()).setPara(b.getNumeroDaConta()));
		}
		if (!this.equalsAtendenteResponsavel(a, b)) {
			list.add(new CampoAlterado().setCampo("Atendente Responsável").setDe(UString.toString(a.getAtendenteResponsavel())).setPara(UString.toString(b.getAtendenteResponsavel())));
		}
		if (!this.equalsBanco(a, b)) {
			list.add(new CampoAlterado().setCampo("Banco").setDe(UString.toString(a.getBanco())).setPara(UString.toString(b.getBanco())));
		}
		if (!this.equalsCep(a, b)) {
			list.add(new CampoAlterado().setCampo("Cep").setDe(UString.toString(a.getCep())).setPara(UString.toString(b.getCep())));
		}
		if (!this.equalsTipo(a, b)) {
			list.add(new CampoAlterado().setCampo("Tipo").setDe(UString.toString(a.getTipo())).setPara(UString.toString(b.getTipo())));
		}
		if (!this.equalsTipoDeSimulacao(a, b)) {
			list.add(new CampoAlterado().setCampo("Tipo de Simulação").setDe(UString.toString(a.getTipoDeSimulacao())).setPara(UString.toString(b.getTipoDeSimulacao())));
		}
		if (!this.equalsDia(a, b)) {
			list.add(new CampoAlterado().setCampo("Dia").setDe(UString.toString(a.getDia())).setPara(UString.toString(b.getDia())));
		}
		if (!this.equalsOrgao(a, b)) {
			list.add(new CampoAlterado().setCampo("Orgão").setDe(UString.toString(a.getOrgao())).setPara(UString.toString(b.getOrgao())));
		}
		if (!this.equalsTelefonePrincipal(a, b)) {
			list.add(new CampoAlterado().setCampo("Telefone Principal"));
		}
		if (!this.equalsTelefoneSecundario(a, b)) {
			list.add(new CampoAlterado().setCampo("Telefone Secundário"));
		}
		if (!this.equalsRubricas(a, b)) {
			list.add(new CampoAlterado().setCampo("Rubricas"));
		}
		if (!this.equalsSimulacoes(a, b)) {
			list.add(new CampoAlterado().setCampo("Simulacões"));
		}
		if (!this.equalsObservacoes(a, b)) {
			list.add(new CampoAlterado().setCampo("Observações"));
		}
		if (!this.equalsExcluido(a, b)) {
			list.add(new CampoAlterado().setCampo("Excluído"));
		}
		return list;
	}
	equalsList(a, b) {
		return UArray.equals(a, b, (x, y) => this.equals(x, y));
	}
	fromJson(json) {
		if (UCommons.isEmpty(json)) return null;
		let o = new Cliente();
		o.setId(json.id);
		if (UCommons.notEmpty(json.agencia)) {
			o.setAgencia(json.agencia);
		}
		if (UCommons.notEmpty(json.atendenteResponsavel)) {
			o.setAtendenteResponsavel(new IdText(json.atendenteResponsavel.id, json.atendenteResponsavel.text));
		}
		if (UCommons.notEmpty(json.bairro)) {
			o.setBairro(json.bairro);
		}
		if (UCommons.notEmpty(json.banco)) {
			o.setBanco(new IdText(json.banco.id, json.banco.text));
		}
		if (UCommons.notEmpty(json.calcular)) {
			o.setCalcular(json.calcular);
		}
		if (UCommons.notEmpty(json.cep)) {
			o.setCep(new IdText(json.cep.id, json.cep.text));
		}
		if (UCommons.notEmpty(json.cidade)) {
			o.setCidade(json.cidade);
		}
		if (UCommons.notEmpty(json.complemento)) {
			o.setComplemento(json.complemento);
		}
		if (UCommons.notEmpty(json.cpf)) {
			o.setCpf(json.cpf);
		}
		if (UCommons.notEmpty(json.dataDeNascimento)) {
			o.setDataDeNascimento(json.dataDeNascimento);
		}
		if (UCommons.notEmpty(json.dia)) {
			o.setDia(new IdText(json.dia.id, json.dia.text));
		}
		if (UCommons.notEmpty(json.email)) {
			o.setEmail(json.email);
		}
		if (UCommons.notEmpty(json.logradouro)) {
			o.setLogradouro(json.logradouro);
		}
		if (UCommons.notEmpty(json.margem)) {
			o.setMargem(json.margem);
		}
		if (UCommons.notEmpty(json.matricula)) {
			o.setMatricula(json.matricula);
		}
		if (UCommons.notEmpty(json.nome)) {
			o.setNome(json.nome);
		}
		if (UCommons.notEmpty(json.numeroDaConta)) {
			o.setNumeroDaConta(json.numeroDaConta);
		}
		if (UCommons.notEmpty(json.orgao)) {
			o.setOrgao(new IdText(json.orgao.id, json.orgao.text));
		}
		if (UCommons.notEmpty(json.rendaBruta)) {
			o.setRendaBruta(json.rendaBruta);
		}
		if (UCommons.notEmpty(json.rendaLiquida)) {
			o.setRendaLiquida(json.rendaLiquida);
		}
		if (UCommons.notEmpty(json.rubricas)) {
			o.setRubricas(ClienteRubricaUtils.getInstance().fromJsonList(json.rubricas));
		}
		if (UCommons.notEmpty(json.simulacoes)) {
			o.setSimulacoes(ClienteSimulacaoUtils.getInstance().fromJsonList(json.simulacoes));
		}
		if (UCommons.notEmpty(json.status)) {
			o.setStatus(new IdText(json.status.id, json.status.text));
		}
		if (UCommons.notEmpty(json.telefonePrincipal)) {
			o.setTelefonePrincipal(TelefoneUtils.getInstance().fromJson(json.telefonePrincipal));
		}
		if (UCommons.notEmpty(json.telefoneSecundario)) {
			o.setTelefoneSecundario(TelefoneUtils.getInstance().fromJson(json.telefoneSecundario));
		}
		if (UCommons.notEmpty(json.tipo)) {
			o.setTipo(new IdText(json.tipo.id, json.tipo.text));
		}
		if (UCommons.notEmpty(json.tipoDeSimulacao)) {
			o.setTipoDeSimulacao(new IdText(json.tipoDeSimulacao.id, json.tipoDeSimulacao.text));
		}
		if (UCommons.notEmpty(json.uf)) {
			o.setUf(json.uf);
		}
		if (UCommons.notEmpty(json.valorDeSimulacao)) {
			o.setValorDeSimulacao(json.valorDeSimulacao);
		}
		o.setExcluido(json.excluido);
		o.setRegistroBloqueado(json.registroBloqueado);
		o.setObservacoes(null);
		return o;
	}
	fromJsonList(jsons) {
		if (UCommons.isEmpty(jsons)) return null;
		return jsons.map(o => this.fromJson(o));
	}
	merge(de, para) {
		para.setId(de.getId());
		para.setAgencia(de.getAgencia());
		para.setAtendenteResponsavel(de.getAtendenteResponsavel());
		para.setBairro(de.getBairro());
		para.setBanco(de.getBanco());
		para.setCalcular(de.getCalcular());
		para.setCep(de.getCep());
		para.setCidade(de.getCidade());
		para.setComplemento(de.getComplemento());
		para.setCpf(de.getCpf());
		para.setDataDeNascimento(de.getDataDeNascimento());
		para.setDia(de.getDia());
		para.setEmail(de.getEmail());
		para.setLogradouro(de.getLogradouro());
		para.setMargem(de.getMargem());
		para.setMatricula(de.getMatricula());
		para.setNome(de.getNome());
		para.setNumeroDaConta(de.getNumeroDaConta());
		para.setOrgao(de.getOrgao());
		para.setRendaBruta(de.getRendaBruta());
		para.setRendaLiquida(de.getRendaLiquida());
		para.setRubricas(de.getRubricas());
		para.setSimulacoes(de.getSimulacoes());
		para.setStatus(de.getStatus());
		para.setTelefonePrincipal(TelefoneUtils.getInstance().clonar(de.getTelefonePrincipal()));
		para.setTelefoneSecundario(TelefoneUtils.getInstance().clonar(de.getTelefoneSecundario()));
		para.setTipo(de.getTipo());
		para.setTipoDeSimulacao(de.getTipoDeSimulacao());
		para.setUf(de.getUf());
		para.setValorDeSimulacao(de.getValorDeSimulacao());
		para.setExcluido(de.getExcluido());
		para.setRegistroBloqueado(de.getRegistroBloqueado());
		para.setRubricas(ClienteRubricaUtils.getInstance().clonarList(de.getRubricas()));
		para.setSimulacoes(ClienteSimulacaoUtils.getInstance().clonarList(de.getSimulacoes()));
		para.setObservacoes(ObservacaoUtils.getInstance().clonarList(de.getObservacoes()));
	}
	clonar(obj) {
		if (UCommons.isEmpty(obj)) return null;
		let o = new Cliente();
		o.setOriginal(obj);
		this.merge(obj, o);
		return o;
	}
	clonarList(array) {
		if (UArray.isEmpty(array)) {
			return null;
		} else {
			return array.map(o => this.clonar(o));
		}
	}
	novo() {
		let o = new Cliente();
		o.setId(--EntityCampos.novos);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		o.setRubricas([]);
		o.setSimulacoes([]);
		return o;
	}
}
