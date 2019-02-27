package ru.azzgzz.gf.field;

import ru.azzgzz.gf.polynomial.Polynomial;

public class StaticField {
    private static int characteristic = 3;
    private static int dimention = 4;
    private static int maxDeg = dimention*2;
    private static Polynomial modulo = null;

    private static StaticField staticField;

    private StaticField(){}

    public static int getCharacteristic() {
        return characteristic;
    }

    public static int getDimention() {
        return dimention;
    }

    public static int getMaxDeg() {
        return maxDeg;
    }

    public static Polynomial getModulo() {
        return modulo;
    }

    static void setCharacteristic(int characteristic) {
        StaticField.characteristic = characteristic;
    }

    static void setDimention(int dimention) {
        StaticField.dimention = dimention;
    }

    static void setModulo(Polynomial modulo) {
        StaticField.modulo = modulo;
    }
}
