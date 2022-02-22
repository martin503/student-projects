package symulacja;

import bierny.OkręgWyborczy;

import java.util.ArrayList;

// Komisja wyborcza, która liczy metodą Sainta.
public class Sainte extends LiczenieZWykładnikiem {

    public int[][] zliczMandaty(ArrayList<OkręgWyborczy> listaOkręgów) {
        return zliczMandaty(listaOkręgów, 2);
    }

    @Override
    public String toString() {
        return "Sainte-Laguë";
    }
}
