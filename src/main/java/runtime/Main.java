package main.java.runtime;
import java.awt.EventQueue;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import main.java.club.Athlete;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.gui.Application;
import main.java.gui.Login;
import main.java.observer.TableManager;

import org.xml.sax.SAXException;

/**
 * The Main class starts the application.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Main {

    /** Stores an instance of the RuntimeManager. */
    private static RuntimeManager runtimeManager;

    /** Stores the thread sleeptime. */
    private static final int SLEEP_TIME = 500;

    /**
     * Start the application.
     * @param args Input parameters
     * @throws ParserConfigurationException TBD
     * @throws SAXException TBD
     * @throws IOException TBD
     */
    public static void main(final String[] args) throws
    ParserConfigurationException, SAXException, IOException {

        runtimeManager = RuntimeManager.getRuntimeManagerInstance();
        Club club = new Club("myclub");

        runtimeManager.setClub(club);

//        XMLManager xman = new XMLManager(runtimeManager, "clubmembers");
        TableManager tman = new TableManager();

//        if (xman != null) {
//            System.out.println("XMLManager established.");
//            club.registerClubObserver(xman);
//        }

        if (tman != null) {
            System.out.println("Tablemanager established.");
            club.registerClubObserver(tman);
        }

//        xman.update();

        ClubMember dummy = new Athlete(333);
        dummy.setUsername("dummy");
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
