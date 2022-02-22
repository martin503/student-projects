package bierny;

import czynny.Partia;

// Abstrakcyjna klasa wyborców, których interesuje jedynie określona grupa kandydatów.
public abstract class Żelazny extends Wyborca {

    protected Partia ulubionaPartia;

    public Żelazny(String imię, String nazwisko, Partia ulubionaPartia) {
        super(imię, nazwisko);
        this.ulubionaPartia = ulubionaPartia;
    }
}
