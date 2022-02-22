package bierny;

import czynny.Cechy;
import czynny.Kandydat;

// Abstrakcyjna klasa wyborców, którzy skupiają się wyłącznie
// na jednej cesze kandydatów.
public abstract class Cechowy extends Wyborca {

    protected int numerCechy;

    public Cechowy(String imię, String nazwisko, int numerCechy) {
        super(imię, nazwisko);
        this.numerCechy = numerCechy - 1;
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        for (Kandydat[] listaPartii : macierzKandydatów) {
            wybórFaworytaPartii(listaPartii);
        }
    }

    protected void wybórFaworytaPartii(Kandydat[] tablica) {
        for (Kandydat k : tablica) {
            if (faworyt == null || porównanieWartościCech(faworyt.cechy().pokażCechę(numerCechy),
                    k.cechy().pokażCechę(numerCechy)))
                faworyt = k;
        }
    }

    protected abstract boolean porównanieWartościCech(int porównywana, int taDruga);
}
