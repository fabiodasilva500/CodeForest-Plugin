package br.usp.each.saeg.code.forest.ui.views;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import swingintegration.example.EmbeddedSwingComposite;
import br.usp.each.saeg.code.forest.inspection.requested.StatusProject;
import br.usp.each.saeg.code.forest.perform.analysis.PerformAnalysis;
import br.usp.each.saeg.code.forest.send.data.IDValidator;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.handlers.RunAnalysisHandler;
import br.usp.each.saeg.code.forest.ui.project.ProjectPersistence;
import br.usp.each.saeg.code.forest.ui.project.ProjectState;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;
import br.usp.each.saeg.code.forest.xml.ReadXmlPerformAnalysis;


//Classe que exibe a View da Análise de Perfomance
public class CodeForestAnalysisView extends ViewPart implements ItemListener {
    public static final String VIEW_ID = "br.usp.each.saeg.code.forest.menu.view.perform.analysis";
    
	   private static final String POPUP_ID_MESSAGE = "";
       	private JPanel principal = new JPanel();
 		private JPanel panTexto = new JPanel();
 		private JPanel panBotoes = new JPanel();
 		private JCheckBox ch[];
 		private List<PerformAnalysis> pa;
 			
  	    private EmbeddedSwingComposite composite;
 	    private IProject project;
 	    private ProjectState state;
 	    private int anterior=-1;
 	    private String requisition=""; 

	
        public CodeForestAnalysisView() {
                super();
                System.out.println("Construindo a tela");
            }
        
     public void setFocus() {
                principal.requestFocus();
        }
     
     @Override
     public void createPartControl(Composite parent) {
    	   try {
               System.setProperty("sun.awt.noerasebackground", "true");
           } catch (NoSuchMethodError error) {
           }
       
           //Buscando informações do estado atual do projeto e do tipo de requisição que está sendo realizada para a análise de performance
           StatusProject st = new StatusProject();
           requisition = st.getRequisicaoAnalise();
           project = ProjectUtils.getCurrentSelectedProject();
          
           if(project==null && requisition.equalsIgnoreCase("firstAnalysis")){
               return;
               }
           
           if (project == null && requisition.equalsIgnoreCase("secondAnalysis")) {
           project = st.getProject();
           }
           
           state = ProjectPersistence.getStateOf(project);
           
           //A condição foi adequada para permitir a geração da análise de perfomance
           //pois o método state.containsAnalysis ainda não foi executado
           if (state == null) {
               return;
           }

           if (composite != null) {
               composite.setVisible(false);
               composite.dispose();
               composite = null;
           }
     
      InputDialog inputDialog = new InputDialog(Display.getCurrent().getActiveShell(),"ID",POPUP_ID_MESSAGE,"", new IDValidator());
	  inputDialog.open();
	  System.out.println("ID number is "+inputDialog.getValue());
	  CodeForestUIPlugin.ui(project, this, "ID number is "+inputDialog.getValue());

     pa=new ArrayList<PerformAnalysis>();
     ReadXmlPerformAnalysis le = new ReadXmlPerformAnalysis();
     pa = le.verificaArquivosGerados();
     
     System.out.println("Tamanho da lista de arquivos:"+pa.size());

         composite = new EmbeddedSwingComposite(parent, SWT.EMBEDDED) {
             @Override
             protected JComponent createSwingComponent() {
                 return createContent();
             }
         };
         composite.populate();
     }
     
     
     
     
     private JPanel createContent () {
		principal.setLayout(new BorderLayout());
		panTexto.setLayout(new GridLayout(pa.size(), pa.size()));
				
		ch=new JCheckBox[pa.size()];
		  
		System.out.println("Criando");
		for (int i=0;i<pa.size();i++){
		ch[i] = new JCheckBox(pa.get(i).getId()+" "+pa.get(i).getNomeExibicao());
		ch[i].addItemListener(this);
		panTexto.add(ch[i]);
		}
		

		  principal.add(panBotoes, BorderLayout.SOUTH);
		  principal.add(panTexto, BorderLayout.NORTH);
		 
		  return principal;
}

	
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		for (int i=0;i<ch.length;i++){
		if(ch[i].isSelected()){
		System.out.println("Selecionada:"+pa.get(i).getId()+" "+pa.get(i).getNomeExibicao());
        
		//Trecho removido pois não será possibilitada uma nova seleção na CheckBox
		/*if(anterior>=0){
        ch[anterior].setSelected(false);
        }
		anterior = i;
       */
		
		try {
			RunAnalysisHandler r = new RunAnalysisHandler(project, pa.get(i).getNomeCompleto());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		ch[i].setEnabled(false);
		}
	 }
	

   }
			

