package Testing;

import Generators.FileGenerator;
import Generators.GraphDrawer;
import Generators.RandomDirectedGraphGenerator;
import Generators.WeightGenerator;
import Random.RandomInt;
import Structures.*;
import org.jgrapht.Graph;
import org.jgrapht.io.GraphMLExporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
//import com.google.common.graph.Graphs;

public class TestingMain {
    static int n, n_relazioni, n_istanze;
    static RandomDirectedGraphGenerator relationsGraphGenerator;
    static Graph<Integer, Edge> relationsGraph;
    static Agent[] agents;
    //static Agent[][] coalitions;
    static ArrayList<Agent> [] coalitions;
    static Agent[][] startingCoalitions;
    static HashMap<Integer, Integer> partition;
    static HashMap<Integer, Integer> startingPartition;
    static MFCoreStatus status;
    static MFCoreStatus initialStatus;
    static WeightGenerator wg;
    static Set<Integer> vertexSet;

    //---------------------------------------------------------
    //---------------------------------------------------------

    public static void main(String[] args) {

        System.out.println("Inserisci il numero di agenti: ");
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        System.out.println("Inserisci il numero di relazioni: ");
        n_relazioni = scan.nextInt();
        System.out.println("Inserisci il numero di istanze da provare: ");
        n_istanze = scan.nextInt();


        initGraph();
        initStructures();
        //showGraph();
        System.out.println("Ciao");
        //findCoreEquilibrium();

    }
    
    public static void initGraph() {
        //creazione grafi
        relationsGraphGenerator = new RandomDirectedGraphGenerator(n, n_relazioni);
        relationsGraph = relationsGraphGenerator.generateGraph();
        wg = new WeightGenerator(relationsGraph);
        wg.generateWeights();
        //GraphDrawer.setGraph(relationsGraph);
        //FileGenerator.graphFileGenerator(relationsGraph);
        //GraphDrawer.main(null);
        vertexSet = relationsGraph.vertexSet();

    }

    static void initStructures() {
        //inizializzazione agenti, coalizioni e partizioni
        agents = new Agent[n + 1];
        agents[0] = null;
        //coalitions = new Agent[n + 1][n + 1];
        coalitions = new ArrayList [n+1];
        for (int i=0; i<=n; i++){
            coalitions[i]= new ArrayList<Agent>();
        }
        startingCoalitions = new Agent[n + 1][n + 1];
        //coalitions[0][0] = null;
        //coalitions[0].add(null);
        startingCoalitions[0][0] = null;
        partition = new HashMap<Integer, Integer>();
        startingPartition = new HashMap<Integer, Integer>();

        //costruisco lista di agenti e di coalizioni di uguale cardinalità
        for (Integer v : vertexSet) {
            agents[v] = new Agent(v);
        }
        initSetting();
    }

    static void initSetting() {
        //costruisco partitione e coalizioni iniziali
        for (Agent a : agents) {
            if (a == null)
                continue;
            int random = RandomInt.randomIntWithExclusion(0, (n / 2) + 1, 0);
            //a.setUtility(calculateUtility(a.getID()));
            partition.put(a.getID(), random);
            Agent x = new Agent(a.getID());
            startingPartition.put(x.getID(), random);
            //coalitions[random][a.getID()] = a;
            coalitions[random].add(a);
            startingCoalitions[random][x.getID()] = x;
        }
        for (Agent a : agents) {
            if (a == null)
                continue;
            a.setUtility(calculateUtility(a.getID()));
            startingCoalitions[startingPartition.get(a.getID())][a.getID()].setUtility(calculateUtility(a.getID()));
        }

        //inizializzo gli stati
        initialStatus = new MFCoreStatus(startingPartition);
        status = new MFCoreStatus(partition);
    }

    static double calculateUtility(int i) {
        double sum = 0;
        int coalition_cardinality = 1;
        int j = partition.get(i);
        //Agent[] coalition = coalitions[j];
        ArrayList<Agent> coalition = coalitions[j];
        for (Agent a : coalition) {
            if (a == null || a.getID() == i)
                continue;
            coalition_cardinality++;
            if (relationsGraph.containsEdge(i, a.getID())) {
                Edge e = relationsGraph.getEdge(i, a.getID());
                sum += relationsGraph.getEdgeWeight(e);

            }
        }
        if (coalition_cardinality - 1 < 1) {
            sum = 0;
            return sum;
        } else {
            sum = (sum) / ((coalition_cardinality - 1));
            return sum;
        }
    }

    static void refreshUtility() {
        for (Agent a : agents) {
            if (a == null)
                continue;
            a.setUtility(calculateUtility(a.getID()));
        }
    }
    public static void showGraph () {
            GraphDrawer.setGraph(relationsGraph);
            FileGenerator.graphFileGenerator(relationsGraph);
            GraphDrawer.main(null);
        }
    public static void deviation (ArrayList < Agent > T) {
        int targetCoalition=0;
        for (int i=1; i<coalitions.length; i++){
            //trovo il primo indice di una colazione vuota in cui spostare gli agenti
            if (coalitions[i].isEmpty() || coalitions[i]==null){
                targetCoalition=i;
                break;
            }
        }
        for (Agent t : T){
            int startingCoalition = partition.get(t.getID());
            coalitions[startingCoalition].remove(t);
            coalitions[targetCoalition].add(t);
            partition.replace(t.getID(),targetCoalition);
        }
        refreshUtility();

        }

    }
