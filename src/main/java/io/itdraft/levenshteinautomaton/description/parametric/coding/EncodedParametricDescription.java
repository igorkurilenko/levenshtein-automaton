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
    private final UIntPackedArray boundaryOffsets;
    private final int[] degreeMinusStateLength;

    /**
     * Constructor. Use the {@code ParametricDescriptionEncoder} app to create
     * constructor's parameters values.
     *
     * @param automatonDegree         the degree of the Levenshtein-automaton this
     *                                {@code EncodedParametricDescription} is created for.
     * @param inclTransposition       whether {@code EncodedParametricDescription} is created
     *                                for the Levenshtein-automaton which supports transposition
     *                                as a primitive edit operation.
     * @param encodedTransitionsTable the encoded parametric states transitions table.
     * @param encodedBoundaryOffsets  the encoded parametric states minimal boundary offsets
     *                                (a complement to the transitions table).
     * @param degreeMinusStateLength  //todo: provide explanation of this arg.
     */
    public EncodedParametricDescription(int automatonDegree,
                                        boolean inclTransposition,
                                        UIntPackedArray encodedTransitionsTable,
                                        UIntPackedArray encodedBoundaryOffsets,
                                        int[] degreeMinusStateLength) {
        this.automatonDegree = automatonDegree;
        this.inclTransposition = inclTransposition;
        this.encodedTransitionsTable = encodedTransitionsTable;
        this.boundaryOffsets = encodedBoundaryOffsets;
        this.degreeMinusStateLength = degreeMinusStateLength;
        this.parametricStatesCount = degreeMinusStateLength.length;
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
     * the encoded parametric states minimal boundary offsets
     * (a complement to the transitions table).
     */
    public UIntPackedArray getBoundaryOffsets() {
        return boundaryOffsets;
    }

    /**
     * //todo: provide explanation of {@code getDegreeMinusStateLength} method.
     */
    public int[] getDegreeMinusStateLength() {
        return degreeMinusStateLength;
    }
}
