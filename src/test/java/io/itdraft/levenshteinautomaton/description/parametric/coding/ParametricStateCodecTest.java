package io.itdraft.levenshteinautomaton.description.parametric.coding;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ParametricStateCodecTest {

    @Test(expected = NoSuchElementException.class)
    public void testGetMinBoundaryWithException() throws Exception {
        ParametricStateCodec.getMinBoundary(185, 30);
    }

    @Test
    public void testGetMinBoundary() throws Exception {
        assertTrue(ParametricStateCodec.getMinBoundary(129,30) == 4);
    }

    @Test
    public void testIsFinal() throws Exception {
        assertTrue(ParametricStateCodec.isFinal(44, 7, 2, EncodedParametricDescription.get(1, true)));
    }

    @Test
    public void testIsFailure() throws Exception {
        assertTrue(ParametricStateCodec.isFailure(185, 30));
    }
}