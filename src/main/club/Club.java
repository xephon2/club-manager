package main.club;
import java.util.ArrayList;
import java.util.List;

import main.utility.Messages;

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
    private List<main.observer.ClubObserver> clubObservers = new ArrayList<>();

    /** Stores all club members. */
    private List<ClubMember> clubMembers = new ArrayList<ClubMember>();


    /* *****************************************
     * Methods
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
            final main.observer.ClubObserver clubObserver) {
        if (clubObserver == null) {
            throw new NullPointerException(Messages.getString("Messages.1")); //$NON-NLS-1$
        }
        if (!clubObservers.contains(clubObserver)) {
            clubObservers.add(clubObserver);
        }
        System.out.println(Messages.getString("Messages.2") + clubObserver); //$NON-NLS-1$
    }

    /**
     * Unregisters an observer from the clubObservers list.
     * @param clubObserver club observer
     */
    public final void unregisterClubObserver(
            final main.observer.ClubObserver clubObserver) {
        clubObservers.remove(clubObserver);
    }

    /**
     * Print all club members to the console.
     */
    public final void printAllClubMembersToConsole() {
        for (ClubMember mg : clubMembers) {
            System.out.println(mg.getFirstName()
                    + Messages.getString("Club.2") + mg.getLastName() + Messages.getString("Club.3") //$NON-NLS-1$ //$NON-NLS-2$
                    + Messages.getString("Club.4") + mg.getId() + Messages.getString("Club.5")); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Notify all observers that are registered at the clubObservers list.
     */
    public final void notifyObservers() {
        for (main.observer.ClubObserver clubObserver : clubObservers) {
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
            if (clubMember.getId() == clubMemberId) {
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
