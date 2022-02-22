package cover.algorithms;

import cover.data.Set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GreedyHeuristic extends Algorithm {

//    Executes greedy heuristic algorithm to given family of sets and range
//    which indicates what set we want to cover
    static public void execute(int range, List<Set> family) {
        int[] found = new int[range];
        int toCover = range;
        int actualBestSet = 0;
        int bestScore = 0;
        List<Set> withoutTaken = new ArrayList<>(List.copyOf(family));
        List<Integer> listToPrint = new ArrayList<>(range/3);

        while (toCover != 0 && withoutTaken.size() != 0) {
            for (int i = 0; i < withoutTaken.size(); i++) {
                int coveredSum = withoutTaken.get(i).coverComponents(found);
                if (coveredSum > bestScore) {
                    bestScore = coveredSum;
                    actualBestSet = i;
                }
                withoutTaken.get(i).uncoverComponents(found);
            }
            if (bestScore > 0) {
                toCover -= withoutTaken.get(actualBestSet).coverComponents(found);
                listToPrint.add(withoutTaken.get(actualBestSet).getId());
                withoutTaken.remove(actualBestSet);
                bestScore = 0;
                actualBestSet = 0;
            }
            else
                break;
        }
        if (toCover == 0) {
            Collections.sort(listToPrint);
            printAnswer(listToPrint);
        }
        else
            System.out.println(0);
    }

}
