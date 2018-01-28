package br.usp.each.saeg.code.forest.inspection.requested;


import org.eclipse.core.commands.ExecutionException;
import br.usp.each.saeg.code.forest.ui.web.RedirectApplicationWebSite;


public class RedirectApplication{
private int numeroDeCactus;
private double valorDeSuspeicao;

public RedirectApplication(){
numeroDeCactus=0;
valorDeSuspeicao=0;
}

public RedirectApplication(int quantCactus, double valorParaInspecao){
System.out.println("Número de cactus:"+quantCactus);
System.out.println("Valor para inspeção:"+valorParaInspecao);

numeroDeCactus=quantCactus;
valorDeSuspeicao=valorParaInspecao;
}

public void requestOperation(String operacao) throws ExecutionException {
System.out.println("Cactus para envio:"+numeroDeCactus);
System.out.println("Suspeição para envio:"+valorDeSuspeicao);
if(operacao.equalsIgnoreCase("webInformation")){
RedirectApplicationWebSite redirect = new RedirectApplicationWebSite();
}
else
if(operacao.equalsIgnoreCase("newInspection")){
	try {
		RunNewInspectionForest ra = new RunNewInspectionForest(numeroDeCactus,valorDeSuspeicao);
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 

}
else
if(operacao.equalsIgnoreCase("performAnalysis")){
RunNewInspectionAnalysis rna = new RunNewInspectionAnalysis();
}
}

}

