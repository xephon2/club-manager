/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.runtime.RuntimeManager;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Stefan Eike (s.eike85@gmail.com)
 *
 */
public class ClubMemberTest {

    static RuntimeManager runtimeManager;
    static Club club;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Create RuntimeManager...");
        runtimeManager = new RuntimeManager();
        System.out.println("Create new Club...");
        club = new Club(null) {};
    }

    /**
     * Test creating new ClubMembers.
     */
    @Test
    public final void testCreateClubMember() {
        ClubMember clubMember1 = new ClubMember(1) {};
        assertEquals(1, clubMember1.getClubMemberId());
        assertEquals(null, clubMember1.getAddress());
        assertEquals(null, clubMember1.getBankAccount());
        assertEquals(null, clubMember1.getClub());
        assertEquals(null, clubMember1.getFirstName());
        assertEquals(null, clubMember1.getHashedPassword());
        assertEquals(null, clubMember1.getLastName());
        assertEquals(null, clubMember1.getUsername());
    }

}
