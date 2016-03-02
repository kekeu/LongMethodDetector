package com.cleverton.longmethoddetector.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cleverton.longmethoddetector.negocio.AnalisadorInformacoesMetodos;

public enum MetodoLongoProviderModel {
	INSTANCE;

	private List<InformacoesMetodoModel> listaMetodosLongos;

	private MetodoLongoProviderModel() {
		listaMetodosLongos = new ArrayList<InformacoesMetodoModel>();
		ArrayList<String> projetos = new ArrayList<>();
		projetos.add("C:\\runtime-EclipseApplication\\Projeto_P2");
		projetos.add("C:\\runtime-EclipseApplication\\TESTJDT");
		
		AnalisadorInformacoesMetodos analisador = new AnalisadorInformacoesMetodos();
		ArrayList<InformacoesMetodoModel> informacoesMetodos;
		try {
			informacoesMetodos = analisador
					.obterInformacoesMetodosDiretorios(projetos);
			listaMetodosLongos.addAll(analisador.getMetodosLongosValorLimiar(informacoesMetodos));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<InformacoesMetodoModel> getMetodosLongos() {
		return listaMetodosLongos;
	}

}
