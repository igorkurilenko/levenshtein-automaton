package io.itdraft.levenshteinautomaton;

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

import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescription;
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec;

public class ParametricLevenshteinAutomaton {

    public static final int INITIAL_STATE = ParametricStateCodec.INITIAL_STATE;

    private final String word;
    private final int n;
    private final int w;
    private final EncodedParametricDescription parametricDescription;
    private int statesCount;

    public static ParametricLevenshteinAutomaton create(
            String word, int degree,
            boolean inclTranspositions) throws ParametricDescriptionNotFoundException {
        EncodedParametricDescription parametricDescription =
                EncodedParametricDescription.get(degree, inclTranspositions);

        if (parametricDescription == null) {
            ParametricDescriptionNotFoundException.throwFor(degree, inclTranspositions);
        }

        return new ParametricLevenshteinAutomaton(word, degree, parametricDescription);
    }

    private ParametricLevenshteinAutomaton(
            String word,
            int degree,
            EncodedParametricDescription parametricDescription) {
        this.word = word;
        this.parametricDescription = parametricDescription;
        this.statesCount = parametricDescription.getStatesCount();
        this.w = Character.codePointCount(word, 0, word.length());
        this.n = degree;
    }

    public int nextState(int xCodePoint, int state) {
        return ParametricStateCodec.transit(xCodePoint, state, w, n, word, parametricDescription);
    }

    public boolean isFailure(int state) {
        return ParametricStateCodec.isFailure(state, statesCount);
    }

    public boolean isFinal(int state) {
        return ParametricStateCodec.isFinal(state, w, n, parametricDescription);
    }
}
