package io.itdraft.levenshteinautomaton;

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
