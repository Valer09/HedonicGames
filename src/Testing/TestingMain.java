package Testing;

import Generators.FileGenerator;
import Generators.GraphDrawer;
import Generators.RandomDirectedGraphGenerator;
import Generators.WeightGenerator;
import Graphs.DirectedWeightedGraph;
import Random.RandomInt;
import Structures.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
//import com.google.common.graph.Graphs;

public class TestingMain {
    static int n, n_relazioni, n_istanze, q,n_grafi;
    static double CONST_T;
    static RandomDirectedGraphGenerator relationsGraphGenerator;
    static Graph<Integer, Edge> relationsGraph;
    static Agent[] agents;
    static ArrayList<Agent> [] coalitions;
    static ArrayList<Agent>[] startingCoalitions;
    static HashMap<Integer, Integer> partition;
    static HashMap<Integer, Integer> startingPartition;
    static MFCoreStatus status;
    static MFCoreStatus initialStatus;
    static WeightGenerator wg;
    static Set<Integer> vertexSet;
    static ArrayList <Agent> newCoalition;
    static int deviations=0;
    static long deviation_avarage=0L;
    static boolean found=false;
    static boolean existsdeviation=true;
    static boolean coreStable=false;


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
        System.out.println("Inserisci il numero q per il calcolo q-stablity: ");
        q=scan.nextInt();
        System.out.println("Inserisci il numero grafi su cui provare: ");
        n_grafi=scan.nextInt();
        System.out.println("Inserisci il numero di minuti massimo per trovare una possibile deviazione per ogni stato: ");
        CONST_T=scan.nextDouble();

        if (q==2){

        }
        else{

        }

