package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import javax.naming.Context;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.views.MetodoLongoView;

public class AtualizadorInformacoesMetodoLongo {

	private MetodoLongoView metodoLongoView;

	public AtualizadorInformacoesMetodoLongo() {
		metodoLongoView = new MetodoLongoView();
	}

	public void refreshAll() {
		new AnalisadorInformacoesMetodos().realizarNovaAnalise();
		IWorkbenchPage page = pageActive();
		if (page != null && page.findView(MetodoLongoView.ID) != null) {
			metodoLongoView.refresh(page);
		}
	}

	public IWorkbenchPage pageActive() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (wb == null) {
			return null;
		}
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		if (win == null) {
			return null;
		}
		IWorkbenchPage page = win.getActivePage();
		if (page == null) {
			return null;
		}
		return page;
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
