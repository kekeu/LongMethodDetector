package br.ufs.smelldetector.negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import br.ufs.smelldetector.model.DadosClasse;
import br.ufs.smelldetector.model.DadosMetodo;

/**
 * 
 * @author Kekeu
 *
 */
public class AnalisadorClasseMetodo {

	public ArrayList<DadosClasse> getInfoMetodosDosArquivos(
			ArrayList<String> files, boolean adicionarArquitetura) throws IOException {
		ArrayList<DadosClasse> dadosClasses = new ArrayList<>();
		for (String localFile : files) {
			String textoDaClasse = readFileToString(localFile);
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(textoDaClasse.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				@Override
				public boolean visit(TypeDeclaration node) {
					DadosClasse classe = new DadosClasse();
					ArrayList<String> listaClassesEI = null;
					if (adicionarArquitetura) {
						listaClassesEI = obterListaClassesEI(node);	
					}
					String nomeDaClasse = node.getName().toString();
					MethodDeclaration[] methodDeclaration = node.getMethods();
					ArrayList<DadosMetodo> metodosDaClasse = new ArrayList<>();
					for (int i = 0; i < methodDeclaration.length; i++) {
						if (!methodDeclaration[i].isConstructor()) {
							metodosDaClasse.add(obterInfoMetodo(cu, methodDeclaration[i]));
						}
					}
					classe.setClassesExtendsImplements(listaClassesEI);
					classe.setDiretorioDaClasse(localFile);
					classe.setMetodos(metodosDaClasse);
					classe.setNomeClasse(nomeDaClasse);
					dadosClasses.add(classe);
					//System.out.println(nomeDaClasse +"  --  "+metodosDaClasse.size());
					//System.out.println();
					return true;
				}
			});
		}
		return dadosClasses;
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

	private ArrayList<String> obterListaClassesEI(TypeDeclaration node) {
		Type superClass = node.getSuperclassType();
		ArrayList<String> listaEI = new ArrayList<>();
		if (superClass != null) {
			listaEI.add(superClass.toString());
		} else {
			listaEI.add(null);
		}
		List<?> lista = node.superInterfaceTypes();
		for (Object object : lista) {
			//System.out.println(object.toString());
			listaEI.add(object.toString());
		}
		return listaEI;
	}

	private DadosMetodo obterInfoMetodo(final CompilationUnit cu, MethodDeclaration metodo) {
		DadosMetodo informacoesMetodo = new DadosMetodo();
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
