package br.usp.each.saeg.code.forest.ui.core;

import org.eclipse.osgi.util.NLS;

/**
 * Text messages for the core plug-in.
 */
public class CoreMessages extends NLS {

  private static final String BUNDLE_NAME = "br.usp.each.saeg.code.forest.ui.core.coremessages";

  public static String JAGUAR_CREATE_LAUNCH_ERROR_message;
  public static String JAGUAR_RUN_LAUNCH_ERROR_message;
  public static String JAGUAR_CLASS_PATH_ERROR_message;
  public static String StatusNO_LOCAL_AGENTJAR_ERROR_message;

  static {
    NLS.initializeMessages(BUNDLE_NAME, CoreMessages.class);
  }

}
