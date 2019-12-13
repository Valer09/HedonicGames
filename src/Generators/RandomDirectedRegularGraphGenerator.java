package Generators;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;
import Random.RandomInt;
import java.util.*;

public class RandomDirectedRegularGraphGenerator {
   private HashMap adjMap =null;
   private int nv,d;
   private boolean weighted,directed,even;
   private Graph graph;


   public RandomDirectedRegularGraphGenerator(int nv, int d, boolean weighted, boolean directed){
       this.nv=nv;
       this.d=d;
       this.weighted=weighted;
       this.directed=directed;
       even= nv % 2 == 0;
       if (weighted && directed)
           graph= new <Integer, DefaultWeightedEdge> SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);
       else if(weighted)
           graph= new <Integer, DefaultWeightedEdge>SimpleWeightedGraph(DefaultWeightedEdge.class);
       else
           graph= new <Integer, DefaultEdge>SimpleGraph(DefaultWeightedEdge.class);
   }

   public Graph <Integer, DefaultWeightedEdge> generateGraph() {
       //rdm will be the vertex with minimun outDegree id nv is not even
       int rdm=0;
       Random randomGenerator = new Random();
       rdm = randomGenerator.nextInt(nv);

       for (int i=0; i<=nv; i++){
           graph.addVertex(i);
       }
       System.out.println(graph.vertexSet());
       Iterator iterator = new DepthFirstIterator (graph);
       List exclusions=new ArrayList();
       //Random creation of edges
       while (iterator.hasNext()){
           Integer vertex= (Integer) iterator.next();
           //if the random vertex isn't rdm->do
           if(!(vertex).equals(rdm)){
               //assigns edges until outdegree is d
               exclusions.add(vertex);
               Integer target=null;
               while (!(graph.outDegreeOf(vertex) == d)) {
                   target = RandomInt.randomIntWithExclusion(0, nv, exclusions);
                   //if degree of terget is d, choise another vertex
                   if (graph.inDegreeOf(target) == d) {
                       exclusions.add(target);
                       continue;
                   }
                   System.out.println("Structures.Vertex: "+vertex+" Target: "+target);
                   graph.addEdge(vertex, target);
                   exclusions.add(target);
               }
               }
           else break;
           exclusions.remove(vertex);
           }

       iterator = new DepthFirstIterator (graph);
       Integer target=null;
       exclusions.add(rdm);
       while (iterator.hasNext()){
           while (!(graph.outDegreeOf(rdm) == d)){
               target= RandomInt.randomIntWithExclusion(  0,nv,exclusions);
               //if degree of terget is d, choise another vertex
               if (graph.inDegreeOf(target)==d) {
                   exclusions.add(target);
                   continue;
               }
               graph.addEdge(rdm,target);

           }

       }
       return graph;
   }
}

