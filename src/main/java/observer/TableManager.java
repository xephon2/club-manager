package main.java.observer;

/**
 * The TableManager updates the table of the application,
 * when the Club object has called notify(). This happens,
 * when a new club member is added to the clubMember list or
 * when a club member is removed from the clubMember list or
 * if the properties of a club member have changed.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class TableManager implements ClubObserver {

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }
}
