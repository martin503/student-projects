package czynny;

import java.lang.reflect.Array;
import java.util.Arrays;

// Klasa reprezentująca cechy.
public class Cechy {

    private int[] wartości;

    public Cechy(int[] wartości) {
        this.wartości = wartości;
    }

    public int pokażCechę(int numerCechy) {
        return wartości[numerCechy];
    }

    public int[] KopiaWartości() {
        return Arrays.copyOf(wartości, wartości.length);
    }

    public void modyfikujCechę(int modyfikator, int numerCechy) {
        if (wartości[numerCechy] > 0 && modyfikator > 0)
            wartości[numerCechy] = Math.min(modyfikator + wartości[numerCechy], 100);
        else if (wartości[numerCechy] < 0 && modyfikator < 0)
            wartości[numerCechy] = Math.max(modyfikator + wartości[numerCechy], -100);
        else
            wartości[numerCechy] += modyfikator;
    }

    public int liczbaCech() {
        return wartości.length;
    }
}
