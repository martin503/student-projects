package cover.algorithms;

import java.util.Iterator;
import java.util.List;

//    Class of algorithms that answer to cover problem
public abstract class Algorithm {

//    Prints answer to stdout from given list of Ids
    public static void printAnswer(List<Integer> answerList) {
        StringBuilder answer = new StringBuilder(answerList.size() * 2 - 1);
        Iterator<Integer> answerIterator = answerList.iterator();
        answer.append(answerIterator.next());
        while (answerIterator.hasNext()) {
            answer.append(String.format(" %d", answerIterator.next()));
        }
        System.out.println(answer.toString());
    }

}
