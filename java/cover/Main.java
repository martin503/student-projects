package cover;

import cover.io.AnalyzeInput;
import cover.io.Input;

public class Main {
    public static void main(String[] args) {
        AnalyzeInput ainp = new AnalyzeInput(Input.input());
        ainp.mainAnalyze();
    }
}
