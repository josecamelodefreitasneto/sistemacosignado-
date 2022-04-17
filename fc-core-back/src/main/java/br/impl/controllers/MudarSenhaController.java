package br.impl.controllers;

import br.auto.controllers.MudarSenhaControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="mudar-senha/")
public class MudarSenhaController extends MudarSenhaControllerAbstract {}
