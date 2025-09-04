package tech.dlii.opencomputers.util;

public final class ExtendedUnicodeHelper {
    public static int length(String s) {
        return s.codePointCount(0, s.length());
    }

    public static String reverse(String s) {
        int[] codePoints = s.codePoints().toArray();
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = codePoints.length - 1; i >= 0; i--) {
            sb.appendCodePoint(codePoints[i]);
        }
        return sb.toString();
    }

    public static String substring(String s, int start, int end) {
        return s.substring(
                s.offsetByCodePoints(0, start),
                s.offsetByCodePoints(0, end)
        );
    }

    private ExtendedUnicodeHelper() {}
}