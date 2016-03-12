package com.cleverton.longmethoddetector.reports;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;

public class ResourceChangeReporter implements IResourceChangeListener {
	public void resourceChanged(IResourceChangeEvent event) {
		AtualizadorInformacoesMetodoLongo atualizador = new AtualizadorInformacoesMetodoLongo();
		atualizador.refreshAll();
		/*switch (event.getType()) {
		case IResourceChangeEvent.POST_CHANGE:
			atualizador.refreshAll();
			break;
		}
		/*if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			IResourceDelta[] teste =  event.getDelta().getAffectedChildren();
			for (int i = 0; i < teste.length; i++) {
				System.out.println("Medança: "+i);
				System.out.println(teste[i].getFullPath().segment(0));
			}
			try {
				event.getDelta().accept(new DeltaPrinter());
			} catch (CoreException e) {
				e.printStackTrace();
			}
			AtualizadorInformacoesMetodoLongo atualizador = new AtualizadorInformacoesMetodoLongo();
			atualizador.refreshAll();
		}*/
	}
}
