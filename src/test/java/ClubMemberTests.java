package test.java;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.clubTest.ClubMemberTest;
import test.java.clubTest.ClubTest;
import test.java.observerTest.XMLManagerTest;

/**
 * Run all Club Manager JUnit4 Tests.
 *
 * @author Stefan Eike (s.eike85@gmail.com)
 */
@RunWith(Suite.class)
@SuiteClasses({
    ClubTest.class,
    ClubMemberTest.class,
    XMLManagerTest.class
    })
public class ClubMemberTests {
}
