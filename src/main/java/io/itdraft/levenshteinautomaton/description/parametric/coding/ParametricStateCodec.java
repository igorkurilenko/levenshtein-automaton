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

import java.util.NoSuchElementException;

import static io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.createEncodedCharacteristicVector;

/**
 * This util class decodes the encoded Levenshtein-automaton state
 * to get minimal boundary, to detect whether it's a final state, or a
 * failure state. Also decodes the next state from the encoded parametric
 * description.
 */
public class ParametricStateCodec {

    /**
     * Initial parametric state. Start the parametric Levenshtein-automaton
     * traverse with {@code ParametricStateCodec.INITIAL_STATE}.
     */
    public static final int INITIAL_STATE = 0;

    /**
     * Decodes the next state from the encoded parametric
     * description and returns it encoded as an integer value.
     *
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
        int boundaryOffset = parametricDescription.getEncodedBoundaryOffsets().get(index);

        return encodeState(nextStateId, minBoundary + boundaryOffset, statesCount);
    }

    /**
     * Returns the current state's minimal boundary.
     *
     * @param encodedState          a Levenshtein-automaton parametric state encoded
     *                              as an integer value.
     * @param parametricStatesCount the number of parametric states in the parametric
     *                              description of the Levenshtein-automaton.
     * @return the minimal boundary of the state, in other words, offset in the input word.
     */
    public static int getMinBoundary(int encodedState, int parametricStatesCount) {
        if (isFailure(encodedState, parametricStatesCount)) {
            throw new NoSuchElementException(
                    "Failure encodedState doesn't have the minimal boundary");
        }

        return decodeMinBoundary(encodedState, parametricStatesCount);
    }

    /**
     * Tests if a state is a final state.
     *
     * @param encodedState a Levenshtein-automaton parametric state encoded
     *                     as an integer value.
     * @param config       the config of the parametric Levenshtein-automaton
     *                     {@code encodedState} belongs to.
     * @return whether a state is a final state or not.
     */
    public static boolean isFinal(int encodedState, LevenshteinAutomatonConfig config) {
        EncodedParametricDescription parametricDescription =
                config.getEncodedParametricDescription();
        int statesCount = parametricDescription.getParametricStatesCount();

        if (isFailure(encodedState, statesCount)) return false;

        int w = config.getWordCodePointCount();
        int n = config.getDegree();
        int minBoundary = getMinBoundary(encodedState, statesCount);
        int stateId = decodeStateId(encodedState, statesCount);
        int[] degreeMinusStateLengthAddendums =
                parametricDescription.getDegreeMinusStateLengthAddendums();

        return minBoundary >= w - n + degreeMinusStateLengthAddendums[stateId];
    }

    /**
     * Tests if a state is a failure state.
     *
     * @param encodedState a Levenshtein-automaton parametric state encoded
     *                     as an integer value.
     * @param config       the config of the parametric Levenshtein-automaton
     *                     {@code encodedState} belongs to.
     * @return whether a state is a failure state or not.
     */
    public static boolean isFailure(int encodedState, LevenshteinAutomatonConfig config) {
        int statesCount = config.getEncodedParametricDescription().getParametricStatesCount();

        return isFailure(encodedState, statesCount);
    }

    /**
     * Tests if a state is a failure state.
     *
     * @param encodedState          a Levenshtein-automaton parametric state encoded
     *                              as an integer value.
     * @param parametricStatesCount the number of parametric states in the parametric
     *                              description of the Levenshtein-automaton.
     * @return whether a state is a failure state or not.
     */
    public static boolean isFailure(int encodedState, int parametricStatesCount) {
        return decodeStateId(encodedState, parametricStatesCount) == parametricStatesCount;
    }

    protected static int relevantSubwordMaxLength(int minBoundary, int n, int w) {
        return Math.min(2 * n + 1, w - minBoundary);
    }

    protected static int decodeMinBoundary(int encodedState, int statesCount) {
        return encodedState / (statesCount + 1);
    }

    protected static int decodeStateId(int encodedState, int statesCount) {
        return encodedState % (statesCount + 1);
    }

    protected static int encodeState(int stateId, int minBoundary, int statesCount) {
        return minBoundary * (statesCount + 1) + stateId;
    }

    private ParametricStateCodec() {
    }
}
