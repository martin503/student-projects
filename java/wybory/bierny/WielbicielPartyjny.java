package bierny;

import czynny.Kandydat;
import czynny.Partia;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Klasa reprezentująca wyborcę z żelaznego elektoratu wyborczego danej partii,
// liczy się dla niego wyłącznie zwycięstwo partii.
public class WielbicielPartyjny extends Żelazny {

    protected static final Random generator = new Random();

    public WielbicielPartyjny(String imię, String nazwisko, Partia ulubionaPartia) {
        super(imię, nazwisko, ulubionaPartia);
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        faworyt = macierzKandydatów[ulubionaPartia.numer()][generator.nextInt(macierzKandydatów[0].length)];
    }
}
