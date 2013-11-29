package main.java.club;
import java.util.ArrayList;
import java.util.List;

import main.java.utility.Messages;

/**
 * The club class implements the ClubObserverBehavior interface.
 * The club can be observed. All observers are stored in
 * the clubObservers List.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Club implements ClubBehavior {

    /* *****************************************
     * Class variables.
     */

    /** Stores the name of a club. */
    private String clubName;

    /** Stores all object references to objects that observe the club. */
    private List<main.java.observer.ClubObserver> clubObservers = new ArrayList<>();

    /** Stores all club members. */
    private List<ClubMember> clubMembers = new ArrayList<ClubMember>();

    /** Stores all types of clubMemberProperties. */
    public enum clubMemberProperties {
        /** The property type is the first name of the club member. */
        FIRSTNAME,
        /** The property type is the last name of the club member. */
        LASTNAME,
        /** The property type is the user name of the club member. */
        USERNAME,
        /** The property type is the street of the club member's address. */
        STREET,
        /** The property type is the house number
         * of the club member's address. */
        HOUSENUMBER,
        /** The property type is the ZIP code of the club member's address. */
        ZIPCODE,
        /** The property type is the city of the club member's address. */
        CITY,
        /** The property type is the name of the club member's bank. */
        BANK,
        /** The property type is the account number
         * of the club member's bank. */
        BANKACCOUNTNUMBER,
        /** The property type is the ID code of the club member's bank. */
        BANKIDCODE
    };


    /* *****************************************
     * Constructor
     */

    /**
     * Create a new club.
     * @param nameOfClub name of the club
     */
    public Club(final String nameOfClub) {
        this.clubName = nameOfClub;
    }


    /* *****************************************
     * Methods
     */

    /**
     * Add a club member to a club.
     * @param clubMember club member
     */
    public final void addClubMember(final ClubMember clubMember) {
        clubMembers.add(clubMember);
        notifyObservers();
    }

    /**
     * Remove a club member from a club.
     * @param clubMember club member
     */
    public final void removeClubMemberFromClub(final ClubMember clubMember) {
        clubMembers.remove(clubMember);
        notifyObservers();
    }

    /**
     * Register an observer at the clubObservers list.
     * @param clubObserver club observer
     */
    public final void registerClubObserver(
            final main.java.observer.ClubObserver clubObserver) {
        if (clubObserver == null) {
            throw new NullPointerException(Messages.getString("Messages.1"));
        }
        if (!clubObservers.contains(clubObserver)) {
            clubObservers.add(clubObserver);
        }
        // System.out.println(Messages.getString("Messages.2") + clubObserver);
    }

    /**
     * Unregisters an observer from the clubObservers list.
     * @param clubObserver club observer
     */
    public final void unregisterClubObserver(
            final main.java.observer.ClubObserver clubObserver) {
        clubObservers.remove(clubObserver);
    }

    /**
     * Print all club members to the console.
     */
    public final void printAllClubMembersToConsole() {
        for (ClubMember mg : clubMembers) {
            System.out.println(
                    mg.getFirstName() + " "
                    + mg.getLastName() + " "
                    + mg.getClubMemberId());
        }
    }

    /**
     * Add properties to new club members or change
     * properties of existing club members.
     * @param clubMemberId ID of the club member
     * @param propertyType type of property to be added or changed
     * @param propertyValue value of the property
     */
    public final void addOrChangeClubMemberProperties(
            final int clubMemberId,
            final clubMemberProperties propertyType,
            final String propertyValue) {
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
                        Integer.parseInt(propertyValue));
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
        notifyObservers();
        printAllClubMembersToConsole();
    }

    /**
     * Notify all observers that are registered at the clubObservers list.
     */
    public final void notifyObservers() {
        for (main.java.observer.ClubObserver clubObserver : clubObservers) {
            clubObserver.update();
        }
    }


    /* *****************************************
     * Getter and Setter
     */

    /**
     * Return the club.
     * @return club
     */
    public final Club getClub() {
        return this;
    }

    /**
     * Return the name of the club.
     * @return club name
     */
    public final String getClubName() {
        return this.clubName;
    }

    /**
     * Return a club member by his id.
     * @param clubMemberId id
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
     * @param clubMemberUserName user name
     * @return ClubMember club member
     */
    public final ClubMember getClubMemberByUserName(
            final String clubMemberUserName) {
        for (ClubMember v : clubMembers) {
            if (v.getUsername().equals(clubMemberUserName)) {
                System.out.println(Messages.getString("Club.6")); //$NON-NLS-1$
                return v;
            }
        }
        return null;
    }

    /**
     * Return a list with all club members.
     * @return clubMembers club members
     */
    public final List<ClubMember> getClubMembers() {
        return clubMembers;
    }   
}