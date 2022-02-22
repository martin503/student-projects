package czynny;

import bierny.OkręgWyborczy;

import java.util.ArrayList;
import java.util.Random;

// Klasa reprezentująca miłośników hazardu i gry w lotto.
public class MiłośnicyLotto extends Partia {

    private static final Random generator = new Random();

    public MiłośnicyLotto(int numer, String nazwa, int budżet) {
        super(numer, nazwa, budżet);
    }

//    Miłośnicy Lotta mają własną maszynę losującą, która ich zdaniem
//    jest kluczem do zwycięstwa w wyborach. Jednak sztab wyborczy nie
//    składa się ze spokojnych ludzi. Jeżeli maszyna losująca 4 razy pod rząd
//    każe wykonać działanie na okręgu, które nie da się pokryć budżetem partii,
//    sztab wyborczy traci rezon i wybiera najtańsze przedsiemwzięcie.
    public void wykonajDziałanie(ArrayList<OkręgWyborczy> listaOkręgów, ArrayList<Działanie> listaDziałań) {
        if (!nieMaKasy) {
            for (int i = 0; i < 4; i++) {
                int losowyOkreg = generator.nextInt(listaOkręgów.size());
                int losoweDziałanie = generator.nextInt(listaDziałań.size());
                if (czyStaćNaDziałanie(listaOkręgów.get(losowyOkreg), listaDziałań.get(losoweDziałanie))) {
                    wykonajOkreśloneDziałanie(listaOkręgów, listaDziałań, losowyOkreg, losoweDziałanie);
                    budżet -= cenaDziałania(listaOkręgów.get(losowyOkreg), listaDziałań.get(losoweDziałanie));
                    return;
                }
            }
            if (czyStaćNaDziałanie(listaOkręgów.get(0), listaDziałań.get(0))) {
                wykonajOkreśloneDziałanie(listaOkręgów, listaDziałań, 0, 0);
                budżet -= cenaDziałania(listaOkręgów.get(0), listaDziałań.get(0));
            }
            else
                nieMaKasy = true;
        }
    }
}
