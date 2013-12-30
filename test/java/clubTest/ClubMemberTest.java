package test.java.clubTest;

import main.java.club.ClubMember;

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
