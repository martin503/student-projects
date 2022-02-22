package czynny;

import bierny.OkręgWyborczy;
import bierny.Wszechstronny;
import bierny.Wyborca;

import java.util.ArrayList;

// Klasa abstrakcyjna reprezentująca Partię.
public abstract class Partia {

    protected int numer;
    protected String nazwa;
    protected int budżet;
    protected boolean nieMaKasy;

    public Partia(int numer, String nazwa, int budżet) {
        this.numer = numer;
        this.nazwa = nazwa;
        this.budżet = budżet;
        this.nieMaKasy = false;
    }

//    Funkcja zwraca 1 jeżeli jest kasa i zwraca 0 jeżeli nie ma pieniędzy
//    na żadne z dostępnych działań.
    public int czyJestKasaDoInta() {
        if(nieMaKasy) return 0;
        else return 1;
    }

    public int numer() {
        return numer;
    }

    public String toString() {
        return nazwa;
    }

//    Funkcja abstrakcyjna reprezentująca wykonanie działania przez daną partię.
//    Implementacja zależy od strategii partii.
    public abstract void wykonajDziałanie(ArrayList<OkręgWyborczy> listaOkręgów,
                                          ArrayList<Działanie> listaDziałań);

//    Funkcja pomocnicza, wykonuje wybrane działanie na wybranym okegu wyborczym.
    public void wykonajOkreśloneDziałanie(ArrayList<OkręgWyborczy> listaOkręgów,
                                          ArrayList<Działanie> listaDziałań,
                                          int numerOkręgu, int numerDziałania) {
        for (Wyborca w : listaOkręgów.get(numerOkręgu).listaWyborców()) {
            if (w instanceof Wszechstronny)
                listaDziałań.get(numerDziałania).aplikuj((Wszechstronny) w);
        }
    }

    protected int cenaDziałania(OkręgWyborczy okreg, Działanie działanie) {
        return okreg.listaWyborców().size() * działanie.cena();
    }

    protected boolean czyStaćNaDziałanie(OkręgWyborczy okreg, Działanie działanie) {
        return cenaDziałania(okreg, działanie) <= budżet;
    }
}
