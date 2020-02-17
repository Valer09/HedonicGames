package Testing;

import Generators.Codifier;
import Random.RandomInt;
import Structures.Agent;

import java.time.LocalDateTime;
import java.util.*;

public class TestRandomRoba {
    public static void main(String [] args){

        long stTime = System.currentTimeMillis();
        long elTime = 0L;
        HashMap<Integer, boolean[]> positions = new HashMap<Integer, boolean[]>();
        List<Integer> exclusions=  new ArrayList<Integer>();
        int k=7;
        exclusions.add(0);
        exclusions.add(1);
        Random r = new Random();
        int randomPostion = r.nextInt((int) 2035800-1);
        //System.out.println("Il num random è: "+randomPostion);
        exclusions.clear();
        //Search for a location in [0 , (n-k)] that has not been tried yet


        boolean temp [] = new boolean [2035800];

        for (int i=0; i< 2035800-1; i++){
            if (i<1000)
                temp[i]=false;
            else
                temp[i]=true;
        }

        LocalDateTime now = LocalDateTime.now();
        while(positions.get(k)[randomPostion]) {
            exclusions.add(randomPostion);
            randomPostion = RandomInt.randomIntWithExclusion(0, (int)2035800-1, exclusions);
            if (elTime > 5*60*1000){

            }
        }


    }
}
