package com.cleverton.longmethoddetector.negocio;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.marker.MarkerFactory;
import com.cleverton.longmethoddetector.views.MetodoLongoView;

public class AtualizadorInformacoesMetodoLongo {

	public void refreshAll() {
		new AnalisadorInformacoesMetodos().realizarNovaAnalise();
		refreshMarcadores();
		refreshView();
		//refreshallProjects();
	}

	public void refreshMarcadores() {
		MarkerFactory marcador = new MarkerFactory();
		marcador.deleteTodosMarcadores();
		try {
			marcador.adicionarMarcadoresMetodosLongos();
		} catch (CoreException e) {
			e.printStackTrace();
		}
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

	/*public static void refreshProjetc(String project) {
		IProject projeto = ResourcesPlugin.getWorkspace().getRoot().getProject(
				GerenciadorProjeto.nomeProjetoPorCaminho(project));
		try {
			projeto.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void refreshallProjects() {
		for(String projeto : Activator.projetos){
			try {
				IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
						GerenciadorProjeto.nomeProjetoPorCaminho(projeto));
				iProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}*/
}
