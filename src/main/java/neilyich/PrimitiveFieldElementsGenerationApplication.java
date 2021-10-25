package neilyich;

import neilyich.field.Polynomial;
import neilyich.field.PrimitivePolynomialsFactory;

public class PrimitiveFieldElementsGenerationApplication {
    public static void main(String[] args) {
        int p = 3;
        var mod = new Polynomial(p);
        mod.set(3, 1);
        mod.set(1, 2);
        mod.set(0, 1);
        var factory = new PrimitivePolynomialsFactory(mod);
        var primitives = factory.findPrimitiveElements();
        System.out.println("Primitive elements of GF(" + p + "^" + mod.getDegree() + ") / (" + mod + ")");
        for(var polynomial: primitives) {
            System.out.println(polynomial.toString());
        }
    }
}
