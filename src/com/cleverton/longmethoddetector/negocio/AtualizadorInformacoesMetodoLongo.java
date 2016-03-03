package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import com.cleverton.longmethoddetector.views.MetodoLongoView;

public class AtualizadorInformacoesMetodoLongo {
	
	private MetodoLongoView metodoLongoView;
	
	public AtualizadorInformacoesMetodoLongo() {
		metodoLongoView = new MetodoLongoView();
	}
	
	public void refreshAll() {
		metodoLongoView.refresh();
	}

	public void addProjectAnalysis() {
		ArrayList<String> projetos = CarregaSalvaArquivos.carregarProjetos();
		projetos.add(InformacoesProjeto.getCurrentProject());
		System.out.println("Adicionou projeto: " +InformacoesProjeto.getCurrentProject());
		CarregaSalvaArquivos.salvaArquivo(projetos);
	}
	
	public void removeProjectAnalysis() {
		ArrayList<String> projetos = CarregaSalvaArquivos.carregarProjetos();
		String projetoSelecionado = InformacoesProjeto.getCurrentProject(); 
		for (int i = 0; i < projetos.size(); i++) {
			if (projetos.get(i).equals(projetoSelecionado)) {
				System.out.println("Removeu Projeto: " + projetos.get(i));
				projetos.remove(i);
			}
		}
		CarregaSalvaArquivos.salvaArquivo(projetos);
	}
}
