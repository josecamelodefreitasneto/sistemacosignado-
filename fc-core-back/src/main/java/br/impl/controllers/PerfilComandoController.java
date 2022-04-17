package br.impl.controllers;

import br.auto.controllers.PerfilComandoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="perfil-comando/")
public class PerfilComandoController extends PerfilComandoControllerAbstract {}
