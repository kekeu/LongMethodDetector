package br.ufs.smelldetector.popup;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import br.ufs.smelldetector.negocio.GerenciadorProjeto;

public class RemoverAnaliseProjeto implements IEditorActionDelegate {

	public RemoverAnaliseProjeto() {
		super();
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		GerenciadorProjeto.removeProjectAnalysis();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		String projetoSelecionado = GerenciadorProjeto.getCurrentProject();
		if (projetoSelecionado != null && 
				GerenciadorProjeto.projetoEstaNaAnalise(projetoSelecionado)) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}
}
