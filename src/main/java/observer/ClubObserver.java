package main.java.observer;

/**
 * The ClubObserver interfaces defines the behavior of
 * all objects that observe a Club object.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public interface ClubObserver {

    /** Update the observer used by the subject. */
    void update();
}
