package main.club;
import main.runtime.RuntimeManager;

/**
 * Abstract class defining all club members behavoir.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public abstract class ClubMember implements ClubMemberProperties {

    /* *****************************************
     * Class variables
     */

    /** Stores the id of a club member. */
    private int id;

    /** Stores the first name of a club member. */
    private String firstName;

    /** Stores the last name of a club member. */
    private String lastName;

    /** Stores the user name of a club member. */
    private String userName;

    /** Stores the address of a club member. */
    private Address address;

    /** Stores the bank account of a club member. */
    private BankAccount bankAccount;

    /** Stores the hashed password of a club member. */
    private String hashedPassword;

    /** Stores the club of a club member. */
    private Club club;


    /* *****************************************
     * Getter and Setter
     */

    /**
     * Creates a club member with an id.
     * @param clubMemberId id
     */
    public ClubMember(final int clubMemberId) {
        this.id = clubMemberId;
    }


    /* *****************************************
     * Getter and Setter
     */

    /**
     * Return the id of the club member.
     * @return the club member id
     */
    public final int getClubMemberId() {
        return this.id;
    }

    /**
     * Set the id of the club member.
     * @param clubMemberId id of the club member
     */
    public final void setClubMemberId(final int clubMemberId) {
        this.id = clubMemberId;
    }

    /**
     * Return the first name of the club member.
     * @return first name
     */
    public final String getFirstName() {
        return this.firstName;
    }

    /**
     * Set the first name of the club member.
     * @param clubMemberFirstName first name
     */
    public final void setFirstName(final String clubMemberFirstName) {
        this.firstName = clubMemberFirstName;
    }

    /**
     * Return the last name of the club member.
     * @return last name
     */
    public final String getLastName() {
        return this.lastName;
    }

    /**
     * Set the last name of the club member.
     * @param clubMemberLastName last name
     */
    public final void setLastName(final String clubMemberLastName) {
        this.lastName = clubMemberLastName;
    }

    /**
     * Return the user name of the club member.
     * @return user name
     */
    public final String getUsername() {
        return this.userName;
    }

    /**
     * Set the user name of the club member.
     * @param clubMemberUserName user name
     */
    public final void setUsername(final String clubMemberUserName) {
        this.userName = clubMemberUserName;
    }

    /**
     * Return the address of the club member.
     * @return address
     */
    public final Address getAddress() {
        return this.address;
    }

    /**
     * Set the address of the club member.
     * @param clubMemberAddress address
     */
    public final void setAddress(final Address clubMemberAddress) {
        this.address = clubMemberAddress;
    }

    /**
     * Return the bank account of the club member.
     * @return bank account
     */
    public final BankAccount getBankAccount() {
        return this.bankAccount;
    }

    /**
     * Set the bank account of the club member.
     * @param clubMemberBankAccount bank account
     */
    public final void setBankAccount(final BankAccount clubMemberBankAccount) {
        this.bankAccount = clubMemberBankAccount;
    }

    /**
     * Return the club of the club member.
     * @return club
     */
    public final Club getClub() {
        return this.club;
    }

    /**
     * Set the club of the club member.
     * @param clubMemberClub club
     */
    public final void setVerein(final Club clubMemberClub) {
        this.club = clubMemberClub;
    }

    /**
     * Create and store a hash of the entered password.
     * @param plainTextPassword plain password
     */
    public final void hashAndStorePassword(final String plainTextPassword) {
        System.out.println("RuntimeManager: " + RuntimeManager.getRuntimeManagerInstance());
        System.out.println("PasswordManager: " + RuntimeManager.getRuntimeManagerInstance().getPasswordManager());
        String password = RuntimeManager.
                getRuntimeManagerInstance().getPasswordManager().
                createHashedPassword(id, plainTextPassword);
        this.hashedPassword = password;
    }

    /**
     * Return the password of the club member.
     * @return password
     */
    public final String getPassword() {
        return hashedPassword;
    }
}
