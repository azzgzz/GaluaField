package ru.azzgzz.gf.field;

import ru.azzgzz.gf.numbers.GaloisNumber;
import ru.azzgzz.gf.numbers.Number;


public class PrimeElementTheorem {
    private static int[] toDec;
    private static int[] inverseDec;
    private static int[] decZech;
    private static int[] logZech;
    private static int ordinal;
    private static Number prime;
    private static PrimeElementTheorem instance;

    private PrimeElementTheorem() {
    }

    public PrimeElementTheorem(GaloisNumber primee) {
        instance = new PrimeElementTheorem();
        prime = primee;
        ordinal = (int) Math.round(Math.pow(StaticField.getCharacteristic(), StaticField.getDimention()));
        toDec = new int[ordinal];
        inverseDec = new int[ordinal + 1];
        decZech = new int[ordinal + 1];
        logZech = new int[ordinal];
    }

    public void fillMatches() throws ArithmeticException {

        GaloisNumber a = new GaloisNumber(1);
        for (int i = 0; i < ordinal - 1; i++) {
            toDec[i] = a.toDecimal();
            inverseDec[toDec[i]] = i;
            decZech[i] = a.plus(1).toDecimal() == 0 ? ordinal : a.plus(1).toDecimal();
            a = a.mult(prime);
        }
        toDec[ordinal - 1] = ordinal;
        inverseDec[ordinal] = ordinal - 1;
        decZech[ordinal - 1] = 1;
        logZech[ordinal - 1] = 0;

        for (int i = 0; i < ordinal - 1; i++) {
            logZech[i] = inverseDec[decZech[i]];
        }

    }


    public static PrimeElementTheorem getInstance() {
        if (instance == null)
            System.out.println("Unknown prime element\n");
        return instance;
    }

    public void print() {
        System.out.println("i    |   Dec    |Dec(a^(z_i)|   Z_i");
        for (int i = 0; i < ordinal; i++) {
            System.out.println(i + "    " + toDec[i] + "    " + decZech[i] + "    " + logZech[i]);
        }
    }



    public int plus(int a, int b) {
        if (a == ordinal)
            return b;
        if (b == ordinal)
            return a;


        return mult(a, logZech[(b-a + ordinal -1) % (ordinal -1)]);

    }


    public int minus (int a, int b) {
        if (b == ordinal)
            return a;
        if (a == b)
            return ordinal;

        return mult (a, logZech[(ordinal - 2 + b - a + ordinal -1) % (ordinal -1)]);
    }


    public int mult(int a, int b) {
        if (a == ordinal | b == ordinal)
            return ordinal;
        return (a + b) % (ordinal - 1);
    }

    public int div (int a, int b) {
        if (b == ordinal)
            throw new ArithmeticException("Divide by zero in Fast divide");
        if (a == ordinal)
            return ordinal;
        return (a - b + ordinal -1) % (ordinal -1);
    }

    public int getOrdinal() {
        return ordinal;
    }
}
