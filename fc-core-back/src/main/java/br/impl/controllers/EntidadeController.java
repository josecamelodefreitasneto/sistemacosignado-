package br.impl.controllers;

import br.auto.controllers.EntidadeControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="entidade/")
public class EntidadeController extends EntidadeControllerAbstract {}
