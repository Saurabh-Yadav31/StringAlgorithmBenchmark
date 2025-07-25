# Benchmark Report (Before Optimization)
*Date: July 25, 2025*

## Overview

This document summarizes the benchmarking results for the **Boyer-Moore**, **KMP**, and **Suffix Tree** algorithms on the small dataset before any optimization.

Two types of benchmark data are presented:

- **Single Run Results:** One-time measurement of search time and memory per pattern.
- **Average Results:** Averaged time and memory over multiple runs (default 10), providing more reliable and stable performance insights.

Suffix Tree build (preprocessing) time and memory are also logged, averaged over multiple runs.

---

## Suffix Tree Build (Preprocessing) Time & Memory (Averages)

- Suffix Tree Build (Single Run)

| Metric    | Time (ms) | Memory (MB) |
|-----------|-----------|-------------|
| Build     | 3.357     | 0.960       |

- Suffix Tree Build (Multiple Run)

| Metric       | Avg Time (ms) | Avg Memory (MB) |
|--------------|---------------|-----------------|
| Build        | 0.928         | 0.281           |
---

## Pattern Benchmark Results

## Single Run Results

| Pattern                        | Algorithm   | Time (ms) | Memory (MB) | Matches                           |
|-------------------------------|-------------|-----------|-------------|-----------------------------------|
| "the"                         | Boyer-Moore | 0.052     | 0.004       | [51, 66, 81, 136, 153, 165]       |
|                               | KMP         | 0.049     | 0.003       | [51, 66, 81, 136, 153, 165]       |
|                               | Suffix Tree | 0.068     | 0.003       | [51, 66, 81, 136, 153, 165]       |
| "ana"                         | Boyer-Moore | 0.047     | 0.003       | [5, 7, 35, 56, 58, 115, 171, 173] |
|                               | KMP         | 0.042     | 0.003       | [5, 7, 35, 56, 58, 115, 171, 173] |
|                               | Suffix Tree | 0.053     | 0.003       | [5, 7, 35, 56, 58, 115, 171, 173] |
| "Banana-Test"                 | Boyer-Moore | 0.031     | 0.003       | [170]                             |
|                               | KMP         | 0.039     | 0.003       | [170]                             |
|                               | Suffix Tree | 0.037     | 0.003       | [170]                             |
| "analysis"                    | Boyer-Moore | 0.030     | 0.003       | [115]                             |
|                               | KMP         | 0.042     | 0.003       | [115]                             |
|                               | Suffix Tree | 0.035     | 0.003       | [115]                             |
| "xyznotfound"                 | Boyer-Moore | 0.026     | 0.003       | [ ]                               |
|                               | KMP         | 0.031     | 0.003       | [ ]                               |
|                               | Suffix Tree | 0.021     | 0.003       | [ ]                               |
| "ab"                         | Boyer-Moore | 0.046     | 0.003       | [158, 185]                        |
|                               | KMP         | 0.033     | 0.003       | [158, 185]                        |
|                               | Suffix Tree | 0.027     | 0.003       | [158, 185]                        |
| "test began at noon. The analyst" | Boyer-Moore | 0.024 | 0.003 | [11]                              |
|                               | KMP         | 0.029     | 0.003       | [11]                              |
|                               | Suffix Tree | 0.018     | 0.003       | [11]                              |
| "'Banana-Test'"               | Boyer-Moore | 0.016     | 0.003       | [169]                             |
|                               | KMP         | 0.036     | 0.003       | [169]                             |
|                               | Suffix Tree | 0.019     | 0.003       | [169]                             |
| "Anna"                       | Boyer-Moore | 0.026     | 0.003       | [99]                              |
|                               | KMP         | 0.032     | 0.003       | [99]                              |
|                               | Suffix Tree | 0.014     | 0.003       | [99]                              | [99]                  | [99]                  | All algorithms agree  |

---

## Average Results (Over Multiple Runs)

| Pattern                        | Algorithm   | Avg Time (ms) | Avg Memory (MB) | Matches                           |
|-------------------------------|-------------|---------------|-----------------|-----------------------------------|
| "the"                         | Boyer-Moore | 0.052         | 0.004           | [51, 66, 81, 136, 153, 165]       |
|                               | KMP         | 0.049         | 0.003           | [51, 66, 81, 136, 153, 165]       |
|                               | Suffix Tree | 0.068         | 0.003           | [51, 66, 81, 136, 153, 165]       |
| "ana"                         | Boyer-Moore | 0.047         | 0.003           | [5, 7, 35, 56, 58, 115, 171, 173] |
|                               | KMP         | 0.042         | 0.003           | [5, 7, 35, 56, 58, 115, 171, 173] |
|                               | Suffix Tree | 0.053         | 0.003           | [5, 7, 35, 56, 58, 115, 171, 173] |
| "Banana-Test"                 | Boyer-Moore | 0.031         | 0.003           | [170]                             |
|                               | KMP         | 0.039         | 0.003           | [170]                             |
|                               | Suffix Tree | 0.037         | 0.003           | [170]                             |
| "analysis"                    | Boyer-Moore | 0.030         | 0.003           | [115]                             |
|                               | KMP         | 0.042         | 0.003           | [115]                             |
|                               | Suffix Tree | 0.035         | 0.003           | [115]                             |
| "xyznotfound"                 | Boyer-Moore | 0.026         | 0.003           | [ ]                               |
|                               | KMP         | 0.031         | 0.003           | [ ]                               |
|                               | Suffix Tree | 0.021         | 0.003           | [ ]                               |
| "ab"                         | Boyer-Moore | 0.046         | 0.003           | [158, 185]                        |
|                               | KMP         | 0.033         | 0.003           | [158, 185]                        |
|                               | Suffix Tree | 0.027         | 0.003           | [158, 185]                        |
| "test began at noon. The analyst" | Boyer-Moore | 0.024     | 0.003           | [11]                              |
|                               | KMP         | 0.029         | 0.003           | [11]                              |
|                               | Suffix Tree | 0.018         | 0.003           | [11]                              |
| "'Banana-Test'"               | Boyer-Moore | 0.016         | 0.003           | [169]                             |
|                               | KMP         | 0.036         | 0.003           | [169]                             |
|                               | Suffix Tree | 0.019         | 0.003           | [169]                             |
| "Anna"                       | Boyer-Moore | 0.026         | 0.003           | [99]                              |
|                               | KMP         | 0.032         | 0.003           | [99]                              |
|                               | Suffix Tree | 0.014         | 0.003           | [99]                              |


---

### Summary and Observations

- **Result Agreement:** All algorithms produce exactly the same match positions for every tested pattern, confirming correctness.
- **Performance:**
    - Boyer-Moore and KMP have very similar performance profiles for small dataset searches.
    - Suffix Tree search shows competitive query speed and very low memory usage per pattern—but its build cost (preprocessing) is higher, as shown.
- **Memory:** Memory usage across all algorithms is consistently low in search phases.
- **Averages:** Averaging over multiple runs ensures performance metrics are robust and minimally affected by transient system or JVM behaviors.

---

### Methodology Notes

- **Suffix Tree Build:** Build time and memory are measured separately, averaged over 5 runs to ensure stable measurement.
- **Search Phases:** Each search operation (for every pattern and algorithm) is averaged over 10 runs.
- **Garbage Collection:** JVM garbage collection is invoked before every measurement to reduce noise.
- **Match Verification:** Match lists for all algorithms are cross-checked to ensure identical output.

---

*This report provides the benchmark baseline ahead of any future code optimizations and performance enhancements.*

