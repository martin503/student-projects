package cover.io;

import cover.algorithms.GreedyHeuristic;
import cover.algorithms.NaiveHeuristic;
import cover.algorithms.OptimalSolutionAlgorithm;
import cover.data.FiniteComponent;
import cover.data.InfiniteComponent;
import cover.data.OneComponent;
import cover.data.Set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//    Class that analyze input
public class AnalyzeInput {
    private final List<Integer> inputList;
    private final List<Set> family;
    private int currentSet = 0;

    private void log(String txt) {
        System.out.println(txt);
    }

    public AnalyzeInput(List<Integer> inputList) {
        this.inputList = inputList;
        this.family = new ArrayList<>();
    }

//    Adds analized set to the family of sets
    private void analyzeSet() {
        currentSet++;
        Integer value = inputList.remove(0);
        if (value == 0 || inputList.size() == 0)
            log("zbiór posty");
        else {
            Set actualGroup = new Set(currentSet);
            List<Integer> groupIntegers = new ArrayList<>();
            log("Zbiór prawidłowy: ");

            do {
                groupIntegers.add(0, value);
                value = inputList.remove(0);
            } while (value != 0);

            Iterator<Integer> groupIterator = groupIntegers.iterator();
            while (groupIterator.hasNext()) {
                value = groupIterator.next();
                if (value > 0)
                    actualGroup.addComponent(new OneComponent(value));
                else {
                    Integer val2 = groupIterator.next();
                    if (val2 > 0)
                        actualGroup.addComponent(new InfiniteComponent(val2, -value));
                    else
                        actualGroup.addComponent(new FiniteComponent(groupIterator.next(), -val2, -value));
                }
            }
            family.add(actualGroup);
        }
    }

//    Analizes question and gives answer to it with specified algorithm
    private void analyzeQuestion() {
        log("Zapytanie");
        Integer val1 = inputList.remove(0);
        Integer val2 = inputList.remove(0);
        if (val2 == 1)
            OptimalSolutionAlgorithm.execute(-val1, family);
        else if (val2 == 2)
            GreedyHeuristic.execute(-val1, family);
        else
            NaiveHeuristic.execute(-val1, family);
        log("     =>" + val1);
        log("     =>" + val2);
    }

//    Decides whether given integers are questions or sets
    public void mainAnalyze() {
        while (inputList.size() > 0) {
            if (inputList.get(0) >= 0)
                analyzeSet();
            else
                analyzeQuestion();
        }
    }
}