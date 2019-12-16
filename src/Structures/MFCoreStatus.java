package Structures;

import Interfaces.Status;

import java.util.HashMap;

/**
 * the status of game is represented from a pair of information:
 * - <b>coalition structure</b> which is a partition of agents in n coalitions.
 *    It is represented with an HashMap that maps every agent i=1...n to coalition j=1...n
 * - <b>equilibrium</b> that indicates whether the game is core stable
 *    It is represented with coreStable boolean value
 */
public class MFCoreStatus implements Status {
    private HashMap<Integer, Integer> partition;
    private Boolean coreStable;

    /**
     * Setting Status
     * @param partition HashMap
     */
    public MFCoreStatus(HashMap<Integer, Integer> partition){
        this.partition=partition;
    }

    /**
     * Return Coalition Structure
     *
     * @return - <b>CoalitionStructure</b>
     */
    @Override
    public HashMap<Integer, Integer> getCoalitionStructure() {
        return partition;
    }

    /**
     * Return Status Obj
     *
     * @return <b>T</b>
     */
    @Override
    public MFCoreStatus getStatus() {
        return null;
    }

    /**
     * Return true if the status of the game is stable
     *
     * @return - <b>boolean</b>
     */
    @Override
    public Boolean isStable() {
        return false;
    }
}
