package br.usp.each.saeg.code.forest.inspection.requested;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;

public class StatusProject {
 private static IProject project;
 private static ExecutionEvent arg;
 private static String nomeArquivo;
 private static int qtdCactus;
 private static double valorDeSuspeicao;
 private static String requisicaoInspecao;
 private static String requisicaoAnalise;
 
 
 public StatusProject(IProject actualProject, String nome){
 project = actualProject;
 nomeArquivo = nome; 
 System.out.println("Status armazenado:"+nomeArquivo);
 }
  
 
 public StatusProject(int numeroDeCactus, double valorMinimo) {
 qtdCactus = numeroDeCactus; 
 valorDeSuspeicao=valorMinimo;
 }
 
 
 
 public static void setRequisicaoInspecao(String operacao){
requisicaoInspecao = operacao;
 }
 
 public static void setRequisicaoAnalise(String operacao){
 requisicaoAnalise = operacao; 
 }
 
public StatusProject() {
	// TODO Auto-generated constructor stub
}


public IProject getProject(){
return project;
 }
 
 public String getNomeArquivo(){
 return nomeArquivo;
 }
 
 
 public static String getRequisicaoInspecao(){
 return requisicaoInspecao;
 }
 
 public static String getRequisicaoAnalise(){
	return requisicaoAnalise;
 }
 

 public static int getNumeroDeCactus(){
 return qtdCactus;
 }
 
 public static double getValorDeSuspeicao(){
 return valorDeSuspeicao;
 }
 
 
 
}
