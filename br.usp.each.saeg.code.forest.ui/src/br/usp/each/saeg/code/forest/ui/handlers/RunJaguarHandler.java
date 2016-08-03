package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.code.forest.ui.core.CodeForestUIPlugin;
import br.usp.each.saeg.code.forest.ui.core.LogListener;
import br.usp.each.saeg.code.forest.ui.project.ProjectPersistence;
import br.usp.each.saeg.code.forest.ui.project.ProjectState;
import br.usp.each.saeg.code.forest.ui.project.ProjectUtils;

/**
 * @author Henrique Ribeiro (henriquelemos0@gmail.com)
 */
public class RunJaguarHandler extends AbstractHandler implements
		IJavaLaunchConfigurationConstants {

	JaguarRunnable jaguar;

	public RunJaguarHandler() {
		super();
		jaguar = new JaguarRunnable();
	}

	public RunJaguarHandler(ILaunchesListener2 listener) {
		super();
		this.jaguar = new JaguarRunnable(listener);
	}

	@Override
	public Object execute(ExecutionEvent arg) throws ExecutionException {
		final IProject project = ProjectUtils.getCurrentSelectedProject();
		if (!project.isOpen()) {
			return null;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return null;
		}

		jaguar.run();

		CodeForestUIPlugin.ui(project, this, "run jaguar");
		return null;
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

		return true;
	}
}
