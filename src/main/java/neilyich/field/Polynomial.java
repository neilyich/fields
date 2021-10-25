package neilyich.field;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Data
public class Polynomial {
    private final int p;
    private Map<Integer, ResidueNumber> coefs;
    private int degree;

    public Polynomial(int p) {
        this.p = p;
        coefs = new HashMap<>();
        coefs.put(0, new ResidueNumber(p, 0));
    }

    public Polynomial(Polynomial other) {
        this.p = other.p;
        this.degree = other.degree;
        this.coefs = other.coefs.keySet().stream().
                collect(Collectors.toMap(
                        pow -> pow,
                        pow -> new ResidueNumber(other.coefs.get(pow))));
    }

    public ResidueNumber get(int pow) {
        var coef = coefs.get(pow);
        if(coef == null) {
            return new ResidueNumber(p, 0);
        }
        return coef;
    }

    public void set(int pow, int value) {
        set(pow, new ResidueNumber(p, value));
    }

    public void set(int pow, ResidueNumber coef) {
        if(coef == null || coef.getValue() == 0) {
            coefs.remove(pow);
            degree = coefs.keySet().stream().max(Comparator.comparingInt(pw -> pw)).orElse(0);
            if(degree == 0 && coefs.containsKey(0) && coefs.get(0).getValue() == 0) {
                coefs = new HashMap<>();
                coefs.put(0, new ResidueNumber(p, 0));
            }
            return;
        }
        assertSameModulo(coef);
        if(isZero()) {
            coefs.remove(0);
        }
        coefs.put(pow, coef);
        if(pow > degree) {
            degree = pow;
        }
    }

    public Polynomial add(Polynomial other) {
        assertSameModulo(other);
        var result = new Polynomial(this);
        other.coefs.forEach((pow, coef) -> result.set(pow, result.get(pow).add(coef)));
        return result;
    }

    public Polynomial sub(Polynomial other) {
        assertSameModulo(other);
        var result = new Polynomial(this);
        other.coefs.forEach((pow, coef) -> result.set(pow, result.get(pow).sub(coef)));
        return result;
    }

    public Polynomial mult(Polynomial other) {
        assertSameModulo(other);
        AtomicReference<Polynomial> result = new AtomicReference<>(new Polynomial(p));
        other.coefs.forEach((otherPow, otherCoef) -> {
            var shift = new Polynomial(p);
            coefs.forEach((pow, coef) -> shift.set(pow + otherPow, coef.mult(otherCoef)));
            result.set(result.get().add(shift));
        });
        return result.get();
    }

    @Data
    @AllArgsConstructor
    public static class DivisionResult {
        private Polynomial quotient;
        private Polynomial remainder;
    }

    public DivisionResult div(Polynomial other) {
        if(other.degree > degree) {
            return new DivisionResult(new Polynomial(p), this);
        }
        var maxPowCoef = other.get(other.degree);
        var q = new Polynomial(p);
        var r = new Polynomial(this);
        for (int i = degree; i >= other.degree; i--) {
            var coef = r.get(i);
            var resultCoef = coef.div(maxPowCoef);
            var pol = new Polynomial(p);
            pol.set(i - other.degree, resultCoef);
            var m = other.mult(pol);
            r = r.sub(m);
            q.set(i - other.degree, resultCoef);
        }
        return new DivisionResult(q, r);
    }

    public Polynomial mod(Polynomial other) {
        return this.div(other).getRemainder();
    }

    public Polynomial pow(int n, Polynomial mod) {
        if(n == 0) {
            var one = new Polynomial(p);
            one.set(0, 1);
            return one;
        }
        var result = new Polynomial(this);
        for (int i = 1; i < n; i++) {
            result = result.mult(this).mod(mod);
        }
        return result;
    }

    public boolean isZero() {
        return degree == 0 && get(0).getValue() == 0;
    }

    private void assertSameModulo(Polynomial other) {
        if(other.getP() != this.p) {
            throw new RuntimeException("different modulo polynomial");
        }
    }

    private void assertSameModulo(ResidueNumber other) {
        if(other.getP() != this.p) {
            throw new RuntimeException("different modulo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return p == that.p && degree == that.degree && toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, toString(), degree);
    }

    @Override
    public String toString() {
        List<Integer> powers = coefs.keySet().stream().sorted(Comparator.comparingInt(i -> -i)).collect(Collectors.toList());
        var builder = new StringBuilder();
        for(var pow : powers) {
            var coef = coefs.get(pow);
            if(coef.getValue() > 1) {
                builder.append(coef);
            }
            else if(coef.getValue() == 1 && pow == 0) {
                builder.append(coef);
            }
            if(pow > 0) {
                builder.append('x');
            }
            if(pow > 1) {
                builder.append('^').append(pow);
            }
            builder.append(" + ");
        }
        return builder.substring(0, builder.length() - 3);
    }
}
