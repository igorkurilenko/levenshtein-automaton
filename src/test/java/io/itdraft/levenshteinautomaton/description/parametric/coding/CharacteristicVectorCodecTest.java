package io.itdraft.levenshteinautomaton.description.parametric.coding;

import junit.framework.Assert;
import org.junit.Test;

public class CharacteristicVectorCodecTest {

    @Test
    public void testCreateEncoded() throws Exception {
        String word = "";
        int vector = CharacteristicVectorCodec.createEncoded('x', word, 0, 10);
        Assert.assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, 4, 6);
        Assert.assertTrue(vector == Integer.parseInt("110", 2));

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, 100, 1000);
        Assert.assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, -1000, -100);
        Assert.assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, 0, 0);
        Assert.assertTrue(vector == CharacteristicVectorCodec.EMPTY);
    }

    @Test
    public void testDecodeJ() throws Exception {
        String word = "xoxoxo";
        int vector = CharacteristicVectorCodec.createEncoded('o', word, 0, word.length());
        Assert.assertTrue(CharacteristicVectorCodec.decodeJ(vector) == 2);

        vector = CharacteristicVectorCodec.createEncoded('y', word, 0, word.length());
        Assert.assertTrue(CharacteristicVectorCodec.decodeJ(vector) == -1);

        word = "";
        vector = CharacteristicVectorCodec.createEncoded('y', word, 0, word.length());
        Assert.assertTrue(CharacteristicVectorCodec.decodeJ(vector) == -1);
    }

    @Test
    public void testSize() throws Exception {
        String word = "";
        int vector = CharacteristicVectorCodec.createEncoded('x', word, 0, 10);
        Assert.assertTrue(CharacteristicVectorCodec.size(vector) == 0);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, 4, 6);
        Assert.assertTrue(CharacteristicVectorCodec.size(vector) == 2);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.createEncoded('x', word, 0, word.length());
        Assert.assertTrue(CharacteristicVectorCodec.size(vector) == word.length());
    }
}