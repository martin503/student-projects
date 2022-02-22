package czynny;

import bierny.OkręgWyborczy;
import bierny.Wszechstronny;
import bierny.Wyborca;

import java.util.ArrayList;

// Klasa reprezentująca partię oszczędną, która wydaje zawsze jak najmniej.
public class Oszczędna extends Partia {

    public Oszczędna(int numer, String nazwa, int budżet) {
        super(numer, nazwa, budżet);
    }

//    Aplikuje działanie na okręgu, żeby koszt był jak najniższy.
    public void wykonajDziałanie(ArrayList<OkręgWyborczy> listaOkręgów, ArrayList<Działanie> listaDziałań) {
        if (!nieMaKasy) {
            if (czyStaćNaDziałanie(listaOkręgów.get(0), listaDziałań.get(0))){
                wykonajOkreśloneDziałanie(listaOkręgów, listaDziałań, 0, 0);
                budżet -= cenaDziałania(listaOkręgów.get(0), listaDziałań.get(0));
            }
            else {
                nieMaKasy = true;
            }
        }
    }
}
