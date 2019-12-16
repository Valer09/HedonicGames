package Testing;

import Generators.FileGenerator;
import Generators.GraphDrawer;
import Generators.RandomDirectedGraphGenerator;
import Generators.WeightGenerator;
import Random.RandomInt;
import Structures.*;
import org.jgrapht.Graph;

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

        //User input to choose number of agents, relations and instances number
        System.out.println("Inserisci il numero di agenti: ");
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        System.out.println("Inserisci il numero di relazioni: ");
        n_relazioni = scan.nextInt();
        System.out.println("Inserisci il numero di istanze da provare: ");
        n_istanze = scan.nextInt();

        //call to methods for initializes Graph and structures
        initGraph();
        initStructures();

        //show relationsGraphs: if you need uncomment below
        //showGraph();

        //run algorithm to search equilibrium
        //findCoreEquilibrium();

    }

    public static void initGraph() {
        //Graph creation
        relationsGraphGenerator = new RandomDirectedGraphGenerator(n, n_relazioni);
        relationsGraph = relationsGraphGenerator.generateGraph();
        wg = new WeightGenerator(relationsGraph);
        wg.generateWeights(0,100);
        vertexSet = relationsGraph.vertexSet();

    }

    /**
     * It initializes all structures
     */
    static void initStructures() {
        //initialize agents, coalitions and partizions
        agents = new Agent[n + 1];
        agents[0] = null;
        coalitions = new ArrayList [n+1];

        //coalitions initialize
        for (int i=0; i<=n; i++){
            coalitions[i]= new ArrayList<Agent>();
        }

        //startingCoalitions initializing and setting to null in (0,0) index; initilizing partition and startingPartition
        startingCoalitions = new Agent[n + 1][n + 1];
        startingCoalitions[0][0] = null;
        partition = new HashMap<Integer, Integer>();
        startingPartition = new HashMap<Integer, Integer>();

        //Building of the list of agents with the same number of nodes of relationsGraph
        for (Integer v : vertexSet) {
            agents[v] = new Agent(v);
        }
        initSetting();
    }

    /**
     * It creates the random setting of structures
     */
    static void initSetting() {
        //Random filling of starting partition and coalitions
        for (Agent a : agents) {
            if (a == null)
                continue;
            /*
            In this implementation we would to avoid many agents alone in a starting coalition,
            so n/2 upper bound is a workaround to limit the number of coalitions used for initialization
            */
            int random = RandomInt.randomIntWithExclusion(0, (n / 2) + 1, 0);
            partition.put(a.getID(), random);

            /*creation of a copies (x) of agents into new memory location for starting partition.
            In this way it will not change when something will be changed in partition.
            (would be happened if just a pointer had been used)*/
            Agent x = new Agent(a.getID());
            startingPartition.put(x.getID(), random);
            coalitions[random].add(a);
            startingCoalitions[random][x.getID()] = x;
        }
        //Setting the utility of agents
        for (Agent a : agents) {
            if (a == null)
                continue;
            a.setUtility(calculateUtility(a.getID()));
            startingCoalitions  [startingPartition.get(a.getID())]
                                [a.getID()].setUtility(calculateUtility(a.getID()));
        }

        //Creation of state of the game
        initialStatus = new MFCoreStatus(startingPartition);
        status = new MFCoreStatus(partition);
    }

    /**
     * It calculates and return the utility un agent number i.
     * It is based on <b>Modified Fractional Hedonic Game</b> utility definition,
     * dividing sum to cardinality of coalition minus 1
     * <br></br>
     * In other variants of Fractional Hedonic Game you will need to divide sum to cardinality of coalition
     * @param i - <b>int</b> - agent
     * @return <b>dobule</b> - utility
     */
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

    /**
     * It refreshes utility of all agents
     */
    static void refreshUtility() {
        for (Agent a : agents) {
            if (a == null)
                continue;
            a.setUtility(calculateUtility(a.getID()));
        }
    }

    /**
     * It will create a reletionsGraph visualizazion through GraphDrawer class.
     * In this way you have one more tool to test your instances
     */
    public static void showGraph () {
            GraphDrawer.setGraph(relationsGraph);
            FileGenerator.graphFileGenerator(relationsGraph);
            GraphDrawer.main(null);
        }

    /**
     * It will perform a deviation of group of agent listed, to an empty coalition
     * @param T List of agents to deviate
     */
    public static void deviation (ArrayList < Agent > T) {
        int targetCoalition=0;
        for (int i=1; i<coalitions.length; i++){
            //Finding an index of empty coalition
            if (coalitions[i].isEmpty() || coalitions[i]==null){
                targetCoalition=i;
                break;
            }
        }

        //deletions from old coalition and insertion on new partiotion; updating of partion
        for (Agent t : T){
            int startingCoalition = partition.get(t.getID());
            coalitions[startingCoalition].remove(t);
            coalitions[targetCoalition].add(t);
            partition.replace(t.getID(),targetCoalition);
        }
        refreshUtility();
        }

    }
