package thebrightcompany.com.kdoctor.utils.ecu.res;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import thebrightcompany.com.kdoctor.utils.ecu.common.UTF8Bundle;


public class Messages
{
	private static final String BUNDLE_NAME = "chienbk.com.garainthepocket.ecu.prot.obd.res.messages"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE;

	public Messages()
	{
		init(BUNDLE_NAME);
	}

	/**
     * Initialize messages with a new message bundle
     *
     * @param bundleName Name of message bundle
     */
	public static void init(String bundleName)
	{
        RESOURCE_BUNDLE = UTF8Bundle.getBundle(bundleName);
	}

	public static String getString(String key, String defaultString)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e)
		{
			return defaultString;
		}
	}
	
	public static String getString(String key)
	{
		return getString(key, '!' + key + '!');
	}
}
