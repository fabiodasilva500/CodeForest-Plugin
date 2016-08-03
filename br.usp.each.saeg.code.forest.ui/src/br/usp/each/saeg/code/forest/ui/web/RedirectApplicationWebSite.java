package br.usp.each.saeg.code.forest.ui.web;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class RedirectApplicationWebSite{

//Classe que redireciona a CodeForest para o site da ferramenta
public RedirectApplicationWebSite(){	
	
	Desktop desktop = null;   
		//Primeiro verificamos se é possível a integração com o desktop   
		if (!Desktop.isDesktopSupported())   
		    throw new IllegalStateException("Desktop resources not supported!");   
		desktop = Desktop.getDesktop();   
		//Agora vemos se é possível disparar o browser default.   
		if (!desktop.isSupported(Desktop.Action.BROWSE))   
		    throw new IllegalStateException("No default browser set!");   
		//Pega a URI de um componente de texto.   
		URI uri = null;
		try {
			uri = new URI("http://localhost:8080/CodeForest/page/index.jsf");
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}   
		//Dispara o browser default, que pode ser o Explorer, Firefox ou outro.   
		try {
			desktop.browse(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


  public static void main(String args[]){
	new RedirectApplicationWebSite();
  }
}
