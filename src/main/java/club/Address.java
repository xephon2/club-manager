package main.java.club;

/**
 * The club and all club members can have an address.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Address {

	/* *****************************************
	 * Class variables
	 */

	/** Stores the street of the address. */
	private String street;

	/** Stores the house number of the address. */
	private int houseNumber;

	/** Stores the zip code of the address. */
	private int zipCode;

	/** Stores the city of the address. */
	private String city;

	/* *****************************************
	 * Constructor
	 */

	/**
	 * Create a new address.
	 * 
	 * @param addressStreet
	 *            street
	 * @param addressHouseNumber
	 *            house number
	 * @param addressZipCode
	 *            zip code
	 * @param addressCity
	 *            city
	 */
	public Address(final String addressStreet, final int addressHouseNumber,
			final int addressZipCode, final String addressCity) {
		this.street = addressStreet;
		this.houseNumber = addressHouseNumber;
		this.zipCode = addressZipCode;
		this.city = addressCity;
	}

	/* *****************************************
	 * Getter and Setter
	 */

	/**
	 * Return the street of the address.
	 * 
	 * @return street street
	 */
	public final String getStreet() {
		return street;
	}

	/**
	 * Set the street of the address.
	 * 
	 * @param addressStreet
	 *            street
	 */
	public final void setStreet(final String addressStreet) {
		this.street = addressStreet;
	}

	/**
	 * Return the house number of the address.
	 * 
	 * @return houseNumber house number
	 */
	public final int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Set the house number of the address.
	 * 
	 * @param addressHouseNumber
	 *            house number
	 */
	public final void setHouseNumber(final int addressHouseNumber) {
		this.houseNumber = addressHouseNumber;
	}

	/**
	 * Return the zipcode of the address.
	 * 
	 * @return zipCode zipcode
	 */
	public final int getZipCode() {
		return zipCode;
	}

	/**
	 * Set the zip code of the address.
	 * 
	 * @param addressZipCode
	 *            zip code
	 */
	public final void setZipCode(final int addressZipCode) {
		this.zipCode = addressZipCode;
	}

	/**
	 * Return the city of the address.
	 * 
	 * @return city city
	 */
	public final String getCity() {
		return city;
	}

	/**
	 * Set the city of the address.
	 * 
	 * @param addressCity
	 *            city
	 */
	public final void setCity(final String addressCity) {
		this.city = addressCity;
	}
}
