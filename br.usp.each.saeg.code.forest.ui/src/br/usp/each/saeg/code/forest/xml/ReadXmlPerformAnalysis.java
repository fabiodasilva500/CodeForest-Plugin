package br.usp.each.saeg.code.forest.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import sun.org.mozilla.javascript.internal.regexp.SubString;

import br.usp.each.saeg.code.forest.inspection.requested.StatusProject;
import br.usp.each.saeg.code.forest.perform.analysis.PerformAnalysis;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.handlers.ViewAsCodeForestKeyboardHandler;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;

public class ReadXmlPerformAnalysis {
private IProject project;


public ReadXmlPerformAnalysis(){
StatusProject st = new StatusProject();
if(st.getRequisicaoAnalise().equalsIgnoreCase("secondAnalysis")){
project=st.getProject();
}
listaArquivos();	
}

public XmlInput listaArquivos(){	
	Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);
    boolean encontrado = false;
    String id = "";
    String nomeArquivo;
    int qtdPacotes;
    int qtdClasses;
    int qtdMetodos;
    
    List<PerformAnalysis> listaArquivos = new ArrayList<PerformAnalysis>();
    
    
	System.out.println("Tamanho:"+xmlFiles.size());
	XmlInput input = new XmlInput();
	XmlPackage packages = new XmlPackage();
	XmlClass classes = new XmlClass();
	
	for (String arquivo:xmlFiles.keySet())
	{
		if (arquivo.indexOf ("coverage") >= 0 &&  arquivo.indexOf("flat")==0 && !encontrado) {
		PerformAnalysis pa = new PerformAnalysis();
		
		String novo = arquivo;
		arquivo="\\jaguar\\"+arquivo;		
		id = novo.substring(novo.length()-17, novo.length()-4);
		nomeArquivo = novo.substring(21, arquivo.lastIndexOf("Heuristic_"));

		input = readXML(project.getFile(arquivo));

		List<XmlPackage> listaPacotes = input.getPackages();
		qtdPacotes = listaPacotes.size();
		
		List<XmlClass> listaClasses = packages.getClasses();
		qtdClasses = listaClasses.size();
		
		List<XmlMethod> listaMetodos = classes.getMethods();
		qtdMetodos = listaMetodos.size();
		
		pa.setId(id);
		pa.setNomeCompleto(arquivo);
		pa.setQtdPacotes(qtdPacotes);
		pa.setQtdClasses(qtdClasses);
		pa.setQtdMetodos(qtdMetodos);
		listaArquivos.add(pa);

		
		System.out.println("ID: "+pa.getId()+" Caminho atual: "+pa.getNomeCompleto()+" Pacotes:"+pa.getQtdPacotes()+" Classes:"+pa.getQtdClasses()+" Métodos:"+pa.getQtdMetodos());
		encontrado =true;
		}
		else{
		System.out.println("String não encontrada "+arquivo);
		}
		}
	
	String heuristica = input.getHeuristica();
	System.out.println("Heurística Atual:"+heuristica);
	return input;
	}



public List<PerformAnalysis> verificaArquivosGerados(){
	
	IProject project = null;
	StatusProject st = new StatusProject();
	if(st.getRequisicaoAnalise().equalsIgnoreCase("secondAnalysis")){
	project= st.getProject();	
	}
	else{
	project = ProjectUtils.getCurrentSelectedProject();
	}
	
	Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);
    boolean encontrado = false;
    String id = "";
    String nomeArquivo; 
    int qtdPacotes;
    int qtdClasses;
    int qtdMetodos;
    
    List<PerformAnalysis> listaArquivos = new ArrayList<PerformAnalysis>();
    
    
	System.out.println("Tamanho:"+xmlFiles.size());
	XmlInput input = new XmlInput();
	XmlPackage packages = new XmlPackage();
	XmlClass classes = new XmlClass();
	
	for (String arquivo:xmlFiles.keySet())
	{
		
		if (arquivo.indexOf ("coverage") >= 0  &&  !arquivo.contains("flat")) {
		PerformAnalysis pa = new PerformAnalysis();
		
		String novo = arquivo;
		arquivo="\\jaguar\\"+arquivo;		
		id = novo.substring(novo.length()-17, novo.length()-4);
		id = id+"   -   ";
		
		
		if(novo.indexOf("dataflow")>=0){
		nomeArquivo = novo.substring(18, arquivo.lastIndexOf("Heuristic_"));
		nomeArquivo = nomeArquivo.replace("Heuristi", "  Heuristic");
		}
		else{
		nomeArquivo = novo.substring(21, arquivo.lastIndexOf("Heuristic_"));
		nomeArquivo = nomeArquivo.replace("Heuristi", "  Heuristic");
		}
		
		List<XmlPackage> listaPacotes = input.getPackages();
		qtdPacotes = listaPacotes.size();
		
		List<XmlClass> listaClasses = packages.getClasses();
		qtdClasses = listaClasses.size();
		
		List<XmlMethod> listaMetodos = classes.getMethods();
		qtdMetodos = listaMetodos.size();
		
		pa.setId(id);
		pa.setNomeExibicao(nomeArquivo);
		pa.setNomeCompleto(arquivo);
		pa.setQtdPacotes(qtdPacotes);
		pa.setQtdClasses(qtdClasses);
		pa.setQtdMetodos(qtdMetodos);
		listaArquivos.add(pa);

		
		System.out.println("ID: "+pa.getId()+" Caminho atual: "+pa.getNomeCompleto()+" Pacotes:"+pa.getQtdPacotes()+" Classes:"+pa.getQtdClasses()+" Métodos:"+pa.getQtdMetodos());
		encontrado =true;
		}
		else{
		System.out.println("String não encontrada "+arquivo);
		}
		}
	
	String heuristica = input.getHeuristica();
	System.out.println("Heurística Atual:"+heuristica);
	return listaArquivos;
	}



//Realiza a leitura do arquivo XML com base na classe XMLInput
private XmlInput readXML(IResource resource) {
   System.out.println("Resource:"+resource);
	
	try {
		return XmlInput.unmarshal(resource.getLocation().toFile());
	} catch (Exception e) {
		CodeForestUIPlugin.log(e);
		return new XmlInput();
	}
}



}


