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

	public static ArrayList<DadosMetodoLongo> filtrarPorValorLimiar(
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
					listaMetodosLongos.add(dadosML);
				}
			}
		}
		return listaMetodosLongos;
	}
	
	public static ArrayList<DadosMetodoLongo> filtrarPorProjetoExemmplo(
			ArrayList<DadosClasse> dadosClasse, LinkedList<DadosComponentesArquiteturais> dca) {
		ArrayList<DadosMetodoLongo> listaMetodosLongos = new ArrayList<>();
		/*for (DadosClasse classe : dadosClasse) {
			// TODO: Implementar a filtragem dos métodos longos por projeto de exemplo
		}*/
		return listaMetodosLongos;
	}
	
}
