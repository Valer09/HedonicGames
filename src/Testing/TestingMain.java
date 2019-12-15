package Testing;

import Generators.FileGenerator;
import Generators.GraphDrawer;
import Generators.RandomDirectedGraphGenerator;
import Generators.WeightGenerator;
import Random.RandomInt;
import Structures.*;
import org.jgrapht.Graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
//import com.google.common.graph.Graphs;

public class TestingMain {
    static int n, n_relazioni;
    static RandomDirectedGraphGenerator relationsGraphGenerator;
    static Graph<Integer, Edge> relationsGraph;
    static Agent [] agents;
    static Agent [][] coalitions;
    static Agent [][] startingCoalitions;
    static HashMap<Integer, Integer> partition;
    static HashMap<Integer, Integer> startingPartition;
    static MFCoreStatus status;
    static MFCoreStatus initialStatus;
    static WeightGenerator wg;
    static Set<Integer> vertexSet;

    //---------------------------------------------------------
    //---------------------------------------------------------

    public static void main(String []args){

    System.out.println("Inserisci il numero di agenti: ");
    Scanner scan = new Scanner(System.in);
    n = scan.nextInt();
    System.out.println("Inserisci il numero di relazioni: ");
    n_relazioni=scan.nextInt();
    initGraph();

    //inizializzazione agenti, coalizioni e partizioni
    agents = new Agent [n+1];
    agents[0]=null;
    coalitions = new Agent [n+1][n+1];
    coalitions [0][0]=null;
    partition = new HashMap<Integer, Integer>();

    //costruisco lista di agenti e di coalizioni di uguale cardinalità
    for (Integer v : vertexSet){
        agents[v]=new Agent(v);
    }

    //costruisco partitione e colaizioni iniziali con assegnamento dell'utilità all'agente
    for (Agent a : agents ){
        int random= RandomInt.randomIntWithExclusion(0,n,0);
        a.setUtility(calculateUtility(a.getID()));
        partition.put(a.getID(),random);
        startingPartition.put(a.getID(),random);
        coalitions[a.getID()][random]=a;
        startingCoalitions[a.getID()][random]=a;
    }
    //inizializzo gli stati
    initialStatus=new MFCoreStatus(startingPartition);
    status=new MFCoreStatus(partition);

    }

    static double calculateUtility(int i){
        double sum=0;
        int coalition_cardinality=0;
        int j=partition.get(i);
        Agent[] coalition =coalitions[j];
        for (Agent a : coalition){
            if (relationsGraph.containsEdge(i,a.getID())){
                Edge e=relationsGraph.getEdge(i,a.getID());
                sum+=relationsGraph.getEdgeWeight(e);
                coalition_cardinality++;
            }
        }
        sum=(sum)/(coalition_cardinality-1);
        return sum;
    }

    public static void initGraph(){
        //creazione grafi
        relationsGraphGenerator= new RandomDirectedGraphGenerator(n,n_relazioni);
        relationsGraph = relationsGraphGenerator.generateGraph();
        wg = new WeightGenerator(relationsGraph);
        wg.generateWeights();
        //GraphDrawer.setGraph(relationsGraph);
        //FileGenerator.graphFileGenerator(relationsGraph);
        //GraphDrawer.main(null);
        vertexSet=relationsGraph.vertexSet();

    }
}
