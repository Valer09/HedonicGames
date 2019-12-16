package Graphs;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class DirectedWeightedGraph <Integer, Edge> extends SimpleDirectedWeightedGraph {
    /**
     * Creates a new graph.
     * @param edgeClass class on which to base the edge supplier
     */
    public DirectedWeightedGraph(Class edgeClass) {
        super(edgeClass);
    }
}
