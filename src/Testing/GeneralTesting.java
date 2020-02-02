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
    static List<Set<Integer>> solution2 = new ArrayList<>();
    static int temp=0;
    static int c=0;

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
        /*  int k=175;
        List<Integer> agentlist = new ArrayList<>();
        for (int i=1; i<=k; i++)
            agentlist.add(i);
        List <Integer> exclusions=  new ArrayList<Integer>();
        exclusions.add(0);
        Set < Integer > solution = new HashSet<>() ;
        int q=RandomInt.randomIntWithExclusion(1,k,1);
        for (int i=1; i <= q; i++){
            Integer x=RandomInt.randomIntWithExclusion(1,k,exclusions);
            exclusions.add(x);
            solution.add(x);
        }*/
        /* List<Set<Integer>> res = new ArrayList<>();
        //System.out.println(getSubsets(agentlist, 2));
        int k= RandomInt.randomIntWithExclusion(1,5,0);
        for (int i=2; i <= k; i++){
            getSubsets(agentlist, i);
        }
       //String s=getSubsets(agentlist,4).toString();
       //System.out.println(getSubsets(agentlist, 5));
        getSubsets(agentlist, 3);
    }
*/
        /*
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
    }*/

        // Program to calculate C(n ,k) in java

        // Returns value of Binomial Coefficient C(n, k)


        int n = 40;
        int k = 7;
        long tot=0L;
        for (int i=1; i<= k; i++){
            tot=tot+binomialCoeff(n,i);
        }
        /*System.out.println("Value of C("+ n + ", " + k+ ") "
                + "is" + " "+ binomialCoeff(n, k));*/
        System.out.println("THE TOTAL IS: "+ tot);

        tot=0L;
        temp=0;
        List<Integer> agentlist = new ArrayList<>();
        for (int i=1; i<=n; i++)
            agentlist.add(i);

        /*for (int i=1; i<= k; i++){
            temp=0;
            getSubsets(agentlist,i);
            tot=tot+temp;
        }*/
        getSubsets(agentlist,k);
        System.out.println("Ovvero è: " +tot);
        System.out.println(c);
    }



    static long binomialCoeff(int n, int k)
    {
        long res = 1L;

        // Since C(n, k) = C(n, n-k)
        if ( k > n - k )
            k = n - k;

        // Calculate value of [n * (n-1) *---* (n-k+1)] / [k * (k-1) *----* 1]
        for (int i = 0; i < k; ++i)
        {
            res *= (n - i);
            res /= (i + 1);
        }

        return res;
    }

    private static void getSubsets (List < Integer > superSet,int k, int idx, Set<Integer > current, List < Set < Integer >> solution){

        c++;
        //successful stop clause
        if (current.size() == k ) {
            temp++;
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


