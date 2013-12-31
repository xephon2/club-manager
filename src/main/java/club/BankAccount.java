package main.java.club;

/**
 * The club and all club members can have a bank account.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class BankAccount {

	/* *****************************************
	 * Class variables
	 */

	/** Name of the bank. */
	private String bank;

	/** Number of the bank account. */
	private int accountNumber;

	/** ID of the bank. */
	private int idCode;

	/* *****************************************
	 * Constructor
	 */

	/**
	 * Creates a new bank account.
	 * 
	 * @param bankName
	 *            name of the bank
	 * @param bankAccountNumber
	 *            account number
	 * @param bankIdCode
	 *            bank id
	 */
	public BankAccount(final String bankName, final int bankAccountNumber,
			final int bankIdCode) {
		this.bank = bankName;
		this.accountNumber = bankAccountNumber;
		this.idCode = bankIdCode;
	}

	/* *****************************************
	 * Getter and Setter
	 */

	/**
	 * Return the name of the bank.
	 * 
	 * @return bank name of the bank
	 */
	public final String getBankName() {
		return bank;
	}

	/**
	 * Set the name of the bank.
	 * 
	 * @param bankName
	 *            name of the bank
	 */
	public final void setBankName(final String bankName) {
		this.bank = bankName;
	}

	/**
	 * Return the account number.
	 * 
	 * @return accountNumber account number
	 */
	public final int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Set the account number.
	 * 
	 * @param bankAccountNumber
	 *            account number
	 */
	public final void setAccountNumber(final int bankAccountNumber) {
		this.accountNumber = bankAccountNumber;
	}

	/**
	 * Return the bank identification code.
	 * 
	 * @return idCode bank identification code
	 */
	public final int getBankIdCode() {
		return idCode;
	}

	/**
	 * Set the bank identification code.
	 * 
	 * @param bankIdCode
	 *            bank identification code
	 */
	public final void setBankIdCode(final int bankIdCode) {
		this.idCode = bankIdCode;
	}
}
