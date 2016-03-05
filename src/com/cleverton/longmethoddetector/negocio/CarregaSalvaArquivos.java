package com.cleverton.longmethoddetector.negocio;

import java.io.IOException;
import java.util.ArrayList;

public class CarregaSalvaArquivos {

	private static LeitorEscritorArquivo LEAProjetos ;

	@SuppressWarnings("unchecked")
	public static ArrayList<String> carregarProjetos() {
		ArrayList<String> projetos = new ArrayList<>();
		LEAProjetos = new LeitorEscritorArquivo(projetos, "Projetos.txt") ;

		try {
			projetos = (ArrayList<String>) LEAProjetos.readFromFile();
			System.out.println("Dados dos alunos carregados com sucesso!");
		} catch (Exception e1) {
			System.out.println("Erro: Arquivo dos alunos não encontrado! \nEstamos criando um novo arquivo.");
		} finally {
			LEAProjetos = null;
		}
		return projetos;
	}

	//joga arrayList em arquivo txt

	public static void salvaArquivo(ArrayList<String> projetos) {
		LEAProjetos = new LeitorEscritorArquivo(projetos, "Projetos.txt");
		try {
			LEAProjetos.salvarContatos() ;
			System.out.println("Dados dos alunos salvos com sucesso!") ;
		} catch (IOException e1) {
			System.out.println("Erro ao salvar dados dos alunos!") ;
			e1.printStackTrace();
		}
	}

}
