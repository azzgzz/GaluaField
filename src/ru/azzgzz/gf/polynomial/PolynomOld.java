package ru.azzgzz.gf.polynomial;


import ru.azzgzz.gf.numbers.ZpNumber;
import ru.azzgzz.gf.field.StaticField;


public class PolynomOld {

    private int deg;
    private ZpNumber[] pol;


    public PolynomOld() {
        deg = 0;
        pol = new ZpNumber[StaticField.getMaxDeg() + 1];
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            pol[i] = new ZpNumber();
        }
    }


    public PolynomOld(PolynomOld x) {
        setDeg(x.getDeg());
        setPol(x.getPol());
    }

    public PolynomOld(int[] p) {
        setDeg(p.length - 1);
        this.pol = new ZpNumber[StaticField.getMaxDeg() + 1];
        for (int i = 0; i < p.length; i++) {
            pol[i] = new ZpNumber(p[i] % StaticField.getCharacteristic());
        }
        for (int i = p.length; i < StaticField.getMaxDeg() + 1; i++) {
            pol[i] = new ZpNumber(0);
        }

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            if (!pol[i].isZero())
                deg = i;
        }
    }

    public PolynomOld(int x) {
        deg = 0;
        pol = new ZpNumber[StaticField.getMaxDeg() + 1];
        pol[0] = new ZpNumber(x);
        for (int i = 1; i < StaticField.getMaxDeg() + 1; i++) {
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
        this.pol = new ZpNumber[StaticField.getMaxDeg() + 1];
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            //System.out.print(pol[i]);
            this.pol[i] = new ZpNumber(pol[i]);
            //System.out.println(" " + this.pol[i]);
        }
    }


    public PolynomOld plus(PolynomOld x) {
        PolynomOld a = new PolynomOld();
        a.setDeg(getDeg() > x.getDeg() ? getDeg() : x.getDeg());

        ZpNumber[] p = new ZpNumber[StaticField.getMaxDeg() + 1];
        ZpNumber[] p1 = getPol();
        ZpNumber[] p2 = x.getPol();

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            p[i] = p1[i].plus(p2[i]);
            //System.out.println(p[i]);
        }

        a.setPol(p);
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }
        return a;
    }

    public PolynomOld minus(PolynomOld x) {
        PolynomOld a = new PolynomOld();
        a.setDeg(getDeg() > x.getDeg() ? getDeg() : x.getDeg());

        ZpNumber[] p = new ZpNumber[StaticField.getMaxDeg() + 1];
        ZpNumber[] p1 = getPol();
        ZpNumber[] p2 = x.getPol();

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            p[i] = p1[i].minus(p2[i]);
            //System.out.println(p[i]);
        }

        a.setPol(p);
        a.deg = 0;
        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }

        return a;
    }


    public PolynomOld simpleMult(PolynomOld x) throws Exception {
        PolynomOld a, b;
        PolynomOld mult = new PolynomOld();

        if (isMore(x)) {
            a = new PolynomOld(this);
            b = new PolynomOld(x);
        } else {
            b = new PolynomOld(this);
            a = new PolynomOld(x);
        }

        for (int i = b.getDeg(); i > 0; i--) {
            mult = mult.plus(a.scalarMult(b.pol[i]));
            mult = mult.leftShift();
//            if (mult.deg >= StaticField.getModulo().deg)
//                mult = mult.simpleMod(StaticField.getModulo());
        }

        mult = mult.plus(a.scalarMult(b.pol[0]));

        return mult;
    }


    public PolynomOld scalarMult(ZpNumber x) {
        PolynomOld a = new PolynomOld(this);

        for (int i = 0; i < deg + 1; i++) {
            a.pol[i] = a.pol[i].mult(x);
        }
        return a;
    }

    public PolynomOld leftShift() {
        PolynomOld a = new PolynomOld(this);

        a.deg++;
        for (int i = StaticField.getMaxDeg(); i > 0; i--) {
            a.pol[i] = a.pol[i - 1];
        }
        a.pol[0] = new ZpNumber();
        return a;
    }

    public boolean isMore(PolynomOld x) {
        if (getDeg() >= x.getDeg())
            return true;
        return false;
    }

    public boolean isZero() {
        if (deg == 0 && pol[0].isZero())
            return true;
        return false;
    }

    public PolynomOld simpleMod(PolynomOld x) throws Exception {
        if (!isMore(x))
            return this;

        PolynomOld a = new PolynomOld(this);
        PolynomOld b;

        while (a.deg >= x.deg) {
            b = new PolynomOld(x);
            while (b.deg < a.deg) {
                b = b.leftShift();
            }
            a = a.minus(b.scalarMult(a.pol[a.deg].div(b.pol[b.deg])));
        }

        for (int i = 0; i < StaticField.getMaxDeg() + 1; i++) {
            if (!a.pol[i].isZero())
                a.deg = i;
        }

        return a;
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


    public PolynomOld plusOneNorm() {
        int i = 0;
        while (i < deg) {
            pol[i].plusOne();
            if (!pol[i].isZero())
                break;
            i++;
        }
        return this;
    }

    public PolynomOld plusOne() {
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
