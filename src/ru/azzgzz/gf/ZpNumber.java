package ru.azzgzz.gf;

public class ZpNumber {

    private int modulo = StaticField.characteristic;
    private int value;

    ZpNumber() {
        setValue(0);
    }

    ZpNumber(int val) {
        setValue(val % getModulo());
    }

    ZpNumber(ZpNumber x) {
        setValue(x.getValue());
    }

    public int getModulo() {
        return modulo;
    }

    protected void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public ZpNumber plus(ZpNumber x) {
        if (this.getModulo() != x.getModulo())
            return new ZpNumber();
        return new ZpNumber((this.getValue() + x.getValue()) % this.getModulo());
    }


    public ZpNumber minus(ZpNumber x) {
        if (this.getModulo() != x.getModulo())
            return new ZpNumber();
        return new ZpNumber((this.getValue() + this.getModulo() - x.getValue()) % this.getModulo());
    }


    public ZpNumber mult(ZpNumber x) {

        return new ZpNumber((getValue() * x.getValue()) % getModulo());
    }


    public ZpNumber div(ZpNumber x) throws Exception {

        try {
            return mult(x.inverse());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }


    public ZpNumber inverse() throws Exception {
        int a = getValue();
        int m = getModulo();

        if (a == 0)
            throw new Exception("divide by zero in ZpNumber.inverse()");

        int[] xy = {0, 0};

        int g = gcd(a, m, xy);

        if (g > 1)
            System.out.println("modulo does not prime!");

        return new ZpNumber((xy[0] + m) % m);
    }


    public boolean isZero() {
        return value == 0 ? true : false;
    }

    public boolean isEqual(int x) { return value == x ? true : false;}

    public void plusOne(){
        value = (value + 1) % StaticField.characteristic;
    }

    @Override
    public String toString() {
        return "" + getValue();
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


}