        for (int j=1; j<=n_grafi; j++){
            System.out.println("CREAZIONE GRAFO NUMERO "+j);
        //call to methods for initializes Graph and structures
            initGraph();
            FileGenerator.graphFileGenerator(relationsGraph);
            File jsonFile=FileGenerator.jsonFilegenerator();
            File txtFile= FileGenerator.txtFileGenerator();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(jsonFile, true));
                writer.append('[');
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        //show relationsGraphs: if you need uncomment below
        //showGraph();
            for (int i=1; i<= n_istanze; i++){
                System.out.println("ESECUZIONE DELL'ISTANZA N. "+i+" PER IL GRAFO NUMERO. "+j);
                clear();
                initStructures();
                //run algorithm to search equilibrium
                    coreStable=calculateQStability(q);


                if(coreStable){
                    System.out.println("Trovato Equilibrio dopo "+deviations+" deviazioni");
                }
                else {
                    System.out.println("Eseguite " + deviations + " deviazioni");
                }

                try {
                    writer = new BufferedWriter(new FileWriter(jsonFile, true));
                    writer.append(FileGenerator.generateJsonFromStatus(initialStatus,status,i));
                    writer.append(',');
                    writer.close();
                    writer = new BufferedWriter(new FileWriter(txtFile, true));
                    writer.write("\n\n****************ISTANZA N. "+i+"***************\n"+"\n----STATO INIZIALE----\n"
                            +initialStatus.toString()
                            +"\n----ULTIMO STATO----\n"
                            +status.toString());
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                writer = new BufferedWriter(new FileWriter(jsonFile, true));
                writer.append("{\"END OF FILE:\" : true}" );
                writer.append(']');
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
        System.out.println("MEDIA DELLE DEVIAZIONI: "+((int)(deviation_avarage))/(n_istanze*n_grafi));
    }

    static void clear(){
        coalitions=null;
        startingCoalitions=null;
        partition=null;
        startingPartition=null;
        status=null;
        initialStatus=null;
        newCoalition=null;
        deviations=0;
        found=false;
        existsdeviation=true;
        coreStable=false;

    }

    static boolean calculateQStability(int q){
        List <Integer> agentlist = new ArrayList<>();
        for(Agent a : agents){
            if (a==null)
                continue;
            agentlist.add(a.getID());
        }

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while(existsdeviation){
            if (elapsedTime < CONST_T*60*1000){
                existsdeviation=false;
                newCoalition =null;
                if (q==2)
                    get2subset(agentlist);
                else
                    if (q>3)
                        get3subset(agentlist);
                    else
                        getSubsets(agentlist, q);
                if(existsdeviation){
                    deviation(newCoalition);
                    deviations++;
                    deviation_avarage=deviation_avarage+deviations;
                }
                else {
                    status.setCoreStable(true);
                    return true;
                }
                elapsedTime = (new Date()).getTime() - startTime;
            }else{
                System.out.println("Equilibrio non trovato nel tempo utile");
                return false;
            }

        }
        return true;
    }

    private static void get3subset (List <Integer> superSet){
        boolean found= true;
        Integer [] set = new Integer [n+1];
        set[0]=null;
        ArrayList<Agent> totest= new ArrayList<Agent>();
        for (Integer i : superSet)
            set[i]=i;
        for (int i=1; i<=set.length-2 && (!existsdeviation); i++){
            found=true;
            for(int j=i+1; j<=set.length-1 && (!existsdeviation); j++){
                for (int k=j+1; k<set.length && (!existsdeviation); k++)
                {
                    totest.clear();
                    found=true;
                    totest.add(new Agent(i));
                    totest.add(new Agent(j));
                    totest.add(new Agent(k));
                    for (Agent a : totest) {
                        a.setUtility(calculateUtility(a.getID(),totest));
                        if (a.getUtility() <= agents[a.getID()].getUtility() ) {
                            found=false;
                            totest.clear();
                            break;
                        }
                    }
                    if(found){
                        newCoalition = totest;
                        existsdeviation=true;
                    }
                }

            }
        }

    }


    private static void get2subset (List <Integer> superSet){
        boolean found= true;
        Integer [] set = new Integer [n+1];
        set[0]=null;
        ArrayList<Agent> totest= new ArrayList<Agent>();
        for (Integer i : superSet)
            set[i]=i;
        for (int i=1; i<=set.length-1 && (!existsdeviation); i++){
            found=true;
            for(int j=i+1; j<=set.length && (!existsdeviation); j++){
                found=true;
                totest.add(new Agent(i));
                totest.add(new Agent(j));
                for (Agent a : totest) {
                    a.setUtility(calculateUtility(a.getID(),totest));
                    if (a.getUtility() <= agents[a.getID()].getUtility() ) {
                        found=false;
                        totest.clear();
                        break;
                    }
                }
                if(found){
                    newCoalition = totest;
                    existsdeviation=true;
                }

            }
        }

    }

    private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current,List<Set<Integer>> solution) {
        if (existsdeviation)
            return;
        //successful stop clause
        if (!found){
        if (current.size() == k) {
            ArrayList<Agent> totest = new ArrayList<Agent>();
            found = true;
            for (Integer a : current)
                totest.add(new Agent(a));
            for (Agent a : totest) {
                a.setUtility(calculateUtility(a.getID(),totest));
                if (a.getUtility() <= agents[a.getID()].getUtility()) {
                    found = false;
                    break;
                }

            }
            if (found) {
                newCoalition = totest;
                existsdeviation=true;
                found=false;
            }
           return;
        }
        }
        //unseccessful stop clause
        if (idx == superSet.size()) return;
        Integer x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        getSubsets(superSet, k, idx+1, current, solution);
        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx+1, current, solution);
    }

    public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
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
        startingCoalitions = new ArrayList [n+1];

        //coalitions initialize
        for (int i=0; i<=n; i++){
            coalitions[i]= new ArrayList<Agent>();
            startingCoalitions[i]= new ArrayList<Agent>();
        }

        //startingCoalitions initializing and setting to null in (0,0) index; initilizing partition and startingPartition
        startingCoalitions[0].add(0,null);
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
            startingCoalitions[random].add(x);
        }
        //Setting the utility of agents in agents
        for (Agent a : agents) {
            if (a == null)
                continue;
            a.setUtility(calculateUtility(a.getID()));
        }
        //setting utility of agents in StartingCoalitions
        for (ArrayList <Agent> coal : startingCoalitions)
            for (Agent a : coal){
                if(a==null)
                    continue;
                a.setUtility(calculateUtility(a.getID()));
            }

        //Creation of state of the game
        Agent[] start = new Agent[n+1];
        for (int i=1; i<agents.length ;i++ ){
            start[i]=new Agent(i);
            start[i].setUtility(agents[i].getUtility());
        }
        initialStatus = new MFCoreStatus(startingPartition);
        initialStatus.set(false,startingPartition,startingCoalitions,start);
        status = new MFCoreStatus(partition);
        status.set(false,partition,coalitions,agents);
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
     * Calculate Utility of an agent i respect to given coalition to testing
     * @param i ID Agent
     * @param newCoalition - Coalition to test
     * @return double - utility
     */
    static double calculateUtility(int i, ArrayList<Agent> newCoalition) {
        double sum = 0;
        int coalition_cardinality = 1;
        int j = partition.get(i);
        for (Agent a : newCoalition) {
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
        for (ArrayList<Agent> coal : coalitions)
            for (Agent a : coal)
                a.setUtility(calculateUtility(a.getID()));
    }

    /**
     * It will create a reletionsGraph visualizazion through GraphDrawer class.
     * In this way you have one more tool to test your instances
     */
    public static void showGraph () {
            GraphDrawer.setGraph(relationsGraph);
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
            for (int i=0; i< coalitions[startingCoalition].size(); i++)
                if (coalitions[startingCoalition].get(i).getID()==t.getID())
                    coalitions[startingCoalition].remove(i);

            coalitions[targetCoalition].add(t);
            partition.replace(t.getID(),targetCoalition);
        }
        refreshUtility();
        }
    }
