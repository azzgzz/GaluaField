package ru.azzgzz.gf;

import java.util.ArrayList;
import java.util.Iterator;

import static ru.azzgzz.gf.StaticField.dimention;
import static ru.azzgzz.gf.StaticField.characteristic;
import static ru.azzgzz.gf.StaticField.modulo;


public class Irreducible {

    ArrayList<ArrayList<Polynom>> irreduciblePolynoms;
    ArrayList<Polynom> gfPolynoms;

    Irreducible() throws Exception {
        generateIrreduciblePolynoms();
        //generateFieldPolynoms();
        generateModulo();
    }

    /**
     * After this, List of irreducible polynomials will forgotten
     */
    public void generateModulo(){
        modulo = new Polynom(irreduciblePolynoms.get(dimention).get(0));
        irreduciblePolynoms = null;
    }

    public void generateIrreduciblePolynoms() throws Exception {
        irreduciblePolynoms = new ArrayList<ArrayList<Polynom>>();

        for (int i = 0; i <= dimention; i++) {
            ArrayList<Polynom> irrsInThisDim = generateNormPolynoms(i);
            irreduciblePolynoms.add(irrsInThisDim);
            if (i > 1)
                deleteReducible(i);
        }
    }

    private ArrayList<Polynom> generateNormPolynoms(int dim) {
        ArrayList<Polynom> a = new ArrayList<Polynom>();

        int[] mass = new int[dim + 1];
        mass[dim] = 1;

        Polynom adding = new Polynom(mass);

        for (int i = 0; i < Math.round(Math.pow(characteristic, dim)); i++) {
            a.add(new Polynom(adding));
            adding.plusOneNorm();

        }

        return a;
    }



    public void generateFieldPolynoms() {
        gfPolynoms = new ArrayList<Polynom>();

        gfPolynoms.add(new Polynom());

        for (int i = 0; i < dimention; i++) {
            ArrayList<Polynom> polysInThisDim = generatePolynoms(i);
            gfPolynoms.addAll(polysInThisDim);
        }
    }

    public ArrayList<Polynom> generatePolynoms(int dim) {
        ArrayList<Polynom> a = new ArrayList<Polynom>();

        int[] mass = new int[dim + 1];
        mass[dim] = 1;

        Polynom adding = new Polynom(mass);

        for (int i = 0; i < Math.round(Math.pow(characteristic, dim)*(characteristic-1)); i++) {
            a.add(new Polynom(adding));
            //System.out.println(adding);
            adding.plusOne();

        }

        return a;
    }



    private void deleteReducible(int dim) throws Exception {
        ArrayList<Polynom> a = irreduciblePolynoms.get(dim);
        ArrayList<Polynom> b;

        Iterator<Polynom> iter = a.iterator();
        while (iter.hasNext()) {
            Polynom cheking = iter.next();
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

    public ArrayList<ArrayList<Polynom>> getIrreduciblePolynoms() {
        return irreduciblePolynoms;
    }

    public void printList(int dim) {
        Iterator<Polynom> iterator = irreduciblePolynoms.get(dim).iterator();

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    public void printField() {
        Iterator<Polynom> iterator = gfPolynoms.iterator();

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

}
