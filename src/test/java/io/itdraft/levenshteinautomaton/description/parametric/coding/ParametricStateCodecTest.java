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