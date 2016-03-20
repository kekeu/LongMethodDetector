package com.cleverton.longmethoddetector.reports;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;

import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;

public class ResourceChangeReporter implements IResourceChangeListener {
	public void resourceChanged(IResourceChangeEvent event) {
		switch (event.getType()) {
		case IResourceChangeEvent.PRE_CLOSE:
			System.out.println("Fechou o projeto");
			AtualizadorInformacoesMetodoLongo atualizador = new AtualizadorInformacoesMetodoLongo();
			atualizador.refreshAll();
			break;
		}
	}
}
