# Boyer-Moore Algorithm: Initial Implementation & Test Log

## 1. Overview

This document details the initial implementation of the Boyer-Moore string matching algorithm, highlights its features, summarizes systematic test procedures, and describes the initial validation results. Optimization and further enhancements are planned for future development stages.

---

## 2. Implementation Summary

- **Bad Character Heuristic:**
    - Implements fast skipping for mismatched characters.
- **Good Suffix Heuristic:**
    - Enables further efficient pattern window shifts for suffix mismatches.
- **All Occurrences Functionality:**
    - Finds the first or all pattern occurrences, including overlaps.
- **File-based Testing:**
    - Reads both text inputs and patterns from organized `/benchmarks` files.
- **Case Sensitivity:**
    - Default search is case-sensitive.

---

## 3. Test Plan & Covered Scenarios

### Test Files

| File Name               | Scenario                  | Patterns Included             |
|-------------------------|--------------------------|-------------------------------|
| sample_text1.txt        | Simple, control case     | pattern1.txt                  |
| robust_test.txt         | Overlaps, multiples      | overlap.txt, pattern.txt      |
| complicated_text.txt    | Case, symbol, multi-line | case.txt, withsymbol.txt, newline.txt, nonascii.txt |
| notfound.txt            | Absent pattern           | xyz.txt                       |

### Patterns Used

- `"pattern"` (basic, multiple, overlapping)
- `"ana"` (overlap in "banana")
- `"PATTERN"` (case-sensitive check)
- `"páttérn"` (non-ASCII/accents)
- `"patt@ern"` (special symbols)
- `"xyz"` (not present)
- `"pattern"` on separate lines (multi-line match)

---

## 4. Key Results and Observations

- **All Occurrences:** Detected correct starting indices in each case.
- **Absent Patterns:** Outputs "Pattern not found" as expected.
- **Case Sensitivity:** Only matches exact-case pattern (e.g., "PATTERN" ≠ "pattern").
- **Overlapping Patterns:** Correctly finds all overlaps (e.g., "ana" in "banana").
- **Special Characters & Word Boundaries:** Matches only if pattern and text are an exact character-by-character match.
- **Edge Cases:** Handles empty pattern/text, and pattern longer than text.

### Example Output

- **pattern.txt   :** [8, 64, 72, 104, 161, 169, 176, 183, 215]
- **overlap.txt   :** [127, 135, 137]
- **case.txt      :** [255]
- **notfound.txt  :** Pattern not found


---

## 5. Known Limitations

- Default mode is **case-sensitive**; does not match "PATTERN" if searching for "pattern".
- ASCII-based implementation; Unicode and non-ASCII matches may be unreliable.
- No optimization or run-time tuning applied yet.

---

## 6. Next Steps (Project Plan)

- **Enhancement:** Unicode/encoding support, case-insensitive search option.
- **Algorithmic Comparison:** Implement KMP and Suffix Tree algorithms for side-by-side functional and performance evaluation.
- **Benchmarking:** Add utilities to measure execution time and memory for all algorithms under identical test conditions.
- **Documentation:** Track enhancements, adjustments, and benchmarking results as implementation progresses.

---

## 7. Lessons Learned

- Boyer-Moore is robust and efficient in ASCII, case-sensitive scenarios.
- Overlapping and tricky pattern cases (e.g., "ana" in "banana", multi-lines, punctuation) now reliably identified.
- Remaining open item: Expand flexibility (encoding, case-insensitivity, formal benchmarking).

**Note:** This document describes the initial implementation. Significant optimization and performance improvements will be performed and documented in future project phases.

