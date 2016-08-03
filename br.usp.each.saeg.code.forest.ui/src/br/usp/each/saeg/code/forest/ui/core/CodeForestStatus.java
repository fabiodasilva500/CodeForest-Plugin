package br.usp.each.saeg.code.forest.ui.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

/**
 * Status objects used by the core plug-in.
 */
public final class CodeForestStatus {

  public final int code;

  public final int severity;

  public final String message;

  private CodeForestStatus(int code, int severity, String message) {
    this.code = code;
    this.severity = severity;
    this.message = message;
  }

  public IStatus getStatus() {
    String m = NLS.bind(message, Integer.valueOf(code));
    return new Status(severity, CodeForestUIPlugin.ID, code, m, null);
  }

  public IStatus getStatus(Throwable t) {
    String m = NLS.bind(message, Integer.valueOf(code));
    return new Status(severity, CodeForestUIPlugin.ID, code, m, t);
  }

  public IStatus getStatus(Object param1, Throwable t) {
    String m = NLS.bind(message, Integer.valueOf(code), param1);
    return new Status(severity, CodeForestUIPlugin.ID, code, m, t);
  }

  public IStatus getStatus(Object param1, Object param2, Throwable t) {
    String m = NLS.bind(message, new Object[] { Integer.valueOf(code), param1,
        param2 });
    return new Status(severity, CodeForestUIPlugin.ID, code, m, t);
  }

  public IStatus getStatus(Object param1) {
    String m = NLS.bind(message, Integer.valueOf(code), param1);
    return new Status(severity, CodeForestUIPlugin.ID, code, m, null);
  }

  /**
   * Error while creating jaguar launch.
   */
  public static final CodeForestStatus JAGUAR_CREATE_LAUNCH_ERROR = new CodeForestStatus(
      5000, IStatus.ERROR, CoreMessages.JAGUAR_CREATE_LAUNCH_ERROR_message);  
  
  /**
   * Error while running jaguar launch.
   */
  public static final CodeForestStatus JAGUAR_RUN_LAUNCH_ERROR = new CodeForestStatus(
      5001, IStatus.ERROR, CoreMessages.JAGUAR_RUN_LAUNCH_ERROR_message);  
  
  /**
   * Status indicating that it was not possible to obtain a local version of the
   * runtime agent file.
   */
  public static final CodeForestStatus NO_LOCAL_AGENTJAR_ERROR = new CodeForestStatus(
      5002, IStatus.ERROR, CoreMessages.StatusNO_LOCAL_AGENTJAR_ERROR_message);

  /**
   * Status indicating that it was not possible to add one of the files to the classpath.
   */
  public static final CodeForestStatus JAGUAR_CLASS_PATH_ERROR = new CodeForestStatus(
      5002, IStatus.ERROR, CoreMessages.JAGUAR_CLASS_PATH_ERROR_message);
  
}
