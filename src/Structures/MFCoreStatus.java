package Structures;

import Interfaces.Status;

public class MFCoreStatus implements Status {
    private MFCoalitionStructure coalitionStructure;
    Boolean coreStable;

    /**
     * Return Coalition Structure
     *
     * @return - <b>CoalitionStructure</b>
     */
    @Override
    public MFCoalitionStructure getCoalitionStructure() {
        return null;
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
