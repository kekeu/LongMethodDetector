package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.jface.preference.IPreferenceStore;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.DadosClasse;
import com.cleverton.longmethoddetector.model.DadosComponentesArquiteturais;
import com.cleverton.longmethoddetector.model.DadosMetodo;
import com.cleverton.longmethoddetector.model.DadosMetodoLongo;
import com.cleverton.longmethoddetector.preferences.PreferenceConstants;

public class FiltrarMetodosLongos {

	public ArrayList<DadosMetodoLongo> filtrarPorValorLimiar(
			ArrayList<DadosClasse> dadosClasse) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int valorLimiar = Integer.parseInt(store.getString(PreferenceConstants.VALOR_LIMIAR));
		ArrayList<DadosMetodoLongo> listaMetodosLongos = new ArrayList<>();
		for (DadosClasse classe : dadosClasse) {
			//System.out.println(classe.getNomeClasse() +"  --  "+classe.getMetodos().size());
			//System.out.println();
			for (DadosMetodo metodo : classe.getMetodos()) {
				if (metodo.getNumeroLinhas() >= valorLimiar) {
					DadosMetodoLongo dadosML = new DadosMetodoLongo();
					dadosML.setCharFinal(metodo.getCharFinal());
					dadosML.setCharInicial(metodo.getCharInicial());
					dadosML.setDiretorioDaClasse(classe.getDiretorioDaClasse());
					dadosML.setLinhaInicial(metodo.getLinhaInicial());
					dadosML.setNomeClasse(classe.getNomeClasse());
					dadosML.setNomeMetodo(metodo.getNomeMetodo());
					dadosML.setNumeroLinhas(metodo.getNumeroLinhas());
					dadosML.setMensagem("Este MÉTODO LONGO contém " + metodo.getNumeroLinhas() + " linhas."
							+ "\n\nÉ recomendável realizar refatoração para diminuir o seu tamanho."
							+ "\nOs métodos do projeto deve ter no máximo " + valorLimiar + " linhas."
							+ " Como foi definido no VALOR LIMIAR.");
					listaMetodosLongos.add(dadosML);
				}
			}
		}
		return listaMetodosLongos;
	}
	
	public ArrayList<DadosMetodoLongo> filtrarPorProjetoExemmplo(
			ArrayList<DadosClasse> dadosClasse, LinkedList<DadosComponentesArquiteturais> dca) {
		GerenciadorComponenteArquitetural gca = new GerenciadorComponenteArquitetural();
		ArrayList<DadosMetodoLongo> metodosLongos = new ArrayList<>();
		for (DadosClasse classe : dadosClasse) {
			boolean analisarProximaRegra = true;
			if (analisarProximaRegra && classe.getClassesExtendsImplements().get(0) != null) {
				analisarProximaRegra = estendeMesmaClasseComponente(classe, metodosLongos, dca);
			}
			if (analisarProximaRegra && gca.temClasseImplementada(classe) && 
					gca.implementaInterfaceDaArquitetura(classe)) {
				analisarProximaRegra = implementaMesmaClasseArquiteturalComponente(classe, metodosLongos, dca);
			}
			if (analisarProximaRegra && gca.temClasseImplementada(classe) && 
					!gca.implementaInterfaceDaArquitetura(classe)) {
				analisarProximaRegra = implementaMesmaClasseAPIComponente(classe, metodosLongos, dca);
			}
			if (analisarProximaRegra) {
				arquiteturaNaoClassificada(classe, metodosLongos, dca);
			}
		}
		return metodosLongos;
	}

	private void arquiteturaNaoClassificada(DadosClasse classe, ArrayList<DadosMetodoLongo> metodosLongos,
			LinkedList<DadosComponentesArquiteturais> dca) {
		for (DadosComponentesArquiteturais componente : dca) {
			if (componente.getExtendsClass() == null && componente.getImplementsArquitecture() == null &&
					componente.getImplementsAPIJava() == null) {
				selecionarMetodos(classe, metodosLongos, componente);
			}
		}
	}

	private boolean implementaMesmaClasseAPIComponente(DadosClasse classe,
			ArrayList<DadosMetodoLongo> metodosLongos, LinkedList<DadosComponentesArquiteturais> dca) {
		for (DadosComponentesArquiteturais componente : dca) {
			for (int i = 1; i < classe.getClassesExtendsImplements().size(); i++) {
				if (classe.getClassesExtendsImplements().get(i).equals(componente.getImplementsAPIJava())) {
					selecionarMetodos(classe, metodosLongos, componente);
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean implementaMesmaClasseArquiteturalComponente(DadosClasse classe,
			ArrayList<DadosMetodoLongo> metodosLongos, LinkedList<DadosComponentesArquiteturais> dca) {
		for (DadosComponentesArquiteturais componente : dca) {
			for (int i = 1; i < classe.getClassesExtendsImplements().size(); i++) {
				if (classe.getClassesExtendsImplements().get(i).equals(componente.getImplementsArquitecture())) {
					selecionarMetodos(classe, metodosLongos, componente);
					return false;
				}
			}
		}
		return true;
	}

	private boolean estendeMesmaClasseComponente(DadosClasse classe, 
			ArrayList<DadosMetodoLongo> metodosLongos, LinkedList<DadosComponentesArquiteturais> dca) {
		for (DadosComponentesArquiteturais componente : dca) {
			if (classe.getClassesExtendsImplements().get(0).equals(componente.getExtendsClass())) {
				selecionarMetodos(classe, metodosLongos, componente);
				return false;
			}
		}
		return true;
	}

	private void selecionarMetodos(DadosClasse classe, ArrayList<DadosMetodoLongo> metodosLongos,
			DadosComponentesArquiteturais componente) {
		for (DadosMetodo metodo : classe.getMetodos()) {
			if (MedianaQuartis.eMaiorQueTerceiroQuartil(metodo.getNumeroLinhas(), 
					componente.getTerceiroQuartil())) {
				DadosMetodoLongo dadosML = new DadosMetodoLongo();
				dadosML.setCharFinal(metodo.getCharFinal());
				dadosML.setCharInicial(metodo.getCharInicial());
				dadosML.setDiretorioDaClasse(classe.getDiretorioDaClasse());
				dadosML.setLinhaInicial(metodo.getLinhaInicial());
				dadosML.setNomeClasse(classe.getNomeClasse());
				dadosML.setNomeMetodo(metodo.getNomeMetodo());
				dadosML.setNumeroLinhas(metodo.getNumeroLinhas());
				dadosML.setMensagem("Este MÉTODO LONGO contém " + metodo.getNumeroLinhas() + " linhas."
						+ "\n\nÉ recomendável realizar refatoração para diminuir o seu tamanho."
						+ "\n\nMétodos de classes que utilizam o COMPONENTE ARQUITETURAL " 
						+ GerenciadorComponenteArquitetural.getClasseComponente(componente)
						+ " devem conter no máximo " + componente.getTerceiroQuartil() + " linhas."
						+ "\nA média de linhas de código para métodos desses componentes arquiteturais é de "
						+ componente.getMediana() + " linhas.");
				metodosLongos.add(dadosML);
			}
		}
	}
}
