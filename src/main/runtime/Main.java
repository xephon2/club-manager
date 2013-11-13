package main.runtime;
import java.awt.EventQueue;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import main.club.Athlete;
import main.club.Club;
import main.club.ClubMember;
import main.club.President;
import main.gui.Application;
import main.gui.Login;
import main.observer.TableManager;
import main.observer.XMLManager;

import org.xml.sax.SAXException;

/**
 * The Main class starts the application.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Main {

    /** Stores an instance of the RuntimeManager. */
    private static RuntimeManager runtimeManager
    = RuntimeManager.getRuntimeManagerInstance();

    /** Stores an instance of the Login window. */
    private static Login login;

    /**
     * Start the application.
     * @param args Input parameters
     * @throws ParserConfigurationException TBD
     * @throws SAXException TBD
     * @throws IOException TBD
     */
    public static void main(final String[] args) throws
    ParserConfigurationException, SAXException, IOException {

        Club club = new Club("komischerhaufen");

        if (runtimeManager != null) {
            System.out.println("RuntimeManager established.");
        } else {
            System.out.println("RuntimeManager cannot be established.");
        }

        runtimeManager.setClub(club);
        runtimeManager.addPasswordManager(new PasswordManager(runtimeManager));

        XMLManager xman = new XMLManager(runtimeManager, "clubmembers");
        TableManager tman = new TableManager();

        if (xman != null) {
            System.out.println("XMLManager established.");
            club.registerClubObserver(xman);
        }

        if (tman != null) {
            System.out.println("Tablemanager established.");
            club.registerClubObserver(tman);
        }

        xman.update();

        ClubMember c1 = new Athlete(123);
        ClubMember c2 = new President(234);

        c1.setFirstName("Karin");
        c1.setLastName("Hof");
        c1.setUsername("karin");
        c1.setClubMemberId(123);
        c1.hashAndStorePassword("passwort");

        c2.setFirstName("Stefan");
        c2.setLastName("Eike");
        c2.setUsername("stefan");
        c2.setClubMemberId(234);
        c2.hashAndStorePassword("passwort");

        club.addClubMember(c1);
        club.addClubMember(c2);


        /**
         * Launch the application.
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Login(runtimeManager).getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Ask every 500ms if someone is logged in.
        while (runtimeManager.getLoggedInUser() == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Application app = new Application(runtimeManager);
                    app.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
