package ru.azzgzz.gf.numbers;

public interface Number {

    Number plus(Number x);

    Number minus(Number x);

    Number mult(Number x);

    Number div(Number x) throws ArithmeticException;

    Number createNumber();

    Number createNumber(int x);

    Number clone();

    boolean isZero();

    boolean isTheOne();

    boolean equals(Number x);

    public int getValue();
}
