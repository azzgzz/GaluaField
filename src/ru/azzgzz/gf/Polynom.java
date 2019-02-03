package ru.azzgzz.gf;


import static ru.azzgzz.gf.StaticField.*;

public class Polynom {

    private int deg;
    private ZpNumber[] pol;


    Polynom() {
        deg = 0;
        pol = new ZpNumber[maxDeg + 1];
        for (int i = 0; i < maxDeg + 1; i++) {
            pol[i] = new ZpNumber();
        }
    }


    Polynom(Polynom x) {
        setDeg(x.getDeg());
        setPol(x.getPol());
    }

    Polynom(int[] p) {
        setDeg(p.length - 1);
        this.pol = new ZpNumber[maxDeg + 1];
        for (int i = 0; i < p.length; i++) {
            pol[i] = new ZpNumber(p[i] % characteristic);
        }
        for (int i = p.length; i < maxDeg + 1; i++) {
            pol[i] = new ZpNumber(0);
        }

        for (int i = 0; i < maxDeg + 1; i++) {
            if (!pol[i].isZero())
                deg = i;
        }
    }

    Polynom(int x) {
        deg = 0;
        pol = new ZpNumber[maxDeg + 1];
        pol[0] = new ZpNumber(x);
        for (int i = 1; i < maxDeg + 1; i++) {
            pol[i] = new ZpNumber();
        }
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public ZpNumber[] getPol() {
        return pol;
    }

    public void setPol(ZpNumber[] pol) {
        this.pol = new ZpNumber[maxDeg + 1];
        for (int i = 0; i < maxDeg + 1; i++) {
            //System.out.print(pol[i]);
            this.pol[i] = new ZpNumber(pol[i]);
            //System.out.println(" " + this.pol[i]);
        }
    }


    public Polynom plus(Polynom x) {
        Polynom a = new Polynom();
        a.setDeg(getDeg() > x.getDeg() ? getDeg() : x.getDeg());

        ZpNumber[] p = new ZpNumber[maxDeg + 1];
        ZpNumber[] p1 = getPol();
        ZpNumber[] p2 = x.getPol();

        for (int i = 0; i < maxDeg + 1; i++) {
            p[i] = p1[i].plus(p2[i]);
            //System.out.println(p[i]);
        }

        a.setPol(p);
        for (int i = 0; i < maxDeg + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }
        return a;
    }

    public Polynom minus(Polynom x) {
        Polynom a = new Polynom();
        a.setDeg(getDeg() > x.getDeg() ? getDeg() : x.getDeg());

        ZpNumber[] p = new ZpNumber[maxDeg + 1];
        ZpNumber[] p1 = getPol();
        ZpNumber[] p2 = x.getPol();

        for (int i = 0; i < maxDeg + 1; i++) {
            p[i] = p1[i].minus(p2[i]);
            //System.out.println(p[i]);
        }

        a.setPol(p);
        a.deg = 0;
        for (int i = 0; i < maxDeg + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }

        return a;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Polynom mult(Polynom x) {
        return this;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Polynom div(Polynom x) {
        return this;
    }


    public Polynom simpleMult(Polynom x) throws Exception {
        Polynom a, b;
        Polynom mult = new Polynom();

        if (isMore(x)) {
            a = new Polynom(this);
            b = new Polynom(x);
        } else {
            b = new Polynom(this);
            a = new Polynom(x);
        }

        for (int i = b.getDeg(); i > 0; i--) {
            mult = mult.plus(a.scalarMult(b.pol[i]));
            mult = mult.leftShift();
            if (mult.deg >= modulo.deg)
                mult = mult.simpleMod(modulo);
        }

        mult = mult.plus(a.scalarMult(b.pol[0]));

        return mult;
    }


    public Polynom scalarMult(ZpNumber x) {
        Polynom a = new Polynom(this);

        for (int i = 0; i < deg + 1; i++) {
            a.pol[i] = a.pol[i].mult(x);
        }
        return a;
    }

    public Polynom leftShift() {
        Polynom a = new Polynom(this);

        a.deg++;
        for (int i = maxDeg; i > 0; i--) {
            a.pol[i] = a.pol[i - 1];
        }
        a.pol[0] = new ZpNumber();
        return a;
    }

    public boolean isMore(Polynom x) {
        if (getDeg() >= x.getDeg())
            return true;
        return false;
    }

    public boolean isZero() {
        if (deg == 0 && pol[0].isZero())
            return true;
        return false;
    }

    public Polynom simpleMod(Polynom x) throws Exception {
        if (!isMore(x))
            return this;

        Polynom a = new Polynom(this);
        Polynom b;

        while (a.deg >= x.deg) {
            b = new Polynom(x);
            while (b.deg < a.deg) {
                b = b.leftShift();
            }
            a = a.minus(b.scalarMult(a.pol[a.deg].div(b.pol[b.deg])));
        }

        for (int i = 0; i < maxDeg + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }

        return a;
    }


    @Deprecated
    public Polynom toPowerOf(int p) {
        Polynom a = new Polynom(this);

        for (int i = 1; i < p; i++) {
            // a = (a.mult(a).simpleMod(modilo);

        }
        return null;/////////////////////////////////////////////////////////////////////// !!!!!!!!!!
    }

    @Override
    public String toString() {
        System.out.println();
        //System.out.println("Char F: " + StaticField.characteristic);
        System.out.println("Deg P: " + deg);

        String poly = "";
        for (int i = deg; i > 0; i--) {
            poly += pol[i].toString() + "x^" + i + " + ";
        }
        poly += pol[0].toString();

        return poly;
    }


    public Polynom plusOneNorm() {
        int i = 0;
        while (i < deg) {
            pol[i].plusOne();
            if (!pol[i].isZero())
                break;
            i++;
        }
        return this;
    }

    public Polynom plusOne() {
        int i = 0;
        while (i <= deg) {
            pol[i].plusOne();
            if (!pol[i].isZero())
                break;
            i++;
        }
        if (i > deg) {
            deg++;
            pol[deg] = new ZpNumber(1);
        }
        return this;
    }
}
