package bierny;

import czynny.Cechy;
import czynny.Kandydat;

// Klasa reprezentująca wybrocę, dla którego najważniejsze jest to, żeby
// kandydat miał jak najmniej wspólnego z wybraną przez niego cechą.
public class Minimalizujący extends Cechowy{

    public Minimalizujący(String imię, String nazwisko, int numerCechy) {
        super(imię, nazwisko, numerCechy);
    }

    @Override
    protected boolean porównanieWartościCech(int porównywana, int taDruga) {
        return porównywana < taDruga;
    }
}
