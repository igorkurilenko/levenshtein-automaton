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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LazyParametricLazyLevenshteinAutomatonTest {

    @Test(expected = ParametricDescriptionNotFoundException.class)
    public void creationWithException() throws ParametricDescriptionNotFoundException {
        LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig("x", 10, false, null));
    }

    @Test
    public void accept() throws ParametricDescriptionNotFoundException {
        String correct = "abcdefg";
        LazyParametricLevenshteinAutomaton automaton =
                LazyParametricLevenshteinAutomaton.create(
                        new LevenshteinAutomatonConfig(correct, 1, false)
                );
        String misspelled = "abxdefg";
        int state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 1, true)
        );
        misspelled = "abcdegf";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 2, false)
        );
        misspelled = "abxdeg";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 2, true)
        );
        misspelled = "bacdxfg";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));
    }

    @Test
    public void reject() throws ParametricDescriptionNotFoundException {
        String correct = "abcdefg";
        LazyParametricLevenshteinAutomaton automaton =
                LazyParametricLevenshteinAutomaton.create(
                        new LevenshteinAutomatonConfig(correct, 1, false)
                );
        String misspelled = "abxdefgx";
        int state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 1, true)
        );
        misspelled = "abcdegfx";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 2, false)
        );
        misspelled = "abxxdeg";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 2, true)
        );
        misspelled = "bacdxxfg";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        // still not the failure state but not yet the final state
        automaton = LazyParametricLevenshteinAutomaton.create(
                new LevenshteinAutomatonConfig(correct, 2, false)
        );
        misspelled = "abcd";
        state = process(misspelled, automaton);

        assertTrue(!automaton.isFinal(state));
    }

    private int process(String misspelled, LazyParametricLevenshteinAutomaton automaton) {
        int state = LazyParametricLevenshteinAutomaton.INITIAL_STATE;
        for (int i = 0, cp = 0; i < misspelled.length(); i += Character.charCount(cp)) {
            cp = misspelled.codePointAt(i);
            state = automaton.nextState(state, cp);
        }
        return state;
    }

}