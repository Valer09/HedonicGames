package Generators;

import Graphs.DirectedWeightedGraph;
import Structures.Edge;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;
import Random.RandomInt;
import java.util.*;

public class RandomDirectedGenerator {
    private HashMap adjMap =null;
    private int nv,n_archi;
    private DirectedWeightedGraph<Integer, Edge> graph;


    public RandomDirectedGenerator(int nv, int n_archi){
        this.nv=nv;
        this.n_archi=n_archi;
            graph= new <Integer, Edge>DirectedWeightedGraph<Integer, Edge>(Edge.class);
    }

    public SimpleDirectedWeightedGraph<Integer, Edge> generateGraph() {
        Integer u; //randomGenerator.nextInt(nv);
        Integer v; //randomGenerator.nextInt(nv);
        List <Integer >disconnected=new <Integer> ArrayList<Integer>();
        List<Integer> exclusions=new ArrayList<Integer>();

        for (int i=1; i<=nv; i++){
            graph.addVertex(i);
            disconnected.add(i);
        }

        int i=0,j=0;
        while (i<n_archi || i < 3*(n_archi)) {
            i++;
            exclusions.clear();
            u= RandomInt.randomIntWithExclusion(1,nv,exclusions);
            exclusions.add(u);
            v= RandomInt.randomIntWithExclusion(1,nv,exclusions);

            if (graph.containsEdge(u,v))
                continue;
            graph.addEdge(u,v);
            disconnected.remove(u);
            disconnected.remove(v);

           // System.out.println(graph.vertexSet());
        }
        i=0;
        Iterator <Integer> iterator = new DepthFirstIterator<Integer, DefaultWeightedEdge>(graph);
        while( (!(disconnected.isEmpty()) || i < 3*(n_archi) ) && iterator.hasNext() ){
            exclusions.clear();
            Integer d= iterator.next();
            exclusions.add(d);
            graph.addEdge(d, RandomInt.randomIntWithExclusion(1,nv,exclusions));
            exclusions.clear();
            i++;
            //System.out.println(graph.toString());
        }
        i=0;
        //System.out.println(graph.vertexSet()+" FINE");
        return graph;
    }
}
