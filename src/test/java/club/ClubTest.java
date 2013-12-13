package test.java.club;

import static org.junit.Assert.assertEquals;

import java.util.List;

import main.java.club.Club;
import main.java.observer.ClubObserver;

import org.junit.Test;

/**
 * Test the Club class.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class ClubTest {

    /**
     * Test the constructor.
     */
    @Test
    public final void testClubConstructor() {
        String clubName = "Test Club";
        Club testClub = new Club("Test Club");
        assertEquals(clubName, testClub.getClubName());
    }

    /**
     * Test the club observer.
     * Create a new club, add an observer and add a new club member to the club.
     */
    @Test
    public final void testClubObserver() {
        Club testClub2 = new Club("Test Club");
        ClubObserver clubObserver = new ClubObserver() {
            @Override
            public void update() {
                System.out.println("ClubObserver has been informed.");
            }
        };
        testClub2.registerClubObserver(clubObserver);
        List<ClubObserver> e = testClub2.returnClubObservers();
        assertEquals(clubObserver, e.get(0));
    }
}
