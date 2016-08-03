package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class CodeForestProjectPropertyTester extends PropertyTester {

    /**
     * deve responder - posso ligar o handler? esqueca o valor esperado declarado no xml!
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        if (!(receiver instanceof IProject)) {
            return false;
        }

        IProject project = (IProject) receiver;

        if (!project.isOpen()) {
            return false;
        }

        return ProjectUtils.javaFilesOf(project).size() > 0;
    }
}
