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
public class MyTestClass {

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
    }

    /**
     * Test method for {@link main.runtime.RuntimeManager#addPasswordManager(main.runtime.PasswordManager)}.
     */
    @Test
    public final void testAddPasswordManager() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link main.runtime.RuntimeManager#matchPassword(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testMatchPassword() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link main.runtime.RuntimeManager#update()}.
     */
    @Test
    public final void testUpdate() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link main.runtime.RuntimeManager#loginUser(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testLoginUser() {
        // Try to login a non-existing user.
        assertEquals(false, runtimeManager.loginUser("testuser", null));
    }

}
