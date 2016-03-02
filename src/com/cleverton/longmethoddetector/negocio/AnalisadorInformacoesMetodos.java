package com.cleverton.longmethoddetector.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.preference.IPreferenceStore;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;
import com.cleverton.longmethoddetector.preferences.PreferenceConstants;

/**
 * Obtém informações de métodos de classes: Local do arquivo, 
 * linha inicial do método e Número de linhas do método 
 * @author Kekeu
 *
 */
public class AnalisadorInformacoesMetodos {

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
		for (String file : files) {
			String textoDaClasse = readFileToString(file);
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(textoDaClasse.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);

			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				public boolean visit(MethodDeclaration node) {
					InformacoesMetodoModel informacoesMetodo = new InformacoesMetodoModel();
					informacoesMetodo.setDiretorioDaClasse(file);
					informacoesMetodo.setLinhaInicial(cu.getLineNumber(node.getName().getStartPosition()));
					informacoesMetodo.setNumeroLinhas(contarNumeroDeLinhas(node));
					informacoesMetodo.setNomeMetodo(node.getName().toString());
					listaInformacoesMetodos.add(informacoesMetodo);
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

	public int contarNumeroDeLinhas(MethodDeclaration metodoDeclaration) {
		String[] linhasMetodo = metodoDeclaration.toString().split("\n"); 
		if (metodoDeclaration.getJavadoc() == null) {
			return linhasMetodo.length;
		} else {
			String[] linhasTextoJavadoc = metodoDeclaration.getJavadoc().toString().split("\n");
			return linhasMetodo.length - linhasTextoJavadoc.length;
		}
	}

}
