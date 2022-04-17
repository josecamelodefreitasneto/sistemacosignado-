/* tcc-java */
import React from 'react';
import DiagramaTd from './DiagramaTd';
import DiagramaTitulo from './DiagramaTitulo';
import SuperComponent from '../../misc/components/SuperComponent';
import {Fragment} from 'react';

export default class Diagrama extends SuperComponent {

	render() {
		return (
			<table>
				<thead>
					<tr>
						<td colSpan={33}>
							<span>Model</span>
						</td>
					</tr>
				</thead>
				<tbody>
					{this.getArquivoExtensao()}
					{this.getBanco()}
					{this.getCep()}
					{this.getClienteStatus()}
					{this.getClienteTipo()}
					{this.getClienteTipoSimulacao()}
					{this.getEntidade()}
					{this.getImportacaoArquivoErroMensagem()}
					{this.getImportacaoArquivoStatus()}
					{this.getIndice()}
					{this.getOrgao()}
					{this.getPerfil()}
					{this.getRubrica()}
					{this.getRubricaTipo()}
					{this.getSystemConfig()}
					{this.getTelefone()}
					{this.getTipoAuditoriaEntidade()}
					{this.getUsuario()}
					{this.getArquivoPath()}
					{this.getAtendente()}
					{this.getCampo()}
					{this.getComando()}
					{this.getLogin()}
					{this.getUsuarioPerfil()}
					{this.getArquivo()}
					{this.getAuditoriaTransacao()}
					{this.getCliente()}
					{this.getPerfilComando()}
					{this.getAuditoriaEntidade()}
					{this.getClienteRubrica()}
					{this.getClienteSimulacao()}
					{this.getFilaScripts()}
					{this.getImportacaoArquivo()}
					{this.getObservacao()}
					{this.getAuditoriaCampo()}
					{this.getImportacaoArquivoErro()}
				</tbody>
			</table>
		);
	}

