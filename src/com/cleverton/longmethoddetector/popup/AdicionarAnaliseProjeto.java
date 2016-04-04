package com.cleverton.longmethoddetector.popup;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.cleverton.longmethoddetector.negocio.GerenciadorProjeto;

public class AdicionarAnaliseProjeto implements IEditorActionDelegate {

	public AdicionarAnaliseProjeto() {
		super();
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void run(IAction action) {
		GerenciadorProjeto.addProjectAnalysis();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		String projetoSelecionado = GerenciadorProjeto.getCurrentProject();
		if (projetoSelecionado != null && 
				GerenciadorProjeto.projetoEstaNaAnalise(projetoSelecionado)) {
			action.setEnabled(false);
		} else {
			action.setEnabled(true);
		}
	}

}