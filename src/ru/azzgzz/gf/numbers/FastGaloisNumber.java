package ru.azzgzz.gf.numbers;

import ru.azzgzz.gf.field.PrimeElementTheorem;

public class FastGaloisNumber implements Number {

    int value;
    private PrimeElementTheorem pet = PrimeElementTheorem.getInstance();


    FastGaloisNumber() {
        value = 0;
    }

    FastGaloisNumber(int x) {
        value = x;
    }

    FastGaloisNumber(FastGaloisNumber x) {
        value = x.value;
    }

    @Override
    public FastGaloisNumber plus(Number x) {
        if (x instanceof FastGaloisNumber)
            return new FastGaloisNumber(pet.plus(value, x.getValue()));
        else
            return new FastGaloisNumber(this);
    }

    @Override
    public FastGaloisNumber minus(Number x) {
        if (x instanceof FastGaloisNumber)
            return new FastGaloisNumber(pet.minus(value, x.getValue()));
        else
            return new FastGaloisNumber(this);
    }

    @Override
    public FastGaloisNumber mult(Number x) {
        if (x instanceof FastGaloisNumber)
            return new FastGaloisNumber(pet.mult(value, x.getValue()));
        else
            return new FastGaloisNumber(this);
    }

    @Override
    public FastGaloisNumber div(Number x) throws ArithmeticException {
        if (x instanceof FastGaloisNumber)
            return new FastGaloisNumber(pet.div(value, x.getValue()));
        else
            return new FastGaloisNumber(this);
    }

    @Override
    public FastGaloisNumber createNumber() {
        return new FastGaloisNumber();
    }

    @Override
    public FastGaloisNumber createNumber(int x) {
        return new FastGaloisNumber(x);
    }

    @Override
    public FastGaloisNumber clone() {
        return new FastGaloisNumber(this);
    }

    @Override
    public boolean isZero() {
        return value == pet.getOrdinal();
    }

    @Override
    public boolean isTheOne() {
        return value == 0;
    }

    @Override
    public boolean equals(Number x) {
        if (x instanceof FastGaloisNumber)
            return value == x.getValue();
        else
            return false;
    }

    @Override
    public int getValue() {
        return value;
    }
}
