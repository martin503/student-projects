package bierny;

import czynny.Cechy;
import czynny.Kandydat;

import java.util.stream.IntStream;

// Klasa reprezentująca wyborcę wszechstronnego, który przypisuje każdej cesze
// kandydata pewną wartość
public class Wszechstronny extends Wyborca {

    protected Cechy cechy;

    public Wszechstronny(String imię, String nazwisko, Cechy cechy) {
        super(imię, nazwisko);
        this.cechy = cechy;
    }

    public Cechy cechy() {
        return cechy;
    }

//    Klasa wewnętrzna, ułatwiająca implementację wyboru faworyta.
    protected class myInt {
        public int someInt;

        public myInt(int someInt) {
            this.someInt = someInt;
        }
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        int div = 0;
        for (int i = 0; i < cechy.liczbaCech(); i++) {
            div += cechy.pokażCechę(i);
        }
        myInt actualBestSum = new myInt(-1);
        for (Kandydat[] listaPartii : macierzKandydatów) {
            wybórFaworytaPartii(listaPartii, div, actualBestSum);
        }
    }

//    Funkcja pomocnicza dla wyboru faworyta, jej zadaniem jest szukanie faworyta
//    w obrębie danej partii.
    protected void wybórFaworytaPartii(Kandydat[] tablica, int div, myInt actualBestSum) {
        try {
            for (Kandydat k : tablica) {
                int sum = 0;
                for (int i = 0; i < cechy.liczbaCech(); i++)
                    sum += cechy.pokażCechę(i) * k.cechy().pokażCechę(i);
                if (actualBestSum.someInt < sum / div) {
                    actualBestSum.someInt = sum / div;
                    faworyt = k;
                }
            }
        }
        catch (ArithmeticException e) {
            for (Kandydat k : tablica) {
                int sum = 0;
                for (int i = 0; i < cechy.liczbaCech(); i++)
                    sum += cechy.pokażCechę(i) * k.cechy().pokażCechę(i);
                if (actualBestSum.someInt < sum) {
                    actualBestSum.someInt = sum;
                    faworyt = k;
                }
            }
        }
    }
}
