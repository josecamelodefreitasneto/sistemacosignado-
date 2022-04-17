package br.impl.controllers;

import br.auto.controllers.PerfilControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="perfil/")
public class PerfilController extends PerfilControllerAbstract {}
