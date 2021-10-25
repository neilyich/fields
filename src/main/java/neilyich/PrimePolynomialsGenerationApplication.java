package neilyich;

import neilyich.field.PrimePolynomialsFactory;

public class PrimePolynomialsGenerationApplication {
    public static void main(String[] args) {
        var factory = new PrimePolynomialsFactory(3);
        var primes = factory.generatePrimesTill(3);
        for (int degree = 1; degree < primes.size(); degree++) {
            System.out.println("Degree " + degree + ":");
            for(var prime : primes.get(degree)) {
                System.out.println(prime.toString());
            }
            System.out.println();
        }
    }
}
