package br.usp.each.saeg.code.forest.perform.analysis;

import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;

public class PerformAnalysis {
private String id;
private String heuristica;
private String nome;
private String nomeCompleto;
private int qtdPacotes;
private int qtdClasses;
private int qtdMetodos;

private static String nomeAux;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getHeuristica() {
	return heuristica;
}
public void setHeuristica(String heuristica) {
	this.heuristica = heuristica;
}


public String getNomeExibicao() {
	return nome;
}
public void setNomeExibicao(String nome) {
	this.nome = nome;
}

public String getNomeCompleto() {
	return nomeCompleto;
}

public void setNomeCompleto(String nomeCompleto) {
	this.nomeCompleto = nomeCompleto;
}


public int getQtdPacotes() {
	return qtdPacotes;
}
public void setQtdPacotes(int qtdPacotes) {
	this.qtdPacotes = qtdPacotes;
}
public int getQtdClasses() {
	return qtdClasses;
}
public void setQtdClasses(int qtdClasses) {
	this.qtdClasses = qtdClasses;
}
public int getQtdMetodos() {
	return qtdMetodos;
}
public void setQtdMetodos(int qtdMetodos) {
	this.qtdMetodos = qtdMetodos;
}

public final String ola(){
return nome;
}


}
