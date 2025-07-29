package algorithm;
import java.util.*;

public class BoyerMoore {

    /**
     * Searches for the first occurrence of pattern in text using the Boyer-Moore algorithm.
     * Previous version: used String.charAt() repeatedly.
     * Optimization: Now uses char[] arrays for text and pattern to improve speed in tight loops.
     */
    public int search(String text, String pattern) {
        // Defensive: check null or empty pattern and text -- moved to top
        if (pattern == null || pattern.length() == 0 || text == null || text.length() < pattern.length()) {
            return -1;
        }

        // My optimization: Convert pattern and text to char[] for faster comparisons in main loop
        char[] txt = text.toCharArray();
        char[] pat = pattern.toCharArray();

        int[] badChar = preprocessBadCharacter(pat); // Now takes pat[]
        int n = txt.length;
        int m = pat.length;

        int shift = 0;
        while (shift <= (n - m)) {
            int j = m - 1;
            // Optimization: Use array comparisons instead of charAt()
            while (j >= 0 && pat[j] == txt[shift + j])
                j--;

            if (j < 0) {
                return shift;
            } else {
                int bcIdx = txt[shift + j] & 0xFF;
                shift += Math.max(1, j - badChar[bcIdx]);
            }
        }
        return -1;
    }

    /**
     * Finds all occurrences of pattern in text, returns a list of starting indices.
     * Returns empty list if not found or on any invalid/empty input.
     * Previous version: used String.charAt(), result default ArrayList.
     * Optimized: now uses char[] for speed, but result is still default ArrayList (per your design).
     */
    public List<Integer> searchAll(String text, String pattern) {
        // Defensive: empty/null checks moved to very top for early exit (optimization)
        List<Integer> resultIndices = new ArrayList<>(); // Default sizing as before
        if (pattern == null || pattern.length() == 0 || text == null || pattern.length() > text.length()) {
            return resultIndices;
        }

        char[] txt = text.toCharArray();
        char[] pat = pattern.toCharArray();

        int[] badChar = preprocessBadCharacter(pat); // Now takes pat[]
        int[] goodSuffix = preprocessGoodSuffix(pat); // Now takes pat[]
        int n = txt.length;
        int m = pat.length;
        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;
            while (j >= 0 && pat[j] == txt[shift + j])
                j--;

            if (j < 0) {
                resultIndices.add(shift);
                shift += goodSuffix[0];
            } else {
                int bcIdx = txt[shift + j] & 0xFF;
                int badCharShift = Math.max(1, j - badChar[bcIdx]);
                int goodSuffixShift = goodSuffix[j];
                shift += Math.max(badCharShift, goodSuffixShift);
            }
        }
        return resultIndices;
    }

    /**
     * Builds the bad character shift table for the pattern.
     * Only covers ASCII chars (0..255).
     * Previous version: String pattern.
     * Optimized: Now accepts char[] for consistent speed throughout code.
     */
    private int[] preprocessBadCharacter(char[] pat) {
        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);

        for (int i = 0; i < pat.length; i++) {
            badChar[pat[i] & 0xFF] = i;
        }
        return badChar;
    }

    /**
     * Preprocesses the good suffix shift table for the pattern.
     * Standard linear implementation.
     * Previous: String pattern.
     * Optimized: Now works on char[] for efficiency.
     */
    private int[] preprocessGoodSuffix(char[] pat) {
        int m = pat.length;
        int[] goodSuffix = new int[m];
        int[] borderPos = new int[m + 1];
        Arrays.fill(goodSuffix, 0);

        int i = m, j = m + 1;
        borderPos[i] = j;
        // Standard algorithm, now using char[] instead of String.charAt()
        while (i > 0) {
            while (j <= m && (i - 1 < 0 || pat[i - 1] != pat[j - 1])) {
                if (goodSuffix[j - 1] == 0) {
                    goodSuffix[j - 1] = j - i;
                }
                j = borderPos[j];
            }
            i--;
            j--;
            borderPos[i] = j;
        }
        // Fill unmatched positions
        j = borderPos[0];
        for (i = 0; i < m; i++) {
            if (goodSuffix[i] == 0)
                goodSuffix[i] = j;
            if (i == j)
                j = borderPos[j];
        }
        return goodSuffix;
    }

    // (For quick check or manual tests; not part of final benchmarks.)
    public static void main(String[] args) {
        BoyerMoore bm = new BoyerMoore();
        String text = "HERE IS A SIMPLE EXAMPLE";
        String pattern = "EXAMPLE";
        System.out.println(bm.searchAll(text, pattern));
    }
}
