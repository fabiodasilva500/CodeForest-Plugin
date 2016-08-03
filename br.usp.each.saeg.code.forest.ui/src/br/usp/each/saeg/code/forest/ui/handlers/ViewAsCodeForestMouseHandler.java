package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;

import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;
import br.usp.each.saeg.code.forest.ui.views.CodeForestMouseView;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class ViewAsCodeForestMouseHandler extends OnlyAfterAnalysisHandler {

    @Override
    public Object execute(ExecutionEvent arg) throws ExecutionException {

        IProject project = ProjectUtils.getCurrentSelectedProject();
        try {
            CodeForestUIPlugin.getActiveWorkbenchWindow().getActivePage().showView(CodeForestMouseView.VIEW_ID, project.getName(), IWorkbenchPage.VIEW_VISIBLE);

        } catch (Exception e) {
            CodeForestUIPlugin.log(e);
        }
        CodeForestUIPlugin.ui(project, this, "code forest mouse");
        return null;
    }

}
