package ru.azzgzz.gf;


import ru.azzgzz.gf.field.FieldController;
import ru.azzgzz.gf.field.Irreducible;
import ru.azzgzz.gf.field.PrimeElementTheorem;
import ru.azzgzz.gf.numbers.GaloisNumber;

public class GaloisField {
    public static void main(String[] args) {

        System.out.println("Start");
        FieldController.setCharacteristic(2);
        FieldController.setDimention(2);

        try {
            Irreducible a = new Irreducible();
            GaloisNumber b = new GaloisNumber(3);

            GaloisNumber prime = b.findPrime();

            PrimeElementTheorem pet = new PrimeElementTheorem(prime);

            pet.fillMatches();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
