package neilyich.field;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class PrimePolynomialsFactory {
    private final int p;

    public List<Set<Polynomial>> generatePrimesTill(int maxDegree) {
        List<Set<Polynomial>> primes = new ArrayList<>();
        primes.add(new HashSet<>()); // no prime polynomials of degree = 0
        PolynomialsIterator.forAllPolynomials(p, maxDegree, candidate -> {
            if(primes.size() == candidate.getDegree()) {
                primes.add(new HashSet<>());
            }
            boolean isPrime = true;
            for (int degree = 0; degree <= candidate.getDegree() / 2; degree++) {
                for(var primePol : primes.get(degree)) {
                    var divResult = candidate.div(primePol);
                    if(divResult.getRemainder().isZero()) {
                        isPrime = false;
                        break;
                    }
                }
                if(!isPrime) {
                    break;
                }
            }
            if(isPrime) {
                primes.get(candidate.getDegree()).add(candidate);
            }
        });
        return primes;
    }

//    private void checkAllPolynomials(int degree) {
//        var pol = new Polynomial(p);
//        pol.set(degree, new ResidueNumber(p, 1));
//        checkAllPolynomials(pol, degree - 1);
//    }
//
//    private void checkAllPolynomials(Polynomial current, int currentVaryingDegree) {
//        for (int i = 0; i < p; i++) {
//            var varying = new Polynomial(current);
//            varying.set(currentVaryingDegree, new ResidueNumber(p, i));
//            boolean isPrime = true;
//            for (int degree = 0; degree <= varying.getDegree() / 2; degree++) {
//                for(var primePol : primes.get(degree)) {
//                    var divResult = varying.div(primePol);
//                    if(divResult.getRemainder().isZero()) {
//                        isPrime = false;
//                        break;
//                    }
//                }
//                if(!isPrime) {
//                    break;
//                }
//            }
//            if(isPrime) {
//                primes.get(varying.getDegree()).add(varying);
//            }
//            if(currentVaryingDegree > 0) {
//                checkAllPolynomials(varying, currentVaryingDegree - 1);
//            }
//        }
//    }
}
