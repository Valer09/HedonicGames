package Structures;

public class Agent {
    private Integer i;
    private double utility;

    public Agent (int i){
        this.i=i;
    }

    /**
     * getID
     * @return ID of agent
     */
    public Integer getID(){
        return i;
    }

    /**
     * getUtility
     * @return utility of agent
     */
    public double getUtility(){
        return utility;
    }

    /**
     * Set utility of agent
     * @param u - <b>double</b> - utility calculated upstream
     */
    public void setUtility(double u){
        this.utility=u;
    }
}
