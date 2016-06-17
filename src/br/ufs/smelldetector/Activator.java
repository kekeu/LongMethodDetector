package br.ufs.smelldetector;

import java.util.ArrayList;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import br.ufs.smelldetector.negocio.AtualizadorInformacoesMetodoLongo;
import br.ufs.smelldetector.negocio.CarregaSalvaArquivo;
import br.ufs.smelldetector.reports.ResourceChangeReporter;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.cleverton.longmethoddetector"; //$NON-NLS-1$
	public static ArrayList<String> projetos;
	// The shared instance
	private static Activator plugin;
	private static IResourceChangeListener listener;

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
		projetos = CarregaSalvaArquivo.carregarProjetos();
		AtualizadorInformacoesMetodoLongo.refreshAll();
		listener = new ResourceChangeReporter();
		adicionarEscutaAlteracaoDiretorio();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		CarregaSalvaArquivo.salvaArquivo(projetos);
		removerEscutaAlteracaoDiretorio();
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
	
	public static void adicionarEscutaAlteracaoDiretorio() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener,
				IResourceChangeEvent.POST_BUILD);
	}
	
	public static void removerEscutaAlteracaoDiretorio() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
	}
}
