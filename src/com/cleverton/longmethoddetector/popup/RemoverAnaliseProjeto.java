package com.cleverton.longmethoddetector.popup;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;
import com.cleverton.longmethoddetector.negocio.GerenciadorProjeto;

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
		AtualizadorInformacoesMetodoLongo atualizador = new AtualizadorInformacoesMetodoLongo();
		GerenciadorProjeto.removeProjectAnalysis();
		atualizador.refreshAll();
		MessageDialog dialog = new MessageDialog(null, "Long Method Detector", null, 
				"Projeto Removido da Analise", 
				MessageDialog.INFORMATION, new String[] {"OK"}, 0);
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}
}
