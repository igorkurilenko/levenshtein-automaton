package io.itdraft.levenshteinautomaton.description.parametric.coding;

import java.util.NoSuchElementException;

public class ParametricStateCodec {

    public static final int INITIAL_STATE = 0;

    public static int transit(int xCodePoint, int state, int w, int n,
                              String word, EncodedParametricDscr parametricDescription) {
        int statesCount = parametricDescription.getStatesCount();

        if (isFailure(state, statesCount)) return state;

        int stateId = decodeStateId(state, statesCount);
        int minBoundary = decodeMinBoundary(state, statesCount);
        int relSubwordMaxLength = relevantSubwordMaxLength(minBoundary, n, w);
        int v = CharacteristicVectorCodec.createEncoded(
                xCodePoint, word, minBoundary, minBoundary + relSubwordMaxLength);
        int index = (v - 1) * statesCount + stateId;
        int nextStateId = parametricDescription.getTransitions().get(index);
        int boundaryOffset = parametricDescription.getBoundaryOffsets().get(index);

        return encodeState(nextStateId, minBoundary + boundaryOffset, statesCount);
    }

    public static int getMinBoundary(int state, int statesCount) {
        if (isFailure(state, statesCount)) {
            throw new NoSuchElementException("Failure state doesn't have the minimal boundary");
        }

        return decodeMinBoundary(state, statesCount);
    }

    public static boolean isFinal(int state, int w, int n,
                                  EncodedParametricDscr parametricDescription) {
        int statesCount = parametricDescription.getStatesCount();
        boolean isFailure = isFailure(state, statesCount);

        if (isFailure) return false;

        int minBoundary = getMinBoundary(state, statesCount);
        int stateId = decodeStateId(state, statesCount);
        int[] degreeMinusStateLength = parametricDescription.getDegreeMinusStateLength();

        return minBoundary >= w - n + degreeMinusStateLength[stateId];
    }

    public static boolean isFailure(int encodedState, int statesCount) {
        return decodeStateId(encodedState, statesCount) == statesCount;
    }

    private static int relevantSubwordMaxLength(int minBoundary, int n, int w) {
        return Math.min(2 * n + 1, w - minBoundary);
    }

    private static int decodeMinBoundary(int encodedState, int statesCount) {
        return encodedState / (statesCount + 1);
    }

    private static int decodeStateId(int encodedState, int statesCount) {
        return encodedState % (statesCount + 1);
    }

    private static int encodeState(int stateId, int minBoundary, int statesCount) {
        return minBoundary * (statesCount + 1) + stateId;
    }

    private ParametricStateCodec() {
    }
}
