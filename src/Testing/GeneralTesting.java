package Testing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Random.RandomInt;

import Generators.FileGenerator;
import JsonOrg.JSONObject;
import Structures.Agent;

public class GeneralTesting {
    static List < Set < Integer >> solution2 = new ArrayList<>();

    public static void main(String[] args) {

        /*ArrayList<Agent> totest = new ArrayList<Agent>();
        Integer[] set;
        set = new Integer[10];
        set[0] = null;
        for (int i = 1; i < 10; i++)
            set[i] = i;
        for (int i = 1; i <= set.length - 2; i++) {
            for (int j = i + 1; j <= set.length - 1; j++) {
                for (int k = j + 1; k < set.length; k++) {
                    totest.add(new Agent(i));
                    totest.add(new Agent(j));
                    totest.add(new Agent(k));
                    System.out.print("{");
                    for (Agent a : totest) {
                        System.out.print(+a.getID() + ",");
                    }
                    System.out.println("}");
                    totest.clear();
                }
            }
        }*/

        List<Integer> agentlist = new ArrayList<>();
        for (int i=1; i<=40; i++)
            agentlist.add(i);
        List <Integer> exclusions=  new ArrayList<Integer>();
        exclusions.add(0);
        Set < Integer > solution = new HashSet<>() ;
        int q=RandomInt.randomIntWithExclusion(1,39,1);
        for (int i=1; i <= q; i++){
            Integer x=RandomInt.randomIntWithExclusion(1,39,exclusions);
            exclusions.add(x);
            solution.add(x);
        }

       /* List<Set<Integer>> res = new ArrayList<>();
        //System.out.println(getSubsets(agentlist, 2));
        int k= RandomInt.randomIntWithExclusion(1,5,0);
        for (int i=2; i <= k; i++){
            getSubsets(agentlist, i);
        }*/
       String s=getSubsets(agentlist,4).toString();
       System.out.println(getSubsets(agentlist, 4));
    }



        private static void getSubsets (List < Integer > superSet,int k, int idx, Set<Integer > current, List < Set < Integer >> solution){

            //successful stop clause
                if (current.size() == k   && current.size() > 1 ) {
                    solution.add(new HashSet<>(current));
                    solution2.add(new HashSet<>(current));
                    return;
                }

            //unseccessful stop clause
            if (idx == superSet.size()) return;
            Integer x = superSet.get(idx);
            current.add(x);
            //"guess" x is in the subset
            getSubsets(superSet, k, idx + 1, current, solution);
            current.remove(x);
            //"guess" x is not in the subset
            getSubsets(superSet, k, idx + 1, current, solution);
        }

    public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
    }

    }


