package algorithm;

import java.util.*;

public class KMP {

    /**
     * Computes the Longest Prefix Suffix (LPS) array used in KMP algorithm.
     * LPS[i] stores the length of the longest proper prefix which is also a suffix for the substring pattern[0..i].
     * @param pattern The pattern string
     * @return The computed LPS array
     */
    public int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0; // length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Searches for all occurrences of the pattern in the text using the KMP algorithm.
     * Returns list of all starting indices where pattern is found.
     * Returns empty list if pattern is null or empty.
     *
     * @param text    The input text to search within
     * @param pattern The pattern to find
     * @return List of indices where pattern starts in text
     */
    public List<Integer> KMPSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();

        // Defensive checks for empty or null pattern or text
        if (pattern == null || pattern.length() == 0 || text == null || pattern.length() > text.length()) {
            return result; // empty list
        }

        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                // Match found, add start index to results
                result.add(i - j);
                // Continue search for next matches
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                // Mismatch after j matches
                if (j != 0) {
                    j = lps[j - 1]; // Use LPS to avoid unnecessary comparisons
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    // Optional test main to verify working implementation
    public static void main(String[] args) {
        KMP kmp = new KMP();
        String text = "ababcababcababc";
        String pattern = "ababc";
        List<Integer> matches = kmp.KMPSearch(text, pattern);
        System.out.println("Pattern found at indices: " + matches);
    }
}
