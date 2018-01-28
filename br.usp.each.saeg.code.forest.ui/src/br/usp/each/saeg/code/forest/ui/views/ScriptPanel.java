package br.usp.each.saeg.code.forest.ui.views;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.code.forest.domain.BranchData;
import br.usp.each.saeg.code.forest.domain.ScriptData;
import br.usp.each.saeg.code.forest.domain.ScriptEligibility;
import br.usp.each.saeg.code.forest.domain.ScriptFilter;
import br.usp.each.saeg.code.forest.domain.TreeData;
import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.core.Configuration;
import br.usp.each.saeg.code.forest.ui.editor.OpenEditor;
import br.usp.each.saeg.code.forest.ui.project.ProjectState;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class ScriptPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JScrollPane scriptScroll;
    private JTable scriptTable;
    private ProjectState state;
    private FilterPanel filterPanel;
    private IProject project;
    private Object parent;

    public ScriptPanel(IProject project, Object parent, ProjectState projectState, FilterPanel panel) {
        this.project = project;
        this.parent = parent;
        this.state = projectState;
        this.filterPanel = panel;

        if (!state.containsAnalysis()) {
            return;
        }

        Map<Integer, ScriptData> scriptElements = new TreeMap<Integer, ScriptData>();
        for (TreeData tree : state.getAllAnalysis()) {
            for (BranchData branch : tree.getBranchData()) {
                if (!ScriptEligibility.isEligible(branch)) {
                    continue;
                }
                ScriptData element = new ScriptData();
                element.setClassName(tree.getName());
                element.setMethodName(branch.getName());
                element.setScriptPosition(branch.getScriptPosition());
                element.setMarker(branch.getOpenMarker());
                element.setScore(branch.getScriptScore());
                if (!scriptElements.containsKey(element.getScriptPosition()) || scriptElements.get(element.getScriptPosition()).getScore() < element.getScore()) {
                    scriptElements.put(element.getScriptPosition(), element);
                }
            }
        }
        Object[][] data = new Object[scriptElements.size()][4];
        int i = 0;
        
        if(Configuration.VIEW_TABLE_VALUES) {
        for (ScriptData scriptData : scriptElements.values()) {
            data[i][0] = scriptData.getClassName();
            data[i][1] = scriptData.getMethodName();
            data[i][2] = String.format("%.6f", scriptData.getScore());
            data[i][3] = scriptData;
            i++;
        }
         ScriptTableModel model = new ScriptTableModel(data, new String[] { "Class", "Method", "Score" });
          setUp(model);
        }
    }

    private void setUp(final ScriptTableModel model) {
        scriptScroll = new JScrollPane();
        scriptTable = new JTable() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    ScriptData data = (ScriptData) getModel().getValueAt(row, 3);
                    int newScore = (int) (data.getScore() * 100);
                    if (newScore <= 25) {
                        c.setBackground(new Color(192, 255, 192));
                    } else if (newScore <= 50) {
                        c.setBackground(new Color(255, 255, 128));
                    } else if (newScore <= 75) {
                        c.setBackground(new Color(255, 204, 153));
                    } else {
                        c.setBackground(new Color(255, 160, 160));
                    }
                }
                return c;
            }
        };

        scriptTable.setModel(model);
        scriptTable.setSelectionModel(new SingleRowSelectionModel());
        scriptTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                ScriptData data = (ScriptData) model.getValueAt(scriptTable.getSelectedRow(), 3);
                OpenEditor.at(data.getMarker());
                filterPanel.applyFilter(new ScriptFilter(data));
                CodeForestUIPlugin.ui(project, parent, "[Script Filter] @ [xml " + data.getScriptPosition() + ", real " + scriptTable.getSelectedRow() + "] " + data.getClassName() + "." + data.getMethodName());
            }
        });

        scriptScroll.setViewportView(scriptTable);

        GroupLayout scriptPanelLayout = new GroupLayout(this);
        this.setLayout(scriptPanelLayout);
        scriptPanelLayout.setHorizontalGroup(
                scriptPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(scriptPanelLayout.createSequentialGroup()
                        .addComponent(scriptScroll))
                );
        scriptPanelLayout.setVerticalGroup(
                scriptPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, scriptPanelLayout.createSequentialGroup()
                        .addComponent(scriptScroll, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                        )
                );

    }
}
