package test.java.club;

import static org.junit.Assert.assertEquals;
import main.java.club.Athlete;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.observer.ClubObserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Stefan Eike (s.eike85@gmail.com)
 *
 */
@RunWith(JUnit4.class)
public class ClubMemberTest {

    /**
     * Test creating new ClubMembers.
     */
    @Test
    public final void testCreateClubMember() {
        ClubMember clubMember1 = new ClubMember(1) {
        };
        assertEquals(1, clubMember1 .getClubMemberId());
        assertEquals(null, clubMember1.getAddress());
        assertEquals(null, clubMember1.getBankAccount());
        assertEquals(null, clubMember1.getClub());
        assertEquals(null, clubMember1.getFirstName());
        assertEquals(null, clubMember1.getHashedPassword());
        assertEquals(null, clubMember1.getLastName());
        assertEquals(null, clubMember1.getUsername());
    }
    
    /**
     * Test the club observer.
     * Create a new club, add an observer and add a new club member to the club.
     */
    @Test
    public final void testClubObserver() {
        Club testClub = new Club("Test Club");
        assertEquals(true, testClub.returnClubObservers().isEmpty());
        ClubObserver clubObserver = new ClubObserver() {
            @Override
            public void update() {
                System.out.println("ClubObserver has been informed.");
            }
        };
        testClub.registerClubObserver(clubObserver);
        assertEquals(false, testClub.returnClubObservers().isEmpty());
        testClub.addClubMember(new Athlete(123));
    }

}
