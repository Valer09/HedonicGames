package Structures;

public class Agent {
    private Integer i;
    private double utility;

    public Agent (int i){
        this.i=i;
    }

    public Integer getID(){
        return i;
    }

    public double getUtility(){
        return utility;
    }
    public void setUtility(double u){
        this.utility=u;
    }
}
