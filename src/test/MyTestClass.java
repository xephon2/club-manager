/**
 * 
 */
package test;

import static org.junit.Assert.*;
import main.club.ClubMember;
import main.runtime.RuntimeManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Stefan Eike (s.eike85@gmail.com)
 *
 */
public class MyTestClass {

    static RuntimeManager runtimeManager;
    static ClubMember clubMember;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Create RuntimeManager...");
        runtimeManager = new RuntimeManager();
        clubMember = new ClubMember(0) {};
    }

    /**
     * Test creating new ClubMembers.
     */
    public final void testClubMember() {
        assertEquals(null, clubMember.getClubMemberId());
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
