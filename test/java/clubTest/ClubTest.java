package test.java.clubTest;

import main.java.club.Club;
import main.java.observer.ClubObserver;
import main.java.observer.XMLManager;
import main.java.runtime.RuntimeManager;

/**
 * Test the Club class.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
@RunWith(JUnit4.class)
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
	 * Test the club observer. Create a new club, add an observer and add a new
	 * club member to the club.
	 */
	@Test
	public final void testClubObserver() {

		Club testClub = new Club("Test Club");
		RuntimeManager runtimeManger = new RuntimeManager();
		runtimeManger.setClub(testClub);
		XMLManager xmlManager = new XMLManager(runtimeManger, "clubmembers");

		testClub.registerClubObserver(xmlManager);
		List<ClubObserver> clubObservers = testClub.getObservers();
		assertEquals(xmlManager, clubObservers.get(0));
	}
}
