package Generators;

import Graphs.DirectedWeightedGraph;
import Structures.Edge;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;
import Random.RandomInt;
import java.util.*;

/**
 * It creates a random directed graph based on number of nodes and number of edges passed in costructor
 * In this graph every node is an Integer and every edge is an instance of Edge class
 */
public class RandomDirectedGraphGenerator {
    private int nv,n_archi;
    private DirectedWeightedGraph<Integer, Edge> graph;

    /**
     * Constructor
     * @param nv - Number of nodes
     * @param n_archi - Number of edges
     */
    public RandomDirectedGraphGenerator(int nv, int n_archi){
        this.nv=nv;
        this.n_archi=n_archi;
            graph= new <Integer, Edge>DirectedWeightedGraph<Integer, Edge>(Edge.class);
    }

    /**
     * Generate Graph
     * @return Directed graph
     */
    public SimpleDirectedWeightedGraph<Integer, Edge> generateGraph() {
        Integer u;
        Integer v;
        List <Integer >disconnected=new <Integer> ArrayList<Integer>();
        List<Integer> exclusions=new ArrayList<Integer>();

        for (int i=1; i<=nv; i++){
            graph.addVertex(i);
            disconnected.add(i);
        }

        int i=0,j=0;
        while (i<n_archi && j < 100*(n_archi)) {
            j++;
            exclusions.clear();
            u= RandomInt.randomIntWithExclusion(1,nv,exclusions);
            exclusions.add(u);
            v= RandomInt.randomIntWithExclusion(1,nv,exclusions);

            if (graph.containsEdge(u,v))
                continue;
            else
                i++;
            graph.addEdge(u,v);
            disconnected.remove(u);
            disconnected.remove(v);
        }
        i=0;
        Iterator <Integer> iterator = new DepthFirstIterator<Integer, DefaultWeightedEdge>(graph);
        while( (!(disconnected.isEmpty())/* && i < 3*(n_archi) */) && iterator.hasNext() ){
            exclusions.clear();
            Integer d= iterator.next();
            exclusions.add(d);
            graph.addEdge(d, RandomInt.randomIntWithExclusion(1,nv,exclusions));
            exclusions.clear();
            i++;
        }
        i=0;
        return graph;
    }
}
