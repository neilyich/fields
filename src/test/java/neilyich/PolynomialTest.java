package neilyich;

import neilyich.field.Polynomial;
import neilyich.field.ResidueNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PolynomialTest {
    @Test
    void test0() {
        Assertions.assertTrue(new Polynomial(17).isZero());
        var a = new Polynomial(11);
        a.set(8, new ResidueNumber(11, 4));
        a.set(3, new ResidueNumber(11, 8));
        a.set(0, new ResidueNumber(11, 1));
        var copy = new Polynomial(a);
        Assertions.assertEquals(a, copy);
        copy.set(0, new ResidueNumber(11, 2));
        Assertions.assertNotEquals(a, copy);
    }

    @Test
    void test1() {
        var a = new Polynomial(5);
        a.set(1, new ResidueNumber(5, 1));
        a.set(0, new ResidueNumber(5, 2));
        var b = new Polynomial(5);
        b.set(2, new ResidueNumber(5, 3));
        b.set(0, new ResidueNumber(5, 4));
        var add = new Polynomial(5);
        add.set(2, new ResidueNumber(5, 3));
        add.set(1, new ResidueNumber(5, 1));
        add.set(0, new ResidueNumber(5, 1));
        Assertions.assertEquals(add, a.add(b));
        Assertions.assertEquals(add, b.add(a));
    }

    @Test
    void test2() {
        var a = new Polynomial(5);
        a.set(1, new ResidueNumber(5, 1));
        a.set(0, new ResidueNumber(5, 2));
        var b = new Polynomial(5);
        b.set(2, new ResidueNumber(5, 3));
        b.set(0, new ResidueNumber(5, 4));
        var sub = new Polynomial(5);
        sub.set(2, new ResidueNumber(5, 2));
        sub.set(1, new ResidueNumber(5, 1));
        sub.set(0, new ResidueNumber(5, 3));
        Assertions.assertEquals(sub, a.sub(b));
        sub.set(2, new ResidueNumber(5, 3));
        sub.set(1, new ResidueNumber(5, 4));
        sub.set(0, new ResidueNumber(5, 2));
        Assertions.assertEquals(sub, b.sub(a));
    }

    @Test
    void test3() {
        var a = new Polynomial(5);
        a.set(1, new ResidueNumber(5, 1));
        a.set(0, new ResidueNumber(5, 2));
        var b = new Polynomial(5);
        b.set(2, new ResidueNumber(5, 3));
        b.set(0, new ResidueNumber(5, 4));
        var mult = new Polynomial(5);
        mult.set(3, new ResidueNumber(5, 3));
        mult.set(2, new ResidueNumber(5, 1));
        mult.set(1, new ResidueNumber(5, 4));
        mult.set(0, new ResidueNumber(5, 3));
        Assertions.assertEquals(mult, a.mult(b));
        Assertions.assertEquals(mult, b.mult(a));
    }

    @Test
    void test4() {
        var a = new Polynomial(5);
        a.set(1, new ResidueNumber(5, 1));
        a.set(0, new ResidueNumber(5, 2));
        var b = new Polynomial(5);
        b.set(2, new ResidueNumber(5, 3));
        b.set(0, new ResidueNumber(5, 4));
        var res1 = a.div(b);
        Assertions.assertTrue(res1.getQuotient().isZero());
        Assertions.assertEquals(a, res1.getRemainder());
        var q = new Polynomial(5);
        q.set(1, new ResidueNumber(5, 3));
        q.set(0, new ResidueNumber(5, 4));
        var r = new Polynomial(5);
        r.set(0, new ResidueNumber(5, 1));
        var res2 = b.div(a);
        Assertions.assertEquals(q, res2.getQuotient());
        Assertions.assertEquals(r, res2.getRemainder());
    }

    @Test
    void test5() {
        var a = new Polynomial(5);
        a.set(2, new ResidueNumber(5, 1));
        a.set(0, new ResidueNumber(5, 2));
        var b = new Polynomial(5);
        b.set(3, new ResidueNumber(5, 3));
        b.set(0, new ResidueNumber(5, 4));
        var res1 = a.div(b);
        Assertions.assertTrue(res1.getQuotient().isZero());
        Assertions.assertEquals(a, res1.getRemainder());
        var q = new Polynomial(5);
        q.set(1, new ResidueNumber(5, 3));
        var r = new Polynomial(5);
        r.set(1, new ResidueNumber(5, 4));
        r.set(0, new ResidueNumber(5, 4));
        var res2 = b.div(a);
        Assertions.assertEquals(q, res2.getQuotient());
        Assertions.assertEquals(r, res2.getRemainder());
    }
}
