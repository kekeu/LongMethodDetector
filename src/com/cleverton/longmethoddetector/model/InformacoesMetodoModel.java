package com.cleverton.longmethoddetector.model;

public class InformacoesMetodoModel {
	
	private int linhaInicial;
	private int numeroLinhas;
	private String diretorioDaClasse;
	private String nomeClasse;
	private String classeExtends;
	private String nomeMetodo;
	private int charInicial;
	private int charFinal;
	
	public InformacoesMetodoModel() {
	}
	
	public InformacoesMetodoModel(int linhaInicial, int numeroLinhas, String diretorioDaClasse, 
			String nomeMetodo, int charInicial, int charFinal) {
		this.linhaInicial = linhaInicial;
		this.numeroLinhas = numeroLinhas;
		this.diretorioDaClasse = diretorioDaClasse;
		this.nomeMetodo = nomeMetodo;
		this.charFinal = charFinal;
		this.charInicial = charInicial;
	}
	
	public String getClasseExtends() {
		return classeExtends;
	}

	public void setClasseExtends(String classeExtends) {
		this.classeExtends = classeExtends;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public int getCharInicial() {
		return charInicial;
	}

	public void setCharInicial(int charInicial) {
		this.charInicial = charInicial;
	}

	public int getCharFinal() {
		return charFinal;
	}

	public void setCharFinal(int charFinal) {
		this.charFinal = charFinal;
	}

	public int getLinhaInicial() {
		return linhaInicial;
	}

	public void setLinhaInicial(int linhaInicial) {
		this.linhaInicial = linhaInicial;
	}

	public int getNumeroLinhas() {
		return numeroLinhas;
	}

	public void setNumeroLinhas(int numeroLinhas) {
		this.numeroLinhas = numeroLinhas;
	}

	public String getDiretorioDaClasse() {
		return diretorioDaClasse;
	}

	public void setDiretorioDaClasse(String diretorioDaClasse) {
		this.diretorioDaClasse = diretorioDaClasse;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}
}
