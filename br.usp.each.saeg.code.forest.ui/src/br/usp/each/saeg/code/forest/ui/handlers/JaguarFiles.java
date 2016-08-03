package br.usp.each.saeg.code.forest.ui.handlers;


import java.io.File;
import java.util.Date;
import java.util.List;

public class JaguarFiles {

	
public File[] readFiles(){
	File directory = new File("./.jaguar"); 
	File fList[] = directory.listFiles(); 

	System.out.println("Numero de arquivos no diretorio : " + fList.length ); 

	for ( int i = 0; i < fList.length; i++ ){ 
	System.out.println(fList[i].getName());

}
	
	 return fList;
}

public JaguarFiles(){
readFiles();
}


public static void main(String[]args){
new JaguarFiles();
}
}
