package com.cleverton.longmethoddetector.model;

public class DadosMetodoLongo {
	
	private String diretorioDaClasse;
	private String nomeClasse;
	private String nomeMetodo;
	private int linhaInicial;
	private int numeroLinhas;
	private int charInicial;
	private int charFinal;
	private String mensagem;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

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
	
	public String getNomeMetodo() {
		return nomeMetodo;
	}
	
	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
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
	
}
