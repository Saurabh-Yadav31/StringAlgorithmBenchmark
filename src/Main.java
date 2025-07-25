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
            // Load test text once
            String text = FileReaderUtil.readFile("benchmarks/small_testcase_text.txt");

            // Load patterns (one per line) including empty patterns as empty lines
            List<String> patterns = Files.readAllLines(Paths.get("benchmarks/small_testcase_patterns.txt"));

            Runtime runtime = Runtime.getRuntime();

            // ----------- Suffix Tree Build (Preprocessing) Benchmark -----------
            // Since the suffix tree build is expensive and reused for all searches,
            // we measure its time and memory usage once, but repeat multiple times to get reliable averages.

            final int buildRuns = 5;  // Number of repetitions for averaging
            double totalBuildTime = 0;
            double totalBuildMem = 0;

            for (int i = 0; i < buildRuns; i++) {
                runtime.gc();  // Suggest garbage collection to minimize noise
                long memBefore = runtime.totalMemory() - runtime.freeMemory();
                long timeBefore = System.nanoTime();

                // Build suffix tree fresh each run for accurate preprocessing measurement
                SuffixTree suffixTreeTemp = new SuffixTree(text);

                long timeAfter = System.nanoTime();
                long memAfter = runtime.totalMemory() - runtime.freeMemory();

                totalBuildTime += (timeAfter - timeBefore) / 1_000_000.0;  // ms
                totalBuildMem += (memAfter - memBefore) / (1024.0 * 1024.0); // MB
            }

            double avgBuildTime = totalBuildTime / buildRuns;
            double avgBuildMem = totalBuildMem / buildRuns;

            System.out.printf("SuffixTree Build (avg of %d runs) | Time: %.3f ms | Memory: %.3f MB%n%n",
                    buildRuns, avgBuildTime, avgBuildMem);

            // Build suffix tree once for actual searches (reuse for all patterns)
            SuffixTree suffixTree = new SuffixTree(text);

            // Instantiate BoyerMoore and KMP objects once (reuse per pattern)
            BoyerMoore bm = new BoyerMoore();
            KMP kmp = new KMP();

            // ----------- Pattern Search Loop -----------
            final int searchRuns = 10;  // Number of repetitions per pattern search for averaging

            for (String pattern : patterns) {
                if (pattern == null) pattern = "";

                System.out.println("Pattern: \"" + pattern + "\"");

                List<Integer> bmResult = new ArrayList<>();
                List<Integer> kmpResult = new ArrayList<>();
                List<Integer> stResult = new ArrayList<>();

                // --- Boyer-Moore search averaging ---
                double bmTotalTime = 0;
                double bmTotalMem = 0;
                for (int i = 0; i < searchRuns; i++) {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    bmResult = bm.searchAll(text, pattern);

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    bmTotalTime += (timeAfter - timeBefore) / 1_000_000.0;
                    bmTotalMem += (memAfter - memBefore) / (1024.0 * 1024.0);
                }
                double bmAvgTime = bmTotalTime / searchRuns;
                double bmAvgMem = bmTotalMem / searchRuns;
                System.out.printf("Boyer-Moore | Avg Time: %.3f ms | Avg Memory: %.3f MB%n", bmAvgTime, bmAvgMem);

                // --- KMP search averaging ---
                double kmpTotalTime = 0;
                double kmpTotalMem = 0;
                for (int i = 0; i < searchRuns; i++) {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    kmpResult = kmp.KMPSearch(text, pattern);

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    kmpTotalTime += (timeAfter - timeBefore) / 1_000_000.0;
                    kmpTotalMem += (memAfter - memBefore) / (1024.0 * 1024.0);
                }
                double kmpAvgTime = kmpTotalTime / searchRuns;
                double kmpAvgMem = kmpTotalMem / searchRuns;
                System.out.printf("KMP         | Avg Time: %.3f ms | Avg Memory: %.3f MB%n", kmpAvgTime, kmpAvgMem);

                // --- Suffix Tree search averaging ---
                double stTotalTime = 0;
                double stTotalMem = 0;
                for (int i = 0; i < searchRuns; i++) {
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();
                    long timeBefore = System.nanoTime();

                    stResult = suffixTree.search(pattern);
                    Collections.sort(stResult);  // Sort because suffix tree output may be unordered

                    long timeAfter = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    stTotalTime += (timeAfter - timeBefore) / 1_000_000.0;
                    stTotalMem += (memAfter - memBefore) / (1024.0 * 1024.0);
                }
                double stAvgTime = stTotalTime / searchRuns;
                double stAvgMem = stTotalMem / searchRuns;
                System.out.printf("SuffixTree  | Avg Time: %.3f ms | Avg Memory: %.3f MB%n",
                        stAvgTime, stAvgMem);

                // Print matches from the last run (should be identical for all runs)
                System.out.println("Boyer-Moore matches: " + bmResult);
                System.out.println("KMP matches        : " + kmpResult);
                System.out.println("Suffix Tree matches: " + stResult);

                // Compare ignoring order by converting to sets
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
