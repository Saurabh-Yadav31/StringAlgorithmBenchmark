# Knuth-Morris-Pratt (KMP) Algorithm: Implementation & Test Log

## 1. Overview

This document details the initial implementation of the Knuth-Morris-Pratt (KMP) string matching algorithm in Java, highlighting the core components, testing methodology, and validation results using a robust sample dataset.

---

## 2. Implementation Summary

- **Partial Match (Failure) Function / LPS Array:**  
  Computes the Longest Prefix Suffix (LPS) array used to skip unnecessary comparisons during the search.

- **Search Function:**  
  Utilizes the LPS array to efficiently find **all occurrences** of the pattern in the text.

- **Test Data Management:**  
  Inputs are read from external files placed in `/benchmarks/`, ensuring consistent and repeatable tests.

- **Case Sensitivity:**  
  The implementation is case-sensitive and matches exact character sequences.

---

## 3. Robust Test Case Description

The primary test data is contained in `sample_test3.txt`, a complex text file designed to test various pattern matching scenarios including repeated patterns, overlapping matches, non-ASCII characters, special symbols, multi-line occurrences, and case variations.

### Sample Text (`sample_test3.txt` extract snippet):

Pattern matching is fun. The pattern-pattern-pattern sequence should match three times.
A complex pattern at the end: patt@ern!
PATTERN? Let's test pattern at the Start, at the end: pattern, and in between pattern.
Now try overlapping: anana in banana, and see if patternpattern finds two matches.
What about non-ASCII: páttérn, patterned, and word-boundaries with pattern-matching or pattern's?
Finally, try pattern
pattern
pattern on separate lines.


---

## 4. Test Results Summary

| Pattern File   | Found Indices                                                        | Pattern Used  |
|----------------|----------------------------------------------------------------------|--------------|
| case_sensitive | [128]                                                                | PATTERN      |
| newline        | [29, 37, 45, 98, 148, 182, 206, 264, 271, 329, 365, 385, 409, 417, 425] | pattern      |
| nonascii       | [320]                                                                | pattern      |
| notfound       | Pattern not found                                                    | xyz          |
| overlap        | [236, 238, 246, 248]                                                | ana          |
| withsymbol     | [118]                                                               | patt@ern     |
| pattern        | [29, 37, 45, 98, 148, 182, 206, 264, 271, 329, 365, 385, 409, 417, 425] | pattern      |

---

## 5. Observations

- **Case Sensitivity:**  
  The search for `"PATTERN"` matched only exact uppercase occurrences, as expected.

- **Multi-line and Overlapping Matches:**  
  Successfully found multiple indices for patterns spread across lines and overlapping sequences such as `"ana"` in `"banana"`.

- **Special Characters and Non-ASCII:**  
  Matches found correctly at expected positions, though non-ASCII support is limited to exact character matches.

- **Absent Patterns:**  
  Correctly indicates no match found for `"xyz"`.

---

## 6. Limitations & Notes

- The current implementation **does not normalize case**; searches are case-sensitive.
- Focused on ASCII-compatible text; complex Unicode patterns may require enhancements.
- The sample test file `sample_test3.txt` provides a robust environment for functional verification but further performance testing and optimization are planned.

---

## 7. Next Steps

- Document and implement case-insensitive search option.
- Continue development and testing of additional algorithms (e.g., Boyer-Moore, Suffix Trees).
- Develop benchmarking utilities for cross-algorithm time and memory usage comparisons.
- Extend test datasets with even more complex real-world cases.

---
## KMP Algorithm Optimization (July 26, 2025)

### Overview

The Knuth-Morris-Pratt (KMP) algorithm was reviewed and optimized to improve both performance and clarity before benchmarking against Boyer-Moore and Suffix Tree in the project. The main changes focused on reducing per-character access overhead, streamlining control flow, and minimizing memory allocations inside performance-critical loops.

### Optimization Details

- **Char Array Conversion**  
  Input strings (`text` and `pattern`) are immediately converted to `char[]` arrays.  
  *Benefit:*
  - Eliminates repeated calls to `String.charAt()` in the main search loop and LPS computation, reducing per-character access latency and redundant bounds/method checks.

- **Tighter LPS Table Construction**  
  The LPS (Longest Prefix Suffix) table helper now accepts a `char[]` pattern and uses direct indexing for comparisons.  
  *Benefit:*
  - Slight reduction in method call overhead; logic remains linear time and clean.

- **Streamlined Main Search Loop**  
  All null and empty input checks are performed with minimal conditions, and the KMP core loop is arranged for the fewest branches possible.
  - Advances indices with direct array access.
  - Maintains textbook KMP efficiency with no unnecessary jumps or checks.
  - Result collection is as previously, using a single list for all matches.

- **Comprehensive Documentation**  
  Comments in code now clearly highlight where and why the optimizations were made, making the changes and performance intent fully transparent.

### Code Sample

// Highlights from the new KMP implementation:
char[] txt = text.toCharArray();

char[] pat = pattern.toCharArray();

int[] lps = computeLPSArray(pat);

int i = 0, j = 0;

while (i < n) {

if (pat[j] == txt[i]) {

i++;

j++;

if (j == m) {

result.add(i - j);

j = lps[j - 1];

}

} else if (j > 0) {

j = lps[j - 1];

} else {

i++;

}

}

---


### Performance Expectations

- **Speed:** Improved search times due to less overhead in inner loop character comparisons, particularly noticeable with large strings or a high pattern match frequency.
- **Memory:** No extra memory is allocated beyond what is absolutely necessary for the algorithm’s operation.
- **Correctness:** All optimizations preserve the exact match behavior and results of the standard KMP implementation; output has been verified against pre-optimization baselines.

### Next Steps

- Benchmark and log the updated KMP results on all datasets.
- Compare before/after timings and memory, and summarize the impact in documentation.
- Repeat similar audit and optimization for other algorithms as needed.

*These optimizations and documentation updates reflect my direct contributions to the project KMP module as of July 26, 2025.*


*This document corresponds to the initial phase of the KMP implementation and will be updated with future optimization and benchmarking results.*
