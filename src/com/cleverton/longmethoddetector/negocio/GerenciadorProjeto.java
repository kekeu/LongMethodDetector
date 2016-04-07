package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.marker.MarkerFactory;

public class GerenciadorProjeto {

	public final static int PROJETO_NAO_ENCONTRADO = -1; 
	
	public static void validaProjetosAtivos(ArrayList<String> projetos) {
		for (int i = 0; i < projetos.size(); i++) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject iProject = workspace.getRoot().getProject(
					nomeProjetoPorCaminho(projetos.get(i)));
			if (!(iProject.exists() && iProject.isOpen())) {
				projetos.remove(i);
			}
		}
		Activator.projetos = projetos;
	}

	public static String nomeProjetoPorCaminho(String caminho) {
		String[] partes = caminho.split("/");
		return partes[partes.length-1];
	}

	public static String getCurrentProject() {    
		IProject project = null;    
		ISelectionService selectionService = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService();;    
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
		if(project == null)
			return null;
		else 
			return project.getLocation().toString();
	}

	public static void addProjectAnalysis() {
		String projetoSelecionado = GerenciadorProjeto.getCurrentProject();
		MessageDialog dialog = null;
		if (projetoSelecionado != null) {
			Activator.projetos.add(projetoSelecionado);
			AtualizadorInformacoesMetodoLongo.refreshAll();
			dialog = new MessageDialog(null, "Long Method Detector", null, 
					"Projeto Adicionado Para Analise", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
			
		} else {
			dialog = new MessageDialog(null, "Long Method Detector", null, 
					"Não foi possível adicionar o projeto.\n"
							+ "Pois, não foi possível obter o projeto selecionado", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
		}
		dialog.open();
	}

	public static void removeProjectAnalysis() {
		String projetoSelecionado = GerenciadorProjeto.getCurrentProject();
		MessageDialog dialog = null;
		if (projetoSelecionado != null) {
			ArrayList<String> projetos = Activator.projetos; 
			for (int i = 0; i < projetos.size(); i++) {
				if (projetos.get(i).equals(projetoSelecionado)) {
					System.out.println("Removeu Projeto: " + projetos.get(i));
					projetos.remove(i);
				}
			}
			Activator.projetos = projetos;
			new MarkerFactory().deleteMarcadorPorProjeto(projetoSelecionado);
			AtualizadorInformacoesMetodoLongo.refreshAll();
			dialog = new MessageDialog(null, "Long Method Detector", null, 
					"Projeto Removido da Analise", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
		} else {
			dialog = new MessageDialog(null, "Long Method Detector", null, 
					"Não foi possível remover o projeto. "
							+ "Pois, não foi possível obter o projeto selecionado", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
		}
		dialog.open();
	}
	
	public static boolean projetoEstaNaAnalise(String verificaProjeto) {
		ArrayList<String> projetos = Activator.projetos;
		for (int i = 0; i < projetos.size(); i++) {
			if (projetos.get(i).equals(verificaProjeto)) {
				return true;
			}
		}
		return false;
	}
	
	public static void removerProjetoPorId(int posicao) {
		Activator.projetos.remove(posicao);
	}
	
	public static void alterarNameProjetoAnalisadoPorPosicao(String novoProjeto, int posicao) {
		Activator.projetos.remove(posicao);
		Activator.projetos.add(novoProjeto);
	}
}
