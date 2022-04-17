package br.impl.controllers;

import br.auto.controllers.UsuarioPerfilControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="usuario-perfil/")
public class UsuarioPerfilController extends UsuarioPerfilControllerAbstract {}
