package symulacja;

import bierny.OkręgWyborczy;
import czynny.Kandydat;

import java.util.ArrayList;
import java.util.stream.IntStream;

// Klasa abstrakcyjna, która liczy głosy zgodnie z algorytmem opartym o
// metode Dhondta lub Sainte.
public abstract class LiczenieZWykładnikiem extends LiczenieGłosów {

//    Działa jak zliczMandaty określone w nadklasie, jednak ta metoda pomocnicza
//    potrzebuje zmiennej mówiącej o ile będzie zwiększał się dzielnik w algorytmie.
    protected int[][] zliczMandaty(ArrayList<OkręgWyborczy> listaOkręgów, int dzielnik) {
        int[][] macierzDoZwrotu = new int[listaOkręgów.size()][listaOkręgów.get(0).macierzKandydatów().length];
        for (int okreg = 0; okreg < listaOkręgów.size(); okreg++) {
            int[] głosyDlaPartii = zbierzGłosyWOkręgu(listaOkręgów, okreg);
            for (int i = 0; i < listaOkręgów.get(okreg).ileMandatów(); i++) {
                int indeks = maxIndeks(głosyDlaPartii);
                głosyDlaPartii[indeks] = głosyDlaPartii[indeks] * (1 + macierzDoZwrotu[okreg][indeks]) /
                        (macierzDoZwrotu[okreg][indeks] + dzielnik + 1);
                macierzDoZwrotu[okreg][indeks]++;
            }
        }
        return macierzDoZwrotu;
    }

//    Zwraca indeks z największą wartością w danej tablicy.
    private int maxIndeks(int[] tablica) {
        int maxIndex = 0;
        int maxValue = 0;
        for (int i = 0; i < tablica.length; i++) {
            if (tablica[i] > maxValue)
                maxIndex = i;
        }
        return maxIndex;
    }
}
