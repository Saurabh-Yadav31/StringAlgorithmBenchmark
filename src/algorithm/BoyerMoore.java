package algorithm;
import java.util.*;

public class BoyerMoore {

    /**
     * Searches for the first occurrence of pattern in text using the Boyer-Moore algorithm.
     * Returns -1 if not found.
     */
    public int search(String text, String pattern) {
        // Defensive: check null or empty pattern and text
        if (pattern == null || pattern.length() == 0 || text == null || text.length() < pattern.length()) {
            return -1;
        }

        int[] badChar = preprocessBadCharacter(pattern);
        int n = text.length();
        int m = pattern.length();

        int shift = 0;
        while (shift <= (n - m)) {
            int j = m - 1;
            // Move pattern from right to left
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j))
                j--;

            // Pattern found
            if (j < 0) {
                // System.out.println("Pattern occurs at index " + shift);
                return shift;
            } else {
                // Defensive char conversion for index
                int bcIdx = text.charAt(shift + j) & 0xFF;
                shift += Math.max(1, j - badChar[bcIdx]);
            }
        }
        // Pattern not found
        return -1;
    }

    /**
     * Finds all occurrences of pattern in text, returns a list of starting indices.
     * Returns empty list if not found or on any invalid/empty input.
     */
    public List<Integer> searchAll(String text, String pattern) {
        List<Integer> resultIndices = new ArrayList<>();

        // Defensive: empty/null checks
        if (pattern == null || pattern.length() == 0 || text == null || pattern.length() > text.length()) {
            return resultIndices;
        }
        int[] badChar = preprocessBadCharacter(pattern);
        int[] goodSuffix = preprocessGoodSuffix(pattern);

        int n = text.length();
        int m = pattern.length();
        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;
            // Compare right to left
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j))
                j--;

            if (j < 0) {
                resultIndices.add(shift);
                shift += goodSuffix[0]; // Use good suffix for full match
            } else {
                int bcIdx = text.charAt(shift + j) & 0xFF;
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
     */
    private int[] preprocessBadCharacter(String pattern) {
        int[] badChar = new int[256];
        // Initialize all occurrences as -1
        Arrays.fill(badChar, -1);

        // Fill value of last occurrence for each char in pattern
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i) & 0xFF] = i;
        }
        return badChar;
    }

    /**
     * Preprocesses the good suffix shift table for the pattern.
     * Standard linear implementation.
     */
    private int[] preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        int[] goodSuffix = new int[m];
        int[] borderPos = new int[m + 1];

        Arrays.fill(goodSuffix, 0);

        int i = m, j = m + 1;
        borderPos[i] = j;

        // Preprocess pattern to get border positions
        while (i > 0) {
            while (j <= m && (i - 1 < 0 || pattern.charAt(i - 1) != pattern.charAt(j - 1))) {
                if (goodSuffix[j - 1] == 0) {
                    goodSuffix[j - 1] = j - i;
                }
                j = borderPos[j];
            }
            i--;
            j--;
            borderPos[i] = j;
        }

        // Fill remaining positions of goodSuffix[]
        j = borderPos[0];
        for (i = 0; i < m; i++) {
            if (goodSuffix[i] == 0)
                goodSuffix[i] = j;
            if (i == j)
                j = borderPos[j];
        }
        return goodSuffix;
    }

    // (For install check or playground; can be omitted in deployment.)
    public static void main(String[] args) {
        System.out.println("Setup working: Boyer-Moore class created!");
    }
}
