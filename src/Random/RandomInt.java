package Random;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RandomInt {
    public static Integer randomIntWithExclusion(int start, int end,List<Integer> exclusions){
        Random randomGenerator = new Random();
        Collections.sort(exclusions);
        Integer rdm=0;
        boolean exists=true;
        //System.out.println(exclusions.size()+ " è la taglia della lista esclusioni");
        int random = start + randomGenerator.nextInt(end - start + 1 - exclusions.size()  );
        for (int ex : exclusions) {
            if (random < ex) {
                break;
            }
            if (exclusions.size()==0)
                break;
            else
            random++;
        }
        return random;

    }

    public static Integer randomIntWithExclusion(int start, int end, Integer exclusions){
        Random randomGenerator = new Random();
        //System.out.println(exclusions.size()+ " è la taglia della lista esclusioni");
        int random = randomGenerator.nextInt(end);
        while (random == exclusions) {
            random = randomGenerator.nextInt(end);
        }
        return random;

    }


}

