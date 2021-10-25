package neilyich;

import neilyich.field.Polynomial;

public class DiscreteLogarithmsCalculationApplication {
    public static void main(String[] args) {
        int p = 3;
        int maxPow = 40;
        var x = new Polynomial(p);
        x.set(1, 1);
        x.set(0, 1);
        var mod = new Polynomial(p);
        mod.set(3, 1);
        mod.set(1, 2);
        mod.set(0, 1);
        System.out.println("Discrete logarithms of GF(" + p + "^" + mod.getDegree() + ") / (" + mod + ")\nBy polynomial: " + x);
        for (int n = 0; n < maxPow; n++) {
            System.out.println(n + ": " + x.pow(n, mod));
        }

    }
}
