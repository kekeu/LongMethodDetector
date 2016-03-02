package com.cleverton.longmethoddetector.popup;

import java.util.ArrayList;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.cleverton.longmethoddetector.negocio.CarregaSalvaArquivos;
import com.cleverton.longmethoddetector.negocio.InformacoesProjeto;


public class AdicionarAnaliseProjeto implements IEditorActionDelegate {

	public AdicionarAnaliseProjeto() {
		super();
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
		// TODO Auto-generated method stub

	}

	/*
	 * This is used to find all the markers for an IResource and any sub resources.
	 * Then output the number of markers that are returned
	 */
	@Override
	public void run(IAction action) {
		ArrayList<String> projetos = CarregaSalvaArquivos.carregarProjetos();
		projetos.add(InformacoesProjeto.getCurrentProject());
		CarregaSalvaArquivos.salvaArquivo(projetos);
		MessageDialog dialog = new MessageDialog(null, "Long Method Detector", null, 
				"Projeto Adicionado Para Analise", 
				MessageDialog.INFORMATION, new String[] {"OK"}, 0);
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}

}