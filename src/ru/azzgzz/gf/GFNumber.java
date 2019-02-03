package ru.azzgzz.gf;


import static ru.azzgzz.gf.StaticField.characteristic;
import static ru.azzgzz.gf.StaticField.dimention;
import static ru.azzgzz.gf.StaticField.modulo;

public class GFNumber {

    private Polynom element;
    private boolean isZero;

    GFNumber() {
        element = new Polynom();
        isZero = true;
    }

    GFNumber(int x) {
        element = new Polynom(x);
        if (x == 0)
            isZero = true;
        else
            isZero = false;
    }

    GFNumber(GFNumber x) {
        element = new Polynom(x.element);
        isZero = x.isZero;
    }

    public boolean isZero() {
        return isZero;
    }

    public boolean isTheOne() {
        return (element.getDeg() == 0 && element.getPol()[0].isEqual(1));
    }

    public GFNumber plus(GFNumber x) {
        GFNumber a = new GFNumber();
        a.element = new Polynom(element.plus(x.element));
        a.isZero = element.isZero();
        return a;
    }

    public GFNumber plus (int x) {
        GFNumber a = new GFNumber();
        a.element = new Polynom(element.plus(new Polynom(x)));
        a.isZero = element.isZero();
        return a;
    }

    public GFNumber minus(GFNumber x) {
        GFNumber a = new GFNumber();
        a.element = new Polynom(element.minus(x.element));
        a.isZero = element.isZero();
        return a;
    }

    public GFNumber mult(GFNumber x) throws Exception {
        if (isZero || x.isZero)
            return new GFNumber();
        GFNumber a = new GFNumber();
        a.element = new Polynom(element.simpleMult(x.element));
        a.isZero = element.isZero();
        return a;
    }

    public GFNumber div(GFNumber x) throws Exception {
        if (x.isZero)
            throw new Exception("Divide by zero in GFNumberDiv");
        if (isZero)
            return new GFNumber();

        return new GFNumber(this.mult(x.inverse()));
    }

    private GFNumber inverse() throws Exception {
        GFNumber a = new GFNumber(this);

        a = a.toPower((int) Math.round(Math.pow(characteristic, dimention)) - 2);

        return a;
    }

    public GFNumber toPower(int x) throws Exception {
        x = x % ((int) Math.round(Math.pow(characteristic, dimention)));
        GFNumber a = new GFNumber(1);
        for (int i = 1; i < x; i++) {
            a = a.mult(this);
        }
        return a;
    }


    @Override
    public String toString() {
        return element.toString();
    }

    public int ordinal() throws Exception {
        if (isZero())
            return 0;
        if (isTheOne())
            return 1;
        GFNumber a = new GFNumber(this);
        int k = 1;
        while (!a.isTheOne()) {
            a = a.mult(this);
            k++;
        }
        return k;
    }

    private int gcd(int a, int b, int[] xy) {
        if (a == 0) {
            xy[0] = 0;
            xy[1] = 1;
            return b;
        }
        int[] xy1 = {0, 0};
        int d = gcd(b % a, a, xy1);
        xy[0] = xy1[1] - (b / a) * xy1[0];
        xy[1] = xy1[0];
        return d;
    }

    public GFNumber plusOne() throws Exception {
        GFNumber a = new GFNumber(this);
        a.element.plusOne();
        if (a.element.getDeg() >= modulo.getDeg())
            a.element.simpleMod(modulo);
        isZero = element.isZero();

        return a;
    }

    public GFNumber findPrime() throws Exception {
        GFNumber a = new GFNumber(characteristic - 1);
        GFNumber b = new GFNumber(a.plusOne());
        int k = a.ordinal();
        int m = (int) Math.round(Math.pow(characteristic, dimention)) - 1;

        while (k < m) {
            a = a.mult(b);
            b = b.plusOne();
            k = a.ordinal();
        }
        return a;
    }




    public int toDecimal(){
        int deg = element.getDeg();
        int decimal = 0;
        for (int i = 0; i <= deg; i++) {
            decimal += ((int) Math.round(Math.pow(characteristic, i))) * element.getPol()[i].getValue();
        }
        return decimal;
    }
}
