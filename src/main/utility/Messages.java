package main.utility;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class reads and returns the UI strings from the message bundle.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public final class Messages {

    /* *****************************************
     * Class variables
     */

    /** Stores the name of the ResourceBundle bundle. */
    private static final String BUNDLE_NAME = "main.utility.messages"; //$NON-NLS-1$

    /** Stores the reference to the ResourceBundle. */
    private static final ResourceBundle RESOURCE_BUNDLE
        = ResourceBundle.getBundle(BUNDLE_NAME);


    /* *****************************************
     * Constructor
     */

    /**
     * Instantiates a Messages object.
     */
    private Messages() {
    }


    /* *****************************************
     * Methods
     */

    /**
     * Get a string from the ResourceBundle by its key
     * and return it.
     * @param key key of the string
     * @return the string
     */
    public static String getString(final String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
