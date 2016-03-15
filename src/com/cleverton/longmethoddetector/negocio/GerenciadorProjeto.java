package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.marker.Marcador;

public class GerenciadorProjeto {

	public static ArrayList<String> validaProjetosAtivos(ArrayList<String> projetos) {
		for (int i = 0; i < projetos.size(); i++) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			String[] aux = projetos.get(i).split("/");
			IProject iProject = workspace.getRoot().getProject(aux[aux.length-1]);
			if (!(iProject.exists() && iProject.isOpen())) {
				projetos.remove(i);
			}
		}
		Activator.projetos = projetos;
		return projetos;
	}
	
	public static String getCurrentProject() {    
		IProject project = null;    
		ISelectionService selectionService = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService();    

		ISelection selection = selectionService.getSelection();    
		if(selection instanceof IStructuredSelection) {    
			Object element = ((IStructuredSelection)selection).getFirstElement();    

			if (element instanceof IResource) {    
				project= ((IResource)element).getProject();    

			}  
			else if (element instanceof IJavaElement) {    
				IJavaProject jProject= ((IJavaElement)element).getJavaProject();    
				project = jProject.getProject();
			}
		}
		return project.getLocation().toString();  
	}
	
	public static void addProjectAnalysis() {
		Activator.projetos.add(GerenciadorProjeto.getCurrentProject());
		System.out.println("Adicionou projeto: " +GerenciadorProjeto.getCurrentProject());
	}

	public static void removeProjectAnalysis() {
		ArrayList<String> projetos = Activator.projetos;
		String projetoSelecionado = GerenciadorProjeto.getCurrentProject(); 
		for (int i = 0; i < projetos.size(); i++) {
			if (projetos.get(i).equals(projetoSelecionado)) {
				System.out.println("Removeu Projeto: " + projetos.get(i));
				new Marcador().deleteMarcadorPorProjeto(projetos.get(i));
				projetos.remove(i);
			}
		}
		Activator.projetos = projetos;
		new Marcador().deleteTodosMarcadores();
	}
	
}
