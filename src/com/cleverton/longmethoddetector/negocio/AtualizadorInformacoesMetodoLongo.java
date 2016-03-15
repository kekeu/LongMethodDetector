package com.cleverton.longmethoddetector.negocio;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.marker.Marcador;
import com.cleverton.longmethoddetector.views.MetodoLongoView;

public class AtualizadorInformacoesMetodoLongo {

	public void refreshAll() {
		new AnalisadorInformacoesMetodos().realizarNovaAnalise();
		refreshMarcadores();
		refreshView();
	}
	
	public void refreshMarcadores() {
		Marcador marcador = new Marcador();
		marcador.deleteTodosMarcadores();
		marcador.adicionarMarcadoresMetodosLongos();
	}
	
	public void refreshView() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			try {
				if (page != null && page.findView(MetodoLongoView.ID) != null) {
					page.hideView(page.findView(MetodoLongoView.ID));
					page.showView(MetodoLongoView.ID);
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} 
	} 	
}
