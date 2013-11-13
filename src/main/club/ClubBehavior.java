package main.club;

import main.observer.ClubObserver;

/**
 * The ClubBehavior interfaces defines the behavior of a Club object
 * to be observed.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public interface ClubBehavior {

    /**
     * Register a new observer to the club.
     * @param clubObserver club observer
     */
    void registerClubObserver(ClubObserver clubObserver);

    /**
     * Unregister an observer from the club.
     * @param clubObserver club observer
     */
    void unregisterClubObserver(ClubObserver clubObserver);

    /** Send a message to all observers. */
    void notifyObservers();
}
