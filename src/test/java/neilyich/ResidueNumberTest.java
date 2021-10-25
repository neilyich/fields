package neilyich;

import neilyich.field.ResidueNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResidueNumberTest {
    @Test
    void test0() {
        var n10 = new ResidueNumber(2, 10);
        var n0 = new ResidueNumber(2, 0);
        var n_4 = new ResidueNumber(2, -4);
        Assertions.assertEquals(n0, n10);
        Assertions.assertEquals(n0, n_4);
        Assertions.assertEquals(0, n0.getValue());
        Assertions.assertEquals(0, n10.getValue());
        Assertions.assertEquals(0, n_4.getValue());

        var n5 = new ResidueNumber(2, 5);
        Assertions.assertNotEquals(n0, n5);
        Assertions.assertEquals(1, n5.getValue());

        Assertions.assertEquals(new ResidueNumber(13, 5), new ResidueNumber(13, -8));
    }

    @Test
    void test1() {
        var a = new ResidueNumber(7, 3);
        Assertions.assertEquals(6, a.add(a).getValue());
        Assertions.assertEquals(0, a.sub(a).getValue());
        var b = new ResidueNumber(7, 5);
        Assertions.assertEquals(1, a.add(b).getValue());
        Assertions.assertEquals(1, b.add(a).getValue());
        Assertions.assertEquals(2, b.sub(a).getValue());
        Assertions.assertEquals(5, a.sub(b).getValue());
    }

    @Test
    void test2() {
        var a = new ResidueNumber(11, 3);
        var b = new ResidueNumber(11, 9);
        Assertions.assertEquals(b, a.mult(a));
        Assertions.assertEquals(5, a.mult(b).getValue());
        Assertions.assertEquals(5, b.mult(a).getValue());
        var c = new ResidueNumber(11, 4);
        Assertions.assertEquals(1, a.mult(c).getValue());

        Assertions.assertEquals(8, a.inverseAdd().getValue());
        Assertions.assertEquals(c, a.inverseMult());
        Assertions.assertEquals(5, b.inverseMult().getValue());
        Assertions.assertEquals(2, b.inverseAdd().getValue());

        Assertions.assertEquals(1, a.mult(a.inverseMult()).getValue());
        Assertions.assertEquals(1, b.mult(b.inverseMult()).getValue());
        Assertions.assertEquals(1, c.mult(c.inverseMult()).getValue());

        Assertions.assertEquals(9, a.div(c).getValue());
    }
}
