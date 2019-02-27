package ru.azzgzz.gf.numbers;

import ru.azzgzz.gf.polynomial.PolynomOld;
import ru.azzgzz.gf.polynomial.Polynomial;
import ru.azzgzz.gf.field.StaticField;


public class GaloisNumber extends Polynomial implements Number {

    private Polynomial modulo = StaticField.getModulo();


    public GaloisNumber() {
        super(new ZpNumber());
    }

    public GaloisNumber(int x) {
        super(new ZpNumber(x));
    }

    public GaloisNumber(Number z) {
        super(z);
    }

    public GaloisNumber(Polynomial x) {
        super(x);
    }

    public GaloisNumber(GaloisNumber x) {
        super((Polynomial) x);
    }


    public GaloisNumber plus (int x) {
        return new GaloisNumber (plus((Polynomial) new GaloisNumber(x)));
    }

    @Override
    public GaloisNumber plus(Number x) {
        if (x instanceof GaloisNumber)
            return new GaloisNumber(plus((Polynomial) x));
        else
            return new GaloisNumber( this);
    }

    @Override
    public GaloisNumber minus(Number x) {
        if (x instanceof GaloisNumber)
            return new GaloisNumber(minus((Polynomial) x));
        else
            return new GaloisNumber( this);
    }

    @Override
    public GaloisNumber mult(Number x) {
        if (x instanceof GaloisNumber)
            return new GaloisNumber(mult((Polynomial) x));
        else
            return new GaloisNumber( this);
    }

    @Override
    public GaloisNumber div(Number x) throws ArithmeticException {
        if (x instanceof GaloisNumber) {
            return mult((Number) ((GaloisNumber) x).inverse());
        }
        else
            return new GaloisNumber( this);
    }


    private GaloisNumber inverse() {
        GaloisNumber a = new GaloisNumber(this);

        a = a.toPower((int) Math.round(Math.pow(StaticField.getCharacteristic(), StaticField.getDimention())) - 2);

        return a;
    }

    public GaloisNumber toPower(int x) throws ArithmeticException {
        x = x % ((int) Math.round(Math.pow(StaticField.getCharacteristic(), StaticField.getDimention())));
        GaloisNumber a = new GaloisNumber(new ZpNumber(1));
        for (int i = 1; i < x; i++) {
            a = (GaloisNumber) a.mult((Polynomial) this);
        }
        return a;
    }


    @Override
    public GaloisNumber div (Polynomial x) {
        return new GaloisNumber(mult ((Polynomial) new GaloisNumber(x).inverse()));
    }

    @Override
    public GaloisNumber createNumber() {
        return new GaloisNumber();
    }

    @Override
    public GaloisNumber createNumber(int x) {
        return new GaloisNumber(new ZpNumber(x));
    }

    @Override
    public GaloisNumber clone() {
        return new GaloisNumber((Polynomial) this);
    }

    @Override
    public boolean equals(Number x) {
        if (x instanceof Polynomial)
            return equals((Polynomial) x);
        else
            return false;
    }

    public int toDecimal(){
        Number[] elements = getCoefficients();
        int decimal = 0;
        for (int i = 0; i <= getDeg(); i++) {
            decimal += ((int) Math.round(Math.pow(StaticField.getCharacteristic(), i))) * elements[i].getValue();
        }
        return decimal;
    }

    @Override
    public int getValue() {
        return 0;
    }

    public GaloisNumber findPrime() {
        PolynomOld a = new PolynomOld(new int[] {1, 0});
        return new GaloisNumber(new Polynomial(a));
    }
}
