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
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class PasswordManager extends Properties {

	/* *****************************************
	 * Class variables
	 */

	/** Stores the ID of the PasswordManager. */
	private static final long serialVersionUID = 7884595892231423639L;

	/** Store singleton instance of the PasswordManager object. */
	private static PasswordManager passwordManager = null;

	/** Stores theSHA value. */
	private static final byte SHA = 40;

	/** Store SHA hash constant. */
	private static final int SHA_HASH_1 = 4;

	/** Store SHA hash constant. */
	private static final byte SHA_HASH_2 = 0x0F;

	/** Store SHA hash constant. */
	private static final int SHA_HASH_3 = 9;

	/** Store SHA hash constant. */
	private static final int SHA_HASH_4 = 10;

	/* *****************************************
	 * Methods
	 */

	/**
	 * Instantiates PasswordManager and returns the instance, if the constructor
	 * is called twice or more. This method corresponds to the singleton
	 * pattern.
	 * 
	 * @return Returns an instance of PasswordManager.
	 */
	public static synchronized PasswordManager getPasswordManagerInstance() {
		if (passwordManager == null) {
			passwordManager = new PasswordManager();
		}
		return passwordManager;
	}

	/**
	 * Create a hash of the user's password using the hasPasswort() method.
	 * 
	 * @param clubMemberId
	 *            The id of the user.
	 * @param plainTextPassword
	 *            The (not hashed) password
	 * @return hashed password
	 */
	public final String createHashedPasswordAndStoreToXml(
			final int clubMemberId, final String plainTextPassword) {
		String hashedPassword = hashPassword(plainTextPassword);
		RuntimeManager runtimeManager = RuntimeManager
				.getRuntimeManagerInstance();

		setProperty(Integer.toString(clubMemberId), hashedPassword);
		try {
			storeToXML(
		        new FileOutputStream(
	                new File(runtimeManager
                        .getClub()
                        .getClubName()
                        + ".xml")
	                ),
	                "nocomment"
                );
			return hashedPassword;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Password can not be hashed");
			return null;
		}
	}

	/**
	 * Create a hash of a plain text password.
	 * This method is needed for storing
	 * the encrypted password in the XML file
	 * and to retrieve the hashed
	 * password from the XML file.
	 *
	 * @param plainTextPassword
	 *            plain text password
	 * @return hash of the plain text password
	 */
	public final String hashPassword(final String plainTextPassword) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] shaHash = new byte[SHA];
			md.update(plainTextPassword.getBytes("iso-8859-1"), 0,
					plainTextPassword.length());
			shaHash = md.digest();
			return convertToHex(shaHash);
		} catch (
		        NoSuchAlgorithmException |
		        UnsupportedEncodingException e1
		        ) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Load a hashed password from XML file.
	 *
	 * @param userName
	 *            user name
	 * @return hashed password
	 */
	public final String fetchPassword(final String userName) {
		System.out.println("fetchPassword()");
		RuntimeManager runtimeManager = RuntimeManager
				.getRuntimeManagerInstance();
		try {
			loadFromXML(
		        new FileInputStream(
	                new File(
                        runtimeManager.getClub()
                        .getClubName()
                        + "_password.xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getProperty(userName);
	}

	/**
	 * Convert a byte array to String and return it.
	 *
	 * @param shaHash
	 *            the byte array
	 * @return the String
	 */
	private String convertToHex(final byte[] shaHash) {

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < shaHash.length; i++) {
			int halfbyte = (shaHash[i] >>> SHA_HASH_1) & SHA_HASH_2;
			int twoHalfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= SHA_HASH_3)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - SHA_HASH_4)));
				}
				halfbyte = shaHash[i] & SHA_HASH_2;
			} while (twoHalfs++ < 1);
		}
		return buf.toString();
	}
}
