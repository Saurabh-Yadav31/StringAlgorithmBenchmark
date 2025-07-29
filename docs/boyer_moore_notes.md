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

| File Name        | Scenario                  | Patterns Included             |
|------------------|--------------------------|-------------------------------|
| sample_text1.txt | Simple, control case     | pattern1.txt                  |
| sample_text2.txt | Overlaps, multiples      | overlap.txt, pattern.txt      |
| sample_text3.txt | Case, symbol, multi-line | case.txt, withsymbol.txt, newline.txt, nonascii.txt |
| notfound.txt     | Absent pattern           | xyz.txt                       |

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

## Boyer-Moore Algorithm Optimization – July 27, 2025

### Overview

This update focuses on optimizing the Boyer-Moore string matching implementation to improve the runtime efficiency, especially for large texts and patterns, without altering algorithm correctness or baseline behavior.

### Changes and Optimizations

- **Use of `char[]` Arrays for Pattern and Text:**  
  All character accesses in the core search loops have been switched from repeated `String.charAt()` calls to direct indexing in `char[]` arrays.  
  *Benefit:*
  - Reduces method call overhead and bounds checking in tight loops.
  - Produces measurable speed improvements in pattern searching.

- **Early Defensive Input Checks:**  
  Null and length validations for `text` and `pattern` strings are now done immediately at the beginning of `search` and `searchAll` methods.  
  *Benefit:*
  - Avoids unnecessary allocations and computations if inputs are invalid or trivial cases.

- **Consistent Preprocessing Inputs:**  
  The `preprocessBadCharacter` and `preprocessGoodSuffix` methods have been refactored to accept `char[]` arrays instead of `String` objects for consistent speed and reduced overhead.

- **Preserved Default Result Collection Structures:**  
  The match positions are still collected in default-sized `ArrayList` objects as per project design, balancing simplicity and memory efficiency.

### No Changes Made

- Case-sensitivity and ASCII-only matching behavior remain unchanged to meet project requirements.
- No additional memory allocations or complex data structures introduced beyond current baseline.

### Summary

This optimization provides a clear performance benefit through low-level code improvements while maintaining the existing algorithm’s behavior and interface. It prepares the Boyer-Moore component for more accurate, consistent benchmarking alongside KMP and Suffix Tree algorithms in the project’s next evaluation phases.

---

*This changelog documents my direct contributions and code updates on the Boyer-Moore module as of July 27, 2025.*


---

## 7. Lessons Learned

- Boyer-Moore is robust and efficient in ASCII, case-sensitive scenarios.
- Overlapping and tricky pattern cases (e.g., "ana" in "banana", multi-lines, punctuation) now reliably identified.
- Remaining open item: Expand flexibility (encoding, case-insensitivity, formal benchmarking).

**Note:** This document describes the initial implementation. Significant optimization and performance improvements will be performed and documented in future project phases.

