package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.code.forest.inspection.requested.StatusProject;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.listeners.CloseAllViewsListener;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;
import br.usp.each.saeg.code.forest.ui.views.CodeForestAnalysisView;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
//Classe invocada no momento da solicitação pelo plugin da opção Perform Analysis
public class ViewAsCodeForestAnalysisHandler extends OnlyAfterAnalysisHandler {

	IProject project;
	
	
    public ViewAsCodeForestAnalysisHandler() {
		super();
	}

	public ViewAsCodeForestAnalysisHandler(IProject project) {
		super();
		this.project = project;
	}

	@Override
    public Object execute(ExecutionEvent arg) throws ExecutionException {
	System.out.println("Argumento teste:"+arg);
		if (project == null){
			project = ProjectUtils.getCurrentSelectedProject();
		}
		
        try {
        	StatusProject st = new StatusProject();
        	st.setRequisicaoAnalise("firstAnalysis");
        	
            closeViews();
    		
        	PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().showView(CodeForestAnalysisView.VIEW_ID, project.getName(), IWorkbenchPage.VIEW_VISIBLE);
        	System.out.println("Habilitando a tela");

        } catch (Exception e) {
        	e.printStackTrace();
//            CodeForestUIPlugin.log(e);
        }
		CodeForestUIPlugin.ui(project, this, "run analysis");
		
		//Enviando o argumento atual para a geração posterior da análise de perfomance
        CodeForestUIPlugin.setActualExecutionEvent(arg);
        	
        return null;
    }
	
	
	//Encerrando todas as Views para a realização de uma nova Análise de Performance 
	private void closeViews(){
	CloseAllViewsListener close = new CloseAllViewsListener();
  	close.removeAnalysisCloseViews();
	}
}
