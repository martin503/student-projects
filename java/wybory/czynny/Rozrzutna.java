package czynny;

import bierny.OkręgWyborczy;
import bierny.Wszechstronny;
import bierny.Wyborca;

import java.util.ArrayList;

// Klasa reprezentująca partię rozrzutną, która wydaje zawsze jak najwięcej.
public class Rozrzutna extends Partia {

    public Rozrzutna(int numer, String nazwa, int budżet) {
        super(numer, nazwa, budżet);
    }

//    Aplikuje działanie na okręgu, żeby koszt był jak najwyższy.
    public void wykonajDziałanie(ArrayList<OkręgWyborczy> listaOkręgów, ArrayList<Działanie> listaDziałań) {
        if (!nieMaKasy) {
            int aktualnaMaxCena = -1;
            int okregMax = 0;
            int działanieMax = 0;
            for (int okreg = listaOkręgów.size() - 1; okreg >= 0; okreg--) {
                for (int działanie = listaDziałań.size() - 1; działanie >= 0; działanie--) {
                    int cena = cenaDziałania(listaOkręgów.get(okreg), listaDziałań.get(działanie));
                    if (cena < budżet && cena > aktualnaMaxCena) {
                        aktualnaMaxCena = cena;
                        okregMax = okreg;
                        działanieMax = działanie;
                    } else if (okreg < okregMax && działanie < działanieMax)
                        break;
                }
            }
            if (aktualnaMaxCena > 0) {
                wykonajOkreśloneDziałanie(listaOkręgów, listaDziałań, okregMax, działanieMax);
                budżet -= aktualnaMaxCena;
            } else {
                nieMaKasy = true;
            }
        }
    }
}
