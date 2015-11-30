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

import io.itdraft.levenshteinautomaton.LevenshteinAutomatonConfig;

import static io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.createEncodedCharacteristicVector;
import static io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec.*;

/**
 * This util class helps to decode the encoded parametric
 * description and retrieve a next state of the Levenshtein-automaton.
 */
public class ParametricDescriptionCodec {

    /**
     * @param encodedStateFrom a Levenshtein-automaton parametric state
     *                         encoded as an integer value to transit from.
     * @param symbolCodePoint  a code point of a next symbol from a word
     *                         is being recognized.
     * @param config           the config of the parametric Levenshtein-automaton
     *                         {@code encodedState} belongs to.
     * @return a next state of the Levenshtein-automaton encoded as an integer value.
     */
    public static int transit(int encodedStateFrom, int symbolCodePoint,
                              LevenshteinAutomatonConfig config) {
        EncodedParametricDescription parametricDescription =
                config.getEncodedParametricDescription();
        int statesCount = parametricDescription.getParametricStatesCount();

        if (isFailure(encodedStateFrom, statesCount)) return encodedStateFrom;

        int n = config.getDegree();
        int w = config.getWordCodePointCount();
        int stateId = decodeStateId(encodedStateFrom, statesCount);
        int minBoundary = decodeMinBoundary(encodedStateFrom, statesCount);
        int relSubwordMaxLength = relevantSubwordMaxLength(minBoundary, n, w);
        int encodedCharacteristicVector = createEncodedCharacteristicVector(
                symbolCodePoint, config.getWord(),
                minBoundary, minBoundary + relSubwordMaxLength);
        int index = (encodedCharacteristicVector - 1) * statesCount + stateId;
        int nextStateId = parametricDescription.getEncodedTransitionsTable().get(index);
        int boundaryOffset = parametricDescription.getBoundaryOffsets().get(index);

        return encodeState(nextStateId, minBoundary + boundaryOffset, statesCount);
    }

    private ParametricDescriptionCodec() {
    }
}
