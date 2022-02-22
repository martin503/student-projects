package bierny;

import czynny.Cechy;
import czynny.Kandydat;
import czynny.Partia;

import java.util.HashMap;

// Klasa reprezentująca wyborcę wszechstronnego, który należy również
// do żelaznego elektoratu danej partii.
public class PartyjnyWszechstronny extends Wszechstronny{

    private Partia ulubionaPartia;

    public PartyjnyWszechstronny(String imię, String nazwisko, Cechy cechy, Partia ulubionaPartia) {
        super(imię, nazwisko, cechy);
        this.ulubionaPartia = ulubionaPartia;
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        int div = 0;
        for (int i = 0; i < cechy.liczbaCech(); i++) {
            div += cechy.pokażCechę(i);
        }
        myInt actualBestSum = new myInt(-1);
        wybórFaworytaPartii(macierzKandydatów[ulubionaPartia.numer()], div, actualBestSum);
    }
}
