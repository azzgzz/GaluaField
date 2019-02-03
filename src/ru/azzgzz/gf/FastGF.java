package ru.azzgzz.gf;

import static ru.azzgzz.gf.StaticField.dimention;
import static ru.azzgzz.gf.StaticField.characteristic;
import static ru.azzgzz.gf.StaticField.modulo;

public class FastGF {
    int[] toDec;
    int[] inverseDec;
    int[] decZech;
    int[] logZech;
    int ordinal;

    GFNumber prime;

    FastGF() {
        ordinal = (int) Math.round(Math.pow(characteristic, dimention));
        toDec = new int[ordinal];
        inverseDec = new int[ordinal];
        decZech = new int[ordinal];
        logZech = new int[ordinal];
        prime = null;
    }

    FastGF(GFNumber prime) {
        this.prime = prime;
        ordinal = (int) Math.round(Math.pow(characteristic, dimention));
        toDec = new int[ordinal];
        inverseDec = new int[ordinal + 1];
        decZech = new int[ordinal + 1];
        logZech = new int[ordinal];
    }

    public void fillMatches() throws Exception {
        GFNumber a = new GFNumber(1);
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


    public void print() {
        System.out.println("i    |   Dec    |Dec(a^(z_i)|   Z_i");
        for (int i = 0; i < ordinal; i++) {
            System.out.println(i + "    " + toDec[i] + "    " + decZech[i] + "    " + logZech[i]);
        }
    }

    public int mult(int a, int b) {
        if (a == ordinal - 1 | b == ordinal - 1)
            return ordinal;
        return (a + b) % (ordinal - 1);
    }


    public int sum(int a, int b) {
        if (a == ordinal - 1)
            return b;
        if (b == ordinal - 1)
            return a;

        a = a % (ordinal-1);
        b = a % (ordinal-1);

        if (a<b)
            return mult(a, logZech[b-a]);
        else
            return  mult(b, logZech[a-b]);

    }
}
