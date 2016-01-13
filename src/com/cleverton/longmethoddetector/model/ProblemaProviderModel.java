package com.cleverton.longmethoddetector.model;

import java.util.ArrayList;
import java.util.List;

public enum ProblemaProviderModel {
	INSTANCE;

	  private List<ProblemaModel> persons;

	  private ProblemaProviderModel() {
	    persons = new ArrayList<ProblemaModel>();
	    // Image here some fancy database access to read the persons and to
	    // put them into the model
	    persons.add(new ProblemaModel("Pacote 1", "Classe 1", "Método 1", 5));
	    persons.add(new ProblemaModel("Pacote 1", "Classe 1", "Método 3", 34));
	  }

	  public List<ProblemaModel> getProblemas() {
	    return persons;
	  }
	
}
