package main.java.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * The PasswordManager encrypts and decrypts passwords.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class PasswordManager extends Properties {

    /* *****************************************
     * Class variables
     */

    /** Stores the ID of the PasswordManager. */
    private static final long serialVersionUID = 7884595892231423639L;

    /** Stores the RuntimeManager. */
    private RuntimeManager runtimeManager;

    /** Stores theSHA value. */
    private final byte SHA = 40;


    /* *****************************************
     * Constructor
     */

    /**
     * Create a new PasswordManager.
     * @param passwordRuntimeManager RuntimeManager
     */
    public PasswordManager(final RuntimeManager passwordRuntimeManager) {
        this.runtimeManager = passwordRuntimeManager;
    }


    /* *****************************************
     * Methods
     */

    /**
     * Create a hash of the user's password using the hasPasswort() method.
     * @param clubMemberId The id of the user.
     * @param plainTextPassword The (not hashed) password
     * @return hashed password
     */
    public final String createHashedPassword(
            final int clubMemberId, final String plainTextPassword) {
        String hashedPassword = hashPassword(plainTextPassword);

        setProperty(Integer.toString(clubMemberId), hashedPassword);
        try {
            storeToXML(new FileOutputStream(
                    new File(runtimeManager.getClub().getClubName()
                            + ".xml")), "nocomment");
            return hashedPassword;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Password can not be hashed");
            return null;
        }
    }

    /**
     * Create a hash of a plain text password.
     * This method is needed for storing the encrypted password in the XML file
     * and to retrieve the hashed password from the XML file.
     * @param plainTextPassword plain text password
     * @return hash of the plain text password
     */
    public final String hashPassword(final String plainTextPassword) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] shaHash = new byte[SHA];
            md.update(plainTextPassword.getBytes("iso-8859-1"),
                    0, plainTextPassword.length());
            shaHash = md.digest();
            return convertToHex(shaHash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Load a hashed password from XML file.
     * @param userName user name
     * @return hashed password
     */
    public final String fetchPassword(final String userName) {
        System.out.println("fetchPassword()");
        try {
            loadFromXML(new FileInputStream(new File(
                    runtimeManager.getClub().getClubName() + "_password.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getProperty(userName);
    }

    /**
     * Convert a byte array to String and return it.
     * @param shaHash the byte array
     * @return the String
     */
    private String convertToHex(final byte[] shaHash) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < shaHash.length; i++) {
            int halfbyte = (shaHash[i] >>> 4) & 0x0F;
            int twoHalfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = shaHash[i] & 0x0F;
            } while(twoHalfs++ < 1);
        }
        return buf.toString();
    }
}
