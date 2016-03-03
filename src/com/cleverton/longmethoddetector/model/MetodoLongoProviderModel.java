package com.cleverton.longmethoddetector.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cleverton.longmethoddetector.negocio.AnalisadorInformacoesMetodos;
import com.cleverton.longmethoddetector.negocio.CarregaSalvaArquivos;

public class MetodoLongoProviderModel {

	public static List<InformacoesMetodoModel> getMetodosLongos() {
		ArrayList<String> projetos = CarregaSalvaArquivos.carregarProjetos();
		System.out.println("Projetos a serem analisados:");
		for (String string : projetos) {
			System.out.println(string);
		}
		AnalisadorInformacoesMetodos analisador = new AnalisadorInformacoesMetodos();
		ArrayList<InformacoesMetodoModel> informacoesMetodos = null;
		try {
			informacoesMetodos = analisador.obterInformacoesMetodosDiretorios(projetos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return analisador.getMetodosLongosValorLimiar(informacoesMetodos);
	}

}
