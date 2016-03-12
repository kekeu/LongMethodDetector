package com.cleverton.longmethoddetector.reports;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;

public class DeltaPrinter implements IResourceDeltaVisitor {
	public boolean visit(IResourceDelta delta) {
		AtualizadorInformacoesMetodoLongo atualizador = new AtualizadorInformacoesMetodoLongo();
		switch (delta.getKind()) {   
		case IResourceDelta.ADDED:
			System.out.println("adicionou");
			break;
		case IResourceDelta.REMOVED:
			System.out.println("removeu");
			break;
		case IResourceDelta.REPLACED:
			System.out.println(IResourceDelta.REPLACED+" -- "+delta.getKind()+" -- " + IResourceDelta.CONTENT + " -- " + IResourceDelta.REPLACED);
			if ((delta.getKind() == IResourceDelta.CONTENT) 
					|| (delta.getKind() == IResourceDelta.REPLACED)) {
				atualizador.refreshAll();
			}
			break;
		}
		return false; // visit the children
	}
}
