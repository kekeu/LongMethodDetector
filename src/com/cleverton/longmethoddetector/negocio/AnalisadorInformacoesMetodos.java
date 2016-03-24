package com.cleverton.longmethoddetector.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.preference.IPreferenceStore;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;
import com.cleverton.longmethoddetector.model.MetodoLongoProviderModel;
import com.cleverton.longmethoddetector.preferences.PreferenceConstants;
import com.cleverton.longmethoddetector.preferences.ValorMetodoLongoPreferencePage;

/**
 * 
 * @author Kekeu
 *
 */
public class AnalisadorInformacoesMetodos {

	public void realizarNovaAnalise() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(
				ValorMetodoLongoPreferencePage.OPCAOVALORLIMIAR)) {
			MetodoLongoProviderModel.INSTANCE.metodosLongos = getMetodosLongosValorLimiar(
					obterMetodosPorProjetosValorLimiar());
		}
	}

	public ArrayList<InformacoesMetodoModel> obterMetodosPorProjetosValorLimiar() {
		ArrayList<String> projetos = Activator.projetos;
		ArrayList<InformacoesMetodoModel> informacoesMetodos = null;
		try {
			informacoesMetodos = obterInformacoesMetodosDiretorios(projetos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return informacoesMetodos;
	}

	public ArrayList<InformacoesMetodoModel> getMetodosLongosValorLimiar(
			ArrayList<InformacoesMetodoModel> informacoesMetodos) {
		ArrayList<InformacoesMetodoModel> metodosLongos = new ArrayList<>();
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int valorLimiar = Integer.parseInt(store.getString(PreferenceConstants.VALOR_LIMIAR));
		for (InformacoesMetodoModel informacoesMetodoModel : informacoesMetodos) {
			if (informacoesMetodoModel.getNumeroLinhas() >= valorLimiar) {
				metodosLongos.add(informacoesMetodoModel);
			}
		}
		return metodosLongos;
	}

	public ArrayList<InformacoesMetodoModel> obterInformacoesMetodosDiretorios(ArrayList<String> diretorios) 
			throws IOException {
		ArrayList<String> listaArquivosdiretorios = new ArrayList<>();
		for (String diretorio : diretorios) {
			listaArquivosdiretorios.addAll(obterArquivosDiretorio(diretorio));
		}
		return obterInformacoesMetodos(listaArquivosdiretorios);
	}

	public ArrayList<String> obterArquivosDiretorio(String nomeDiretorio){
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
								retorno.add(fList[j].getAbsolutePath());
							}
						}
					}
				}
			}
		}
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

	public ArrayList<InformacoesMetodoModel> obterInformacoesMetodos(ArrayList<String> files) throws IOException {
		ArrayList<InformacoesMetodoModel> listaInformacoesMetodos = new ArrayList<>();
		for (String localFile : files) {
			String textoDaClasse = readFileToString(localFile);
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(textoDaClasse.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				@Override
				public boolean visit(TypeDeclaration node) {
					ArrayList<String> listaClassesImplementadas = obterListaClassesImplementadas(node);
					Collections.sort(listaClassesImplementadas);
					String arquiteturaClasse = transformarClassesImplementadasEmString(
							listaClassesImplementadas);
					String nomeDaClasse = node.getName().toString();
					MethodDeclaration[] metodosClasse = node.getMethods();
					for (int i = 0; i < metodosClasse.length; i++) {
						if (!metodosClasse[i].isConstructor()) {
							listaInformacoesMetodos.add(obterInformacaoDoMetodo(localFile, cu,
									nomeDaClasse, arquiteturaClasse, metodosClasse[i]));
						}
					}
					return true;
				}
			});
		}
		return listaInformacoesMetodos;
	}

	//read file content into a string
	public String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return  fileData.toString();	
	}

	public int contarNumeroDeLinhas(MethodDeclaration node) {
		String[] linhasMetodo = node.toString().split("\n"); 
		if (node.getJavadoc() == null) {
			return linhasMetodo.length;
		} else {
			String[] linhasTextoJavadoc = node.getJavadoc().toString().split("\n");
			return linhasMetodo.length - linhasTextoJavadoc.length;
		}
	}

	public int qtdCaracteresJavadoc(MethodDeclaration node) {
		if (node.getJavadoc() == null) {
			return 0;
		}
		return node.getJavadoc().getLength();
	}
	
	private ArrayList<String> obterListaClassesImplementadas(TypeDeclaration node) {
		Type superClass = node.getSuperclassType();
		ArrayList<String> listaExtensImplemteds = new ArrayList<>();
		if (superClass != null) {
			listaExtensImplemteds.add(superClass.toString());
		}
		List<?> lista = node.superInterfaceTypes();
		if (lista.size()>0) {
			listaExtensImplemteds.add(node.getName().toString());
		}
		return listaExtensImplemteds;
	}
	
	private String transformarClassesImplementadasEmString(ArrayList<String> lista) {
		String retorno = "";
		for (String string : lista) {
			retorno += string;
		}
		return retorno;
	}

	private InformacoesMetodoModel obterInformacaoDoMetodo(String localFile, final CompilationUnit cu,
			String nomeClasse, String arquiteturaClasse, MethodDeclaration metodo) {
		InformacoesMetodoModel informacoesMetodo = new InformacoesMetodoModel();
		informacoesMetodo.setDiretorioDaClasse(localFile);
		informacoesMetodo.setNomeClasse(nomeClasse);
		informacoesMetodo.setArquiteturaClasse(arquiteturaClasse);
		informacoesMetodo.setLinhaInicial(cu.getLineNumber(
				metodo.getName().getStartPosition()));
		informacoesMetodo.setNumeroLinhas(contarNumeroDeLinhas(metodo));
		informacoesMetodo.setNomeMetodo(metodo.getName().toString());
		informacoesMetodo.setCharInicial(metodo.getStartPosition());
		informacoesMetodo.setCharFinal(metodo.getLength()+
				metodo.getStartPosition());
		return informacoesMetodo;
	}
}
