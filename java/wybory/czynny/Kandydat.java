package czynny;

// Klasa reprezentująca kandydatów na posłów.
public class Kandydat {

    private String imię;
    private String nazwisko;
    private Partia partia;
    private int numerNaLiście;
    private int liczbaGłosów;
    private Cechy cechy;

    public Kandydat(String imię, String nazwisko, Partia partia, int numerNaLiście, Cechy cechy) {
        this.imię = imię;
        this.nazwisko = nazwisko;
        this.partia = partia;
        this.numerNaLiście = numerNaLiście - 1;
        this.liczbaGłosów = 0;
        this.cechy = cechy;
    }

//    Funkcja oddania głosu na kandydata.
    public void zagłosuj() {
        liczbaGłosów++;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d %d\n", imię, nazwisko, partia.toString(), numerNaLiście + 1, liczbaGłosów);
    }

    public Cechy cechy() {
        return cechy;
    }

    public Partia partia() {
        return partia;
    }

    public int liczbaGłosów() {
        return liczbaGłosów;
    }

    public String imię() {
        return imię;
    }

    public String nazwisko() {
        return nazwisko;
    }
}
