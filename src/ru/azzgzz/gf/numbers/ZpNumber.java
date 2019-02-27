package ru.azzgzz.gf.numbers;

import ru.azzgzz.gf.field.StaticField;

public class ZpNumber implements Number {

    private int modulo = StaticField.getCharacteristic();
    private int value;

    public ZpNumber() {
        value = 0;
    }

    public ZpNumber(int val) {
        value = val % modulo;
    }

    public ZpNumber(ZpNumber x) {
        value = x.value;
    }


    public ZpNumber plus(Number x) {
        if (x instanceof ZpNumber)
            return new ZpNumber((value + ((ZpNumber) x).value) % modulo);
        else
            return new ZpNumber(this);
    }


    public ZpNumber minus(Number x) {
        if (x instanceof ZpNumber)
            return new ZpNumber((value + modulo - ((ZpNumber) x).value) % modulo);
        else
            return new ZpNumber(this);
    }


    public ZpNumber mult(Number x) {
        if (x instanceof ZpNumber)
            return new ZpNumber((value * ((ZpNumber) x).value) % modulo);
        else
            return new ZpNumber(this);
    }


    public ZpNumber div(Number x) throws ArithmeticException {
        if (!(x instanceof ZpNumber))
            return new ZpNumber(this);
        ZpNumber y = (ZpNumber) x;
        try {
            return mult(y.inverse());
        } catch (ArithmeticException e) {
            System.out.println(e);
            throw e;
        }
    }


    private ZpNumber inverse() throws ArithmeticException {
        int a = value;
        int m = modulo;

        if (a == 0)
            throw new ArithmeticException("divide by zero in ZpNumber.inverse()");

        int[] xy = {0, 0};

        int g = gcd(a, m, xy);

        if (g > 1)
            System.out.println("modulo does not prime!");

        return new ZpNumber((xy[0] + m) % m);
    }

    @Override
    public boolean isZero() {
        return value == 0;
    }

    @Override
    public boolean isTheOne() {
        return value == 1;
    }

    public boolean isEqual(int x) {
        return value == x;
    }

    @Override
    public boolean equals(Number x) {
        if (x instanceof ZpNumber)
            return value == ((ZpNumber) x).value;
        else
            return false;
    }

    public void plusOne() {
        value = (value + 1) % modulo;
    }

    @Override
    public String toString() {
        return "" + value;
    }


    @Override
    public Number createNumber() {
        return new ZpNumber();
    }

    @Override
    public Number createNumber(int x) {
        return new ZpNumber(x);
    }

    @Override
    public Number clone() {
        return new ZpNumber(this);
    }

    /**
     * advanced euclidean algorithm
     *
     * @param xy supporting parameter, xy[0] == 1/a
     * @return greatest common divider
     */
    public int gcd(int a, int b, int[] xy) {
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

    @Override
    public int getValue() {
        return value;
    }
}










