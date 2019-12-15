package Structures;

public class Vertex  {
    private Integer ID;
    private double utility;
    public Vertex(Integer i){
        this.ID=i;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }
    public double getUtility(){
        return utility;
    }

    public String toString(){
        return ID.toString();
    }

    public int getInt(){
        return ID;
    }
}
