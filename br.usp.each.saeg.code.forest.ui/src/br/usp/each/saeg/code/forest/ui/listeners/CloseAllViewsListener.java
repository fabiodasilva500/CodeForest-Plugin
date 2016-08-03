package br.usp.each.saeg.code.forest.ui.listeners;

import org.eclipse.ui.*;

import br.usp.each.saeg.code.forest.ui.*;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */

//Classe que finaliza todas as Views do menu da CodeForest
public class CloseAllViewsListener implements IWorkbenchListener {

    public void register() {
        PlatformUI.getWorkbench().addWorkbenchListener(this);
    }

    @Override
    public boolean preShutdown(IWorkbench workbench, boolean forced) {
        closeAllViews();
        return true;
    }
    

    @Override
    public void postShutdown(IWorkbench workbench) {
    }
    
    
    
    public void closePerformAnalysis(){
   IWorkbenchPage page = CodeForestUIPlugin.getActiveWorkbenchWindow().getActivePage();
    if (page != null) {
    IViewReference[] viewReferences = page.getViewReferences();
    for (IViewReference ivr : viewReferences) {
    if (ivr.getId().startsWith("br.usp.each.saeg.code.forest.menu.view.perform.analysis")){
    page.hideView(ivr);
    System.out.println("Finalizando a análise de perfomance");
    }
    else{
    System.out.println(ivr.getId());
    }
    }
    }
    }
    
    
    
    public void closeForest(){
   IWorkbenchPage page = CodeForestUIPlugin.getActiveWorkbenchWindow().getActivePage();
    if (page != null) {
    IViewReference[] viewReferences = page.getViewReferences();
    for (IViewReference ivr : viewReferences) {
    if (ivr.getId().startsWith("br.usp.each.saeg.code.forest.menu.view.code.forest.keyboard")){
    page.hideView(ivr);
    System.out.println("Finalizando a floresta");
    }
    else{
    System.out.println(ivr.getId());
    }
    }
    }
    }
    
    
  
    
    public void removeAnalysisCloseViews(){
     closeAllViews();
    }


    private static void closeAllViews() {
        IWorkbenchPage page = CodeForestUIPlugin.getActiveWorkbenchWindow().getActivePage();
        if (page != null) {
            IViewReference[] viewReferences = page.getViewReferences();
            for (IViewReference ivr : viewReferences) {
                if (ivr.getId().startsWith("br.usp.each.saeg.code.forest")) {
                    page.hideView(ivr);
                }
            }
        }
    }
}
