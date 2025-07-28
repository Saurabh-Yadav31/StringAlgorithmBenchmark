package algorithm;

import java.util.*;

public class KMP {

    /**
     * My optimization: Accepts character arrays for efficient LPS computation
     * (avoids repeated charAt lookups).
     */
    public int[] computeLPSArray(char[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];
        int len = 0;
        lps[0] = 0;
        int i = 1;
        while (i < m) {
            if (pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            } else if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
        return lps;
    }

    /**
     * My optimized KMP implementation:
     * - Converts input strings to char arrays for faster access in search loop.
     * - Streamlines defensive checks and tightens main matching logic.
     * - Minimizes expensive method calls (charAt) and leverages pre-allocated arrays for speed.
     *
     * Returns list of all starting indices for pattern matches.
     */
    public List<Integer> KMPSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        // Defensive input checks moved to the top and streamlined for efficiency
        if (pattern == null || text == null) return result;
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return result;

        // My optimization: Use char arrays for faster character comparison inside the loop
        char[] txt = text.toCharArray();
        char[] pat = pattern.toCharArray();
        int[] lps = computeLPSArray(pat);

        int i = 0, j = 0;
        while (i < n) {
            if (pat[j] == txt[i]) {
                i++; j++;
                if (j == m) {
                    // Pattern found, add start index to result
                    result.add(i - j);
                    // Prepare for next possible match using LPS array (no time wasted)
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                // Use LPS to skip unnecessary checks (classic KMP jump)
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return result;
    }

    // Simple test run (can be removed or kept for quick verification during development)
    public static void main(String[] args) {
        KMP kmp = new KMP();
        String text = "ababcababcababc";
        String pattern = "ababc";
        System.out.println("Pattern found at indices: " + kmp.KMPSearch(text, pattern));
    }
}
