package czynny;

import bierny.OkręgWyborczy;
import bierny.Wszechstronny;
import bierny.Wyborca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

// Klasa reprezentująca partię, która wybiera najlepsze działanie w danym momencie.
public class Zachłanna extends Partia {

    public Zachłanna(int numer, String nazwa, int budżet) {
        super(numer, nazwa, budżet);
    }

//    Wykonuje najlepsze (na dany moment) działanie na okręgu.
    public void wykonajDziałanie(ArrayList<OkręgWyborczy> listaOkręgów, ArrayList<Działanie> listaDziałań) {
        if (!nieMaKasy) {
            Para paraOkregDziałanie = badanieSocjologiczne(listaOkręgów, listaDziałań);
            if (paraOkregDziałanie != null) {
                wykonajOkreśloneDziałanie(listaOkręgów, listaDziałań, paraOkregDziałanie.okręg(),
                        paraOkregDziałanie.działanie());
                budżet -= cenaDziałania(listaOkręgów.get(paraOkregDziałanie.okręg()),
                        listaDziałań.get(paraOkregDziałanie.działanie()));
            }
            else
                nieMaKasy = true;
        }
    }

//    Klasa wewnętrzna, reprezentująca parę.
    private class Para {
        private int działanie;
        private int okręg;

        public Para(int działanie, int okręg) {
            this.działanie = działanie;
            this.okręg = okręg;
        }

        public int działanie() {
            return działanie;
        }

        public int okręg() {
            return okręg;
        }
    }

//    Funkcja pomocnicza, sprawdza jak zmieni się suma ważona po wykonaniu
//    danego działania na danym wyborcy.
    private int różnicaSumyWażonejWyborcy(Działanie działanie,
                                          Wyborca wyborca,
                                          Kandydat kandydat) {
        if (wyborca instanceof Wszechstronny) {
            int[] działanieWartości = działanie.kopiaWartości();
            int[] kandydatWartości = kandydat.cechy().KopiaWartości();
            int[] wyborcaWartości = ((Wszechstronny) wyborca).cechy().KopiaWartości();
            int sumaPrzedZmianą = 0;
            int sumaPoZmianie = 0;
            for (int i = 0; i < wyborcaWartości.length; i++) {
                sumaPrzedZmianą += kandydatWartości[i] * wyborcaWartości[i];
                sumaPoZmianie += (kandydatWartości[i] +
                        działanieWartości[i]) * wyborcaWartości[i];
            }
            return sumaPoZmianie - sumaPrzedZmianą;
        }
        else
            return 0;
    }

//    Funkcja pomocnicza, sprawdza jak zmieni się suma ważona po wykonaniu
//    danego działania na danym okręgu.
    private int różnicaDlaOkręgu (OkręgWyborczy okręg, Działanie działanie) {
        int sumaZmianSumyWażonej = 0;
        for (Wyborca wyborca : okręg.listaWyborców()) {
            for (Kandydat kandydat : okręg.macierzKandydatów()[this.numer]) {
                sumaZmianSumyWażonej += różnicaSumyWażonejWyborcy(działanie, wyborca, kandydat);
            }
        }
        return sumaZmianSumyWażonej;
    }

//    Funkcja pomocnicza przeprowadzająca badanie socjologiczne,
//    zwraca parę indeksów (działanie, okręg), która mówi
//    partii jakie przedsiemwzięcie jest najbardziej opłacalne na
//    daną chwilę.
    private Para badanieSocjologiczne(ArrayList<OkręgWyborczy> listaOkręgów, ArrayList<Działanie> listaDziałań) {
        int okregMax = -1;
        int działanieMax = -1;
        int największaZmiana = 0;
        for (int działanie = 0; działanie < listaDziałań.size(); działanie++) {
            int sumaZmianSumyWażonej;
            for (int okreg = 0; okreg < listaOkręgów.size(); okreg++) {
                sumaZmianSumyWażonej = różnicaDlaOkręgu(listaOkręgów.get(okreg), listaDziałań.get(działanie));
                if (czyStaćNaDziałanie(listaOkręgów.get(okreg), listaDziałań.get(działanie)) &&
                        sumaZmianSumyWażonej > największaZmiana) {
                    okregMax = okreg;
                    działanieMax = działanie;
                    największaZmiana = sumaZmianSumyWażonej;
                }
            }
        }
        if (okregMax != -1)
            return new Para(działanieMax, okregMax);
        else
            return null;
    }
}
