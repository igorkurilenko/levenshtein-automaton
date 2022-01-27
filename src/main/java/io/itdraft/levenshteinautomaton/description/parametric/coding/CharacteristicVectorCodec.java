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

import static java.lang.Integer.numberOfLeadingZeros;

/**
 * This util class computes a characteristic vector and
 * encodes it as an integer value.
 */
public final class CharacteristicVectorCodec {

    public static final int MAX_ALLOWED_VECTOR_SIZE = Integer.SIZE - 1;

    // The empty characteristic vector is represented as 1.
    // There is one-bit which denotes the boundary of the characteristic vector.
    // If there isn't the boundary then two different characteristic vectors can
    // be represented by the same integer value.
    // E.g. <0,1,0,1> and <0,0,0,0,1,0,1> could have been represented as 5.
    // But if the boundary is introduced than the first vector will be 10101
    // and the latter 10000101 in binary (21 and 133 in decimal).
    public static final int EMPTY = 0x01;

    /**
     * Creates the characteristic vector <code>&lt;b<sub>i</sub>, ..., b<sub>k</sub>&gt;</code>
     * of an alpha with respect to {@code word} where {@code i = from}
     * and {@code k = until - 1}. If the specified interval is invalid then an empty vector will
     * be returned (no exceptions are thrown).
     * <p>
     * <b>Note:</b>
     * </p>
     * <p>
     * If {@code until - from} exceeds
     * {@link CharacteristicVectorCodec#MAX_ALLOWED_VECTOR_SIZE}
     * then invalid characteristic vector will be returned.
     * </p>
     *
     * @param alphaCodePoint an alpha's code point the characteristic
     *                       vector is being built for.
     * @param wordCodePoints code points of the word the characteristic vector is being built for.
     * @param from           minimal boundary of {@code word} to start building the
     *                       characteristic vector.
     * @param until          maximal boundary until which the characteristic
     *                       vector is being built for.
     * @return an encoded characteristic vector as integer value of the {@code word}'s
     * range from {@code from} up to (but not including) {@code until}.
     */
    public static int computeEncodedCharacteristicVector(final int alphaCodePoint,
                                                         final int[] wordCodePoints,
                                                         final int from, final int until) {
        assert until - from <= MAX_ALLOWED_VECTOR_SIZE :
                "Specified range for characteristic vector creation exceeds maximal allowed value.";

        int vector = EMPTY;
        int start = Math.max(from, 0);
        int end = Math.min(until, wordCodePoints.length);

        for (int i = start; i < end; i++) {
            vector <<= 1;

            if (wordCodePoints[i] == alphaCodePoint) vector |= 1;
        }

        return vector;
    }

    /**
     * <code>j&#8712;{1, ..., k}</code> is the minimal index in the characteristic vector
     * <code>&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;</code> where <code>b<sub>j</sub> = 1</code>.
     *
     * @return Minimal index {@code j} where <code>b<sub>j</sub>=1</code> in the characteristic
     * vector or {@code  -1} if <code>b<sub>j</sub>=0</code> for each {@code j}.
     */
    public static int decodeJ(int encodedVector) {
        int result = -1;

        if ((result = positionOfFirstNonZero(encodedVector)) >= 0) {
            result++;
        }

        return result;
    }

    /**
     * Returns a size of the characteristic vector.
     *
     * @param encodedVector an encoded characteristic vector.
     * @return number of elements in the characteristic vector.
     */
    public static int size(int encodedVector) {
        return Integer.SIZE - numberOfLeadingZeros(encodedVector) - 1;
    }

    /**
     * Returns a position of the first one-bit or -1 if there are zero-bits only in a
     * relevant subword characteristic vector. Position numbering starts with 0.
     */
    private static int positionOfFirstNonZero(int encodedVector) {
        int boundaryPosition = numberOfLeadingZeros(encodedVector);
        int vWithoutBoundary = encodedVector ^ (1 << Integer.MAX_VALUE - boundaryPosition);
        int result = -1;

        if (vWithoutBoundary != 0) {
            result = numberOfLeadingZeros(vWithoutBoundary) - boundaryPosition - 1;
        }

        return result;
    }

    public static String toBinaryString(int encodedVector) {
        return Integer.toBinaryString(encodedVector).substring(1);
    }

    private CharacteristicVectorCodec() {
    }
}
