package com.cleverton.longmethoddetector.model;

import java.util.ArrayList;

public class DadosClasse {

	private String diretorioDaClasse;
	private String nomeClasse;
	private ArrayList<String> classesExtendsImplements;
	private int grupoArquitetural;
	private ArrayList<DadosMetodo> metodos;
	
	public String getDiretorioDaClasse() {
		return diretorioDaClasse;
	}
	
	public void setDiretorioDaClasse(String diretorioDaClasse) {
		this.diretorioDaClasse = diretorioDaClasse;
	}
	
	public String getNomeClasse() {
		return nomeClasse;
	}
	
	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
	
	public ArrayList<String> getClassesExtendsImplements() {
		return classesExtendsImplements;
	}
	
	public void setClassesExtendsImplements(ArrayList<String> classesExtendsImplements) {
		this.classesExtendsImplements = classesExtendsImplements;
	}
	
	public int getGrupoArquitetural() {
		return grupoArquitetural;
	}
	
	public void setGrupoArquitetural(int grupoArquitetural) {
		this.grupoArquitetural = grupoArquitetural;
	}
	
	public ArrayList<DadosMetodo> getMetodos() {
		return metodos;
	}
	
	public void setMetodos(ArrayList<DadosMetodo> metodos) {
		this.metodos = metodos;
	}
	
}
