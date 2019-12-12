package Generators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import Random.RandomInt;
import java.util.Random;
import java.util.Set;

public class WeightGenerator {
    private boolean is_directed;
    private Graph g;
    public WeightGenerator (Graph g){
        this.g=g;
    }
    public void generateWeights(){
        Set<DefaultWeightedEdge> edges= g.edgeSet();
        for ( DefaultWeightedEdge e : edges  ){
            g.setEdgeWeight(e, RandomInt.randomIntWithExclusion(0,100,0));
        }
    }
}
