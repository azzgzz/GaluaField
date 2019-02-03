package ru.azzgzz.gf;

public class FiniteField {

    private int character;
    private Polynom modulo;

    public int getCharact() {
        return character;
    }

    public void setCharact(int character) {
        this.character = character;
    }

    public Polynom getModulo() {
        return modulo;
    }

    public void createModulo() {
        this.modulo = irreduciblePolynomials();
    }


    private Polynom irreduciblePolynomials () {
        return null;
    }


    public class Symbol{
    }
}
