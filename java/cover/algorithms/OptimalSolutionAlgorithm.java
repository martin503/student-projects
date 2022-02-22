package cover.algorithms;

import cover.data.Set;

import java.util.ArrayList;
import java.util.List;

public class OptimalSolutionAlgorithm extends Algorithm {

//    Returns first different index of set
    private static int firstDiffId(Integer[] set1, Integer[] set2) {
        int minLen = Math.min(set1.length, set2.length);
        for (int i = 0; i < minLen; i++) {
            if (!set1[i].equals(set2[i]))
                return i;
        }
        return minLen;
    }

//    Executes algorithm to given family of sets and range
//    which indicates what set we want to cover.
//    This algorithm always gives right answer to the given problem.
    public static void execute(int range, List<Set> family) {
        int[] found = new int[range];
        int toCover = range;
        NextSetsIterator setIterator = new NextSetsIterator(family.size(), family.size());
        SetsIds set;
        SetsIds priorSet = new SetsIds(new Integer[0]);
        Integer[] best = null;

        while ((set = setIterator.next()) != null) {
            int firstDiffId = firstDiffId(set.set, priorSet.set);
            for (int i = firstDiffId; i < priorSet.set.length; i++) {
                toCover += family.get(priorSet.set[i]).uncoverComponents(found);
            }

            for (int i = firstDiffId; i < set.set.length; i++) {
                toCover -= family.get(set.set[i]).coverComponents(found);
                if (toCover == 0) {
                    set.setLength(i + 1);
                    best = set.set.clone();
                    setIterator.setPosCount(i);
                    break;
                }
            }

            priorSet = set;
        }

        if (best != null) {
            List<Integer> listToPrint = new ArrayList<>(best.length);
            for (Integer integer : best) {
                listToPrint.add(family.get(integer).getId());
            }
            printAnswer(listToPrint);
        }
        else
            System.out.println(0);
    }

}
