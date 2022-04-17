package br.impl.controllers;

import br.auto.controllers.ObservacaoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="observacao/")
public class ObservacaoController extends ObservacaoControllerAbstract {}
