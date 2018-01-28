package br.usp.each.saeg.code.forest.ui.project;

import java.text.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.eclipse.ui.texteditor.*;
import br.usp.each.saeg.code.forest.domain.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */

//Classe que retorna sempre o status atual do projeto
public class ProjectState {

	//Classe que contém todas as informações do status da inspeção
    private final Map<String, Collection<TreeData>> analysisResult = new HashMap<String, Collection<TreeData>>();
    private final Set<String> marked = new HashSet<String>();
    private final Map<String, Collection<SimpleMarkerAnnotation>> fileAnnotations = new HashMap<String, Collection<SimpleMarkerAnnotation>>();
    private boolean analyzed = false;
    private ScriptFilter filter;
    //private final String folderName = "/tmp/" + "##project##_" + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + "/";
    private final String folderName = System.getProperty("user.home") + System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator");// + "##project##";
    private final String logFileName = folderName + "code_forest_commands.log";
    private int count = 1;
    private final String screenFileName = folderName + "screenshot" + "_" + "##number##" + ".jpg";

    public Map<String, Collection<TreeData>> getAnalysisResult() {
        return analysisResult;
    }

    public Set<String> getMarked() {
        return marked;
    }

    public Map<String, Collection<SimpleMarkerAnnotation>> getFileAnnotations() {
        return fileAnnotations;
    }

    public List<TreeData> getAllAnalysis() {
        Collection<Collection<TreeData>> all = analysisResult.values();
        List<TreeData> result = new ArrayList<TreeData>();
        for (Collection<TreeData> subColl : all) {
            for (TreeData data : subColl) {
                result.add(data);
            }
        }
        return result;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }
    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }
    
    //Método que verifica se a análise já foi realizada
    public boolean containsAnalysis() {
        return analysisResult.size() > 0;
    }

    public synchronized void clear() {
        analysisResult.clear();
        marked.clear();
        fileAnnotations.clear();
        analyzed = false;
    }

    public void setScriptFilter(ScriptData data) {
        filter = new ScriptFilter(data);
    }

    public boolean containsScriptFilter() {
        return filter != null;
    }

    public void clearScriptFilter() {
        filter = null;
    }

    public ScriptFilter getScriptFilter() {
        return filter;
    }

    public String formatLogFileName(String projectName) {
        return logFileName.replace("##project##", projectName);
    }

    public String formatScreenFileName(String projectName) {
        return screenFileName.replace("##project##", projectName).replace("##number##", StringUtils.leftPad(String.valueOf(count++), 8, '0'));
    }

    public String formatFolderFileName(String projectName) {
        return folderName.replace("##project##", projectName);
    }
}
