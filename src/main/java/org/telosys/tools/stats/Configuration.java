package org.telosys.tools.stats;

/**
 * Created by maeln on 04/02/16.
 */
public class Configuration
{
	private final static String telosysSaasLocation = "../telosys-saas/";

	private final static String TELOSYS_DIR = "TelosysTools";
	
	private final static String MODEL_EXTENSION = ".model";
	
	private final static String TEMPLATE_DIR = "templates";

	public static String getModelExtension() {
		return MODEL_EXTENSION;
	}
	
	public static String getTelosysSaasLocation() {
		return telosysSaasLocation;
	}

	public static String getTelosysDir() {
		return TELOSYS_DIR;
	}

	public static String getTemplateDir() {
		return TEMPLATE_DIR;
	}
}
