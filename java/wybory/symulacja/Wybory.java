package symulacja;

import bierny.*;
import czynny.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

// Klasa, której celem jest symulacja wyborów.
// Dane są pobierane ze standardowego wejścia.
public class Wybory {

    private HashMap<String, Partia> mapaPartii;
    private ArrayList<Działanie> listaDziałań; // Posortowana po koszcie działań
    private ArrayList<OkręgWyborczy> listaOkręgów; // Posortowana po ilości wyborców
    private Partia[] tablicaPartii;
    private DHondta metodaDHondta;
    private Sainte metodaSainta;
    private Hare metodaHara;

    public Wybory(File toRead) throws FileNotFoundException {
        Scanner skaner = new Scanner(toRead);
        skaner.nextInt();
        skaner.nextInt();
        int liczbaDziałań = skaner.nextInt();
        int liczbaCech = skaner.nextInt();
        skaner.nextLine();
        int[] indeksyDoScalenia = weźNaturalneIntyZLinii(skaner);
        String[] nazwyPartii = weźStringiZLinii(skaner);
        int[] budżetyPartii = weźNaturalneIntyZLinii(skaner);
        String[] strategiePartii = weźStringiZLinii(skaner);
        int[] wyborcyWOkręgach = weźNaturalneIntyZLinii(skaner);
        this.tablicaPartii = stwórzTablicePartii(nazwyPartii,
                budżetyPartii, strategiePartii);
        this.mapaPartii = new HashMap<String, Partia>();
        inicjalizujMapę(tablicaPartii);
        this.listaOkręgów = stwórzListęOkręgówWyborczych(this.tablicaPartii,
                wyborcyWOkręgach, indeksyDoScalenia);

        for (OkręgWyborczy okreg : listaOkręgów) {
            for (Kandydat[] kandydaciPartii : okreg.macierzKandydatów()) {
                for (int i = 0; i < kandydaciPartii.length; i++) {
                    kandydaciPartii[i] = weźKandydata(skaner, mapaPartii);
                }
            }
        }

        for (OkręgWyborczy okreg : listaOkręgów) {
            for (int i = 0; i < okreg.liczbaWyborców(); i++) {
                okreg.dodajWyborce(weźWyborcę(skaner));
            }
        }

//        Sortowanie okręgów po ilości wyborców.
        Collections.sort(listaOkręgów);

        this.listaDziałań = new ArrayList<>(liczbaDziałań);
        for (int i = 0; i < liczbaDziałań; i++) {
                listaDziałań.add(new Działanie(weźInty(skaner, liczbaCech)));
        }

//        Sortowanie działań po ich koszcie.
        Collections.sort(listaDziałań);

        this.metodaDHondta = new DHondta();
        this.metodaHara = new Hare();
        this.metodaSainta = new Sainte();
        skaner.close();
    }

//    Funkcja przeprowadzania kampanii wyborczej.
    public void przeprowadźKampanię() {
        while (czyJakiśMaKasę())
        for (Partia partia : tablicaPartii) {
            partia.wykonajDziałanie(listaOkręgów, listaDziałań);
        }
    }

//    Funkcja pomocnicza mówiąca czy którakolwiek z partii, może
//    jeszcze wykonywać jakieś działanie.
    private boolean czyJakiśMaKasę() {
        int[] tablicaKasy = new int[tablicaPartii.length];
        for (int i = 0; i < tablicaKasy.length; i++) {
            tablicaKasy[i] = tablicaPartii[i].czyJestKasaDoInta();
        }
        return IntStream.of(tablicaKasy).sum() > 0;
    }

//    Wypisuje wyniki wyborów, przy przeliczaniu głosów metoda DHondta.
    public void wynikiDHondta() {
        metodaDHondta.wynikiWyborów(listaOkręgów, tablicaPartii);
    }

//    Wypisuje wyniki wyborów, przy przeliczaniu głosów metoda Sainta.
    public void wynikiSainta() {
        metodaSainta.wynikiWyborów(listaOkręgów, tablicaPartii);
    }

//    Wypisuje wyniki wyborów, przy przeliczaniu głosów metoda Hara.
    public void wynikiHara() {
        metodaHara.wynikiWyborów(listaOkręgów, tablicaPartii);
    }

//    Przeprowadza wybory, każdy wyborca oddaje głos.
    public void przeprowadźWybory() {
        for (OkręgWyborczy okręg : listaOkręgów) {
            for (Wyborca wyborca : okręg.listaWyborców()) {
                wyborca.wybórFaworyta(okręg.macierzKandydatów());
                wyborca.oddajGłos();
            }
        }
    }

//    Inicjalizuje mapę partii, kluczami są nazwy, wartościami Partie.
    private void inicjalizujMapę(Partia[] partie) {
        for (Partia p : partie) {
            mapaPartii.put(p.toString(), p);
        }
    }

//    Bierze wyborcę ze standardowego wejścia.
    private Wyborca weźWyborcę(Scanner skaner) {
        String[] input = weźStringiZLinii(skaner);
        Wyborca wyborcaDoZwrotu = null;
        switch (input[3]) {
            case "1":
                wyborcaDoZwrotu = new WielbicielPartyjny(input[0],
                        input[1], mapaPartii.get(input[4]));
                break;
            case "2":
                wyborcaDoZwrotu = new WielbicielKandydata(input[0], input[1],
                        mapaPartii.get(input[4]), Integer.parseInt(input[5]));
                break;
            case "3":
                wyborcaDoZwrotu = new Minimalizujący(input[0], input[1],
                        Integer.parseInt(input[4]));
                break;
            case "4":
                wyborcaDoZwrotu = new Maksymalizujący(input[0], input[1],
                        Integer.parseInt(input[4]));
                break;
            case "5":
                int[] tablicaWartościCech = new int[input.length - 4];
                for (int i = 4; i < input.length; i++) {
                    if (!input[i].equals(""))
                        tablicaWartościCech[i - 4] = (Integer.parseInt(input[i]));
                }
                wyborcaDoZwrotu = new Wszechstronny(input[0], input[1],
                        new Cechy(tablicaWartościCech));
                break;
            case "6":
                wyborcaDoZwrotu = new PartyjnyMinimalizujący(input[0],
                        input[1], Integer.parseInt(input[4]),
                        mapaPartii.get(input[5]));
                break;
            case "7":
                wyborcaDoZwrotu = new PartyjnyMaksymalizujący(input[0],
                        input[1], Integer.parseInt(input[4]), mapaPartii.get(input[5]));
                break;
            case "8":
                int[] tablicaWartościCech2 = new int[input.length - 5];
                int i = 4;
                while (i < input.length - 1) {
                    if (!input[i].equals(""))
                        tablicaWartościCech2[i - 4] = (Integer.parseInt(input[i]));
                    i++;
                }
                wyborcaDoZwrotu = new PartyjnyWszechstronny(input[0],
                        input[1], new Cechy(tablicaWartościCech2),
                        mapaPartii.get(input[i]));
                break;
        }
        return wyborcaDoZwrotu;
    }

//    Bierze kandydata ze standardowego wejścia.
    private Kandydat weźKandydata(Scanner skaner, HashMap<String, Partia> mapaPartii) {
        String[] input = weźStringiZLinii(skaner);
        int[] tablicaWartościCech = new int[input.length - 5];
        for (int i = 5; i < input.length; i++) {
            if (!input[i].equals(""))
                tablicaWartościCech[i - 5] = (Integer.parseInt(input[i]));
        }
        return new Kandydat(input[0], input[1], mapaPartii.get(input[3]),
                Integer.parseInt(input[4]), new Cechy(tablicaWartościCech));
    }

//    Tworzy listę okręgów wyborczych (już scalonych), na podstawie tablicy partii,
//    tablicy mówiącej o ilości wyborców w każdym podstawowym okręgu i tablicy
//    indeksów podstawowych okręgów wyborczzych do scalenia.
    private ArrayList<OkręgWyborczy> stwórzListęOkręgówWyborczych
    (Partia[] partie,int[] ilościWyborców, int[] indeksyDoScalenia) {
        OkręgWyborczy[] podstawoweOkręgiWyborcze =
                podstawoweOkregiWyborcze(partie, ilościWyborców);
        ArrayList<OkręgWyborczy> listaDoZwrotu = new ArrayList<>
                (podstawoweOkręgiWyborcze.length - (indeksyDoScalenia.length - 1) / 2);
        for (int indeksOkregu = 0, indeksScalania = 1;
             indeksOkregu < podstawoweOkręgiWyborcze.length; indeksOkregu++) {
            if (indeksScalania == indeksyDoScalenia.length ||
                    indeksyDoScalenia[indeksScalania] - 1 > indeksOkregu)
                listaDoZwrotu.add(podstawoweOkręgiWyborcze[indeksOkregu]);
            else {
                indeksScalania += 2;
                listaDoZwrotu.add(new ScalonyOkręg(podstawoweOkręgiWyborcze[indeksOkregu],
                        podstawoweOkręgiWyborcze[indeksOkregu + 1]));
                indeksOkregu++;
            }
        }
        return listaDoZwrotu;
    }

//    Tworzy tablicę podstawowych okręgów wyborczych.
    private OkręgWyborczy[] podstawoweOkregiWyborcze
    (Partia[] partie, int[] ilościWyborców) {
        OkręgWyborczy[] podstawoweOkręgiWyborcze =
                new OkręgWyborczy[ilościWyborców.length];
        for (int i = 0; i < ilościWyborców.length; i++) {
            podstawoweOkręgiWyborcze[i] =
                    new OkręgWyborczy(partie.length, ilościWyborców[i], i);
        }
        return podstawoweOkręgiWyborcze;
    }

//    Tworzy tablice partii, na podstawie ich nazw, budżetów i strategii.
    private Partia[] stwórzTablicePartii
    (String[] nazwyPartii, int[] budżetyPartii, String[] strategiePartii) {
        Partia[] tablicaDoZwrotu = new Partia[nazwyPartii.length];
        for (int i = 0; i < nazwyPartii.length; i++) {
            switch (strategiePartii[i]) {
                case "R":
                    tablicaDoZwrotu[i] = new Rozrzutna(i,
                            nazwyPartii[i], budżetyPartii[i]);
                    break;
                case "S":
                    tablicaDoZwrotu[i] = new Oszczędna(i,
                            nazwyPartii[i], budżetyPartii[i]);
                    break;
                case "Z":
                    tablicaDoZwrotu[i] = new Zachłanna(i,
                            nazwyPartii[i], budżetyPartii[i]);
                    break;
                case "W":
                    tablicaDoZwrotu[i] = new MiłośnicyLotto(i,
                            nazwyPartii[i], budżetyPartii[i]);
                    break;
            }
        }
        return tablicaDoZwrotu;
    }

//    Funkcja, która bierze ze standardowego wejścia określoną liczbę intów.
    private int[] weźInty(Scanner skaner, int ileIntów) {
        int[] doZwrotu = new int[ileIntów];
        for (int i = 0; i < ileIntów; i++) {
            doZwrotu[i] = skaner.nextInt();
        }
        return doZwrotu;
    }

//    Funkcja, która bierze z standardowego wejścia linię naturalnych intów.
    private int[] weźNaturalneIntyZLinii(Scanner skaner) {
        String linia = skaner.nextLine().trim();
        String[] podzielonaLinia = linia.split("\\s*(,|\\s)\\s*");
        int[] tablicaDoZwrotu = new int[podzielonaLinia.length];
        for (int i = 0; i < tablicaDoZwrotu.length; i++) {
            tablicaDoZwrotu[i] =
                    (Integer.parseInt(podzielonaLinia[i].replaceAll("[\\D]", "")));
        }
        return tablicaDoZwrotu;
    }

//    Funkcja, która bierze z standardowego wejścia linię wyrazów.
    private String[] weźStringiZLinii(Scanner skaner) {
        String linia = skaner.nextLine();
        return linia.split(" ");
    }
}
