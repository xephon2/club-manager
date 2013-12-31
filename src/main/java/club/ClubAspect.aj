package main.java.club;

import main.java.club.Club;

public aspect ClubAspect {

    /** This pointcut is set when a new club member is added to the club. */
    pointcut clubMemberAdded() : execution(* addClubMember(..));

    /** When the pointcut is reached, print the whole club to the console. */
    after() : clubMemberAdded() {
        Club club = (Club)thisJoinPoint.getThis();
        ClubMember latestMember = club.getLatestNewClubMember();
        System.out.println("\n++++++++++++++++++++++++++\n"
                + "The club '"
                + club.getClubName()
                + "' has a new member: '\n"
                + " ID: " + latestMember.getClubMemberId() + "\n"
                + " First Name: " + latestMember.getFirstName() + "\n"
                + " Last Name: " + latestMember.getLastName() + "\n"
                + " User Name: " + latestMember.getUsername() + "\n"
                + "Now the club has the following members: "
                );
        club.printAllClubMembersToConsole();
        System.out.println("++++++++++++++++++++++++++\n");
    }

    /** This pointcut is set when a club member is removed from the club. */
    pointcut clubMemberRemoved() : execution(* removeClubMemberFromClub(..));

    /** When the pointcut is reached, print the whole club to the console. */
    after() : clubMemberRemoved() {
        Club club = (Club)thisJoinPoint.getThis();
        ClubMember lostMember = club.getLatestLostClubMember();
        System.out.println("\n++++++++++++++++++++++++++\n"
                + club.getClubName()
                + "' has a new member: '\n"
                + " ID: " + lostMember.getClubMemberId() + "\n"
                + " First Name: " + lostMember.getFirstName() + "\n"
                + " Last Name: " + lostMember.getLastName() + "\n"
                + " User Name: " + lostMember.getUsername() + "\n"
                + "Now the club has the following members: "
                );
        club.printAllClubMembersToConsole();
        System.out.println("++++++++++++++++++++++++++\n");
    }
}
