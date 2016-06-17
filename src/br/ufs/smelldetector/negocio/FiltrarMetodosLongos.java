package br.ufs.smelldetector.negocio;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.jface.preference.IPreferenceStore;

import br.ufs.smelldetector.Activator;
import br.ufs.smelldetector.model.DadosClasse;
import br.ufs.smelldetector.model.DadosComponentesArquiteturais;
import br.ufs.smelldetector.model.DadosMetodo;
import br.ufs.smelldetector.model.DadosMetodoLongo;
import br.ufs.smelldetector.preferences.PreferenceConstants;

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
				if (metodo.getNumeroLinhas() > valorLimiar) {
					DadosMetodoLongo dadosML = new DadosMetodoLongo();
					dadosML.setCharFinal(metodo.getCharFinal());
					dadosML.setCharInicial(metodo.getCharInicial());
					dadosML.setDiretorioDaClasse(classe.getDiretorioDaClasse());
					dadosML.setLinhaInicial(metodo.getLinhaInicial());
					dadosML.setNomeClasse(classe.getNomeClasse());
					dadosML.setNomeMetodo(metodo.getNomeMetodo());
					dadosML.setNumeroLinhas(metodo.getNumeroLinhas());
					dadosML.setMensagem("Long method. Methods in this system have on maximum "
							+ valorLimiar + " lines of code. "
							+ "\nMake sure refactoring could be applied.");
					dadosML.setType("Long Method");
					listaMetodosLongos.add(dadosML);
				}
			}
		}
		return listaMetodosLongos;
	}
	
	public ArrayList<DadosMetodoLongo> filtrarPorProjetoExemploGeral(
			ArrayList<DadosClasse> dadosClasse, int valorLimiarGlobal, int medianaGlobal) {
		ArrayList<DadosMetodoLongo> listaMetodosLongos = new ArrayList<>();
		for (DadosClasse classe : dadosClasse) {
			for (DadosMetodo metodo : classe.getMetodos()) {
				if (metodo.getNumeroLinhas() > valorLimiarGlobal) {
					DadosMetodoLongo dadosML = new DadosMetodoLongo();
					dadosML.setCharFinal(metodo.getCharFinal());
					dadosML.setCharInicial(metodo.getCharInicial());
					dadosML.setDiretorioDaClasse(classe.getDiretorioDaClasse());
					dadosML.setLinhaInicial(metodo.getLinhaInicial());
					dadosML.setNomeClasse(classe.getNomeClasse());
					dadosML.setNomeMetodo(metodo.getNomeMetodo());
					dadosML.setNumeroLinhas(metodo.getNumeroLinhas());
					dadosML.setMensagem("Long method. Methods in this system have between "
							+ medianaGlobal + " and " + valorLimiarGlobal + " lines of code. "
							+ "\nMake sure refactoring could be applied.");
					dadosML.setType("Long Method");
					listaMetodosLongos.add(dadosML);
				}
			}
		}
		return listaMetodosLongos;
	}
	
	public ArrayList<DadosMetodoLongo> filtrarPorProjetoExemploPreocupacaoArquitetural(
			ArrayList<DadosClasse> dadosClasse, LinkedList<DadosComponentesArquiteturais> dca) {
		GerenciadorProjetoExemplo gca = new GerenciadorProjetoExemplo();
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
				dadosML.setMensagem("Long method. Methods in this architectural concern have between "
						+ componente.getMediana() + " and " + componente.getTerceiroQuartil() 
						+ " lines of code. Make sure refactoring could be applied.");
				dadosML.setType("Long Method");
				metodosLongos.add(dadosML);
			}
		}
	}
}
