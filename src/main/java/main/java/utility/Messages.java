package main.java.utility;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * This class reads and returns the UI strings from the message bundle.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public final class Messages {

	/* *****************************************
	 * Class variables
	 */

	/** Stores the name of the ResourceBundle bundle. */
	private static final String BUNDLE_NAME = "main.java.utility.messages";

	/** Stores the reference to the ResourceBundle. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/** Stores the singleton instance of the Messages object. */
	private static Messages messages = null;

	/* *****************************************
	 * Constructor
	 */

	/**
	 * Instantiates a Messages object.
	 */
	public Messages() {
	}

	/* *****************************************
	 * Methods
	 */

	/**
	 * Instantiates RuntimeManager and returns the instance, if the constructor
	 * is called twice or more. This method corresponds to the singleton
	 * pattern.
	 *
	 * @return Returns an instance of RuntimeManager.
	 */
	public static synchronized Messages getMessagesInstance() {
		if (messages == null) {
			messages = new Messages();
		}
		return messages;
	}

	/**
	 * Get a string from the ResourceBundle by its key and return it.
	 *
	 * @param key
	 *            key of the string
	 * @return the string
	 */
	public String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
