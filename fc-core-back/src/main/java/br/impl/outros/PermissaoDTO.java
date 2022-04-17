package br.impl.outros;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissaoDTO {
	private int id;
	private String nome;
	private List<String> comandos;
}
