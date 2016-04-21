package io.itdraft.levenshteinautomaton.description.parametric.coding.util;

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

import io.itdraft.levenshteinautomaton.util.UIntPackedArray;
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