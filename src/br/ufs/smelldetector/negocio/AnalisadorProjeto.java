package br.ufs.smelldetector.negocio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.ufs.smelldetector.model.DadosClasse;

public class AnalisadorProjeto {

	public ArrayList<DadosClasse> getInfoMetodosPorProjetos(
			ArrayList<String> projetos, boolean AdicionarArquitetura) {
		ArrayList<DadosClasse> dadosTodasClasses = null;
		ArrayList<String> listaArquivosdiretorios = new ArrayList<>();
		for (String diretorio : projetos) {
			listaArquivosdiretorios.addAll(getArquivosPorProjeto(diretorio));
		}
		AnalisadorClasseMetodo analisadorClasseMetodo = new AnalisadorClasseMetodo();
		try {
			if (AdicionarArquitetura) {
				dadosTodasClasses = analisadorClasseMetodo
						.getInfoMetodosDosArquivos(listaArquivosdiretorios, true);
			} else {
				dadosTodasClasses = analisadorClasseMetodo
						.getInfoMetodosDosArquivos(listaArquivosdiretorios, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dadosTodasClasses;
	}

	public ArrayList<String> getArquivosPorProjeto(String nomeDiretorio){
		//System.out.println();
		//System.out.println();
		ArrayList<String> retorno = new ArrayList<>();
		ArrayList<File> listaArquivos = new ArrayList<>();
		File directory = new File(nomeDiretorio);
		listaArquivos.add(directory);
		while (!isAllFile(listaArquivos)) {
			//get all the files from a directory
			for (int i = 0; i < listaArquivos.size(); i++) {
				if (listaArquivos.get(i).isDirectory()) {
					File[] fList = listaArquivos.get(i).listFiles();
					listaArquivos.remove(i);
					for (int j = 0; j < fList.length; j++) {
						if (fList[j].isDirectory()) {
							listaArquivos.add(fList[j]);
						} else {
							if (fList[j].getPath().endsWith(".java")) {
								//System.out.println(fList[j].getAbsolutePath());
								retorno.add(fList[j].getAbsolutePath());
							}
						}
					}
				}
			}
		}
		//System.out.println();
		//System.out.println();
		return retorno;
	}

	public boolean isAllFile(ArrayList<File> listaFiles) {
		for (File file : listaFiles) {
			if (file.isDirectory()) {
				return false;
			}
		}
		return true;
	}

}
