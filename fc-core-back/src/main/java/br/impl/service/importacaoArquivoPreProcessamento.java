package br.impl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.impl.outros.ServiceModelo;
import gm.utils.map.MapSO;

public class importacaoArquivoPreProcessamento {
	int id;
	ServiceModelo<?> service;
	List<MapSO> validados = new ArrayList<>();
	Map<Integer, String> erros = new HashMap<>();
	int linhaErro;
}