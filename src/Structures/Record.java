package Structures;

public class Record{
    private int nodi,archi,q;
    private double time;
    private boolean random, core;

    public Record( int archi, int q, double time, boolean random, boolean core) {
        this.archi = archi;
        this.q = q;
        this.time = time;
        this.random = random;
        this.core = core;
    }

    public String toString(){
        return "Q: "+this.q+", time: "+this.time+", random: "+ this.random + ", CORE: "+ this.core+"\n";
    }
}