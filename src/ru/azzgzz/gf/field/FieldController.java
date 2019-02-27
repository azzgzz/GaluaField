package ru.azzgzz.gf.field;

import ru.azzgzz.gf.polynomial.PolynomOld;
import ru.azzgzz.gf.polynomial.Polynomial;

public class FieldController {

    public static void setCharacteristic(int characteristic) {
        StaticField.setCharacteristic(characteristic);
    }

    public static void setDimention(int dimention) {
        StaticField.setDimention(dimention);
    }

    public static void setModulo(Polynomial modulo) {
        StaticField.setModulo(modulo);
    }

    public static void setModulo (PolynomOld modulo) { StaticField.setModulo(new Polynomial(modulo));}
}
