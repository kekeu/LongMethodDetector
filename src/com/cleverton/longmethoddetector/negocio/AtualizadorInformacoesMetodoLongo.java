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
		if (ProviderModel.INSTANCE.metodoslongos == null) {
			ProviderModel.INSTANCE.metodoslongos = new ArrayList<>();
		}
		refreshView();
		refreshMarcadores(ProviderModel.INSTANCE.metodoslongos);
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
			System.out.println("Métodos longos valor limiar: " + 
					ProviderModel.INSTANCE.metodoslongos.size() + " métodos encontrados.");
		} else {
			GerenciadorProjetoExemplo gpe = new GerenciadorProjetoExemplo();
			if (store.getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(
					ValorMetodoLongoPreferencePage.OPCAOPROJETOEXEMPLO)) {
				if (store.getString(PreferenceConstants.CALCULAR_GERAL_POR_PREOCUPACAO).equals(
						ValorMetodoLongoPreferencePage.OPCAOCALCULARPORPREOCUPACAO)) {
					ProviderModel.INSTANCE.dadosClasses = analisadorProjeto.getInfoMetodosPorProjetos(
							Activator.projetos, true);
					
					if (ProviderModel.INSTANCE.dadosComponentesArquiteturais == null) {
						ProviderModel.INSTANCE.dadosComponentesArquiteturais = gpe.
							criarTabelaCompArquiteturais(store.getString(PreferenceConstants.PROJETO_EXEMPLO),
									store.getInt(PreferenceConstants.PORCENTAGEM_PROJETO_EXEMPLO));
					}
					ProviderModel.INSTANCE.metodoslongos = filtrarMetodos.
							filtrarPorProjetoExemploPreocupacaoArquitetural(ProviderModel.
								INSTANCE.dadosClasses,ProviderModel.INSTANCE.dadosComponentesArquiteturais);
					System.out.println("Métodos longos valor preocupação arquitetural: " + 
							ProviderModel.INSTANCE.metodoslongos.size() + " métodos encontrados.");
				} else {
					ProviderModel.INSTANCE.dadosClasses = analisadorProjeto.getInfoMetodosPorProjetos(
							Activator.projetos, true);
					if (ProviderModel.INSTANCE.valorLimiarGlobal == 0) {
						ProviderModel.INSTANCE.valorLimiarGlobal = gpe.
								obterValorLimiarGlobal(store.getString(PreferenceConstants.PROJETO_EXEMPLO),
										store.getInt(PreferenceConstants.PORCENTAGEM_PROJETO_EXEMPLO));
					}
					if (ProviderModel.INSTANCE.medianaGlobal == 0) {
						ProviderModel.INSTANCE.medianaGlobal = gpe.
								obterMedianaGlobal(store.getString(PreferenceConstants.PROJETO_EXEMPLO),
										store.getInt(PreferenceConstants.PORCENTAGEM_PROJETO_EXEMPLO));
					}
					// Adicionar na lista de métodos longos
					ProviderModel.INSTANCE.metodoslongos = filtrarMetodos.filtrarPorProjetoExemploGeral(
						ProviderModel.INSTANCE.dadosClasses, ProviderModel.INSTANCE.valorLimiarGlobal, 
						ProviderModel.INSTANCE.medianaGlobal);
					System.out.println("Métodos longos valor global projeto exemplo: " + 
							ProviderModel.INSTANCE.metodoslongos.size() + " métodos encontrados.");
				}
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
