package ru.azzgzz.gf.field;

import ru.azzgzz.gf.polynomial.PolynomOld;

import java.util.ArrayList;
import java.util.Iterator;



public class Irreducible {

    ArrayList<ArrayList<PolynomOld>> irreduciblePolynoms;
    ArrayList<PolynomOld> gfPolynomOlds;

    public Irreducible() throws Exception {
        generateIrreduciblePolynomials();
        //generateFieldPolynoms();
        generateModulo();
    }

    /**
     * After this, List of irreducible polynomials will forgotten
     */
    public void generateModulo(){
        FieldController.setModulo(new PolynomOld(irreduciblePolynoms.get(StaticField.getDimention()).get(0)));
        irreduciblePolynoms = null;
    }

    public void generateIrreduciblePolynomials() throws Exception {
        irreduciblePolynoms = new ArrayList<ArrayList<PolynomOld>>();

        for (int i = 0; i <= StaticField.getDimention(); i++) {
            ArrayList<PolynomOld> irrsInThisDim = generateNormPolynomials(i);
            irreduciblePolynoms.add(irrsInThisDim);
            if (i > 1)
                deleteReducible(i);
        }
    }

    private ArrayList<PolynomOld> generateNormPolynomials(int dim) {
        ArrayList<PolynomOld> a = new ArrayList<PolynomOld>();

        int[] mass = new int[dim + 1];
        mass[dim] = 1;

        PolynomOld adding = new PolynomOld(mass);

        for (int i = 0; i < Math.round(Math.pow(StaticField.getCharacteristic(), dim)); i++) {
            a.add(new PolynomOld(adding));
            adding.plusOneNorm();

        }

        return a;
    }



    public void generateFieldPolynoms() {
        gfPolynomOlds = new ArrayList<PolynomOld>();

        gfPolynomOlds.add(new PolynomOld());

        for (int i = 0; i < StaticField.getDimention(); i++) {
            ArrayList<PolynomOld> polysInThisDim = generatePolynoms(i);
            gfPolynomOlds.addAll(polysInThisDim);
        }
    }

    public ArrayList<PolynomOld> generatePolynoms(int dim) {
        ArrayList<PolynomOld> a = new ArrayList<PolynomOld>();

        int[] mass = new int[dim + 1];
        mass[dim] = 1;

        PolynomOld adding = new PolynomOld(mass);

        for (int i = 0; i < Math.round(Math.pow(StaticField.getCharacteristic(), dim)*(StaticField.getCharacteristic()-1)); i++) {
            a.add(new PolynomOld(adding));
            //System.out.println(adding);
            adding.plusOne();

        }

        return a;
    }



    private void deleteReducible(int dim) throws Exception {
        ArrayList<PolynomOld> a = irreduciblePolynoms.get(dim);
        ArrayList<PolynomOld> b;

        Iterator<PolynomOld> iter = a.iterator();
        while (iter.hasNext()) {
            PolynomOld cheking = iter.next();
            for (int i = 1; i <= dim/2; i++) {
                b = irreduciblePolynoms.get(i);
                int size = b.size();
                for (int j = 0; j < size; j++) {
                    if (cheking.simpleMod(b.get(j)).isZero()) {
                        iter.remove();
                        j = size;
                        i = dim;
                    }
                }
            }
        }

    }

    public ArrayList<ArrayList<PolynomOld>> getIrreduciblePolynoms() {
        return irreduciblePolynoms;
    }

    public void printList(int dim) {
        Iterator<PolynomOld> iterator = irreduciblePolynoms.get(dim).iterator();

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    public void printField() {
        Iterator<PolynomOld> iterator = gfPolynomOlds.iterator();

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

}
