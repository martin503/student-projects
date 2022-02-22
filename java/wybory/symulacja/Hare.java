package symulacja;

import bierny.OkręgWyborczy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

// Komisja wyborcza, która liczy metodą Hara.
public class Hare extends LiczenieGłosów {

    public int[][] zliczMandaty(ArrayList<OkręgWyborczy> listaOkręgów) {
        int[][] macierzDoZwrotu = new int[listaOkręgów.size()][listaOkręgów.get(0).macierzKandydatów().length];
        for (int okreg = 0; okreg < listaOkręgów.size(); okreg++) {
            int[] głosyDlaPartii = zbierzGłosyWOkręgu(listaOkręgów, okreg);
            int[] indeksyPosortowane = posortowanaTablicaIndeksówPoWartościach(głosyDlaPartii);
            int sumaGłosów = IntStream.of(głosyDlaPartii).sum();
            int liczbaMandatów = listaOkręgów.get(okreg).ileMandatów();
            for (int i = 0 ; i < macierzDoZwrotu[okreg].length; i++) {
                macierzDoZwrotu[okreg][i] = głosyDlaPartii[i] * liczbaMandatów / sumaGłosów;
            }
            int niewykorzystaneMandaty = liczbaMandatów - IntStream.of(macierzDoZwrotu[okreg]).sum();
            for (int i = 0; i < niewykorzystaneMandaty; i++) {
                macierzDoZwrotu[okreg][indeksyPosortowane[i]]++;
            }
        }
        return macierzDoZwrotu;
    }

//    Zwraca tablicę indeksów danej tablicy, która jest posortowana
//    po wartościach odpowiadających indeksom.
    private int[] posortowanaTablicaIndeksówPoWartościach(int[] tablica) {
        class Para implements Comparable<Para>{
            protected int indeks;
            protected int wartość;

            public Para(int indeks, int wartość) {
                this.indeks = indeks;
                this.wartość = wartość;
            }

            @Override
            public int compareTo(Para o) {
                return Integer.compare(wartość, o.wartość);
            }
        }
        Para[] nowaTablica = new Para[tablica.length];
        for (int i = 0; i < tablica.length; i++) {
            nowaTablica[i] = new Para(i, tablica[i]);
        }
        Arrays.sort(nowaTablica);
        int[] tablicaIndeksów = new int[tablica.length];
        for (int i = 0; i < nowaTablica.length; i++) {
            tablicaIndeksów[i] = nowaTablica[i].indeks;
        }
        return tablicaIndeksów;
    }

    @Override
    public String toString() {
        return "Hare’a-Niemeyera";
    }
}
