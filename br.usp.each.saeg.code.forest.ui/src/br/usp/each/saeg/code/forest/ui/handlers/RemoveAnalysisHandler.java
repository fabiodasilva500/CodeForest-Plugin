package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.code.forest.send.data.ScpSend;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.core.PluginCleanup;
import br.usp.each.saeg.code.forest.ui.listeners.CloseAllViewsListener;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
//Classe que solicita a limpeza do plugin
public class RemoveAnalysisHandler extends OnlyAfterAnalysisHandler {

    @Override
    public Object execute(ExecutionEvent arg) throws ExecutionException {
        IProject project = ProjectUtils.getCurrentSelectedProject();
        PluginCleanup.clean(project);
        CloseAllViewsListener close = new CloseAllViewsListener();
        close.closePerformAnalysis();
        
        CodeForestUIPlugin.ui(project, this, "removing views");
        ScpSend scp = new ScpSend();
        scp.sendFile();
        return null;
    }
}
