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

import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescription;
import io.itdraft.levenshteinautomaton.util.UIntPackedArray;

import java.util.NoSuchElementException;

/**
 * Represents the encoded parametric description of the Levenshtein-automaton.
 */
public class EncodedParametricDescription implements ParametricDescription {

    public static final int INITIAL_STATE_ID = 0;

    private final int degree;
    private final boolean inclTransposition;
    private final int parametricStatesCount;
    private final UIntPackedArray encodedTransitionsTable;
    private final UIntPackedArray encodedBoundaryOffsets;
    private final int[] degreeMinusStateLengthAddendums;

    /**
     * Constructor. Use the {@code ParametricDescriptionEncoder} app to create
     * constructor's parameters values.
     *
     * @param degree                          the degree of the Levenshtein-automaton this
     *                                        {@code EncodedParametricDescription} is created for.
     * @param inclTransposition               whether {@code EncodedParametricDescription} is
     *                                        created for the Levenshtein-automaton which supports
     *                                        transposition as a primitive edit operation.
     * @param encodedTransitionsTable         the encoded parametric states transitions table.
     * @param encodedBoundaryOffsets          the encoded parametric states minimal boundary offsets
     *                                        (a complement to the transitions table).
     * @param degreeMinusStateLengthAddendums addendums required to detect whether an encoded
     *                                        parametric state is final. <p>Position {@code i^#e} is
     *                                        accepting if <p>{@code w - i <= n - e}.</p>
     *                                        By definition a state is final if it contains an accepting position.
     *                                        Let {@code j^#f} be the max boundary position of the state.
     *                                        Let {@code i^#j} be the min boundary position of the state.
     *                                        The state becomes final as soon as max boundary position becomes
     *                                        an accepting position: <p>{@code w - j <= n - f}.</p>
     *                                        Let assume that {@code stateLength = j - i}. Then
     *                                        <p>{@code j = i + stateLength}</p>
     *                                        <p>{@code w - (i + stateLength) <= n - f}</p>
     *                                        then the condition for state to become final turns into the following form
     *                                        <p>{@code i >= w - n + f - stateLength}</p>
     *                                        This formula is applied in {@code ParametricStateCodec}.
     *                                        {@code w, n} and {@code i} are dependent of context,
     *                                        but the {@code f - stateLength} addendum (where {@code f}
     *                                        is the degree of the max boundary position fo the state) is static for
     *                                        each state.
     *                                        </p>
     */
    public EncodedParametricDescription(int degree,
                                        boolean inclTransposition,
                                        UIntPackedArray encodedTransitionsTable,
                                        UIntPackedArray encodedBoundaryOffsets,
                                        int[] degreeMinusStateLengthAddendums) {
        this.degree = degree;
        this.inclTransposition = inclTransposition;
        this.encodedTransitionsTable = encodedTransitionsTable;
        this.encodedBoundaryOffsets = encodedBoundaryOffsets;
        this.degreeMinusStateLengthAddendums = degreeMinusStateLengthAddendums;
        this.parametricStatesCount = degreeMinusStateLengthAddendums.length;
    }

    /**
     * Returns the degree of the Levenshtein-automaton this {@code EncodedParametricDescription}
     * is created for.
     * <p>
     * <p><b>Note:</b><br/>
     * Automaton recognizes the set of all words
     * where the Levenshtein-distance between a word from the set
     * and a word the automaton is built for does not exceed the degree.
     * </p>
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Whether this {@code EncodedParametricDescription} is created for the Levenshtein-automaton
     * which supports transposition as a primitive edit operation.
     */
    public boolean doesInclTransposition() {
        return inclTransposition;
    }

    /**
     * Decodes the next stateId from the encoded parametric
     * description and returns it encoded as an integer value.
     *
     * @param curStateId           current Levenshtein-automaton parametric state
     *                             encoded as an integer.
     * @param characteristicVector a characteristic vector of a relevant subword.
     * @return a next state id of the Levenshtein-automaton encoded as an integer value.
     */
    public int getNextStateId(int characteristicVector, int curStateId) {
        if (isFailureState(curStateId)) return curStateId;

        int stateRealId = decodeStateRealId(curStateId);
        int minBoundary = decodeMinBoundary(curStateId);
        int index = (characteristicVector - 1) * parametricStatesCount + stateRealId;
        int nextStateRealId = encodedTransitionsTable.get(index);
        int boundaryOffset = encodedBoundaryOffsets.get(index);

        return encodedStateId(nextStateRealId, minBoundary + boundaryOffset, parametricStatesCount);
    }

    public int getInitialStateId() {
        return INITIAL_STATE_ID;
    }

    public boolean isFinalState(int stateId, int w) {
        if (isFailureState(stateId)) return false;

        int n = degree;
        int minBoundary = getStateMinBoundary(stateId);
        int stateRealId = decodeStateRealId(stateId);

        return minBoundary >= w - n + degreeMinusStateLengthAddendums[stateRealId];
    }

    public boolean isFailureState(int stateId) {
        return decodeStateRealId(stateId) == parametricStatesCount;
    }

    public int getStateMinBoundary(int stateId) {
        if (isFailureState(stateId)) {
            throw new NoSuchElementException(
                    "Failure encodedState doesn't have the minimal boundary");
        }

        return decodeMinBoundary(stateId);
    }

    private int decodeMinBoundary(int stateId) {
        return stateId / (parametricStatesCount + 1);
    }

    private int decodeStateRealId(int stateId) {
        return stateId % (parametricStatesCount + 1);
    }

    private int encodedStateId(int stateRealId, int minBoundary, int statesCount) {
        return minBoundary * (statesCount + 1) + stateRealId;
    }

    @Override
    public String toString() {
        return toJavaString();
    }

    public String toJavaString() {
        StringBuilder sb = new StringBuilder();
        sb.append("new EncodedParametricDescription(")
                .append(getDegree()).append(", ")
                .append(doesInclTransposition()).append(", \n\t");

        appendUIntPackedArray(sb, encodedTransitionsTable)
                .append(", \n\t");

        appendUIntPackedArray(sb, encodedBoundaryOffsets)
                .append(", \n\t");

        appendIntArray(sb, degreeMinusStateLengthAddendums)
                .append(")");

        return sb.toString();
    }

    private StringBuilder appendUIntPackedArray(StringBuilder sb, UIntPackedArray arr) {
        sb.append("new UIntPackedArray(")
                .append(arr.getBitsPerValue()).append(", ")
                .append("new long[]{");

        final int[] i = {1};
        arr.foreach((value, isFinal) -> {
            sb.append("0x").append(Long.toHexString(value)).append("L");

            if (!isFinal) {
                sb.append(", ");

                if (i[0]++ % 5 == 0) {
                    sb.append("\n\t\t");
                }
            }
        });

        sb.append("})");

        return sb;
    }

    private StringBuilder appendIntArray(StringBuilder sb, int[] arr) {
        sb.append("new int[]{");

        for (int i = 0, length = arr.length; i < length; i++) {
            sb.append(arr[i]);

            if (i != length - 1) {
                sb.append(", ");
            }
        }

        sb.append('}');

        return sb;
    }
}
