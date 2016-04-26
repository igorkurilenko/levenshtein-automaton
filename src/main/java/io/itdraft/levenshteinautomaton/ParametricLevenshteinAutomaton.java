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

import static io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.computeEncodedCharacteristicVector;
import static io.itdraft.levenshteinautomaton.util.StringUtil.toCodePoints;

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
        wordCodePoints = config.getWordCodePoints();
        n = config.getDegree();
        w = wordCodePoints.length;
        this.parametricDescription = parametricDescription;
    }

    /**
     * Method to traverse the automaton.
     *
     * @param curStateId     current state.
     * @param alphaCodePoint code point of a next alpha from the word
     *                       is being recognized.
     * @return next automaton state.
     */
    public int getNextStateId(int curStateId, int alphaCodePoint) {
        int minBoundary = parametricDescription.getStateMinBoundary(curStateId);

        int characteristicVector = computeRelevantSubwordCharacteristicVector(
                minBoundary, alphaCodePoint);

        return parametricDescription.getNextStateId(characteristicVector, curStateId);
    }

    private int computeRelevantSubwordCharacteristicVector(int wordOffset, int codePoint) {
        int relevantSubwordLength = relevantSubwordLength(wordOffset, n, w);

        return computeEncodedCharacteristicVector(
                codePoint, wordCodePoints, wordOffset, wordOffset + relevantSubwordLength);
    }

    public int getInitialStateId() {
        return parametricDescription.getInitialStateId();
    }

    private int relevantSubwordLength(int minBoundary, int n, int w) {
        return Math.min(2 * n + 1, w - minBoundary);
    }

    /**
     * Tests if `state` is a failure state.
     */
    public boolean isFailureState(int stateId) {
        return parametricDescription.isFailureState(stateId);
    }

    /**
     * Tests if `state` is a final state.
     */
    public boolean isFinalState(int stateId) {
        return parametricDescription.isFinalState(stateId, w);
    }

    public static void main(String[] args) throws ParametricDescriptionNotFoundException {
        int[] misspelled = toCodePoints("xx");
        LevenshteinAutomatonConfig config = new LevenshteinAutomatonConfig("", 2, true);
        ParametricLevenshteinAutomaton a = ParametricLevenshteinAutomaton.create(config);
        int stateId = a.getInitialStateId();

        for (int i = 0; i < misspelled.length; i++) {
            stateId = a.getNextStateId(stateId, misspelled[i]);
        }

        System.out.println(a.isFinalState(stateId));
    }
}
