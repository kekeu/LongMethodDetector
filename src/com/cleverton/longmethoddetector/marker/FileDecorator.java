package com.cleverton.longmethoddetector.marker;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import com.cleverton.longmethoddetector.negocio.GerenciadorProjeto;

public class FileDecorator extends LabelProvider implements ILightweightLabelDecorator {

	//public static final String ICON = "/icons/find_menor.png";
	private static Font font = new Font(null, "Arial", 10, 0);
	private static Color color = new Color(null, 153, 255, 153);

	@Override
	public void decorate(Object resource, IDecoration decoration) {
		if (((IResource)resource).getType() == IResource.PROJECT)
		{
			if (GerenciadorProjeto.projetoEstaNaAnalise(((IResource)resource).getProject()
					.getLocation().toString())) {
				//decoration.addOverlay(ImageDescriptor.createFromFile(FileDecorator.class, ICON));
				decoration.addSuffix(" Projeto Em Análise - MLD");
				decoration.setFont(font);
				decoration.setForegroundColor(color);
			} else {
				decoration.addOverlay(null);
				decoration.addSuffix("");
				decoration.setFont(null);
				decoration.setForegroundColor(null);
			}
		}
		int markers = MarkerFactory.findMarkers((IResource) resource).size();
		if (markers > 0) {
			//decoration.addOverlay(ImageDescriptor.createFromFile(FileDecorator.class, ICON));
			//decoration.addPrefix("<T> ");
			decoration.addSuffix(" " + markers + " Método(s) Longo(s)");
			decoration.setFont(font);
			decoration.setForegroundColor(color);
		}
	}

}
