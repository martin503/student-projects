package cover.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//    Class that takes input from a user
public class Input {
    //    Takes input and converts it to series of integers to further analyzation
    public static List<Integer> input() {
        Scanner input = new Scanner(System.in);
        List<Integer> listToReturn = new ArrayList<>();
        while(input.hasNextLine()){
            String line = input.nextLine();
            if(line.length() == 0) continue;
            String[] splittedLine = line.split(" ");
            for (String s : splittedLine) {
                if (!s.equals(""))
                listToReturn.add(Integer.parseInt(s));
            }
        }
        input.close();
        return listToReturn;
    }
}