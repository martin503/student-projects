package czynny;

import bierny.Wszechstronny;

import java.util.Arrays;
import java.util.stream.IntStream;

// Klasa reprezentująca Działania.
public class Działanie implements Comparable<Działanie> {

    private int[] wartości;
    int cena;

    public Działanie(int[] wartości) {
        this.wartości = wartości;
        this.cena = IntStream.of(wartości).map(Math::abs).sum();
    }

//    Aplikuje działanie na danym wyborcy.
    public void aplikuj(Wszechstronny wyborca) {
        Cechy cechyWyborcy = wyborca.cechy();
        for (int i = 0; i < wartości.length; i++) {
            cechyWyborcy.modyfikujCechę(wartości[i], i);
        }
    }

    public int[] kopiaWartości() {
        return Arrays.copyOf(wartości, wartości.length);
    }

//    Zwraca cenę działania.
    public int cena() {
        return cena;
    }

    public int compareTo(Działanie doPorównania) {
        int cenaTego = this.cena();
        int cenaDoPorównania = doPorównania.cena();
        return Integer.compare(cenaTego, cenaDoPorównania);
    }
}
