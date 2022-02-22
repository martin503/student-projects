package symulacja;

import bierny.OkręgWyborczy;

import java.util.ArrayList;

// Komisja wyborcza, która liczy metodą Dhondta.
public class DHondta extends LiczenieZWykładnikiem {

    public int[][] zliczMandaty(ArrayList<OkręgWyborczy> listaOkręgów) {
        return zliczMandaty(listaOkręgów, 1);
    }

    @Override
    public String toString() {
        return "D’Hondta";
    }
}
