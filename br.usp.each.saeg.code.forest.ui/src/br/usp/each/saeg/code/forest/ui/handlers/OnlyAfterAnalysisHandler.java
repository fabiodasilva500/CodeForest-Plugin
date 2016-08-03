package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.commands.*;
import org.eclipse.core.resources.*;
import br.usp.each.saeg.code.forest.ui.project.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public abstract class OnlyAfterAnalysisHandler extends AbstractHandler {

    @Override
    public boolean isEnabled() {
        IProject project = ProjectUtils.getCurrentSelectedProject();
        String classeAtual="";
        if (project == null || !project.isOpen()) {
            return false;
        }
        
        System.out.println("Classe corrente:"+this.getClass());
        classeAtual = String.valueOf(this.getClass());
        
        ProjectState state = ProjectPersistence.getStateOf(project);
        
        if(classeAtual.contains("br.usp.each.saeg.code.forest.ui.handlers.ViewAsCodeForestAnalysisHandler")){
        System.out.println("Análise de perfomance");
        return true;	
        }
        else{
        if (state == null) {
        System.out.println("Não analisado");
            return false;
        }
        if (state.isAnalyzed()) {
        System.out.println("Já analisado");
        	return true;
        }
        }
        
        
        
        return false;
    }
}
