package symulacja;

import bierny.OkręgWyborczy;
import czynny.Partia;

import java.util.ArrayList;

// Klasa abstrakcyjna reprezentująca komisję wyborczą.
// Jej zadaniem jest zebranie głosów i przeliczenie ich na mandaty.
public abstract class LiczenieGłosów {

//    Funkcja, która wypisuje wyniki wyborów.
    public void wynikiWyborów(ArrayList<OkręgWyborczy> listaOkręgów, Partia[] tablicaPartii) {
        int[][] macierzMandatów = zliczMandaty(listaOkręgów);
        StringBuilder wyniki = new StringBuilder(this.toString());
        wyniki.append("\n");

        for (int okreg = 0; okreg < listaOkręgów.size(); okreg++) {
            wyniki.append(listaOkręgów.get(okreg).toString());
            for (int partia = 0; partia < tablicaPartii.length; partia++) {
                wyniki.append(String.format("%s %d\n",tablicaPartii[partia].toString() ,
                        macierzMandatów[okreg][partia]));
            }
        }

        for (Partia partia : tablicaPartii) {
            wyniki.append(String.format("%s %d\n", partia.toString(),
                    mandatyDlaPartii(macierzMandatów, partia)));
        }

        System.out.println(wyniki.toString());
    }

//    Funkcja odpowiadająca za zliczenie mandatów.
//    Implementacja w podklasach o określonych algorytmach
//    przeliczania głosów na mandaty. Zwraca macierz mandatów
//    posegregowaną okręgami, a następnie partiami.
    protected abstract int[][] zliczMandaty(ArrayList<OkręgWyborczy> listaOkręgów);

//    Złicza mandaty jakie zdobyła partia w danych wyborach.
    private int mandatyDlaPartii(int[][] macierzMandatów, Partia partia) {
        int sumaMandatów = 0;
        for (int i = 0; i < macierzMandatów.length; i++) {
            sumaMandatów += macierzMandatów[i][partia.numer()];
        }
        return sumaMandatów;
    }

//    Zbiera głosy w danym okręgu. Pole w tablicy odpowiada ilości,
//    głosów zebranych przez partię u numerzez odpowiadającym indeksowi pola.
    protected int[] zbierzGłosyWOkręgu(ArrayList<OkręgWyborczy> listaOkręgów, int okreg) {
        int[] głosyDlaPartii = new int[listaOkręgów.get(0).macierzKandydatów().length];
        for (int i = 0; i < listaOkręgów.get(okreg).macierzKandydatów().length; i++) {
            for(int j = 0; j < listaOkręgów.get(okreg).macierzKandydatów()[i].length; j++) {
                głosyDlaPartii[i] += listaOkręgów.get(okreg).macierzKandydatów()[i][j].liczbaGłosów();
            }
        }
        return głosyDlaPartii;
    }

}
