package main.java.club;

import java.util.ArrayList;
import java.util.List;

import main.java.observer.ClubObserver;

/**
 * The club class implements the ClubObserverBehavior interface. The club can be
 * observed. All observers are stored in the clubObservers List.
 *
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Club {

    /* *****************************************
     * Class variables.
     */

    /** Stores the name of a club. */
	private String clubName;

	/** Stores all object references to objects that observe the club. */
	private List<ClubObserver> clubObservers = new ArrayList<>();

	/** Stores all club members. */
	private List<ClubMember> clubMembers = new ArrayList<ClubMember>();

	/** Stores all types of clubMemberProperties. */
	public enum clubMemberProperties {
		/** First name of the club member. */
		FIRSTNAME,
		/** Last name of the club member. */
		LASTNAME,
		/** User name of the club member. */
		USERNAME,
		/** Street of the club member's address. */
		STREET,
		/** House number of the club member's address. */
		HOUSENUMBER,
		/** ZIP code of the club member's address. */
		ZIPCODE,
		/** City of the club member's address. */
		CITY,
		/** Name of the club member's bank. */
		BANK,
		/** Account number of the club member's bank. */
		BANKACCOUNTNUMBER,
		/** ID code of the club member's bank. */
		BANKIDCODE
	};

	/** Stores the latest new club member. */
	private ClubMember latestNewClubMember;

	/** Stores the latest lost club member. */
	private ClubMember latestLostClubMember;

	/* *****************************************
    * Constructor
    */

	/**
    * Create a new club.
    *
    * @param nameOfClub
    *            name of the club
    */
	public Club(final String nameOfClub) {
		this.clubName = nameOfClub;
	}

	/* *****************************************
    * Methods
    */

	/**
    * Add a club member to a club and inform the observers.
    * FIXME Replace the manual call of notify() with a
    * listener.
    *
    * @param clubMember
    *            club member
    */
	public final void addClubMember(final ClubMember clubMember) {
		clubMembers.add(clubMember);

		/** Promote the clubMember as the latest new club member. */
		latestNewClubMember = clubMember;

        /** Inform the club observers. */
		notifyObservers();
	}

	/**
    * Remove a club member from a club.
    *
    * @param clubMember
    *            club member
    */
	public final void removeClubMemberFromClub(
			final ClubMember clubMember) {
        clubMembers.remove(clubMember);

        /** Promote the clubMember as the latest lost club member. */
	    latestLostClubMember = clubMember;

	    /** Inform the club observers. */
		notifyObservers();
	}

	/**
    * Register an observer at the clubObservers list.
    *
    * @param clubObserver
    *            club observer
    */
	public final void registerClubObserver(
			final ClubObserver clubObserver) {
		if (clubObserver == null) {
			System.out.println("Observer cannot be registered.");
		}
		if (!clubObservers.contains(clubObserver)) {
			clubObservers.add(clubObserver);
		}
	}

	/**
    * Unregisters an observer from the clubObservers list.
    *
    * @param clubObserver
    *            club observer
    */
	public final void unregisterClubObserver(
			final ClubObserver clubObserver
		) {
		clubObservers.remove(clubObserver);
	}

	/**
    * Print all club members to the console.
    */
	public final void printAllClubMembersToConsole() {
		System.out.println("The club '" + clubName + "' has "
				+ clubMembers.size() + " member(s):");
		for (ClubMember mg : clubMembers) {
			System.out.println(mg.getFirstName() + " "
					+ mg.getLastName() + " "
					+ mg.getClubMemberId());
		}
	}

	/**
    * Add properties to new club members or
    * change properties of existing club members.
    *
    * @param clubMemberId
    *            ID of the club member
    * @param propertyType
    *            type of property to be added or changed
    * @param propertyValue
    *            value of the property
    */
	public final void addOrChangeClubMemberProperties(
			final int clubMemberId,
			final clubMemberProperties propertyType,
			final String propertyValue
			) {
		ClubMember clubmember = getClubMemberById(clubMemberId);
		switch (propertyType) {
		case FIRSTNAME:
			clubmember.setFirstName(propertyValue);
			break;
		case LASTNAME:
			clubmember.setLastName(propertyValue);
			break;
		case USERNAME:
			clubmember.setUsername(propertyValue);
			break;
		case STREET:
			clubmember.getAddress().setStreet(propertyValue);
			break;
		case HOUSENUMBER:
			clubmember.getAddress().setHouseNumber(
					Integer.parseInt(propertyValue));
			break;
		case ZIPCODE:
			clubmember.getAddress().setZipCode(
				Integer.parseInt(propertyValue)
			);
			break;
		case CITY:
			clubmember.getAddress().setCity(propertyValue);
			break;
		case BANK:
			clubmember.getBankAccount().setBankName(propertyValue);
			break;
		case BANKACCOUNTNUMBER:
			clubmember.getBankAccount().setAccountNumber(
					Integer.parseInt(propertyValue));
			break;
		case BANKIDCODE:
			clubmember.getBankAccount().setBankIdCode(
					Integer.parseInt(propertyValue));
			break;
		default:
			System.out.println("Something is wrong"
					+ "in addOrChangeProperties");
		}

		System.out.println("The clubMember '" + clubMemberId
				+ "' has changed the property '" + propertyType
				+ "' to the value '" + propertyValue + "'.");

		notifyObservers();
		printAllClubMembersToConsole();
	}

	/**
    * Notify all observers that are registered at the clubObservers list.
    */
	public final void notifyObservers() {
		for (ClubObserver clubObserver : clubObservers) {
			System.out.println("The observer '" + clubObserver
					+ "' has been informed.");
			clubObserver.update();
		}
	}

	/* *****************************************
    * Getter and Setter
    */

	/**
    * Return the club.
    *
    * @return club
    */
	public final Club getClub() {
		return this;
	}

	/**
    * Return the name of the club.
    *
    * @return club name
    */
	public final String getClubName() {
		return this.clubName;
	}

	/**
    * Return a club member by his id.
    *
    * @param clubMemberId
    *            id
    * @return clubMember club member
    */
	public final ClubMember getClubMemberById(final int clubMemberId) {
		for (ClubMember clubMember : clubMembers) {
			if (clubMember.getClubMemberId() == clubMemberId) {
				return clubMember;
			}
		}
		return null;
	}

	/**
     * Return a club member by his user name.
     *
     * @param clubMemberUserName
     *            user name
     * @return ClubMember club member
     */
	public final ClubMember getClubMemberByUserName(
			final String clubMemberUserName) {
		for (ClubMember clubMember : clubMembers) {
			if (clubMember.getUsername().equals(
					clubMemberUserName
				)) {
				return clubMember;
			}
		}
		return null;
	}

	/**
     * Return a list with all club members.
     *
     * @return clubMembers club members
     */
	public final List<ClubMember> getClubMembers() {
		return clubMembers;
	}

	/**
     * Return the list of all club observers. This method is currently only
     * needed for unit tests.
     *
     * @return clubObservers list of all club observers
     */
	public final List<ClubObserver> getObservers() {
		return clubObservers;
	}

	/**
     * Returns the latest new club member.
     * @return the latest new member in the club
     */
    public final ClubMember getLatestNewClubMember() {
        return latestNewClubMember;
    }

    /**
     * Returns the latest lost club member.
     * @return the latest lost member of the club
     */
    public final ClubMember getLatestLostClubMember() {
        return latestLostClubMember;
    }
}
