package bierny;

// Klasa reprezentująca scalony okręg wyborczy.
public class ScalonyOkręg extends OkręgWyborczy {
    
    private int numerOkręguOWiększymIndeksie;
    public ScalonyOkręg(OkręgWyborczy okreg1, OkręgWyborczy okreg2) {
        super(okreg1.macierzKandydatów.length,
                okreg1.liczbaWyborców + okreg2.liczbaWyborców,
                okreg1.numerOkręgu);
        numerOkręguOWiększymIndeksie = numerOkręgu + 1;
    }

    @Override
    public String toString() {
        return (numerOkręguOWiększymIndeksie + super.toString());
    }
}
