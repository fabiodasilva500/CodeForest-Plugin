package br.usp.each.saeg.code.forest.inspection.requested;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.handlers.OnlyAfterAnalysisHandler;
import br.usp.each.saeg.code.forest.ui.listeners.CloseAllViewsListener;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;
import br.usp.each.saeg.code.forest.ui.views.CodeForestAnalysisView;
import br.usp.each.saeg.code.forest.ui.views.CodeForestKeyboardView;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
//Classe invocada no momento da solicitação pelo plugin da opção Perform Analysis
public class RunNewInspectionAnalysis extends OnlyAfterAnalysisHandler {

	IProject project;
	
	
    public RunNewInspectionAnalysis() throws ExecutionException {
    	super();
      	StatusProject st = new StatusProject();		
		st.setRequisicaoAnalise("secondAnalysis");
		project = st.getProject();
		
		//Enviando para o método execute o argumento armazenado na ViewAsCodeForestAnalysisHandler
		execute(CodeForestUIPlugin.getActualEvent());
	}

	@Override
    public Object execute(ExecutionEvent arg) throws ExecutionException {
	System.out.println("Argumento teste:"+arg);
		if (project == null){
			project = ProjectUtils.getCurrentSelectedProject();
		}
		
        try {
               	Display.getDefault().asyncExec(new Runnable() {
        	    public void run() {
        	    	try {
        	    		closeViews();
        	        	PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().showView(CodeForestAnalysisView.VIEW_ID, project.getName(), IWorkbenchPage.VIEW_VISIBLE);
    				} catch (PartInitException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
          	         }

    			private void closeViews() {
    				CloseAllViewsListener c=new CloseAllViewsListener();
    	    		c.closeForest();				
    			}
        	});
        	
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
	
	
	private void closeViews(){
//	CloseAllViewsListener close = new CloseAllViewsListener();
// 	close.removeAnalysisCloseViews();
	}
}
