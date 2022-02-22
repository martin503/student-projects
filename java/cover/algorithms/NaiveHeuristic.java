package cover.algorithms;

import cover.data.Set;

import java.util.ArrayList;
import java.util.List;

public class NaiveHeuristic extends Algorithm {

//    Executes naive heuristic algorithm to given family of sets and range
//    which indicates what set we want to cover
    static public void execute(int range, List<Set> family) {
        int[] found = new int[range];
        int toCover = range;
        List<Integer> listToPrint = new ArrayList<>(range/3);

        for (int i = 0 ; i < family.size() && toCover != 0; i++) {
            int coveredSum = family.get(i).coverComponents(found);
            if (coveredSum != 0) {
                toCover -= coveredSum;
                listToPrint.add(family.get(i).getId());
            }
        }
        if (toCover == 0)
            printAnswer(listToPrint);
        else
            System.out.println(0);
    }
}
