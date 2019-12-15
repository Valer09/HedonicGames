package Structures;

import Interfaces.Status;

import java.util.HashMap;

public class MFCoreStatus implements Status {
    private HashMap<Integer, Integer> partition;
    private Boolean coreStable;


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
