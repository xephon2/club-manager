package test.java.club;

import static org.junit.Assert.assertEquals;
import main.java.club.ClubMember;

import org.junit.Test;

/**
 * @author Stefan Eike (s.eike85@gmail.com)
 *
 */
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

}
