package io.itdraft.levenshteinautomaton.description.parametric.coding;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static io.itdraft.levenshteinautomaton.util.StringUtil.toCodePoints;
import static org.junit.Assert.assertTrue;

import io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec;
import org.junit.Test;

public class CharacteristicVectorCodecTest {

    @Test
    public void testCreateEncoded() throws Exception {
        String word = "";
        int vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 0, 10);
        assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 4, 6);
        assertTrue(vector == Integer.parseInt("110", 2));

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 100, 1000);
        assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), -1000, -100);
        assertTrue(vector == CharacteristicVectorCodec.EMPTY);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 0, 0);
        assertTrue(vector == CharacteristicVectorCodec.EMPTY);
    }

    @Test
    public void testDecodeJ() throws Exception {
        String word = "xoxoxo";
        int vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('o', toCodePoints(word), 0, word.length());
        assertTrue(CharacteristicVectorCodec.decodeJ(vector) == 2);

        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('y', toCodePoints(word), 0, word.length());
        assertTrue(CharacteristicVectorCodec.decodeJ(vector) == -1);

        word = "";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('y', toCodePoints(word), 0, word.length());
        assertTrue(CharacteristicVectorCodec.decodeJ(vector) == -1);
    }

    @Test
    public void testSize() throws Exception {
        String word = "";
        int vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 0, 10);
        assertTrue(CharacteristicVectorCodec.size(vector) == 0);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 4, 6);
        assertTrue(CharacteristicVectorCodec.size(vector) == 2);

        word = "xoxoxo";
        vector = CharacteristicVectorCodec.computeEncodedCharacteristicVector('x', toCodePoints(word), 0, word.length());
        assertTrue(CharacteristicVectorCodec.size(vector) == word.length());
    }
}