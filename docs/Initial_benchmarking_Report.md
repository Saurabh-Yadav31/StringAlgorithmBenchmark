# Benchmarking Infrastructure & Initial Results – July 24, 2025

## 1. Overview

Today’s work focused on building a robust benchmarking framework for comparing the **Boyer-Moore**, **KMP**, and **Suffix Tree** string matching algorithms. The goal was to establish precise and consistent measurement of execution time and memory usage across diverse test patterns and texts, while validating correctness thoroughly.

## 2. Benchmarking Infrastructure

- Added **precise timing** using `System.nanoTime()` for nanosecond-resolution execution duration measurement.
- Integrated **memory profiling** via Java’s `Runtime.getRuntime()`, calculating heap usage before and after algorithm execution.
- Encapsulated measurement logic inline to maintain clarity and immediate feedback during testing.
- Discussed and documented the critical note that **Suffix Tree’s main memory/time cost occurs during build** (outside search timing) — aligned with multi-query usage scenarios.

---

## 3. Dataset Preparation

- Prepared **small**, **medium** (pre-existing), and **large** datasets placed in `/benchmarks`:
    - **Small dataset:** Approx. 200 characters, including overlapping patterns and unicode.
    - **Large dataset:** Extended logs and paragraphs (~100,000 chars) mixing repeated patterns, Unicode words (e.g., résumé, café), special patterns, and multi-line logs.
- Created matched pattern list files for each dataset covering:
    - Short and long patterns
    - Rare and frequent occurrences
    - Absent patterns (negative tests)
    - Patterns with special/unicode characters and overlapping sequences

## 4. Testing & Validation

### Functional Correctness (Unified Test Case)

Tested all three algorithms on a unified text and multiple patterns, confirming:

| Pattern                                   | Boyer-Moore Matches                                                                                                  | KMP Matches                                                                                                          | Suffix Tree Matches                                                                                                  | Result               |
|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|----------------------|
| "" (empty string)                         | [ ]                                                                                                                  | [ ]                                                                                                                  | [ ]                                                                                                                  | All algorithms agree  |
| "pattern"                                | [150, 322, 754, 988, 1146, 1171, 1179, 1187, 1242, 1469, 1724, 1836, 2137, 2259, 2556, 2665, 2673, 2681, 2850, 2887] | [150, 322, 754, 988, 1146, 1171, 1179, 1187, 1242, 1469, 1724, 1836, 2137, 2259, 2556, 2665, 2673, 2681, 2850, 2887] | [150, 322, 754, 988, 1146, 1171, 1179, 1187, 1242, 1469, 1724, 1836, 2137, 2259, 2556, 2665, 2673, 2681, 2850, 2887] | All algorithms agree  |
| "ana"                                   | [821, 831, 833, 2736, 2746, 2748, 2861]                                                                              | [821, 831, 833, 2736, 2746, 2748, 2861]                                                                              | [821, 831, 833, 2736, 2746, 2748, 2861]                                                                              | All algorithms agree  |
| "résumé"                                | [1570, 2798, 2868]                                                                                                   | [1570, 2798, 2868]                                                                                                   | [1570, 2798, 2868]                                                                                                   | All algorithms agree  |
| "aaa"                                   | [2284, 2285, 2286, 2287, 2338]                                                                                       | [2284, 2285, 2286, 2287, 2338]                                                                                       | [2284, 2285, 2286, 2287, 2338]                                                                                       | All algorithms agree  |
| "café"                                  | [1553, 1685, 1733]                                                                                                   | [1553, 1685, 1733]                                                                                                   | [1553, 1685, 1733]                                                                                                   | All algorithms agree  |
| "notfound"                              | [ ]                                                                                                                  | [ ]                                                                                                                  | [ ]                                                                                                                  | All algorithms agree  |
| "Pattern"                               | [974, 2047, 2832]                                                                                                    | [974, 2047, 2832]                                                                                                    | [974, 2047, 2832]                                                                                                    | All algorithms agree  |
| "a very long pattern that does not exist in the text" | [ ]                                                                                                                  | [ ]                                                                                                                  | [ ]                                                                                                                  | All algorithms agree  |


All algorithms returned **identical match locations** per pattern.

### Performance Benchmarking (Initial Results)

Measured runtime in milliseconds and memory consumption (in MB) per pattern:

| Pattern                                   | Boyer-Moore Time (ms) | Boyer-Moore Memory (MB) | KMP Time (ms) | KMP Memory (MB) | Suffix Tree Time (ms) | Suffix Tree Memory (MB) | Result                 |
|-------------------------------------------|-----------------------|-------------------------|---------------|-----------------|-----------------------|-------------------------|------------------------|
| "" (empty string)                         | 0.028                 | 0.960                   | 0.028         | 0.200           | 0.315                 | 0.080                   | All algorithms agree    |
| "pattern"                                | 0.210                 | 0.080                   | 0.295         | 0.080           | 0.090                 | 0.080                   | All algorithms agree    |
| "ana"                                   | 0.194                 | 0.080                   | 0.254         | 0.080           | 0.066                 | 0.080                   | All algorithms agree    |
| "résumé"                                | 0.092                 | 0.080                   | 0.258         | 0.080           | 0.059                 | 0.080                   | All algorithms agree    |
| "aaa"                                   | 0.156                 | 0.080                   | 0.242         | 0.080           | 0.038                 | 0.080                   | All algorithms agree    |
| "café"                                  | 0.143                 | 0.080                   | 0.255         | 0.080           | 0.037                 | 0.080                   | All algorithms agree    |
| "notfound"                              | 0.061                 | 0.080                   | 0.323         | 0.080           | 0.030                 | 0.080                   | All algorithms agree    |
| "Pattern"                               | 0.090                 | 0.080                   | 0.343         | 0.080           | 0.056                 | 0.080                   | All algorithms agree    |
| "a very long pattern that does not exist in the text" | 0.049                 | 0.080                   | 0.319         | 0.080           | 0.025                 | 0.080                   | All algorithms agree    |



- The suffix tree’s search is fastest per query.
- Boyer-Moore and KMP are competitive with reasonable memory use.
- Memory measurements exclude suffix tree build phase.

## 5. Notes on Suffix Tree Build Cost

- The main memory and time cost for the suffix tree are during **construction**.
- Benchmarking per-query excludes build cost to simulate multi-query real-world usage.
- For one-off query scenarios, build cost should be included.

## 6. Benchmarking Approach

Instrumentation was done inline in the main test loop, includes:

runtime.gc();

long memBefore = runtime.totalMemory() - runtime.freeMemory();
long startTime = System.nanoTime();

List<Integer> result = algorithm.searchAll(text, pattern);

long endTime = System.nanoTime();
long memAfter = runtime.totalMemory() - runtime.freeMemory();

System.out.printf(
"%s | Time: %.3f ms | Memory: %.3f MB%n",
algorithmName,
(endTime - startTime) / 1_000_000.0,
(memAfter - memBefore) / (1024.0 * 1024.0)
);


## 7. Next Steps

- Create reusable benchmarking utility methods.
- Automate multi-run benchmarking and averaging.
- Document suffix tree build time and memory usage explicitly.
- Expand data sets and benchmark report presentations.
- Explore algorithmic optimizations and refinements.

---

*Prepared by Saurabh Kumar Yadav on July 24, 2025.*

