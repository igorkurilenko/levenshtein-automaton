package io.itdraft.levenshteinautomaton.util;

public class StringUtil {

    public static int[] toCodePoints(String s) {
        final int[] result = new int[Character.codePointCount(s, 0, s.length())];
        final int length = s.length();

        for (int i = 0, j = 0; i < length; ++j) {
            final int codePoint = Character.codePointAt(s, i);
            result[j] = codePoint;
            i += Character.charCount(codePoint);
        }

        return result;
    }

    public static int codePointCount(String s) {
        return s.codePointCount(0, s.length());
    }

}
