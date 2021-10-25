package neilyich.field;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PolynomialsIterator {
    public static void forAllPolynomials(int p, int maxDegree, Consumer<Polynomial> action) {
        for (int degree = 1; degree <= maxDegree; degree++) {
            iterateAllPolynomials(p, degree, action);
        }
    }

    private static void iterateAllPolynomials(int p, int degree, Consumer<Polynomial> action) {
        var pol = new Polynomial(p);
        pol.set(degree, new ResidueNumber(p, 1));
        iterateAllPolynomials(pol, degree - 1, action);
    }

    private static void iterateAllPolynomials(Polynomial current, int currentVaryingDegree, Consumer<Polynomial> action) {
        int p = current.getP();
        for (int i = 0; i < p; i++) {
            var varying = new Polynomial(current);
            varying.set(currentVaryingDegree, new ResidueNumber(p, i));
            action.accept(varying);
            if(currentVaryingDegree > 0) {
                iterateAllPolynomials(varying, currentVaryingDegree - 1, action);
            }
        }
    }
}
