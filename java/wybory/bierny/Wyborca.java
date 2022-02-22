package bierny;

import czynny.Kandydat;

// Abstrakcyjna klasa reprezentująca wyborcę
public abstract class Wyborca {

    protected String imię;
    protected String nazwisko;
    protected Kandydat faworyt = null;

    public Wyborca(String imię, String nazwisko) {
        this.imię = imię;
        this.nazwisko = nazwisko;
    }

//    Abstrakcyjna metoda wyboru faworyta wyborcy, na którego wyborca odda swój głos.
//    Implementacja w każdej nieabstrakcyjnej podklasie wywodzącej się z Wyborcy.
    public abstract void wybórFaworyta(Kandydat[][] macierzKandydatów);

//    Metoda reprezentująca oddanie głosu przez danego wyborcę.
    public void oddajGłos() {
        faworyt.zagłosuj();
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s\n", imię, nazwisko,
                faworyt.imię(), faworyt.nazwisko());
    }
}
