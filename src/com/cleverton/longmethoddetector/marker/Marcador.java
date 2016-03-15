package com.cleverton.longmethoddetector.marker;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;
import com.cleverton.longmethoddetector.model.MetodoLongoProviderModel;

public class Marcador {

	public final static String ID_MARCADOR = "com.cleverton.longmethoddetector.mymarker";
	public final static String ATRIBUTO_DESCRICAO = "description";
	public final static int DEPTH = IResource.DEPTH_INFINITE;

	public void criarMeuMarcador(IResource resource, InformacoesMetodoModel informacoes) {
		try {
			IMarker marker = resource.createMarker(ID_MARCADOR);
			marker.setAttribute(IMarker.MESSAGE, "Esse é um método longo com " + 
					informacoes.getNumeroLinhas() + " linhas.");
			marker.setAttribute(IMarker.LINE_NUMBER, informacoes.getLinhaInicial());
			marker.setAttribute(ATRIBUTO_DESCRICAO, "Verifique a posiibilidade de refotarar esse método.");
		} catch (CoreException e) {
			System.out.println("Problema no criar marcador");
			e.printStackTrace();
		}
	}

	public void adicionarMarcadoresMetodosLongos() {
		for (InformacoesMetodoModel informacoesML : MetodoLongoProviderModel.
				INSTANCE.metodosLongos) {
			String localWorkspace = alterarDireotioAbsolutoPorWorkspace(
					informacoesML.getDiretorioDaClasse());
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(localWorkspace));
			criarMeuMarcador(file, informacoesML);
		}
	}

	public void deleteTodosMarcadores() {
		for (String nomeProjeto : Activator.projetos) {
			deleteMarcadorPorProjeto(nomeProjeto);
		}
	}

	public void deleteMarcadorPorProjeto(String projeto) {
		String[] partesProjeto = projeto.split("/");
		try {
			ResourcesPlugin.getWorkspace().getRoot().getProject(
					partesProjeto[partesProjeto.length-1]).
			deleteMarkers(ID_MARCADOR, true, DEPTH);
		} catch (CoreException e) {
			System.out.println("Problemas no delete marcador");
			e.printStackTrace();
		}
	}

	public static String alterarDireotioAbsolutoPorWorkspace(String local) {
		String retorno = "";
		ArrayList<String> projetos = Activator.projetos;
		for (String projeto : projetos) {
			local = local.replace("\\", "/");
			if (local.contains(projeto)) {
				String[] partesProjeto = projeto.split("/");
				String nomeProjeto = partesProjeto[partesProjeto.length-1];
				String novoLocal = local.replace(projeto, "");
				retorno = "/" + nomeProjeto + novoLocal;
				retorno = retorno.replace("/", "\\");
				return retorno;
			}
		}
		return retorno;
	}

}
