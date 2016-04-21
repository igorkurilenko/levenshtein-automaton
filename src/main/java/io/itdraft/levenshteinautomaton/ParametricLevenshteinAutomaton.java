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

import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescription;
import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescriptionFactory;
import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescriptionNotFoundException;
import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescriptionFactory;

import static io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.createEncodedCharacteristicVector;

/**
 * A class to represent the Levenshtein-automaton. Actual computation of states isn't
 * performed because parametric automaton is based on the parametric description.
 * <p>
 * <b>Note:</b><br/>
 * Lazy because computes a characteristic vector on every transition.
 * </p>
 */
public class ParametricLevenshteinAutomaton {
    private final int[] wordCodePoints;
    private final ParametricDescription parametricDescription;
    /**
     * The length of the word.
     */
    private final int w;
    /**
     * The degree of the automaton
     */
    private final int n;

    public static ParametricLevenshteinAutomaton create(
            LevenshteinAutomatonConfig config) throws ParametricDescriptionNotFoundException {
        return create(config, new EncodedParametricDescriptionFactory());
    }

    /**
     * Factory method to create an instance of {@code LevenshteinAutomaton}.
     *
     * @return an instance of {@code LevenshteinAutomaton}.
     * @throws ParametricDescriptionNotFoundException if parametric description for specified
     *                                                {@code degree} and {@code inclTransposition}
     *                                                is not found.
     */
    public static ParametricLevenshteinAutomaton create(
            LevenshteinAutomatonConfig config,
            ParametricDescriptionFactory parametricDescriptionFactory)
            throws ParametricDescriptionNotFoundException {
        ParametricDescription parametricDescription =
                parametricDescriptionFactory.getParametricDescription(
                        config.getDegree(), config.doesInclTransposition());

        if (parametricDescription == null) {
            ParametricDescriptionNotFoundException.throwFor(
                    config.getDegree(), config.doesInclTransposition());
        }

        return new ParametricLevenshteinAutomaton(config, parametricDescription);
    }

    private ParametricLevenshteinAutomaton(
            LevenshteinAutomatonConfig config, ParametricDescription parametricDescription) {
        this.wordCodePoints = config.getWordCodePoints();
        this.n = config.getDegree();
        this.w = wordCodePoints.length;
        this.parametricDescription = parametricDescription;
    }

    /**
     * Method to traverse the automaton.
     *
     * @param curStateId  current state.
     * @param codePoint code point of a next symbol from a word
     *                  is being recognized.
     * @return next automaton state.
     */
    public int getNextStateId(int curStateId, int codePoint) {
        int characteristicVector = getRelevantSubwordCharacteristicVector(curStateId, codePoint);

        return parametricDescription.getNextStateId(characteristicVector, curStateId);
    }

    private int getRelevantSubwordCharacteristicVector(int curState, int codePoint) {
        int minBoundary = parametricDescription.getStateMinBoundary(curState);
        int relSubwordMaxLength = relevantSubwordMaxLength(minBoundary, n, w);
        return createEncodedCharacteristicVector(
                codePoint, wordCodePoints, minBoundary, minBoundary + relSubwordMaxLength);
    }

    public int getInitialStateId() {
        return parametricDescription.getInitialStateId();
    }

    private int relevantSubwordMaxLength(int minBoundary, int n, int w) {
        return Math.min(2 * n + 1, w - minBoundary);
    }

    /**
     * Tests if `state` is a failure state.
     */
    public boolean isStateFailure(int stateId) {
        return parametricDescription.isStateFailure(stateId);
    }

    /**
     * Tests if `state` is a final state.
     */
    public boolean isStateFinal(int stateId) {
        return parametricDescription.isStateFinal(stateId, w);
    }
}
