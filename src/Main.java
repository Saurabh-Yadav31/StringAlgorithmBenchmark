import algorithm.BoyerMoore;
import algorithm.KMP;
import algorithm.SuffixTree;
import utils.FileReaderUtil;  // Assuming this is your util to read full file text

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Load big test text once
            String text = FileReaderUtil.readFile("benchmarks/large_testcase_text.txt");

            // Load patterns (one per line) including empty patterns as empty lines
            List<String> patterns = Files.readAllLines(Paths.get("benchmarks/large_testcase_patterns.txt"));

            BoyerMoore bm = new BoyerMoore();
            KMP kmp = new KMP();
            SuffixTree suffixTree = new SuffixTree(text);

            Runtime runtime = Runtime.getRuntime();

            for (String pattern : patterns) {
                if (pattern == null) pattern = "";

                System.out.println("Pattern: \"" + pattern + "\"");

                List<Integer> bmResult = new ArrayList<>();
                List<Integer> kmpResult = new ArrayList<>();
                List<Integer> stResult = new ArrayList<>();

                // Boyer-Moore timing and memory
                try {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    bmResult = bm.searchAll(text, pattern);

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    System.out.printf("Boyer-Moore | Time: %.3f ms | Memory: %.3f MB%n",
                            (timeAfter - timeBefore) / 1_000_000.0,
                            (memAfter - memBefore) / (1024.0 * 1024.0));
                } catch (Exception e) {
                    System.out.println("Boyer-Moore error: " + e.getMessage());
                }

                // KMP timing and memory
                try {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    kmpResult = kmp.KMPSearch(text, pattern);

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    System.out.printf("KMP         | Time: %.3f ms | Memory: %.3f MB%n",
                            (timeAfter - timeBefore) / 1_000_000.0,
                            (memAfter - memBefore) / (1024.0 * 1024.0));
                } catch (Exception e) {
                    System.out.println("KMP error: " + e.getMessage());
                }

                // Suffix Tree timing and memory
                try {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    stResult = suffixTree.search(pattern);
                    Collections.sort(stResult); // Sort because suffix tree output is unordered

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    System.out.printf("SuffixTree  | Time: %.3f ms | Memory: %.3f MB%n",
                            (timeAfter - timeBefore) / 1_000_000.0,
                            (memAfter - memBefore) / (1024.0 * 1024.0));
                } catch (Exception e) {
                    System.out.println("SuffixTree error: " + e.getMessage());
                }

                // Print matches
                System.out.println("Boyer-Moore matches: " + bmResult);
                System.out.println("KMP matches        : " + kmpResult);
                System.out.println("Suffix Tree matches: " + stResult);

                // Compare ignoring order by sets
                Set<Integer> bmSet = new HashSet<>(bmResult);
                Set<Integer> kmpSet = new HashSet<>(kmpResult);
                Set<Integer> stSet = new HashSet<>(stResult);

                if (bmSet.equals(kmpSet) && bmSet.equals(stSet)) {
                    System.out.println("Result: All algorithms agree.\n");
                } else {
                    System.out.println("Warning: Results differ among algorithms!\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
