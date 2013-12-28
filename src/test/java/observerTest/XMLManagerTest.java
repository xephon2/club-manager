/**
 * Test the behavior of the XMLManager.
 */
package test.java.observerTest;

import static org.junit.Assert.assertEquals;
import main.java.club.Club;
import main.java.runtime.RuntimeManager;

import org.junit.Test;

/**
 * @author Stefan Eike (s.eike85@gmail.com)
 *
 */
public class XMLManagerTest {

    /**
     * Test method for {@link main.java.observer.XMLManager#loadXMLClub()}.
     */
    @Test
    public final void testLoadXMLClub() {
        RuntimeManager runtimeManager = new RuntimeManager();
        Club club = new Club("name");
        runtimeManager.setClub(club);

        assertEquals(
            "Compare the name of the club",
            "name",
            runtimeManager.getClub().getClubName()
        );
    }
}
