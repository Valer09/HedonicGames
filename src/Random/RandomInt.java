package Random;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * It can generate a random positive int in range from start to end and excluding a set of numers listed
 */
public class RandomInt {

    /**
     *
     * It can generate a random positive int in range from start to end and excluding a set of numers listed
     * @param start lower bound
     * @param end upped bound
     * @param exclusions list of integer to excluding
     * @return random integer
     */
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

    /**
     * It can generate a random positive int in range from start to end and excluding one
     * @param start lower bound
     * @param end upper bound
     * @param exclusions number to exclude
     * @return random integer
     */
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

