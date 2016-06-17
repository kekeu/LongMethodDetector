package br.ufs.smelldetector.reports;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

import br.ufs.smelldetector.Activator;
import br.ufs.smelldetector.negocio.AtualizadorInformacoesMetodoLongo;
import br.ufs.smelldetector.negocio.GerenciadorProjeto;

public class ResourceChangeReporter implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		switch (event.getType()) {
		case IResourceChangeEvent.POST_BUILD:
			if (temMudancasProjetosEmAnalise(event)) {
				System.out.println("Alterou algo ");
				AtualizadorInformacoesMetodoLongo.refreshAll();
			}
			//System.out.println("Resources have changed.");
			//event.getDelta().accept(new DeltaPrinter());*/
			break;
		}
	}

	private boolean temMudancasProjetosEmAnalise(IResourceChangeEvent event) {
		IResourceDelta[] locaisAlterados = event.getDelta().getAffectedChildren();
		for (IResourceDelta iResourceDelta : locaisAlterados) {
			for (String projetoEmAnalise : Activator.projetos) {
				if (GerenciadorProjeto.nomeProjetoPorCaminho(iResourceDelta.getFullPath().toString())
						.equals(GerenciadorProjeto.nomeProjetoPorCaminho(projetoEmAnalise))) {
					return true;
				}
			}
		}
		return false;
	}
}


/*class DeltaPrinter implements IResourceDeltaVisitor {
	public boolean visit(IResourceDelta delta) {
		IResource res = delta.getResource();
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			System.out.print("Resource ");
			System.out.print(res.getFullPath());
			System.out.println(" was added.");
			break;
		case IResourceDelta.REMOVED:
			System.out.print("Resource ");
			System.out.print(res.getFullPath());
			System.out.println(" was removed.");
			break;
		case IResourceDelta.CHANGED:
			System.out.print("Resource ");
			System.out.print(delta.getFullPath());
			System.out.println(" has changed.");
			int flags = delta.getFlags();
			if ((flags & IResourceDelta.CONTENT) != 0) {
				System.out.println("--> Content Change");
			}
			if ((flags & IResourceDelta.REPLACED) != 0) {
				System.out.println("--> Content Replaced");
			}
			if ((flags & IResourceDelta.MARKERS) != 0) {
				System.out.println("--> Marker Change");
				IMarkerDelta[] markers = delta.getMarkerDeltas();
				// if interested in markers, check these deltas
			}
			break;
		}
		return true; // visit the children
	}
}*/