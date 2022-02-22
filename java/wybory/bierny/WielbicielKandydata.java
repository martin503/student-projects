package bierny;

import czynny.Kandydat;
import czynny.Partia;

import java.util.HashMap;
import java.util.Map;

// Klasa reprezentująca wyborcę z żelaznego elektoratu kandydata.
public class WielbicielKandydata extends Żelazny {

    private int numerNaLiście;

    public WielbicielKandydata(String imię, String nazwisko, Partia ulubionaPartia, int numerNaLiście) {
        super(imię, nazwisko, ulubionaPartia);
        this.numerNaLiście = numerNaLiście - 1;
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        faworyt = macierzKandydatów[ulubionaPartia.numer()][numerNaLiście];
    }
}
