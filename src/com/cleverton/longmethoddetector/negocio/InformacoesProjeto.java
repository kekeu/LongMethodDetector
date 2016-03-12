package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import org.eclipse.core.internal.resources.Project;
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

public class InformacoesProjeto {

	public static ArrayList<String> validaProjetosAtivos(ArrayList<String> projetos) {
		for (int i = 0; i < projetos.size(); i++) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			String[] aux = projetos.get(i).split("/");
			IProject iProject = workspace.getRoot().getProject(aux[aux.length-1]);
			if (!(iProject.exists() && iProject.isOpen())) {
				projetos.remove(i);
			}
		}
		CarregaSalvaArquivos.salvaArquivo(projetos);
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
	
}
