package Structures;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {
    private double w;

    public Edge(){
        super();
        w=super.getWeight();
    }
    public double getWeight(){
        return super.getWeight();
    }

    @Override
    public String toString() {
        String info= super.toString();
        return info+" weight: "+this.getWeight();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
