package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.marker.MarkerFactory;
import com.cleverton.longmethoddetector.model.DadosMetodoLongo;
import com.cleverton.longmethoddetector.model.ProviderModel;
import com.cleverton.longmethoddetector.preferences.PreferenceConstants;
import com.cleverton.longmethoddetector.preferences.ValorMetodoLongoPreferencePage;
import com.cleverton.longmethoddetector.views.MetodoLongoView;

public class AtualizadorInformacoesMetodoLongo {

	public static void refreshAll() {
		AnalisadorProjeto analisadorProjeto = new AnalisadorProjeto();
		GerenciadorProjeto.validaProjetosAtivos(Activator.projetos);
		atulizarDadosProviderModel(analisadorProjeto);
		if (ProviderModel.INSTANCE.metodoslongos != null) {
			refreshView();
			refreshMarcadores(ProviderModel.INSTANCE.metodoslongos);
		}
		//refreshallProjects();
	}

	private static void atulizarDadosProviderModel(AnalisadorProjeto analisadorProjeto) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		FiltrarMetodosLongos filtrarMetodos = new FiltrarMetodosLongos();
		if (store.getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(
				ValorMetodoLongoPreferencePage.OPCAOVALORLIMIAR)) {
			ProviderModel.INSTANCE.dadosClasses = analisadorProjeto.getInfoMetodosPorProjetos(
					Activator.projetos, false);
			ProviderModel.INSTANCE.metodoslongos = filtrarMetodos.filtrarPorValorLimiar(
					ProviderModel.INSTANCE.dadosClasses);
		} else {
			if (store.getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(
					ValorMetodoLongoPreferencePage.OPCAOPROJETOEXEMPLO)) {
				ProviderModel.INSTANCE.dadosClasses = analisadorProjeto.getInfoMetodosPorProjetos(
						Activator.projetos, true);
				GerenciadorComponenteArquitetural gca = new GerenciadorComponenteArquitetural();
				if (ProviderModel.INSTANCE.dadosComponentesArquiteturais == null) {
					ProviderModel.INSTANCE.dadosComponentesArquiteturais = 
							gca.criarTabelaComponentesArquiteturais(store.
									getString(PreferenceConstants.PROJETO_EXEMPLO));
				}
				ProviderModel.INSTANCE.metodoslongos = filtrarMetodos.filtrarPorProjetoExemmplo(
						ProviderModel.INSTANCE.dadosClasses, 
						ProviderModel.INSTANCE.dadosComponentesArquiteturais); 
			}
		}
	}

	public static void refreshMarcadores(ArrayList<DadosMetodoLongo> metodosLongos) {
		MarkerFactory marcador = new MarkerFactory();
		marcador.deleteTodosMarcadores();
		marcador.adicionarMarcadoresMetodosLongos(metodosLongos);
	}

	public static void refreshView() {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window != null) {
					IWorkbenchPage page = window.getActivePage();
					try {
						if (page != null && page.findView(MetodoLongoView.ID) != null) {
							page.hideView(page.findView(MetodoLongoView.ID));
							page.showView(MetodoLongoView.ID);
							page.activate(page.getActiveEditor());
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		}); 
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
	} */
}
