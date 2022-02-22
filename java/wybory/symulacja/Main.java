package symulacja;

import bierny.Wyborca;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

//    Jako argument programu należy podać nazwę pliku do zczytania.
//    Pilk powinien znajdować się w tym samym folderze co src.
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File toRead = new File(args[0]);
            Wybory wybory = new Wybory(toRead);
            wybory.przeprowadźKampanię();
            wybory.przeprowadźWybory();
            wybory.wynikiDHondta();
            wybory.wynikiSainta();
            wybory.wynikiHara();
        }
        catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        }
        catch (ArrayIndexOutOfBoundsException e2) {
            System.out.println("Nie podano poprawnie nazwy pliku.");
        }
    }
}
