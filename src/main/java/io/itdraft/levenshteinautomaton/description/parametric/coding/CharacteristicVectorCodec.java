package io.itdraft.levenshteinautomaton.description.parametric.coding;

import static java.lang.Integer.numberOfLeadingZeros;

public final class CharacteristicVectorCodec {

    public static final int MAX_ALLOWED_SIZE = Integer.SIZE - 1;

    // The empty characteristic vector is represented as 1.
    // There is one-bit which denotes the boundary of the characteristic vector.
    // If there isn't the boundary then two different characteristic vectors can
    // be represented by the same integer value.
    // E.g. <0,1,0,1> and <0,0,0,0,1,0,1> could have been represented as 5.
    // But if the boundary is introduced than the first vector will be 10101
    // and the latter 10000101 in binary (21 and 133 in decimal).
    public static final int EMPTY = 0x01;

    public static int createEncoded(final int xCodePoint, final String word,
                                    final int from, final int until) {
        assert (until - from <= MAX_ALLOWED_SIZE);

        int r = 0, i = 0, curCodePoint = 0;
        int vector = EMPTY;

        while (r < until && i < word.length()) {
            curCodePoint = word.codePointAt(i);

            if (from <= r && r < until) {
                vector <<= 1;
                if (curCodePoint == xCodePoint) vector |= 1;
            }

            i += Character.charCount(curCodePoint);
            r += 1;
        }

        return vector;
    }

    /**
     * <code>j&#8712;{1, ..., k}</code> is the minimal index in the characteristic vector
     * <code>&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;</code> where <code>b<sub>j</sub> = 1</code>.
     *
     * @return Minimal index <code>j</code> where <code>b<sub>j</sub>=1</code> in the characteristic
     * vector or <code>-1</code> if <code>b<sub>j</sub>=0</code> for any <code>j</code>.
     */
    public static int decodeJ(int encodedVector) {
        int result = -1;

        if ((result = positionOfFirstNonZero(encodedVector)) >= 0) {
            result++;
        }

        return result;
    }

    /**
     * Returns a size of the characteristic vector <code>v</code>.
     */
    public static int size(int v) {
        return Integer.SIZE - numberOfLeadingZeros(v) - 1;
    }

    /**
     * Returns a position of the first one-bit or -1 if there are zero-bits only in a
     * relevant subword characteristic vector. Position numbering starts with 0.
     */
    private static int positionOfFirstNonZero(int v) {
        int boundaryPosition = numberOfLeadingZeros(v);
        int vWithoutBoundary = v ^ (1 << Integer.MAX_VALUE - boundaryPosition);
        int result = -1;

        if (vWithoutBoundary != 0) {
            result = numberOfLeadingZeros(vWithoutBoundary) - boundaryPosition - 1;
        }

        return result;
    }

    private CharacteristicVectorCodec() {
    }
}
