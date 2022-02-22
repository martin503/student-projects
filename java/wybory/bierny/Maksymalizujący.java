package bierny;

import czynny.Kandydat;
import symulacja.Wybory;

// Klasa reprezentująca wyborcę, który ceni daną cechę najbardziej.
public class Maksymalizujący extends Cechowy{

    public Maksymalizujący(String imię, String nazwisko, int numerCechy) {
        super(imię, nazwisko, numerCechy);
    }

    @Override
    protected boolean porównanieWartościCech(int porównywana, int taDruga) {
        return porównywana > taDruga;
    }
}
