package br.impl.controllers;

import br.auto.controllers.AuditoriaTransacaoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="auditoria-transacao/")
public class AuditoriaTransacaoController extends AuditoriaTransacaoControllerAbstract {}
