package ru.azzgzz.gf.polynomial;

import ru.azzgzz.gf.field.StaticField;
import ru.azzgzz.gf.numbers.Number;

public class Polynomial {

    private int deg;
    private Number[] coefficients;


    public Polynomial(Number z) {
        deg = 0;
        coefficients = new Number[StaticField.getMaxDeg() + 1];
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            coefficients[i] = z.createNumber();
        }
    }

    public Polynomial(Polynomial x) {
        deg = x.deg;
        setCoefficients(x.coefficients);
    }


    public Polynomial plus(Polynomial x) {
        Polynomial a = new Polynomial(x.coefficients[0]);

        Number[] p = new Number[StaticField.getMaxDeg() + 1];

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            p[i] = coefficients[i].plus(x.coefficients[i]);
        }

        a.setCoefficients(p);
        a.findDeg();
        return a;
    }


    public Polynomial minus(Polynomial x) {
        Polynomial a = new Polynomial(x.coefficients[0]);

        Number[] p = new Number[StaticField.getMaxDeg() + 1];

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            p[i] = coefficients[i].minus(x.coefficients[i]);
        }

        a.setCoefficients(p);
        a.findDeg();

        return a;
    }


    public Polynomial mult(Polynomial x) {
        Polynomial a, b;
        Polynomial result = new Polynomial(x.coefficients[0]);

        if (isMore(x)) {
            a = new Polynomial(this);
            b = new Polynomial(x);
        } else {
            b = new Polynomial(this);
            a = new Polynomial(x);
        }

        for (int i = b.getDeg(); i > 0; i--) {
            result = result.plus(a.scalarMult(b.coefficients[i]));
            result = result.leftShift();
            if (result.deg >= StaticField.getModulo().deg)
                result = result.div(StaticField.getModulo());
        }

        result = result.plus(a.scalarMult(b.coefficients[0]));

        return result;
    }


    public Polynomial div(Polynomial x) {
        if (!isMore(x))
            return this;

        Polynomial a = new Polynomial(this);
        Polynomial b;

        while (a.deg >= x.deg) {
            b = new Polynomial(x);
            while (b.deg < a.deg) {
                b = b.leftShift();
            }
            a = a.minus(b.scalarMult(a.coefficients[a.deg].div(b.coefficients[b.deg])));
        }

        a.findDeg();

        return a;
    }


    public Polynomial scalarMult(Number z) {
        Polynomial a = new Polynomial(this);

        for (int i = 0; i < deg + 1; i++) {
            a.coefficients[i] = a.coefficients[i].mult(z);
        }
        return a;
    }

    protected Polynomial leftShift() {
        Polynomial a = new Polynomial(this);

        a.deg++;
        for (int i = StaticField.getMaxDeg(); i > 0; i--) {
            a.coefficients[i] = a.coefficients[i - 1];
        }
        a.coefficients[0] = a.coefficients[0].createNumber();
        return a;
    }

    public boolean isMore(Polynomial x) {
        return deg >= x.deg;
    }


    private void findDeg() {
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            if (!coefficients[i].isZero())
                deg = i;
        }
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public Number[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Number[] coefficients) {
        this.coefficients = new Number[StaticField.getMaxDeg() + 1];
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            this.coefficients[i] = coefficients[i].clone();
        }
    }

    public boolean isZero() {
        return (deg == 0 && coefficients[0].isZero());
    }

    public boolean isTheOne() {
        return (deg == 0 && coefficients[0].isTheOne());
    }

    public boolean equals(Polynomial p) {
        if (deg == p.deg) {
            for (int i = 0; i < deg; i++) {
                if (!coefficients[i].equals(p.coefficients[i]))
                    return false;
            }
        }
        return true;
    }

    public Polynomial (PolynomOld x) {
        deg = x.getDeg();
        setCoefficients(x.getPol());
    }
}
