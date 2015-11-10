package io.itdraft.levenshteinautomaton.description.parametric.coding.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongFunction;

public class UIntPackedArray {

    private final static long[] MASKS = new long[]{
            0x1, 0x3, 0x7, 0xf,
            0x1f, 0x3f, 0x7f, 0xff,
            0x1ff, 0x3ff, 0x7ff, 0xfff,
            0x1fff, 0x3fff, 0x7fff, 0xffff,
            0x1ffff, 0x3ffff, 0x7ffff, 0xfffff,
            0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
            0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
            0x1fffffff, 0x3fffffff, 0x7fffffffL, 0xffffffffL,
            0x1ffffffffL, 0x3ffffffffL, 0x7ffffffffL, 0xfffffffffL,
            0x1fffffffffL, 0x3fffffffffL, 0x7fffffffffL, 0xffffffffffL,
            0x1ffffffffffL, 0x3ffffffffffL, 0x7ffffffffffL, 0xfffffffffffL,
            0x1fffffffffffL, 0x3fffffffffffL, 0x7fffffffffffL, 0xffffffffffffL,
            0x1ffffffffffffL, 0x3ffffffffffffL, 0x7ffffffffffffL, 0xfffffffffffffL,
            0x1fffffffffffffL, 0x3fffffffffffffL, 0x7fffffffffffffL, 0xffffffffffffffL,
            0x1ffffffffffffffL, 0x3ffffffffffffffL, 0x7ffffffffffffffL, 0xfffffffffffffffL,
            0x1fffffffffffffffL, 0x3fffffffffffffffL, 0x7fffffffffffffffL};
    public static final long[] EMPTY = new long[]{};

    private final long[] packed;
    private final int bitsPerValue;

    public UIntPackedArray(int bitsPerValue, long[] packed) {
        this.packed = packed;
        this.bitsPerValue = bitsPerValue;
    }

    public int getBitsPerValue() {
        return bitsPerValue;
    }

    public int get(int index) {
        return unpack(packed, index, bitsPerValue);
    }

    public void foreach(LongFunction<Void> f) {
        for (long x : packed) {
            f.apply(x);
        }
    }

    public static UIntPackedArray pack(int[] xs) {
        if (xs == null || xs.length == 0) {
            throw new IllegalArgumentException("xs must be not null or empty");
        }

        final int maxX = max(xs);
        final int bitsPerValue = Math.max(1, bitsUsed(maxX));
        int bitsLeft = Long.SIZE;
        long pendingValue = 0;
        final List<Long> packed = new LinkedList<>();

        for (long x : xs) {
            if (x < 0) {
                throw new IllegalArgumentException("xs must contain positive integers only");
            }

            if (bitsLeft >= bitsPerValue) {
                pendingValue += x << (Long.SIZE - bitsLeft);
                bitsLeft -= bitsPerValue;

                if (bitsLeft == 0) {
                    packed.add(pendingValue);
                    bitsLeft = Long.SIZE;
                    pendingValue = 0;
                }

            } else {
                pendingValue += (x & MASKS[bitsLeft - 1]) << (Long.SIZE - bitsLeft);
                packed.add(pendingValue);

                pendingValue = x >> bitsLeft;
                bitsLeft = Long.SIZE - (bitsPerValue - bitsLeft);
            }
        }

        if (bitsLeft < Long.SIZE) {
            packed.add(pendingValue);
        }

        return new UIntPackedArray(bitsPerValue, toPrimitive(packed));
    }

    private static long[] toPrimitive(List<Long> xs) {
        long[] result = new long[xs.size()];
        for (int i = 0; i < xs.size(); i++) result[i] = xs.get(i);
        return result;
    }

    private static int unpack(long[] packed, int index, int bitsPerValue) {
        final long bitLoc = bitsPerValue * index;
        final int dataLoc = (int) (bitLoc >> 6);
        final int bitStart = (int) (bitLoc & 63);

        if (bitStart + bitsPerValue <= 64) {
            return (int) ((packed[dataLoc] >> bitStart) & MASKS[bitsPerValue - 1]);

        } else {
            final int part = 64 - bitStart;
            return (int) (((packed[dataLoc] >> bitStart) & MASKS[part - 1]) +
                    ((packed[1 + dataLoc] & MASKS[bitsPerValue - part - 1]) << part));
        }
    }


    private static int bitsUsed(int x) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(x);
    }

    private static int max(int[] xs) {
        int max = xs[0];

        for (int i = 1; i < xs.length; i++) {
            if (xs[i] > max) {
                max = xs[i];
            }
        }

        return max;
    }
}
