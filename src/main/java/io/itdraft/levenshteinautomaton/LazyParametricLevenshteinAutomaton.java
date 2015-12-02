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
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricDescriptionCodec;
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec;

/**
 * A class to represent the Levenshtein-automaton. Actual computation of states isn't
 * performed because parametric automaton is based on the parametric description.
 * <p>
 * <b>Note:</b><br/>
 * Lazy because computes a characteristic vector on every transition.
 * </p>
 */
public class LazyParametricLevenshteinAutomaton {

    /**
     * Initial state of the Levenshtein-automaton. Use it to begin a traverse.
     */
    public static final int INITIAL_STATE = ParametricStateCodec.INITIAL_STATE;

    private final LevenshteinAutomatonConfig config;

    /**
     * Factory method to create an instance of {@code LazyParametricLevenshteinAutomaton}.
     *
     * @return an instance of {@code LazyParametricLevenshteinAutomaton}.
     * @throws ParametricDescriptionNotFoundException if parametric description for specified
     *                                                {@code degree} and {@code inclTransposition}
     *                                                is not found.
     */
    public static LazyParametricLevenshteinAutomaton create(LevenshteinAutomatonConfig config)
            throws ParametricDescriptionNotFoundException {
        EncodedParametricDescription parametricDescription =
                config.getEncodedParametricDescription();

        if (parametricDescription == null) {
            ParametricDescriptionNotFoundException.throwFor(
                    config.getDegree(), config.doesInclTransposition());
        }

        return new LazyParametricLevenshteinAutomaton(config);
    }

    private LazyParametricLevenshteinAutomaton(
            LevenshteinAutomatonConfig config) {
        this.config = config;
    }

    /**
     * Returns a next state.
     *
     * @param stateFrom       a state to transit from.
     * @param symbolCodePoint code point of a next symbol from a word
     *                        is being recognized.
     * @return a next state encoded as integer value.
     */
    public int nextState(int stateFrom, int symbolCodePoint) {
        return ParametricDescriptionCodec.transit(stateFrom, symbolCodePoint, config);
    }

    /**
     * Tests if `state` is a failure state.
     */
    public boolean isFailure(int state) {
        return ParametricStateCodec.isFailure(state, config);
    }

    /**
     * Tests if `state` is a final state.
     */
    public boolean isFinal(int state) {
        return ParametricStateCodec.isFinal(state, config);
    }
}
