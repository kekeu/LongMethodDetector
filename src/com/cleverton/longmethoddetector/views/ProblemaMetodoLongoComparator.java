package com.cleverton.longmethoddetector.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.cleverton.longmethoddetector.model.ProblemaModel;

public class ProblemaMetodoLongoComparator extends ViewerComparator {
	  private int propertyIndex;
	  private static final int DESCENDING = 1;
	  private int direction = DESCENDING;

	  public ProblemaMetodoLongoComparator() {
	    this.propertyIndex = 0;
	    direction = DESCENDING;
	  }

	  public int getDirection() {
	    return direction == 1 ? SWT.DOWN : SWT.UP;
	  }

	  public void setColumn(int column) {
	    if (column == this.propertyIndex) {
	      // Same column as last sort; toggle the direction
	      direction = 1 - direction;
	    } else {
	      // New column; do an ascending sort
	      this.propertyIndex = column;
	      direction = DESCENDING;
	    }
	  }

	  @Override
	  public int compare(Viewer viewer, Object e1, Object e2) {
	    ProblemaModel p1 = (ProblemaModel) e1;
	    ProblemaModel p2 = (ProblemaModel) e2;
	    int rc = 0;
	    switch (propertyIndex) {
	    case 0:
	      rc = p1.getPastaDeOrigem().compareTo(p2.getPastaDeOrigem());
	      break;
	    case 1:
	      rc = p1.getNomeClasse().compareTo(p2.getNomeClasse());
	      break;
	    case 2:
	      rc = p1.getNomeMetodo().compareTo(p2.getNomeMetodo());
	      break;
	    case 3:
	      if (p1.getNumeroDaLinhaInicial() >= p2.getNumeroDaLinhaInicial()) {
	        rc = 1;
	      } else
	        rc = -1;
	      break;
	    default:
	      rc = 0;
	    }
	    // If descending order, flip the direction
	    if (direction == DESCENDING) {
	      rc = -rc;
	    }
	    return rc;
	  }

	} 
