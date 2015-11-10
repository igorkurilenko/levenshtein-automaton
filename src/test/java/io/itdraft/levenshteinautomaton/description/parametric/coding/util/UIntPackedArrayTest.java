package io.itdraft.levenshteinautomaton.description.parametric.coding.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UIntPackedArrayTest {

    @Test
    public void testPack() throws Exception {
        int[] xs = new int[]{1, 1, 8899, 1, 1, 12, 2, 2, 2, 5, 56, 6, 27, 1, 12, 3, 41, 2, 56,
                6, 34, 7, 356, 3, 4, 0, 0, 5, 2, 234, 1};

        UIntPackedArray packed = UIntPackedArray.pack(xs);

        for (int i = 0; i < xs.length; i++) {
            assertTrue(xs[i] == packed.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPackMustBeNotEmpty() {
        UIntPackedArray.pack(new int[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPackMustBeNotNull() {
        UIntPackedArray.pack(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPackWithMustContainPositives() {
        UIntPackedArray.pack(new int[]{1, 1, 1, 1, -1});
    }
}