package io.itdraft.levenshteinautomaton.util;

public class IntArrayUtil {

    public static int[] getUniqueElements(final int[] arr, final int length) {
        int uniqueElementsCount = countUniqueElements(arr, length);
        int[] result = new int[uniqueElementsCount];

        for (int i = 0; i < length; i++) {
            int alpha = arr[i];
            if (alpha != 0) {
                int index = getIndexOfFreeHashCell(result, alpha, uniqueElementsCount);
                if (index >= 0) {
                    result[index] = alpha;
                }
            }
        }

        return result;
    }

    private static int countUniqueElements(final int[] arr, final int length) {
        if (length <= 0) return 0;
        if (length == 1) return 1;
        if (length == 2) return arr[0] == arr[1] ? 1 : 2;

        return doCountUniqueElements(arr, length);
    }

    private static int doCountUniqueElements(final int[] arr, final int length) {
        final int[] hash = new int[length];
        int result = 0;
        boolean takeZero = false;

        for (int i = 0; i < length; i++) {
            final int alpha = arr[i];

            if (alpha == 0) {
                takeZero = true;

            } else {
                final int index = getIndexOfFreeHashCell(hash, alpha, length);

                if (index >= 0) {
                    hash[index] = alpha;
                    result++;
                }
            }
        }

        return takeZero ? result + 1 : result;
    }

    private static int getIndexOfFreeHashCell(final int[] hash, final int alpha, final int length) {
        for (int index = Math.abs(alpha % length); ; index = (index + 1) % length) {
            final int inHash = hash[index];

            if (inHash == 0) return index;

            if (inHash == alpha) return -1;
        }
    }

    public static int findInHash(final int[] hash, final int alpha, final int length) {
        if (length <= 0) return -1;
        if (length == 1) return hash[0] == alpha ? 0 : -1;

        return doFindInHash(hash, alpha, length);
    }

    private static int doFindInHash(final int[] hash, final int alpha, final int length) {
        for (int j = 0, index = Math.abs(alpha % length); j < length; j++) {
            final int inHash = hash[index];

            if (inHash == alpha) return index;

            index = (index + 1) % length;
        }

        return -1;
    }

    private IntArrayUtil() {
    }
}
