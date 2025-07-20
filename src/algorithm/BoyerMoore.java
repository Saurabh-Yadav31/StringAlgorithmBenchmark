package algorithm;
import java.util.*;

public class BoyerMoore {

    // Main search function
    public int search(String text, String pattern) {
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
                System.out.println("Pattern occurs at index " + shift);
                return shift; // returns on first occurrence for now
            } else {
                // Calculate the bad character shift
                shift += Math.max(1, j - badChar[text.charAt(shift + j)]);
            }
        }
        // Pattern not found
        return -1;
    }
    // Finds all occurrences of pattern in text and returns their starting indices
    public List<Integer> searchAll(String text, String pattern) {
        List<Integer> resultIndices = new ArrayList<>();
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
                int badCharShift = Math.max(1, j - badChar[text.charAt(shift + j)]);
                int goodSuffixShift = goodSuffix[j];
                shift += Math.max(badCharShift, goodSuffixShift);
            }
        }
        return resultIndices;
    }


    // Builds the bad character shift table
    private int[] preprocessBadCharacter(String pattern) {
        int[] badChar = new int[256];
        // Initialize all occurrences as -1
        for (int i = 0; i < 256; i++) {
            badChar[i] = -1;
        }
        // Fill the actual value of last occurrence for each character
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
        return badChar;
    }

    // Placeholder for good suffix heuristic
    private int[] preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        int[] goodSuffix = new int[m];
        int[] borderPos = new int[m + 1];

        // Step 1: Initialize all occurrences as 0
        for (int i = 0; i < m; i++)
            goodSuffix[i] = 0;

        int i = m, j = m + 1;
        borderPos[i] = j;  // border at pattern[m]

        // Step 2: Preprocess pattern to get border positions
        while (i > 0) {
            while (j <= m && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {
                if (goodSuffix[j - 1] == 0) {
                    goodSuffix[j - 1] = j - i;
                }
                j = borderPos[j];
            }
            i--; j--;
            borderPos[i] = j;
        }

        // Step 3: Fill the remaining positions of goodSuffix[]
        j = borderPos[0];
        for (i = 0; i < m; i++) {
            if (goodSuffix[i] == 0)
                goodSuffix[i] = j;
            if (i == j)
                j = borderPos[j];
        }
        return goodSuffix;
    }


    public static void main(String[] args) {
        System.out.println("Setup working: Boyer-Moore class created!");
    }
}
