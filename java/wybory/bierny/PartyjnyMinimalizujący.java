package bierny;

import czynny.Kandydat;
import czynny.Partia;

import java.util.HashMap;

// Klasa która reprezentuje wyborcę Minimalizującego, który należy również
// do żelaznego elektoratu danej partii.
public class PartyjnyMinimalizujący extends Minimalizujący{

    private Partia ulubionaPartia;

    public PartyjnyMinimalizujący(String imię, String nazwisko, int numerCechy, Partia ulubionaPartia) {
        super(imię, nazwisko, numerCechy);
        this.ulubionaPartia = ulubionaPartia;
    }

    public void wybórFaworyta(Kandydat[][] macierzKandydatów) {
        wybórFaworytaPartii(macierzKandydatów[ulubionaPartia.numer()]);
    }
}
