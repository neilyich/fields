package neilyich.field;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class ResidueNumber {
    int p;
    int value;

    private static final Map<Integer, Integer> inverseMultiplicativeCache = new HashMap<>();

    public ResidueNumber(int p, int value) {
        this.p = p;
        var val = value % p;
        if(val < 0) {
            this.value = p + val;
        }
        else {
            this.value = val;
        }
    }

    public ResidueNumber(ResidueNumber other) {
        this.p = other.p;
        this.value = other.value;
    }

    public ResidueNumber add(ResidueNumber other) {
        assertSameModulo(other);
        return new ResidueNumber(p, value + other.value);
    }

    public ResidueNumber sub(ResidueNumber other) {
        assertSameModulo(other);
        return new ResidueNumber(p, value - other.value);
    }

    public ResidueNumber mult(ResidueNumber other) {
        assertSameModulo(other);
        return new ResidueNumber(p, value * other.value);
    }

    public ResidueNumber div(ResidueNumber other) {
        assertSameModulo(other);
        return mult(other.inverseMult());
    }

    public ResidueNumber inverseAdd() {
        return new ResidueNumber(p, p - value);
    }

    public ResidueNumber inverseMult() {
        var cachedInverse = inverseMultiplicativeCache.get(value);
        if(cachedInverse != null) {
            return new ResidueNumber(p, cachedInverse);
        }
        for (int i = 1; i < p; i++) {
            if((i * value) % p == 1) {
                inverseMultiplicativeCache.put(value, i);
                return new ResidueNumber(p, i); // todo euclid algorithm
            }
        }
        throw new RuntimeException("No inverse element for " + value + " mod " + p);
    }

    private void assertSameModulo(ResidueNumber other) {
        if(other.p != this.p) {
            throw new RuntimeException("different modulo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidueNumber that = (ResidueNumber) o;
        return p == that.p && value == that.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
