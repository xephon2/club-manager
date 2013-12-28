package main.java.runtime;

import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.observer.ClubObserver;

/**
 * RuntimeManager is the managing class and uses the singleton pattern,
 * meaning, that the class can only be instantiated once.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class RuntimeManager implements ClubObserver {

    /* *****************************************
     * Class variables
     */

    /** Holds an instance of itself, needed for using the singleton pattern. */
    private static RuntimeManager runtimeManager;

    /** Holds an instance of PasswordManager. */
    private PasswordManager passwordManager;

    /** Holds a single instance of club,
     * meaning that club can only be instantiated once. */
    private Club club;

    /** Holds an instance of club member (of the logged in user). */
    // TODO Refactor this -> multi-user system
    private ClubMember loggedInUser;



    /* *****************************************
     * Getter and Setter
     */

    /**
     * Sets an instance of VereinsMitglied as the logged in user.
     * @param loggedInClubMember The instance of ClubMember
     * of the logged in user.
     */
    public final void setLoggedInUser(final ClubMember loggedInClubMember) {
        this.loggedInUser = loggedInClubMember;
    }

    /**
     * Set the club to be used in the RuntimeManager.
     * Currently, the RuntimeManager can handle only one club.
     * @param runtimeManagerClub club
     */
    public final void setClub(final Club runtimeManagerClub) {
        this.club = runtimeManagerClub;
    }

    /**
     * Return the club of the runtime manager.
     * @return club
     */
    public final Club getClub() {
        return this.club;
    }

    /**
     * Return the password manager.
     * @return PasswordManager
     */
    public final PasswordManager getPasswordManager() {
        return passwordManager;
    }

    /* *****************************************
     * Methods
     */

    /** Instantiates RuntimeManager and returns the
     *  instance, if the constructor is called twice or more.
     *  This method corresponds to the singleton pattern.
     *  @return Returns an instance of RuntimeManager.
     */
    public static synchronized RuntimeManager getRuntimeManagerInstance() {
        if (runtimeManager == null) {
            runtimeManager = new RuntimeManager();
        }
        return runtimeManager;
    }

    /**
     * Returns the instance of VereinsMitglied of the logged in user.
     * @return Returns logged in user
     */
    public final ClubMember getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * Add a PasswordManager to the RuntimeManager.
     * @param runtimePasswordManager A PasswordManager
     */
    public final void addPasswordManager(
            final PasswordManager runtimePasswordManager) {
        this.passwordManager = runtimePasswordManager;
    }

    /**
     * Check, if the user's password matches to the stored password hash.
     * @param username The user name of the user
     * @param enteredPassword The entered password
     * @return Return TRUE, if the password is correct, otherwise return FALSE.
     */
    public final boolean matchPassword(final String username,
            final String enteredPassword) {

        String hashedEstimatedPassword = getClub().
                getClubMemberByUserName(username).
                getHashedPassword();

        // Get singleton instance of the PasswordManager.
        PasswordManager passwordManager;
        passwordManager = PasswordManager.getPasswordManagerInstance();

        // Hash the password
        String hashedEnteredPassword;
        hashedEnteredPassword = passwordManager.hashPassword(enteredPassword);

        if (hashedEnteredPassword.equals(hashedEstimatedPassword)) {
            return true;
        } else {
            System.out.println("user '" + username
                    + "' has entered a wrong password.");
            return false;
        }
    }

    /**
     * The observer method is calld when new messages
     * are consumed from the subject.
     */
    @Override
    public final void update() {
    }

    /**
     * Check if user name and password is correct and login the user afterwards.
     * @param userName user name of the user
     * @param password password of the user
     * @return return true, if the user has been successfully logged in.
     */
    public final boolean loginUser(final String userName,
            final String password) {
        ClubMember clubmember = null;
        if (getClub() != null) {
            clubmember = getClub().getClubMemberByUserName(userName);
        }
        if (clubmember != null) {
            if (matchPassword(userName, password)) {
                setLoggedInUser(clubmember);
                System.out.println("loginUser: user logged in");
                return true;
            } else {
                System.out.println("loginUser: password does not match");
                return false;
            }
        } else {
            System.out.println("loginUser: user does not exist.");
            return false;
        }
    }
}
