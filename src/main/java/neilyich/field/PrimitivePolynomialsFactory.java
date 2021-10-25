package neilyich.field;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class PrimitivePolynomialsFactory {
    private final Polynomial mod;

    public Set<Polynomial> findPrimitiveElements() {
        int p = mod.getP();
        int m = mod.getDegree();
        long n = (Math.round(Math.pow(p, m)) - 1);
        var dividers = findPrimeDividers(n);
        Set<Polynomial> primitives = new HashSet<>();
        var one = new Polynomial(p);
        one.set(0, 1);
        PolynomialsIterator.forAllPolynomials(p, m - 1, polynomial -> {
            boolean isPrimitive = true;
            for(var divider : dividers) {
                if(one.equals(polynomial.pow(divider, mod))) {
                    isPrimitive = false;
                    break;
                }
            }
            if(isPrimitive) {
                primitives.add(polynomial);
            }
        });
        return primitives;
    }

    private Set<Integer> findPrimeDividers(long n) {
        Set<Integer> dividers = new HashSet<>();
        int sqrt = (int) Math.floor(Math.sqrt(n));
        for (int i = 2; i < sqrt; i++) {
            while (n % i == 0) {
                dividers.add(i);
                dividers.add((int) (n / i));
                n /= i;
            }
        }
        return dividers;
    }

}
