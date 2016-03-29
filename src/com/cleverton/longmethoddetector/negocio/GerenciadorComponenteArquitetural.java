package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cleverton.longmethoddetector.model.DadosClasse;
import com.cleverton.longmethoddetector.model.ProviderModel;

public class GerenciadorComponenteArquitetural {

	public static void main(String[] args) {
		GerenciadorComponenteArquitetural analisadorProjeto = new GerenciadorComponenteArquitetural();
		analisadorProjeto.criarGruposComponentesArquiteturais();
		System.out.println();
	}


	public void criarGruposComponentesArquiteturais() {
		String projetoExemplo = "C:\\runtime-EclipseApplication\\Projeto_P2";
		/*Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.VALOR_LIMIAR)*/
		ArrayList<String> listaProjetoExemplo = new ArrayList<String>();
		listaProjetoExemplo.add(projetoExemplo);
		AnalisadorProjeto analisador = new AnalisadorProjeto();

		List<DadosClasse> listaClasses = analisador.getInfoMetodosPorProjetos(listaProjetoExemplo, true);
		LinkedList<List<DadosClasse>> grupos = new LinkedList<List<DadosClasse>>();
		for (DadosClasse classe: listaClasses) {
			classificarClassesGrupos(classe, grupos);
		}
		for (int i = 0; i < grupos.size(); i++) {
			System.out.println("Grupo "+ (i+1));
			for (int j = 0; j < grupos.get(i).size(); j++) {
				System.out.print(grupos.get(i).get(j).getNomeClasse() +"  --  ");
			}
			System.out.println();
			System.out.println();
		}
	}

	public void classificarClassesGrupos(DadosClasse classe, LinkedList<List<DadosClasse>> grupos) {
		boolean adicionou = false;
		for(List<DadosClasse> grupo: grupos) {
			if (adicionarClasseGrupo(classe, grupo)) {
				adicionou = true;   
			}   
		}
		if (!adicionou) {
			ArrayList<DadosClasse> novoGrupo = new ArrayList<>();
			novoGrupo.add(classe);
			grupos.add(novoGrupo);
		}
	}

	public boolean adicionarClasseGrupo(DadosClasse classe, List<DadosClasse> grupo) {
		// Regra 1
		if ((classe.getClassesExtendsImplements().get(0) != null) && 
				(classe.getClassesExtendsImplements().get(0).equals(
						grupo.get(0).getClassesExtendsImplements().get(0)))) {
			grupo.add(classe);
			return true;
		} else {
			// Regra 2
			if(temClasseImplementada(classe) && implementaInterfaceDaArquitetura(classe) && 
					implementaMesmaClasseDoGrupo(classe, grupo)) {
				grupo.add(classe);
				return true;
			} else {
				// Regra 3
				if (temClasseImplementada(classe) && !implementaInterfaceDaArquitetura(classe) &&
						implementaMesmaClasseDoGrupo(classe, grupo)) {
					grupo.add(classe);
					return true;
				} /*else {
					// Regra 4
					if (classePossuiPrefixoSufixoIgual(classe, grupo)) {
						// TODO: Falta implementar
					}
				}*/
			}
		}
		return false;
	}

	private boolean classePossuiPrefixoSufixoIgual(DadosClasse classe, List<DadosClasse> grupo) {
		// TODO Implementar
		return false;
	}

	public boolean implementaMesmaClasseDoGrupo(DadosClasse classe, List<DadosClasse> grupo) {
		if (!temClasseImplementada(grupo.get(0))) {
			return false;
		}
		for (int i = 1; i < classe.getClassesExtendsImplements().size(); i++) {
			for (int j = 1; j < grupo.get(0).getClassesExtendsImplements().size(); j++) {
				if (classe.getClassesExtendsImplements().get(i).equals(
						grupo.get(0).getClassesExtendsImplements().get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean temClasseImplementada(DadosClasse classe) {
		if (classe.getClassesExtendsImplements().size() > 1) {
			return true;
		}
		return false;
	}
	
	public boolean implementaInterfaceDaArquitetura(DadosClasse classe) {
		ArrayList<String> interfacesAPI = ProviderModel.INSTANCE.getInterfacesAPI(); 
		for (int i = 1; i < classe.getClassesExtendsImplements().size(); i++) {
			for (int j = 0; j < interfacesAPI.size(); j++) {
				if (classe.getClassesExtendsImplements().get(i).equals(interfacesAPI.get(j))) {
					return false;
				}
			}
		}
		return true;
	}

}