	getImportacaoArquivoErro() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ImportacaoArquivoErro"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3]}/>
				<DiagramaTd nome={"importacaoArquivo"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3]}/>
				<DiagramaTd nome={"linha"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3]}/>
				<DiagramaTd nome={"erro"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2]}/>
			</Fragment>
		);
	}

	getAuditoriaCampo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"AuditoriaCampo"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3]}/>
				<DiagramaTd nome={"auditoriaEntidade"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3]}/>
				<DiagramaTd nome={"campo"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3]}/>
				<DiagramaTd nome={"de"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3]}/>
				<DiagramaTd nome={"para"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3]}/>
			</Fragment>
		);
	}

	getObservacao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Observacao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"texto"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"anexo"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3]}/>
				<DiagramaTd nome={"entidade"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3]}/>
				<DiagramaTd nome={"registro"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3]}/>
			</Fragment>
		);
	}

	getImportacaoArquivo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ImportacaoArquivo"} itens={[5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,8,8,8,8,8,8,8,9,3]}/>
				<DiagramaTd nome={"arquivo"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"delimitador"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"atualizarRegistrosExistentes"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"entidade"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"status"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"totalDeLinhas"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"processadosComSucesso"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"processadosComErro"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3]}/>
				<DiagramaTd nome={"arquivoDeErros"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3]}/>
			</Fragment>
		);
	}

	getFilaScripts() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"FilaScripts"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"operacao"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"sql"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,0,3]}/>
			</Fragment>
		);
	}

	getClienteSimulacao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ClienteSimulacao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"cliente"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"parcelas"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"indice"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"valor"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"contratado"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3]}/>
			</Fragment>
		);
	}

	getClienteRubrica() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ClienteRubrica"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"cliente"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"rubrica"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"valor"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,0,3]}/>
			</Fragment>
		);
	}

	getAuditoriaEntidade() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"AuditoriaEntidade"} itens={[5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,8,8,8,8,8,8,8,8,8,8,9,3,0,3]}/>
				<DiagramaTd nome={"transacao"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"entidade"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"tipo"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"registro"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3]}/>
				<DiagramaTd nome={"numeroDaOperacao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3]}/>
			</Fragment>
		);
	}

	getPerfilComando() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"PerfilComando"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"perfil"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"comando"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getCliente() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Cliente"} itens={[5,5,5,5,5,5,5,5,5,5,5,5,8,8,8,8,8,8,8,8,9,9,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"cpf"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"dataDeNascimento"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"status"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"atendenteResponsavel"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"tipo"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"matricula"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"orgao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"banco"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"agencia"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"numeroDaConta"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"telefonePrincipal"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"telefoneSecundario"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"email"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"cep"} itens={[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"complemento"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"rendaBruta"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"rendaLiquida"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"margem"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"tipoDeSimulacao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"valorDeSimulacao"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"dia"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getAuditoriaTransacao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"AuditoriaTransacao"} itens={[5,5,5,5,5,5,5,5,5,5,8,8,8,8,8,8,8,8,9,8,5,5,9,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"login"} itens={[1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"comando"} itens={[1,1,1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"data"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"tempo"} itens={[0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getArquivo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Arquivo"} itens={[5,5,5,5,5,5,5,5,8,8,8,8,8,8,8,8,8,8,5,8,5,5,5,9,8,9,9,3,0,3,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"tamanho"} itens={[0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"path"} itens={[1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"extensao"} itens={[1,1,1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,3,0,3,0,3]}/>
				<DiagramaTd nome={"checksum"} itens={[0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getUsuarioPerfil() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"UsuarioPerfil"} itens={[0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"usuario"} itens={[1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"perfil"} itens={[1,1,1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getLogin() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Login"} itens={[5,5,5,5,5,8,8,8,8,8,9,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"usuario"} itens={[1,1,1,1,1,2,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"data"} itens={[0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"token"} itens={[0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getComando() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Comando"} itens={[5,5,5,5,8,8,8,8,8,8,5,9,8,8,8,8,8,9,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"entidade"} itens={[1,1,1,1,2,3,3,3,3,3,0,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[0,0,0,0,0,3,3,3,3,3,0,3,3,3,3,3,3,3,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getCampo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Campo"} itens={[5,5,8,8,8,8,8,8,8,8,5,5,8,8,8,8,8,5,5,8,5,5,5,5,8,5,5,8,5,9,0,3]}/>
				<DiagramaTd nome={"entidade"} itens={[1,1,2,3,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"tipo"} itens={[1,1,1,2,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[0,0,0,0,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
				<DiagramaTd nome={"nomeNoBanco"} itens={[0,0,0,0,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,3,0,3]}/>
			</Fragment>
		);
	}

	getAtendente() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Atendente"} itens={[5,8,8,8,8,8,8,8,8,8,5,5,9,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[0,3,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"email"} itens={[0,3,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"usuario"} itens={[1,2,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getArquivoPath() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ArquivoPath"} itens={[8,8,8,8,8,8,8,8,9,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,3,3,3,3,3,3,3,3,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"extensao"} itens={[2,3,3,3,3,3,3,3,3,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"itens"} itens={[0,3,3,3,3,3,3,3,3,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getUsuario() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Usuario"} itens={[8,9,8,8,8,9,9,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"login"} itens={[3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"senha"} itens={[3,3,3,3,3,3,3,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getTipoAuditoriaEntidade() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"TipoAuditoriaEntidade"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getTelefone() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Telefone"} itens={[8,5,8,8,8,5,5,8,5,8,5,5,5,9,9,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"ddd"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"numero"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"whatsapp"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"recado"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,3,3,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getSystemConfig() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"SystemConfig"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"valor"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getRubricaTipo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"RubricaTipo"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getRubrica() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Rubrica"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"tipo"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"codigo"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getPerfil() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Perfil"} itens={[8,5,8,8,8,5,5,9,5,8,5,5,5,5,5,8,9,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,3,0,3,0,0,0,0,0,3,3,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getOrgao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Orgao"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"codigo"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getIndice() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Indice"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em12"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em15"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em18"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em24"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em30"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em36"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em48"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em60"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em72"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em84"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"em96"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getImportacaoArquivoStatus() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ImportacaoArquivoStatus"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getImportacaoArquivoErroMensagem() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ImportacaoArquivoErroMensagem"} itens={[8,5,8,8,8,5,5,5,5,8,5,5,5,5,5,8,5,5,5,8,5,5,5,5,8,5,5,8,5,5,5,9]}/>
				<DiagramaTd nome={"mensagem"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,3]}/>
			</Fragment>
		);
	}

	getEntidade() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Entidade"} itens={[8,5,9,9,9,5,5,5,5,8,5,5,5,5,5,8,5,5,5,9,5,5,5,5,9,5,5,9,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,0]}/>
				<DiagramaTd nome={"nomeClasse"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,0]}/>
				<DiagramaTd nome={"primitivo"} itens={[3,0,3,3,3,0,0,0,0,3,0,0,0,0,0,3,0,0,0,3,0,0,0,0,3,0,0,3,0,0,0,0]}/>
			</Fragment>
		);
	}

	getClienteTipoSimulacao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ClienteTipoSimulacao"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}

	getClienteTipo() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ClienteTipo"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}

	getClienteStatus() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ClienteStatus"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}

	getCep() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Cep"} itens={[8,5,5,5,5,5,5,5,5,8,5,5,5,5,5,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"numero"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"uf"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"cidade"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"bairro"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"logradouro"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}

	getBanco() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"Banco"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"codigo"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}

	getArquivoExtensao() {
		return (
			<Fragment>
				<DiagramaTitulo nome={"ArquivoExtensao"} itens={[9,5,5,5,5,5,5,5,5,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
				<DiagramaTd nome={"nome"} itens={[3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}/>
			</Fragment>
		);
	}
}
Diagrama.COLS = 32;

Diagrama.defaultProps = SuperComponent.defaultProps;
