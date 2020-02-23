package App;//import com.google.common.graph.Graphs;

import Structures.Agent;

import Generators.*;
import Random.RandomInt;
import Structures.*;
import org.jgrapht.Graph;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//import com.google.common.graph.Graphs;

public class App {
    static int countertemp=0;
    static int n, n_max, n_relazioni, n_rel_max, n_istanze, start_q ,q, q_max, n_grafi, rel_increment,n_of_time_increment;
    static double CONST_T , time_increment, start_time;
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
    static boolean okInput=false;
    static boolean randomDeviation=false;
    static boolean dynamicMode=false;


    //---------------------------------------------------------
    //---------------------------------------------------------

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Seleziona modalità di esecuzione: \n" +
                "inserisci '1' per modalità variazione nodi,archi,k,tempo e mosse\n" +
                "iserisci '2' per modalità variazione grafi e setting");
        if(scan.nextInt() == 1)
            dynamicMode=true;
        else
            dynamicMode=false;

        if (!dynamicMode) {

            //User input to choose number of agents, relations and instances number
            System.out.println("Inserisci il numero di agenti: ");
            n = scan.nextInt();
            while (!okInput) {
                System.out.println("Inserisci il numero di relazioni: ");
                n_relazioni = scan.nextInt();
                if (n_relazioni > n * (n - 1))
                    System.out.println("Attenzione, il numero di relazione deve essere al massimo n*n-1, con n=numero di agenti.\nInserisci nuovamente ");
                else
                    okInput = true;
            }
            okInput = false;
            while (!okInput) {
                System.out.println("Inserisci il numero q per il calcolo q-stablity: ");
                q = scan.nextInt();
                if (q > n - 1)
                    System.out.println("Attenzione, il numero q deve avere una cardinalità massima di n-1;");
                else
                    okInput = true;
            }


            System.out.println("Inserisci il numero grafi su cui provare: ");
            n_grafi = scan.nextInt();
            System.out.println("Inserisci il numero di istanze da provare: ");
            n_istanze = scan.nextInt();
            System.out.println("Inserisci il numero di minuti massimo per trovare una possibile deviazione per ogni stato: ");
            CONST_T = scan.nextDouble();
            System.out.println("Vuoi eseguire il programma in maniera da cercare sottoinsiemi di agenti da deviare di dimensione randomica?\n Se si, inserisci 1: ");
            if (scan.nextInt() == 1)
                randomDeviation = true;
            for (int j = 1; j <= n_grafi; j++) {
                System.out.println("CREAZIONE GRAFO NUMERO " + j);
                //call to methods for initializes Graph and structures
                initGraph();
                FileGenerator.graphFileGenerator(relationsGraph);
                File jsonFile = FileGenerator.jsonFilegenerator();
                File txtFile = FileGenerator.txtFileGenerator();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(jsonFile, true));
                    writer.append('[');
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //show relationsGraphs: if you need uncomment below
                //showGraph();
                for (int i = 1; i <= n_istanze; i++) {
                    System.out.println("ESECUZIONE DELL'ISTANZA N. " + i + " PER IL GRAFO NUMERO. " + j);
                    clear();
                    initStructures();
                    //run algorithm to search equilibrium
                    coreStable = calculateQStability(q);

                    if (coreStable) {
                        System.out.println("Trovato Equilibrio dopo " + deviations + " deviazioni");
                    } else {
                        System.out.println("Eseguite " + deviations + " deviazioni");
                    }

                    try {
                        writer = new BufferedWriter(new FileWriter(jsonFile, true));
                        writer.append(FileGenerator.generateJsonFromStatus(initialStatus, status, i));
                        writer.append(',');
                        writer.close();
                        writer = new BufferedWriter(new FileWriter(txtFile, true));
                        writer.write("\n\n****************ISTANZA N. " + i + "***************\n" + "\n----STATO INIZIALE----\n"
                                + initialStatus.toString()
                                + "\n----ULTIMO STATO----\n"
                                + status.toString());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    writer = new BufferedWriter(new FileWriter(jsonFile, true));
                    writer.append("{\"END OF FILE:\" : true}");
                    writer.append(']');
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("MEDIA DELLE DEVIAZIONI: " + ((int) (deviation_avarage)) / (n_istanze * n_grafi));

        }




        else
        {
            System.out.println("Inserisci il numero n di agenti: ");
            n = scan.nextInt();
            while (!okInput) {
                System.out.println("Inserisci il numero n di relazioni iniziale: ");
                n_relazioni = scan.nextInt();
                if (n_relazioni > n * (n - 1))
                    System.out.println("Attenzione, il numero di relazione deve essere al massimo n*n-1, con n=numero di agenti.\nInserisci nuovamente ");
                else
                    okInput = true;
            }
            okInput = false;

            while (!okInput) {
                System.out.println("Inserisci il numero n di relazioni massimo: ");
                n_rel_max = scan.nextInt();
                if (n_rel_max < n_relazioni || n_rel_max > n * (n - 1))
                    System.out.println("Attenzione, il numero di relazione massimo deve essere maggiore o uguale al n. di relazioni iniziale.\n" +
                            "inoltre esso deve essere al massimo n*n-1, con n=numero di agenti\nInserisci nuovamente ");
                else
                    okInput = true;
            }
            okInput = false;
            while (!okInput) {
                System.out.println("Inserisci il numero q iniziale per il calcolo q-stablity: ");
                start_q = scan.nextInt();
                q = start_q;
                if (start_q > n - 1)
                    System.out.println("Attenzione, il numero q deve avere una cardinalità massima di n-1;");
                else
                    okInput = true;
            }
            okInput = false;
            while (!okInput) {
                System.out.println("Inserisci il numero q massimo per il calcolo q-stablity: ");
                q_max = scan.nextInt();
                if (q_max > n - 1 || q_max < q)
                    System.out.println("Attenzione, il numero q massimo deve avere una cardinalità massima di n-1; Inoltre deve essere maggiore del numero q iniziale.");
                else
                    okInput = true;
            }
            okInput = false;

            while (!okInput) {
                System.out.println("Inserisci il numero di incremento di relazioni : ");
                rel_increment = scan.nextInt();
                if (rel_increment < 1 || rel_increment >= n_rel_max)
                    System.out.println("Attenzione, il numero di cui si incrementano le relazioni è troppo alto.");
                else
                    okInput = true;
            }
            okInput = false;

            while (!okInput) {
                System.out.println("Inserisci il numero di minuti da incrementare ad ogni passo : ");
                time_increment = scan.nextDouble();

                if (time_increment < 0 || time_increment >= 10)
                    System.out.println("Attenzione, il numero di minuti da incrementare deve esse compreso tra 0 e 10. Scegliendo 0 non si avrà variazione. ");
                else
                    okInput = true;
            }
            okInput = false;

            while (!okInput) {
                System.out.println("Inserisci il numero di volte di cui incrementare il tempo : ");
                n_of_time_increment = scan.nextInt();
                okInput=true;
            }
            okInput = false;
            while (!okInput) {
                System.out.println("Inserisci il numero di minuti iniziale : ");
                CONST_T = scan.nextDouble();
                start_time = CONST_T;

                if (start_time < 0 || start_time >= 10)
                    System.out.println("Attenzione, il numero di minuti da incrementare deve esse compreso tra 0 e 10. Scegliendo 0 non si avrà variazione. ");
                else
                    okInput = true;
            }
            okInput = false;

            for (n_relazioni=n_relazioni;  n_relazioni <= n_rel_max; n_relazioni=n_relazioni+rel_increment){
                System.out.println("CREAZIONE GRAFO");
                //call to methods for initializes Graph and structures
                initGraph();
                FileGenerator.graphFileGenerator(relationsGraph);
                File jsonFile = FileGenerator.jsonFilegenerator();
                File txtFile = FileGenerator.txtFileGenerator();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(jsonFile, true));
                    writer.append('[');
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //show relationsGraphs: if you need uncomment below
                //showGraph();
                clear();
                initStructures();
                for (int i = 0; i <= 1; i++) {
                    if (i==0){
                        //System.out.println("ESECUZIONE con "+n_relazioni+" relazioni, "+ "modalità classica");
                        randomDeviation=false;
                        for (q= start_q; q <= q_max; q++){
                            for(int j=0; j<=n_of_time_increment; j++ ){
                                if (j==0){
                                    CONST_T = start_time;
                                    System.out.println("ESECUZIONE con "+n_relazioni+" relazioni, "+"q= "+q+", tempo= "+CONST_T+ "modalità classica");
                                    resetCoalitions();
                                    resetPartition();
                                    refreshUtility();
                                    found=false;
                                    status = new MFCoreStatus(partition);
                                    status.set(false,partition,coalitions,agents);

                                    //run algorithm to search equilibrium
                                    coreStable = calculateQStability(q);

                                    if (coreStable) {
                                        System.out.println("Trovato Equilibrio dopo " + deviations + " deviazioni");
                                    }
                                    else {
                                        System.out.println("Eseguite " + deviations + " deviazioni");
                                    }
                                    try {
                                        writer = new BufferedWriter(new FileWriter(jsonFile, true));
                                        writer.append(FileGenerator.generateJsonFromStatusRdm(initialStatus, status, i, n_relazioni, randomDeviation, q, CONST_T));
                                        writer.append(',');
                                        writer.close();
                                        writer = new BufferedWriter(new FileWriter(txtFile, true));
                                        writer.write("\n\n**ESECUZIONE CON k= " + n_relazioni  + "archi, " + "q=" +q+", tempo= "+CONST_T +" con mosse classiche "+
                                                "\n----STATO INIZIALE----\n"
                                                + initialStatus.toString()
                                                + "\n----ULTIMO STATO----\n"
                                                + status.toString());
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    CONST_T = CONST_T+time_increment;
                                    resetCoalitions();
                                    resetPartition();
                                    refreshUtility();
                                    found=false;
                                    status = new MFCoreStatus(partition);
                                    status.set(false,partition,coalitions,agents);

                                    //run algorithm to search equilibrium
                                    coreStable = calculateQStability(q);

                                    if (coreStable) {
                                        System.out.println("Trovato Equilibrio dopo " + deviations + " deviazioni");
                                    }
                                    else {
                                        System.out.println("Eseguite " + deviations + " deviazioni");
                                    }
                                    try {
                                        writer = new BufferedWriter(new FileWriter(jsonFile, true));
                                        writer.append(FileGenerator.generateJsonFromStatusRdm(initialStatus, status, i, n_relazioni, randomDeviation, q, CONST_T));
                                        writer.append(',');
                                        writer.close();
                                        writer = new BufferedWriter(new FileWriter(txtFile, true));
                                        writer.write("\n\n**ESECUZIONE CON k= " + n_relazioni  + "archi, " + "q=" +q+", tempo= "+CONST_T +" con mosse classiche "+
                                                "\n----STATO INIZIALE----\n"
                                                + initialStatus.toString()
                                                + "\n----ULTIMO STATO----\n"
                                                + status.toString());
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("ESECUZIONE con "+n_relazioni+" relazioni, "+ "modalità random");
                        randomDeviation=true;
                        for (q=start_q; q <= q_max; q++  ){

                            for(int j=0; j<=n_of_time_increment; j++ ){
                                if (j==0){
                                    CONST_T = start_time;
                                    System.out.println("ESECUZIONE con "+n_relazioni+" relazioni, "+"q= "+q+", tempo= "+CONST_T+ "modalità random");
                                    resetCoalitions();
                                    resetPartition();
                                    refreshUtility();
                                    found=false;
                                    status = new MFCoreStatus(partition);
                                    status.set(false,partition,coalitions,agents);

                                    //run algorithm to search equilibrium
                                    coreStable = calculateQStability(q);

                                    if (coreStable) {
                                        System.out.println("Trovato Equilibrio dopo " + deviations + " deviazioni");
                                    }
                                    else {
                                        System.out.println("Eseguite " + deviations + " deviazioni");
                                    }

                                    try {
                                        writer = new BufferedWriter(new FileWriter(jsonFile, true));
                                        writer.append(FileGenerator.generateJsonFromStatusRdm(initialStatus, status, i, n_relazioni, randomDeviation, q, CONST_T));
                                        writer.append(',');
                                        writer.close();
                                        writer = new BufferedWriter(new FileWriter(txtFile, true));
                                        writer.write("\n\n**ESECUZIONE CON k= " + n_relazioni  + "archi, " + "q=" +q+", tempo= "+CONST_T +" con mosse random "+
                                                "\n----STATO INIZIALE----\n"
                                                + initialStatus.toString()
                                                + "\n----ULTIMO STATO----\n"
                                                + status.toString());
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    CONST_T = CONST_T+time_increment;
                                    resetCoalitions();
                                    resetPartition();
                                    refreshUtility();
                                    found=false;
                                    status = new MFCoreStatus(partition);
                                    status.set(false,partition,coalitions,agents);

                                    //run algorithm to search equilibrium
                                    coreStable = calculateQStability(q);

                                    if (coreStable) {
                                        System.out.println("Trovato Equilibrio dopo " + deviations + " deviazioni");
                                    }
                                    else {
                                        System.out.println("Eseguite " + deviations + " deviazioni");
                                    }

                                    try {
                                        writer = new BufferedWriter(new FileWriter(jsonFile, true));
                                        writer.append(FileGenerator.generateJsonFromStatusRdm(initialStatus, status, i, n_relazioni, randomDeviation, q, CONST_T));
                                        writer.append(',');
                                        writer.close();
                                        writer = new BufferedWriter(new FileWriter(txtFile, true));
                                        writer.write("\n\n**ESECUZIONE CON k= " + n_relazioni  + "archi, " + "q=" +q+", tempo= "+CONST_T +" con mosse random "+
                                                "\n----STATO INIZIALE----\n"
                                                + initialStatus.toString()
                                                + "\n----ULTIMO STATO----\n"
                                                + status.toString());
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                try {
                    writer = new BufferedWriter(new FileWriter(jsonFile, true));
                    writer.append("{\"END OF FILE:\" : true}");
                    writer.append(']');
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("TOTALE: "+countertemp);
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
        existsdeviation=true;
        while(existsdeviation){
            if (elapsedTime < CONST_T*60*1000){
                existsdeviation=false;
                newCoalition =null;

                //**RANDOM**/
                if (randomDeviation){
                    //System.out.println("Ricerca random");
                    getRandomSubset(agentlist);

                    if(existsdeviation){
                        //System.out.println("Deviazione di una coalizione di dimensione "+newCoalition.size()+"\n"+ newCoalition);
                        deviation(newCoalition);
                        deviations++;
                        deviation_avarage=deviation_avarage+deviations;
                    }
                    else {
                        status.setCoreStable(true);
                        return true;
                    }
                    elapsedTime = (new Date()).getTime() - startTime;
                }
                //**END RANDOM**/
                else{
                    //System.out.println("Ricenrca classica");
                    if (q==2)
                        get2subset(agentlist);
                    else
                    if (q==3){
                        get2subset(agentlist);
                        if (!existsdeviation)
                            get3subset(agentlist);
                    }
                    else
                    {
                        for (int i=2; i <=q; i++){
                            getSubsets(agentlist, i);
                            if (existsdeviation) break;
                        }
                    }
                    if(existsdeviation){
                        //System.out.println("Deviazione di una coalizione di dimensione "+newCoalition.size()+"\n"+ newCoalition);
                        deviation(newCoalition);
                        deviations++;
                        deviation_avarage=deviation_avarage+deviations;
                    }
                    else {
                        status.setCoreStable(true);
                        return true;
                    }
                    elapsedTime = (new Date()).getTime() - startTime;
                }

            }else{
                System.out.println("Equilibrio non trovato nel tempo utile");
                status.setCoreStable(false);
                return false;

            }
        }
        return true;
    }

    private static void getRandomSubset( List <Integer> superSet) {

        found =false;
        long counter[] = new long[q+1];
        HashMap<Integer, boolean[]> positions = new HashMap<Integer, boolean[]>();
        counter[0] = 0;
        counter[1] = 0;
        for (int i = 2; i <= q; i++) {
            int coeff = (int) Codifier.binomialCoeff(n, i);
            counter[i] = coeff;
            positions.put(i, new boolean[coeff]);
        }

        List <Integer> exclusions=  new ArrayList<Integer>();
        List <Integer> exclusionsK=  new ArrayList<Integer>();
        exclusionsK.add(0);
        exclusionsK.add(1);

        while(existsSubset(counter) && !found) {

            exclusions.clear();
            exclusions.add(0);
            exclusions.add(1);

            //random k
            int k = RandomInt.randomIntWithExclusion(0, q, exclusionsK);

            //jump while statements if
            if (counter[k]<=0 ){
                exclusionsK.add(k);
                continue;
            }

            long maxPosition = Codifier.binomialCoeff(n, k);

            Random r = new Random();

            //random position of combination in [0, (n-k)]
            int randomPostion = r.nextInt((int) maxPosition-1);
            //System.out.println("Il num random è: "+randomPostion);
            exclusions.clear();
            //Search for a location in [0 , (n-k)] that has not been tried yet

            long stTime = System.currentTimeMillis();
            long elTime = 0L;
            boolean loop=false;
            while(positions.get(k)[randomPostion] ) {

                exclusions.add(randomPostion);
                randomPostion = RandomInt.randomIntWithExclusion(0, (int)maxPosition-1, exclusions);
                //System.out.println(counter[k]);
                if (counter[k] < maxPosition*0.001){
                    HashMap<Integer, Integer> remainingPositionsIDX = new HashMap<Integer, Integer>();
                    int count=0;
                    for (int i=0; i <=positions.get(k).length-1; i++ ){
                        if (!positions.get(k)[i]){
                            count++;
                            remainingPositionsIDX.put(count,i);
                        }
                    }
                    randomPostion=remainingPositionsIDX.get(RandomInt.randomIntWithExclusion(0,remainingPositionsIDX.size(),0));
                    countertemp++;
                    System.out.println("Loop: rimasto la millesima parte del coeff binomiale. Cosidero non trovata deviazione per questo k");
                    System.out.println(countertemp);
                }
                elTime = (new Date()).getTime() - stTime;
            }
            if (loop)
                continue;
            Long [] decodedSet = Codifier.decode(n, k, randomPostion);
            ArrayList<Agent> totest= new ArrayList<Agent>();
            for (int i=0; i<= decodedSet.length-1; i++){
                totest.add(  new Agent(decodedSet[i].intValue())  );
            }

            for (Agent a : totest) {
                a.setUtility(calculateUtility(a.getID(),totest));
                if (a.getUtility() <= agents[a.getID()].getUtility() ) {
                    found=false;
                    totest.clear();
                    counter[k]--;
                    positions.get(k)[randomPostion]=true;
                    break;
                }
                found=true;
            }

            if(found){
                newCoalition = totest;
                existsdeviation=true;
            }

        }

    }

    private static boolean existsSubset(long[] counter){
        //System.out.println(Arrays.toString(counter));
        for (int i=2; i<= counter.length-1; i++){
            if (counter[i] > 0)
                return true;
        }
        return false;
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

    private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current) {
        if (existsdeviation)
            return;
        //successful stop clause
        if (!found){
            if ( current.size() == k && current.size() > 1 ) {
                //    System.out.println(current.size());
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
        getSubsets(superSet, k, idx+1, current);
        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx+1, current);
    }

    public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>());
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

            if (a == null || ( a.getID() != null && a.getID() == i  ))
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
     * It reset coalition as startingCoalition
     */
    static void resetCoalitions() {
        for (int i=0; i<=coalitions.length-1; i++){
            coalitions[i].clear();
        }
        for (int i=1; i<=startingCoalitions.length-1; i++) {
            if (startingCoalitions[i]==null)
                continue;
            for (Agent a : startingCoalitions[i]){
                coalitions[i].add(new Agent(a.getID(), a.getUtility()));
            }

        }
    }


    /**
     * It reset partition as startingPartition
     */
    static void resetPartition() {
        partition.clear();
        for (   Map.Entry<Integer, Integer> entry : startingPartition.entrySet()  ){
            partition.put(entry.getKey(), entry.getValue());
        }
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

