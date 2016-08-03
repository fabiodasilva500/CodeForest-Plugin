package br.usp.each.saeg.code.forest.ui.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.media.j3d.Canvas3D;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.code.forest.domain.ScriptFilter;
import br.usp.each.saeg.code.forest.inspection.requested.RedirectApplication;
import br.usp.each.saeg.code.forest.metaphor.Forest;
import br.usp.each.saeg.code.forest.metaphor.integration.RangeSlider;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;


/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class FilterPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel toolsPanel = new JPanel();
    private JTabbedPane parentPane = new JTabbedPane();

    private JPanel rankingPane = new JPanel();
    private RangeSlider slider = new RangeSlider(0, 100);
    private JLabel minValue = new JLabel();
    private JLabel minLabel = new JLabel();
    private JLabel maxValue = new JLabel();
    private JLabel maxLabel = new JLabel();

    private JPanel searchPane = new JPanel();
    private JTextField searchTerm = new JTextField();
    private JButton redirectApplication = new JButton("Web Information");

    private JPanel filterPanel = new JPanel();
    private final Forest forest;
    private volatile boolean updateChanges = true;
    private IProject project;
    private Object parent;
    private final JButton btnProcessRequest = new JButton("Run");
    private final JSeparator separator = new JSeparator();
    private final JSeparator separator_1 = new JSeparator();
    private final JSeparator separator_2 = new JSeparator();
    private final JCheckBox chDefaultClasses = new JCheckBox("Default of 8 classes");
    private final JCheckBox chDefaultValue = new JCheckBox("Default of 0.8 value");
    private final JSpinner jQtdClasses = new JSpinner();
    private final JSpinner jValue = new JSpinner();
    private JRadioButton rbWebInformation = new JRadioButton("Web Information");    
    private JRadioButton rbNewInspection = new JRadioButton("New Inspection");
    private JRadioButton rbPerformAnalysis = new JRadioButton("Perform Analysis");
   

 
    public FilterPanel(final IProject project, final Forest arg, final Canvas3D canvas, final Object parent) {
        this.forest = arg;
        this.project = project;
        this.parent = parent;
        setUp();

        slider.setOrientation(RangeSlider.HORIZONTAL);
        slider.setMajorTickSpacing(10);
        slider.setValue(0);
        slider.setUpperValue(100);
        slider.setPaintTicks(true);
        
        add(separator_1, BorderLayout.CENTER);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!updateChanges) {
                    return;
                }
                forest.getRestrictions().setMinScore(slider.getValue() / 100f);
                forest.getRestrictions().setMaxScore(slider.getUpperValue() / 100f);
                updateSliderValues();
                forest.applyForestRestrictions();
                canvas.requestFocus();
                CodeForestUIPlugin.ui(project, parent, "Slider Restrictions [ " + forest.getRestrictions() + " ]");
            }
        });

        searchTerm.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent arg) {
                if (!updateChanges) {
                    return;
                }
                forest.getRestrictions().setTerm(searchTerm.getText());
                forest.applyForestRestrictions();
                CodeForestUIPlugin.ui(project, parent, "Term Restrictions [ " + forest.getRestrictions() + " ]");
            }

            @Override
            public void insertUpdate(DocumentEvent arg) {
                if (!updateChanges) {
                    return;
                }
                forest.getRestrictions().setTerm(searchTerm.getText());
                forest.applyForestRestrictions();
                CodeForestUIPlugin.ui(project, parent, "Term Restrictions [ " + forest.getRestrictions() + " ]");
            }

            @Override
            public void changedUpdate(DocumentEvent arg) {
            }
        });
        

        
        defaultOption();
        verifyRadioSelection();
        verifyCheckSelection();
        processRequest();
        
    }

    public void applyFilter(ScriptFilter scriptFilter) {
        if (scriptFilter == null) {
            return;
        }
        updateChanges = false;
        forest.getRestrictions().setMinScore(slider.getValue() / 100f);
        forest.getRestrictions().setMaxScore(slider.getUpperValue() / 100f);
        forest.getRestrictions().setTerm(scriptFilter.getSearchString());
        forest.applyForestRestrictions();

        slider.setUpperValue(Math.round(100 * scriptFilter.getMaximumScore()));
        slider.setValue(Math.round(100 * scriptFilter.getMinimumScore()));
        updateSliderValues();
        searchTerm.setText(scriptFilter.getSearchString());
        updateChanges = true;
        CodeForestUIPlugin.ui(project, parent, "Filter Restrictions [ " + forest.getRestrictions() + " ]");
    }

    public void resetFilter() {
        updateChanges = false;
        forest.getRestrictions().reset();
        forest.applyForestRestrictions();

        slider.setUpperValue(Math.round(100 * forest.getRestrictions().getMaxScore()));
        slider.setValue(Math.round(100 * forest.getRestrictions().getMinScore()));
        updateSliderValues();
        searchTerm.setText(forest.getRestrictions().getTerm());
        updateChanges = true;
        CodeForestUIPlugin.ui(project, parent, "Reset Filter Restrictions [ " + forest.getRestrictions() + " ]");
    }

    private void updateSliderValues() {
        minValue.setText(formatScore(slider.getValue()));
        maxValue.setText(formatScore(slider.getUpperValue()));
    }

    private static String formatScore(int score) {
        if (score < 100) {
            return "0." + StringUtils.leftPad(String.valueOf(score), 2, '0');
        }
        return "1.00";
    }

    private void setUp() {
        setLayout(new BorderLayout());

        rankingPane.setBorder(BorderFactory.createTitledBorder("Ranking"));
        minLabel.setText("Min");
        maxLabel.setText("Max");
        minValue.setText("0.00");
        maxValue.setText("1.00");

        GroupLayout rankingPaneLayout = new GroupLayout(rankingPane);
        rankingPane.setLayout(rankingPaneLayout);
        rankingPaneLayout.setHorizontalGroup(rankingPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(slider, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGroup(rankingPaneLayout.createSequentialGroup().addComponent(minLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(minValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(51, 51, 51).addComponent(maxLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(maxValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        rankingPaneLayout.setVerticalGroup(rankingPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rankingPaneLayout.createSequentialGroup().addGroup(rankingPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(minLabel).addComponent(maxLabel).addComponent(minValue).addComponent(maxValue)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

        searchPane.setBorder(BorderFactory.createTitledBorder("Filter by method"));

        GroupLayout searchPaneLayout = new GroupLayout(searchPane);
        searchPane.setLayout(searchPaneLayout);
        searchPaneLayout.setHorizontalGroup(searchPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(searchTerm));
        searchPaneLayout.setVerticalGroup(searchPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(searchTerm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

        GroupLayout toolsPanelLayout = new GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(toolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                toolsPanelLayout.createSequentialGroup().addGroup(toolsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(rankingPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(searchPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE)));
        toolsPanelLayout.setVerticalGroup(toolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                toolsPanelLayout.createSequentialGroup().addComponent(rankingPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(searchPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGap(0, 0, Short.MAX_VALUE)));

        parentPane.addTab("Filters", toolsPanel);
        

        GroupLayout filterPanelLayout = new GroupLayout(filterPanel);
        filterPanelLayout.setHorizontalGroup(
        	filterPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(filterPanelLayout.createSequentialGroup()
        			.addContainerGap(25, Short.MAX_VALUE)
        			.addComponent(parentPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
        		.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        		.addGroup(filterPanelLayout.createSequentialGroup()
        			.addGap(20)
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(filterPanelLayout.createSequentialGroup()
        					.addComponent(chDefaultValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGap(6))
        				.addComponent(chDefaultClasses, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(filterPanelLayout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jValue))
        				.addGroup(filterPanelLayout.createSequentialGroup()
        					.addGap(1)
        					.addComponent(jQtdClasses, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        		.addComponent(separator, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        		.addGroup(filterPanelLayout.createSequentialGroup()
        			.addGap(19)
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(rbPerformAnalysis, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(rbWebInformation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(rbNewInspection, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
        			.addGap(18)
        			.addComponent(btnProcessRequest)
        			.addContainerGap(20, Short.MAX_VALUE))
        );
        filterPanelLayout.setVerticalGroup(
        	filterPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(filterPanelLayout.createSequentialGroup()
        			.addComponent(parentPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
        			.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(9)
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jQtdClasses, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chDefaultClasses))
        			.addGap(3)
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chDefaultValue))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(separator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(filterPanelLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(filterPanelLayout.createSequentialGroup()
        					.addComponent(rbWebInformation, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rbNewInspection, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnProcessRequest))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(rbPerformAnalysis))
        );
        filterPanel.setLayout(filterPanelLayout);

        add(filterPanel, BorderLayout.LINE_END);
    }




    public void defaultOption(){
        jQtdClasses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
	     jValue.setModel(new SpinnerNumberModel(0.1, 0.1, 1, 0.1));
	     chDefaultClasses.setSelected(true);
        chDefaultValue.setSelected(true);
        jQtdClasses.setEnabled(false);
        jValue.setEnabled(false);
        btnProcessRequest.setEnabled(false);
   }
   
   public void verifyRadioSelection(){
          rbWebInformation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
	         if(rbWebInformation.isSelected()){
	        	rbNewInspection.setSelected(false);
	        	rbPerformAnalysis.setSelected(false);
	         }
	         if(!rbWebInformation.isSelected() && !rbPerformAnalysis.isSelected() && !rbNewInspection.isSelected()){
		         btnProcessRequest.setEnabled(false);	  
		         }
		         else{
		         btnProcessRequest.setEnabled(true);	 
		         }
			}
		});
       
       rbNewInspection.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
	         if(rbNewInspection.isSelected()){
		        rbWebInformation.setSelected(false);
	        	rbPerformAnalysis.setSelected(false);
	         }
	         if(!rbWebInformation.isSelected() && !rbPerformAnalysis.isSelected() && !rbNewInspection.isSelected()){
	         btnProcessRequest.setEnabled(false);	  
	         }
	         else{
	         btnProcessRequest.setEnabled(true);	 
	         }
			}
		});
 
       rbPerformAnalysis.addItemListener(new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent arg0) {
      if(rbPerformAnalysis.isSelected()){
	    rbWebInformation.setSelected(false);
   	rbNewInspection.setSelected(false);
      }
      if(!rbWebInformation.isSelected() && !rbPerformAnalysis.isSelected() && !rbNewInspection.isSelected()){
      btnProcessRequest.setEnabled(false);	  
      }
      else{
	         btnProcessRequest.setEnabled(true);	 
	         }
		}
	});

   }
   
   
   public void verifyCheckSelection(){
       chDefaultClasses.addActionListener(new ActionListener() {
			
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				if(chDefaultClasses.isSelected()){	
  					jQtdClasses.setEnabled(false);
  			        jQtdClasses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
  					}
  					else{
  				    jQtdClasses.setEnabled(true);	
  			        jQtdClasses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
   					}	
  			    if(!rbWebInformation.isSelected() && !rbPerformAnalysis.isSelected() && !rbNewInspection.isSelected()){
  			    btnProcessRequest.setEnabled(false);	  
  			    }
  			    else{
  			         btnProcessRequest.setEnabled(true);	 
  			         }
  			}
  		});
          chDefaultValue.addActionListener(new ActionListener() {
  			
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				if(chDefaultValue.isSelected()){	
  					jValue.setEnabled(false);
  			        jValue.setModel(new SpinnerNumberModel(0.1, 0.1, 1, 0.1));
  					}
  					else{
  				   jValue.setEnabled(true);	
 			       jValue.setModel(new SpinnerNumberModel(0.1, 0.1, 1, 0.1));
  					}					
  			}
  		});
          
   }
   
    
    public void processRequest(){
 	   ActionListener redirect=new ActionListener(){
        @Override
 	  public void actionPerformed(ActionEvent e) {
        try{
 	  if(rbWebInformation.isSelected()) {
       RedirectApplication rAp = new RedirectApplication();
 	   rAp.requestOperation("webInformation");
 	   }
 	   else
 	   if(rbNewInspection.isSelected()) {
 	   int numberOfCactus=0;
 	   double valueForInspection=0;
 	   if(chDefaultClasses.isSelected()) {
 	   numberOfCactus=7;
 	   }
 	   else{
 	   Object o = jQtdClasses.getValue();
        Number n = (Number) o;
        numberOfCactus = n.intValue();
 	   }
 	  if(chDefaultValue.isSelected()) {
 	   valueForInspection=0.8;
 	   }
 	   else{
       Object o = jValue.getValue();
       Number n = (Number) o;
       valueForInspection = n.doubleValue();
 	   }
 	   
 	   RedirectApplication rAp = new RedirectApplication(numberOfCactus, valueForInspection);
 	   rAp.requestOperation("newInspection");
 	   }
 	   else
 	   if(rbPerformAnalysis.isSelected()){
 	   RedirectApplication rAp = new RedirectApplication();
 	   rAp.requestOperation("performAnalysis");
 	   }		
 		}
        catch(ExecutionException e1){
        e1.printStackTrace();
        }
        
 		}
 	   };
        
    btnProcessRequest.addActionListener(redirect);
    
}
}





