
//import com.google.common.graph.Graphs;
import Generators.RandomDirectedGenerator;
import Structures.Edge;
import Testing.DrawTest;
import org.jgrapht.graph.*;
import org.jgrapht.*;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

/*        System.out.println("Hello Java");

        Graph<Integer, DefaultWeightedEdge> mygraph= new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

        mygraph.addVertex(1);
        mygraph.addVertex(2);
        mygraph.addEdge(1,2,new DefaultWeightedEdge());
        mygraph.addEdge(2,1,new DefaultWeightedEdge());
        mygraph.setEdgeWeight(1,2,100);
        mygraph.setEdgeWeight(2,1,130);


        Set<Integer> vertex= mygraph.vertexSet();
        Set <DefaultWeightedEdge> edges = mygraph.edgeSet();

        System.out.println("NODI: "+vertex);
        System.out.println("ARCHI: "+edges);
        System.out.println("Tipo di grafo diretto: "+mygraph.getType().isDirected());
        System.out.println("peso: "+mygraph.getEdgeWeight(mygraph.getEdge(1,2)));
        System.out.println("peso: "+mygraph.getEdgeWeight(mygraph.getEdge(2,1)));
        System.out.println("");


        Graph<Integer, DefaultWeightedEdge> mygraph2= new SimpleWeightedGraph(DefaultWeightedEdge.class);
        mygraph2.addVertex(1);
        mygraph2.addVertex(2);
        mygraph2.addVertex(3);
        mygraph2.addVertex(4);
        mygraph2.addEdge(1,2, new DefaultWeightedEdge());
        mygraph2.addEdge(2,1, new DefaultWeightedEdge());
        mygraph2.addEdge(2,3, new DefaultWeightedEdge());
        mygraph2.addEdge(3,2, new DefaultWeightedEdge());
        mygraph2.addEdge(3,4, new DefaultWeightedEdge());

        mygraph2.setEdgeWeight(1,2,100);
        mygraph2.setEdgeWeight(2,1,130);
        mygraph2.setEdgeWeight(2,3,10);
        mygraph2.setEdgeWeight(3,2,1);

        Set<Integer> vertex2= mygraph2.vertexSet();
        Set <DefaultWeightedEdge> edges2 = mygraph2.edgeSet();

        System.out.println("ARCHI: "+vertex2);
        System.out.println("NODI: "+edges2);
        System.out.println("Il grafo è diretto: "+mygraph2.getType().isDirected());
        System.out.println("peso: "+mygraph2.getEdgeWeight(mygraph2.getEdge(1,2)));
        System.out.println("peso: "+mygraph2.getEdgeWeight(mygraph2.getEdge(2,1)));

        System.out.println("peso: "+mygraph2.getEdgeWeight(mygraph2.getEdge(2,3)));
        System.out.println("peso: "+mygraph2.getEdgeWeight(mygraph2.getEdge(3,2)));
        System.out.println("peso: "+mygraph2.getEdgeWeight(mygraph2.getEdge(3,4)));

        System.out.println("");
        System.out.println("-----TESTING WITH MY CLASSES");
        System.out.println("");
        mygraph.addVertex(10);
        mygraph.addVertex(11);
        mygraph.addEdge(10,11, new Structures.Edge());
        System.out.println(  (mygraph.getEdge(10,11)).toString()     );
        System.out.println(mygraph.degreeOf(1));
        System.out.println(mygraph.outDegreeOf(1));
        System.out.println(mygraph.inDegreeOf(1));*/

        //---------------------------------------------------------
        //----------------------------------------------------------

        RandomDirectedGenerator rdmgn= new RandomDirectedGenerator(5,15);
        Graph <Integer, Edge> generatedgraph = rdmgn.generateGraph();
        DrawTest.setGraph(generatedgraph);
        DrawTest.main(null);






    }
}

