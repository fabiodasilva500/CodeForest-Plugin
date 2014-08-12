package br.usp.each.saeg.code.forest.ui.handlers;

import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import br.usp.each.saeg.code.forest.domain.TreeDataBuilder;
import br.usp.each.saeg.code.forest.domain.TreeDataBuilderResult;
import br.usp.each.saeg.code.forest.source.parser.ParsingResult;
import br.usp.each.saeg.code.forest.source.parser.SourceCodeParser;
import br.usp.each.saeg.code.forest.source.parser.SourceCodeUtils;
import br.usp.each.saeg.code.forest.ui.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.markers.CodeMarkerFactory;
import br.usp.each.saeg.code.forest.ui.project.ProjectPersistence;
import br.usp.each.saeg.code.forest.ui.project.ProjectState;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;
import br.usp.each.saeg.code.forest.xml.XmlInput;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class RunAnalysisHandler extends AbstractHandler {

	private IProject project;
	
	public RunAnalysisHandler() {
		super();
	}

	public RunAnalysisHandler(IProject project) {
		super();
		this.project = project;
	}

	@Override
	public Object execute(ExecutionEvent arg) throws ExecutionException {
		if (project == null){
				project = ProjectUtils.getCurrentSelectedProject();
		}
		
		if (!project.isOpen()) {
			return null;
		}
		
		try {
			project.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		XmlInput xmlInput = readXML(project.getFile("codeforest.xml"));

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return null;
		}
		Map<IResource, List<Map<String, Object>>> resourceMarkerProps = new IdentityHashMap<IResource, List<Map<String, Object>>>();

		for (List<IResource> files : ProjectUtils.javaFilesOf(project).values()) {
			for (IResource file : files) {
				ParsingResult result = parse(file, xmlInput);
				TreeDataBuilderResult buildResult = TreeDataBuilder.from(result, SourceCodeUtils.read(file));
				resourceMarkerProps.put(buildResult.getResource(), buildResult.getMarkerProperties());
				state.getAnalysisResult().put(result.getURI(), buildResult.getTreeData());
			}
		}

		CodeMarkerFactory.scheduleMarkerCreation(resourceMarkerProps);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(arg);
		IWorkbenchPage page = window.getActivePage();
		for (IEditorReference editorRef : page.getEditorReferences()) {
			CodeForestUIPlugin.getEditorTracker().annotateEditor(editorRef);
		}
		state.setAnalyzed(true);
		CodeForestUIPlugin.ui(project, this, "run analysis");

		return null;
	}

	private XmlInput readXML(IResource resource) {
		try {
			return XmlInput.unmarshal(resource.getLocation().toFile());
		} catch (Exception e) {
			CodeForestUIPlugin.log(e);
			return new XmlInput();
		}
	}

	private ParsingResult parse(final IResource file, final XmlInput input) {
		// quando abrir o arquivo no editor, adiciona as anotacoes... por isso
		// existe o listener

		ASTParser parser = ASTParser.newParser(4);//AST.JLS4
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		char[] trimmedSource = SourceCodeUtils.readAndTrim(file);
		parser.setSource(trimmedSource);
		parser.setResolveBindings(true);

		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.DISABLED);
		parser.setCompilerOptions(options);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		ParsingResult result = new ParsingResult(file);
		cu.accept(new SourceCodeParser(cu, trimmedSource, input, result));
		return result;
	}

	@Override
	public boolean isEnabled() {
		IProject project = ProjectUtils.getCurrentSelectedProject();
		if (project == null) {
			return false;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return false;
		}
		
		Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);

		if (!xmlFiles.containsKey("codeforest.xml") || xmlFiles.get("codeforest.xml").size() > 1) {
			return false;
		}
		
		if (!state.isAnalyzed()) {
			return true;
		}

		return false;
	}
}
