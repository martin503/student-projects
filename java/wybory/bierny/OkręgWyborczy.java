package bierny;

import czynny.Kandydat;
import czynny.Partia;

import java.util.ArrayList;
import java.util.HashMap;

// Klasa reprezentująca pojedynczy okręg wyborczy.
public class OkręgWyborczy implements Comparable<OkręgWyborczy> {

    protected int liczbaWyborców;
    protected ArrayList<Wyborca> listaWyborców;
    protected Kandydat[][] macierzKandydatów;
    protected int numerOkręgu;

    public OkręgWyborczy(int ilePartii, int ileWyborców, int numerOkręgu) {
        this.liczbaWyborców = ileWyborców;
        this.listaWyborców = new ArrayList<Wyborca>(ileWyborców);
        this.macierzKandydatów = new Kandydat[ilePartii][ileWyborców / 10];
        this.numerOkręgu = numerOkręgu;
    }

    public void dodajWyborce(Wyborca doDodania) {
        listaWyborców.add(doDodania);
    }

    public int liczbaWyborców() {
        return liczbaWyborców;
    }

    public int ileMandatów() {
        return liczbaWyborców / 10;
    }

    public ArrayList<Wyborca> listaWyborców() {
        return listaWyborców;
    }

    public Kandydat[][] macierzKandydatów() {
        return macierzKandydatów;
    }

    public int compareTo(OkręgWyborczy doPorównania) {
        int wielkośćTego = listaWyborców.size();
        int wielkośćDoPorównania = doPorównania.listaWyborców.size();
        return Integer.compare(wielkośćTego, wielkośćDoPorównania);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder(String.format("%d\n", numerOkręgu + 1));
        for (Wyborca wyborca : listaWyborców) {
            toReturn.append(wyborca.toString());
        }
        for (Kandydat[] listaPartii : macierzKandydatów) {
            for (Kandydat kandydat : listaPartii) {
                toReturn.append(kandydat.toString());
            }
        }
        return toReturn.toString();
    }
}
