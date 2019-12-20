package Structures;

import Interfaces.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * the status of game is represented from a pair of information:
 * - <b>coalition structure</b> which is a partition of agents in n coalitions.
 *    It is represented with an HashMap that maps every agent i=1...n to coalition j=1...n
 * - <b>equilibrium</b> that indicates whether the game is core stable
 *    It is represented with coreStable boolean value
 */
public class MFCoreStatus implements Status {
    private HashMap<Integer, Integer> partition;
    private ArrayList<Agent>[] coalitions;
    private Boolean coreStable=false;
    private Agent[] agents;

    /**
     * Setting Status
     * @param partition HashMap
     */
    public MFCoreStatus(HashMap<Integer, Integer> partition){
        this.partition=partition;
    }

    /**
     * Agent getter
     * @return Agent array
     */
    public Agent[] getAgents() {
        return agents;
    }

    /**
     * Agent setter
     * @param agents - array of Agents
     */
    public void setAgents(Agent[] agents) {
        this.agents = agents;
    }

    /**
     * General setting of all status component
     * @param status - bollean core status
     * @param partition - partition HashMap Integer-Integer
     * @param coalitions - coalitions, array of arraylist of agents
     * @param agents - array of agents
     */
   public void set(boolean status, HashMap<Integer, Integer> partition, ArrayList<Agent> [] coalitions, Agent[] agents){
        this.coreStable=status;
        this.coalitions=coalitions;
        this.partition=partition;
        this.agents=agents;
   }

    /**
     * coreStability setting
     * @param coreStable - boolean
     */
    public void setCoreStable(Boolean coreStable){
        this.coreStable=coreStable;
    }
    /**
     * Coalition setting
     * @param coalitions - array of arraylist of agents
     */
    public void setCoalitions (ArrayList<Agent> [] coalitions){
        this.coalitions=coalitions;
    }

    /**
     * Partition setting
     * @param partition - HashMap integer-integer
     */
    public void setPartition(HashMap<Integer, Integer> partition){
        this.partition=partition;
    }

    /**
     * Return Coalition Structure
     *
     * @return - <b>CoalitionStructure</b>
     */
    @Override
    public HashMap<Integer, Integer> getCoalitionStructure() {
        return partition;
    }

    /**
     * Return Status Obj
     *
     * @return <b>T</b>
     */
    @Override
    public MFCoreStatus getStatus() {
        return null;
    }

    /**
     * Return true if the status of the game is stable
     *
     * @return - <b>boolean</b>
     */
    @Override
    public Boolean isStable() {
        return coreStable;
    }

    /**
     * Return coalitions of actual status
     * @return ArrayList
     */
    public ArrayList<Agent>[] getCoalitions() {
        return coalitions;
    }

    /**
     * Print a string which show status
     * @return String
     */
    public String toString(){
        String s= "CORE: "+isStable()+"\n";
        s=s+"PARTITIONS: {"+ "\n";
        for (Agent a : agents){
            if (a==null)
                continue;
            s=s+"A"+a.getID()+" -> "+"C"+getCoalitionStructure().get(a.getID())+" ; \n";
        }
        s=s+"}\nCOALITIONS: \n\n";
        for (int i=0; i<coalitions.length; i++){
            if (i==0)
                continue;
            s=s+"C"+i+": ";
            for (Agent a : coalitions[i])
                s=s+"A"+a.getID()+", ";
            s=s+"\n";
        }
        s=s+"\n AGENTS:\n";
        for(Agent a : agents)
            if (a==null)
                continue;
            else
                s=s+a.toString()+"\n";

        return s;

    }
}
