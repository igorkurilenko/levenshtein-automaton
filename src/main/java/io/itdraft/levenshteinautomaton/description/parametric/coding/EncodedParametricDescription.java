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

import io.itdraft.levenshteinautomaton.description.parametric.coding.util.UIntPackedArray;

/**
 * Represents the encoded parametric description of the Levenshtein-automaton.
 */
public class EncodedParametricDescription {
    private final int automatonDegree;
    private final boolean inclTransposition;
    private final int parametricStatesCount;
    private final UIntPackedArray encodedTransitionsTable;
    private final UIntPackedArray encodedBoundaryOffsets;
    private final int[] degreeMinusStateLengthAddendums;

    /**
     * Constructor. Use the {@code ParametricDescriptionEncoder} app to create
     * constructor's parameters values.
     *
     * @param automatonDegree                 the degree of the Levenshtein-automaton this
     *                                        {@code EncodedParametricDescription} is created for.
     * @param inclTransposition               whether {@code EncodedParametricDescription} is
     *                                        created for the Levenshtein-automaton which supports
     *                                        transposition as a primitive edit operation.
     * @param encodedTransitionsTable         the encoded parametric states transitions table.
     * @param encodedBoundaryOffsets          the encoded parametric states minimal boundary offsets
     *                                        (a complement to the transitions table).
     * @param degreeMinusStateLengthAddendums This addendums are required for
     *                                        {@link ParametricStateCodec} to detect whether an
     *                                        encoded parametric state is final. Inspect javadoc of
     *                                        {@link EncodedParametricDescription#getDegreeMinusStateLengthAddendums()}
     *                                        for details.
     */
    public EncodedParametricDescription(int automatonDegree,
                                        boolean inclTransposition,
                                        UIntPackedArray encodedTransitionsTable,
                                        UIntPackedArray encodedBoundaryOffsets,
                                        int[] degreeMinusStateLengthAddendums) {
        this.automatonDegree = automatonDegree;
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
    public int getAutomatonDegree() {
        return automatonDegree;
    }

    /**
     * Whether this {@code EncodedParametricDescription} is created for the Levenshtein-automaton
     * which supports transposition as a primitive edit operation.
     */
    public boolean doesInclTransposition() {
        return inclTransposition;
    }

    /**
     * Returns the number of parametric states this {@code EncodedParametricDescription} contains.
     */
    public int getParametricStatesCount() {
        return parametricStatesCount;
    }

    /**
     * Returns the encoded parametric states transitions table.
     */
    public UIntPackedArray getEncodedTransitionsTable() {
        return encodedTransitionsTable;
    }

    /**
     * The encoded parametric states minimal boundary offsets
     * (a complement to the transitions table).
     */
    public UIntPackedArray getEncodedBoundaryOffsets() {
        return encodedBoundaryOffsets;
    }

    /**
     * This addendums are required for {@link ParametricStateCodec}
     * to detect whether an encoded parametric state is final.
     * <p>Position {@code i^#e} is accepting if <p>{@code w - i <= n - e}.</p>
     * By definition a state is final if it contains an accepting position.
     * Let {@code j^#f} be the max boundary position of the state.
     * Let {@code i^#j} be the min boundary position of the state.
     * The state becomes final as soon as max boundary position becomes
     * an accepting position: <p>{@code w - j <= n - f}.</p>
     * Let assume that {@code stateLength = j - i}. Then
     * <p>{@code j = i + stateLength}</p>
     * <p>{@code w - (i + stateLength) <= n - f}</p>
     * then the condition for state to become final turns into the following form
     * <p>{@code i >= w - n + f - stateLength}</p>
     * This formula is applied in {@code ParametricStateCodec}.
     * {@code w, n} and {@code i} are dependent of context,
     * but the {@code f - stateLength} addendum (where {@code f}
     * is the degree of the max boundary position fo the state) is static for
     * each state.</p>
     */
    public int[] getDegreeMinusStateLengthAddendums() {
        return degreeMinusStateLengthAddendums;
    }

    @Override
    public String toString() {
        return toJavaString();
    }

    public String toJavaString() {
        StringBuilder sb = new StringBuilder();
        sb.append("new EncodedParametricDescription(")
                .append(getAutomatonDegree()).append(", ")
                .append(doesInclTransposition()).append(", \n\t");

        appendUIntPackedArray(sb, getEncodedTransitionsTable())
                .append(", \n\t");

        appendUIntPackedArray(sb, getEncodedBoundaryOffsets())
                .append(", \n\t");

        appendIntArray(sb, getDegreeMinusStateLengthAddendums())
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
