package br.usp.each.saeg.code.forest.ui.handlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.statushandlers.StatusManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(ErrorHandler.class.getName());

	static public void showDialog(IStatus status){
		logger.error(status.getMessage(), status.getException());
		StatusManager.getManager().handle(status,StatusManager.SHOW);
	}
}
