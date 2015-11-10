package io.itdraft.levenshteinautomaton;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ParametricLevenshteinAutomatonTest {

    @Test(expected = ParametricDescriptionNotFoundException.class)
    public void creationWithException() throws ParametricDescriptionNotFoundException {
        ParametricLevenshteinAutomaton.create("x", 10, false);
    }

    @Test
    public void accept() throws ParametricDescriptionNotFoundException {
        String correct = "abcdefg";
        ParametricLevenshteinAutomaton automaton =
                ParametricLevenshteinAutomaton.create(correct, 1, false);
        String misspelled = "abxdefg";
        int state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 1, true);
        misspelled = "abcdegf";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 2, false);
        misspelled = "abxdeg";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 2, true);
        misspelled = "bacdxfg";
        state = process(misspelled, automaton);
        assertTrue(automaton.isFinal(state));
    }

    @Test
    public void reject() throws ParametricDescriptionNotFoundException {
        String correct = "abcdefg";
        ParametricLevenshteinAutomaton automaton =
                ParametricLevenshteinAutomaton.create(correct, 1, false);
        String misspelled = "abxdefgx";
        int state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 1, true);
        misspelled = "abcdegfx";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 2, false);
        misspelled = "abxxdeg";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        automaton = ParametricLevenshteinAutomaton.create(correct, 2, true);
        misspelled = "bacdxxfg";
        state = process(misspelled, automaton);
        assertTrue(!automaton.isFinal(state));
        assertTrue(automaton.isFailure(state));

        // still not the failure state but not yet the final state
        automaton = ParametricLevenshteinAutomaton.create(correct, 2, false);
        misspelled = "abcd";
        state = process(misspelled, automaton);

        assertTrue(!automaton.isFinal(state));
    }

    private int process(String misspelled, ParametricLevenshteinAutomaton automaton) {
        int state = ParametricLevenshteinAutomaton.INITIAL_STATE;
        for (int i = 0, cp = 0; i < misspelled.length(); i+= Character.charCount(cp)) {
            cp = misspelled.codePointAt(i);
            state = automaton.nextState(cp, state);
        }
        return state;
    }

}