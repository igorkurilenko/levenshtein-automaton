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

import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescription;
import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescriptionFactory;
import io.itdraft.levenshteinautomaton.util.StringUtil;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

public class EncodedParametricDescriptionTest {

    private ParametricDescriptionFactory factory = new EncodedParametricDescriptionFactory();

    @Test
    public void testIsFailure() throws Exception {
        int degree = 2;
        boolean inclTransposition = false;
        ParametricDescription description =
                factory.getParametricDescription(degree, inclTransposition);
        int failureStateId = 185;

        assertTrue(description.isFailureState(failureStateId));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMinBoundaryWithException() throws Exception {
        int degree = 2;
        boolean inclTransposition = false;
        ParametricDescription description =
                factory.getParametricDescription(degree, inclTransposition);
        int failureStateId = 185;

        description.getStateMinBoundary(failureStateId);// failure state doesn't have min boundary
    }

    @Test
    public void testGetMinBoundary() throws Exception {
        int degree = 2;
        boolean inclTransposition = false;
        ParametricDescription description =
                factory.getParametricDescription(degree, inclTransposition);
        int stateId = 129;

        assertTrue(description.getStateMinBoundary(stateId) == 4);
    }

    @Test
    public void testIsFinal() throws Exception {
        int degree = 1;
        boolean inclTransposition = true;
        ParametricDescription description =
                factory.getParametricDescription(degree, inclTransposition);
        int stateId = 44;
        String word = "abcdefg";

        assertTrue(description.isFinalState(stateId, StringUtil.codePointCount(word)));
    }
}