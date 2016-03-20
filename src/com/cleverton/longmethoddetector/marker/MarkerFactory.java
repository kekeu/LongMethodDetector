package com.cleverton.longmethoddetector.marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;
import com.cleverton.longmethoddetector.model.MetodoLongoProviderModel;
import com.cleverton.longmethoddetector.negocio.GerenciadorProjeto;

public class MarkerFactory {

	//Marker ID
	public final static String ID_MARCADOR = "com.cleverton.longmethoddetector.mymarker";
	//Annotation ID
	public static final String ID_ANNOTATION = "com.cleverton.longmethoddetector.myannotation";

	public static IMarker criarMarcador(IResource resource, InformacoesMetodoModel informacoes) 
			throws CoreException {
		IMarker marker = null;
		//note: you use the id that is defined in your plugin.xml
		marker = resource.createMarker(ID_MARCADOR);
		marker.setAttribute(IMarker.MESSAGE, "Esse é um método longo com " + 
				informacoes.getNumeroLinhas() + " linhas. \nÉ recomendável realizar refatoração para"
						+ " diminuir o seu tamanho.");
		//compute and set char start and char end
		marker.setAttribute(IMarker.CHAR_START, informacoes.getCharInicial());
		marker.setAttribute(IMarker.CHAR_END, informacoes.getCharFinal());
		return marker;
	}

	public static IMarker marcadorOpenEditor(IFile file, int linha) throws CoreException {
		IMarker marker = file.createMarker(IMarker.LINE_NUMBER);
		marker.setAttribute(IMarker.LINE_NUMBER, linha);
		marker.setAttribute(IWorkbenchPage.CHANGE_EDITOR_AREA_SHOW, 
				"org.eclipse.ui.DefaultTextEditor");
		return marker;
	}

	/*
	 * returns a list of a resources markers
	 */
	public static List<IMarker> findMarkers(IResource resource) {
		try {
			return Arrays.asList(resource.findMarkers(ID_MARCADOR, true, IResource.DEPTH_ZERO));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}

	/*
	 * Returns a list of markers that are linked to the resource or any sub resource of the resource
	 */
	public static List<IMarker> findAllMarkers(IResource  resource) {
		try {
			return Arrays.asList(resource.findMarkers(ID_MARCADOR, true, IResource.DEPTH_INFINITE));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}

	public void adicionarMarcadoresMetodosLongos() throws CoreException {
		ITextEditor editor = (ITextEditor) Activator.getActiveWorkbenchPage().getActiveEditor();
		for (InformacoesMetodoModel informacoesML : MetodoLongoProviderModel.
				INSTANCE.metodosLongos) {
			String localWorkspace = alterarDireotioAbsolutoPorWorkspace(
					informacoesML.getDiretorioDaClasse());
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(localWorkspace));
			if (editor == null) {
				editor = (ITextEditor)IDE.openEditor(Activator.getActiveWorkbenchPage(), file);
			}
			IMarker marker = criarMarcador(file, informacoesML);
			if (!marker.exists()) {
				adicionarAnnotation(marker, informacoesML.getCharInicial(),
						informacoesML.getCharFinal(), editor);
			}
		}
	}

	public void deleteTodosMarcadores() {
		for (String nomeProjeto : Activator.projetos) {
			deleteMarcadorPorProjeto(nomeProjeto);
		}
	}

	public void deleteMarcadorPorProjeto(String projeto) {
		try {
			ResourcesPlugin.getWorkspace().getRoot().getProject(
					GerenciadorProjeto.nomeProjetoPorCaminho(projeto)).
			deleteMarkers(ID_MARCADOR, true, IResource.DEPTH_INFINITE);
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
				String nomeProjeto = GerenciadorProjeto.nomeProjetoPorCaminho(projeto);
				String novoLocal = local.replace(projeto, "");
				retorno = "/" + nomeProjeto + novoLocal;
				retorno = retorno.replace("/", "\\");
				return retorno;
			}
		}
		return retorno;
	}

	public static void adicionarAnnotation(IMarker marker, int inicio, int fim, ITextEditor editor) {
		//The DocumentProvider enables to get the document currently loaded in the editor
		IDocumentProvider idp = editor.getDocumentProvider();
		//This is the document we want to connect to. This is taken from the current editor input.
		IDocument document = idp.getDocument(editor.getEditorInput());
		//The IannotationModel enables to add/remove/change annoatation to a Document loaded in an Editor
		IAnnotationModel iamf = idp.getAnnotationModel(editor.getEditorInput());
		//Note: The annotation type id specify that you want to create one of your annotations
		SimpleMarkerAnnotation ma = new SimpleMarkerAnnotation(ID_ANNOTATION, marker);
		//Finally add the new annotation to the model
		iamf.connect(document);
		iamf.addAnnotation(ma,new Position(inicio, fim));
		iamf.disconnect(document);
	}

}
