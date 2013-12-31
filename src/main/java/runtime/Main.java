package main.java.runtime;

import java.awt.EventQueue;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.java.club.Athlete;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.gui.Application;
import main.java.gui.Login;
import main.java.observer.TableManager;
import main.java.observer.XMLManager;

/**
 * The Main class starts the application.
 *
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Main {

    /** Stores an instance of the RuntimeManager. */
    private static RuntimeManager runtimeManager;

    /** Stores the thread sleeptime. */
    private static final int SLEEP_TIME = 500;

    /**
     * Start the application.
     *
     * @param args Input parameters
     * @throws ParserConfigurationException TBD
     * @throws SAXException TBD
     * @throws IOException TBD
     */
    public static void main(final String[] args) throws
            ParserConfigurationException,
            SAXException,
            IOException {

        Club club = new Club("myclub");
        runtimeManager = new RuntimeManager(club);

        XMLManager xman = new XMLManager(runtimeManager, "clubmembers");
        // TableManager tman = new TableManager();

        /*
         * FIXME This does not work.
         * Either create a runtime manager
         * WITH a club or load a club from XML
         * WITHOUT having a RuntimeManager.
         */
        // Club club = xman.loadXMLClub();
        // runtimeManager.setClub(club);

        if (xman != null) {
            System.out.println("XMLManager established.");
            club.registerClubObserver(xman);
            xman.update();
        }

//        if (tman != null) {
//            System.out.println("Tablemanager established.");
//            club.registerClubObserver(tman);
//            tman.update();
//        }

        ClubMember dummy = new Athlete(333);
        dummy.setUsername("dummy");
        dummy.setFirstName("dummy");
        dummy.setLastName("dummy");
        dummy.hashAndStorePassword("dummy");
        club.addClubMember(dummy);

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
                Thread.sleep(SLEEP_TIME);
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
