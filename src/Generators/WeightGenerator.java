package Generators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import Random.RandomInt;

import java.util.Set;

/**
 * It will assign random weights to graph
 */
public class WeightGenerator {
    private Graph g;

    /**
     * @param g Graph
     */
    public WeightGenerator (Graph g){
        this.g=g;
    }


    public void generateWeights(int starting, int end){
        Set<DefaultWeightedEdge> edges= g.edgeSet();
        for ( DefaultWeightedEdge e : edges  ){
            g.setEdgeWeight(e, RandomInt.randomIntWithExclusion(starting,end,0));
        }
    }
}
