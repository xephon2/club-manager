package main.java.club;

/**
 * Defines the behavior of all club members.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public interface ClubMemberProperties {

	/**
	 * Set the club member id.
	 * 
	 * @param id
	 *            club member id
	 */
	void setClubMemberId(int id);

	/**
	 * Return the first name of the club member.
	 * 
	 * @return first name
	 */
	String getFirstName();

	/**
	 * Set the first name of the club member.
	 * 
	 * @param firstname
	 *            first name
	 */
	void setFirstName(String firstname);

	/**
	 * Return the last name of the club member.
	 * 
	 * @return last name
	 */
	String getLastName();

	/**
	 * Set the last name of the club member.
	 * 
	 * @param lastname
	 *            last name
	 */
	void setLastName(String lastname);

	/**
	 * Return the user name of the club member.
	 * 
	 * @return user name
	 */
	String getUsername();

	/**
	 * Set the user name of the club member.
	 * 
	 * @param username
	 *            user name
	 */
	void setUsername(String username);

	/**
	 * Return the bank account of the club member.
	 * 
	 * @return bank account
	 */
	BankAccount getBankAccount();

	/**
	 * Set the bank account of the club member.
	 * 
	 * @param bankAccount
	 *            bank account
	 */
	void setBankAccount(BankAccount bankAccount);

	/**
	 * Return the address of the club member.
	 * 
	 * @return address
	 */
	Address getAddress();

	/**
	 * Set the address of the club member.
	 * 
	 * @param address
	 *            adress
	 */
	void setAddress(Address address);
}
