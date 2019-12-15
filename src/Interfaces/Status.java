package Interfaces;

public interface Status<T> {

    /**
     * Return Coalition Structure
     * @return - <b>CoalitionStructure</b>
     */
    Object getCoalitionStructure();

    /**
     * Return a string info representing the status
     * @return - <b>String</b>
     */
    String toString();

    /**
     * Return Status Obj
     * @return <b>T</b>
     */
    T getStatus();

    /**
     * Return true if the status of the game is stable
     * @return - <b>boolean</b>
     */
    Boolean isStable();
}
