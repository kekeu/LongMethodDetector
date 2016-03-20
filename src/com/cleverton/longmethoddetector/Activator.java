package com.cleverton.longmethoddetector;

import java.util.ArrayList;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.ITextEditor;
import org.osgi.framework.BundleContext;

import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;
import com.cleverton.longmethoddetector.negocio.CarregaSalvaArquivos;
import com.cleverton.longmethoddetector.reports.ResourceChangeReporter;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.cleverton.longmethoddetector"; //$NON-NLS-1$
	public static ArrayList<String> projetos;
	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		projetos = CarregaSalvaArquivos.carregarProjetos();
		new AtualizadorInformacoesMetodoLongo().refreshAll();
		IResourceChangeListener listener = new ResourceChangeReporter();
		   ResourcesPlugin.getWorkspace().addResourceChangeListener(listener,
		      IResourceChangeEvent.PRE_CLOSE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		CarregaSalvaArquivos.salvaArquivo(projetos);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	public static IWorkbenchPage getActiveWorkbenchPage() {
		return getActiveWorkbenchWindow().getActivePage();
	}
	
	/**
	 * Always good to have this static method as when dealing with IResources
	 * having a interface to get the editor is very handy
	 * @return
	 * @throws PartInitException 
	 */
	public static ITextEditor getActiveEditor() throws PartInitException {
		return (ITextEditor) getActiveWorkbenchPage().getActiveEditor();
	}
}
