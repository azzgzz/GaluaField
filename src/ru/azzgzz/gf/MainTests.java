package ru.azzgzz.gf;

//import java.util.Scanner;

import static ru.azzgzz.gf.StaticField.dimention;
import static ru.azzgzz.gf.StaticField.characteristic;
import static ru.azzgzz.gf.StaticField.modulo;

public class MainTests {
    public static void main(String[] args) {

        System.out.println("Start");
        characteristic = 2;
        dimention = 2;

        try {
            Irreducible a = new Irreducible();
            GFNumber b = new GFNumber(3);

            GFNumber prime = b.findPrime();
            System.out.println(b);
            System.out.println(modulo);

            FastGF fgf = new FastGF(prime);

            fgf.fillMatches();
            fgf.print();

        } catch (Exception e) {
            System.out.println(e);
        }


        //in.close();
    }
}
