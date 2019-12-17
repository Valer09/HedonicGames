public class SubSets_Algorithms {

    /* private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current,List<Set<Integer>> solution) {
        level++;
        //successful stop clause
        if (current.size() == k) {
            solution.add(new HashSet<>(current));
            return;
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
        level++;
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
    }*/



    /*
    static void combinations2(Integer[] arr, int len, int startPosition, Integer[] result){
        if (len == 0){
            return ;
        }
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = arr[i];
            res.add(result);
            combinations2(arr, len-1, i+1, result);
        }
    }
     */


    /*
    public static Set<Set<Integer>> powerSet(Set<Integer> originalSet, int q) {
        Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
        if (originalSet.isEmpty() ) {
            sets.add(new HashSet<Integer>());
            return sets;
        }
        List<Integer> list = new ArrayList<Integer>(originalSet);
        Integer head = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
        for (Set<Integer> set : powerSet(rest, q)) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }




    /* static boolean calculateQStability(int i, int j, Agent [] list) {

        public static Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
            Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
            if (originalSet.isEmpty()) {
                sets.add(new HashSet<Integer>());
                return sets;
            }
            List<Integer> list = new ArrayList<Integer>(originalSet);
            Integer head = list.get(0);
            Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
            for (Set<Integer> set : powerSet(rest)) {
                Set<Integer> newSet = new HashSet<Integer>();
                newSet.add(head);
                newSet.addAll(set);
                sets.add(newSet);
                sets.add(set);
            }
            return sets;
        }

    }*/



















}
