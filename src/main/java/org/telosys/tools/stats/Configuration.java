package org.telosys.tools.stats;

/**
 * Created by maeln on 04/02/16.
 */
public class Configuration
{
	private final static String telosysSaasLocation = "../telosys-saas/";

	private final static String TELOSYS_DIR = "TelosysTools";

	public static String getTelosysSaasLocation()
	{
		return telosysSaasLocation;
	}

	public static String getTelosysDir() {
		return TELOSYS_DIR;
	}
}
