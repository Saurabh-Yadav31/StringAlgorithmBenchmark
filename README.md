# String Pattern-Matching Algorithm Benchmarking (Java)

## Project Overview

This project implements and compares classic string matching algorithms in Java. The initial phase focuses on the Boyer-Moore algorithm, with Knuth-Morris-Pratt (KMP) and Suffix Tree algorithms to follow. Performance benchmarking and optimization will become core parts of later stages.

---

## Boyer-Moore: Implementation Summary

- **Both Bad Character and Good Suffix heuristics** are used for efficient pattern searching.
- **All occurrences (and first occurrence) search:** Handles multiple, overlapping, and standard matches.
- **Robust testing:** Validated on simple, complex, and edge-case text/pattern pairs via organized `/benchmarks` directory.
- **Current design is case-sensitive** and ASCII-oriented.
- **Edge cases (empty pattern/text, pattern longer than text) are handled gracefully.**

**This is an initial implementation. Further optimization and code tuning will occur in upcoming project phases.**

---

## Testing Highlights

- **Tested with robust and complicated input files,** including multiple, overlapping, and absent patterns, case differences, symbols, and multi-line cases.
- **Sample output:**
- **pattern.txt   :** [8, 64, 72, 104, 161, 169, 176, 183, 215]
- **overlap.txt   :** [127, 135, 137]
- **case.txt      :** [255]
- **notfound.txt  :** Pattern not found


**Details of implementation and all test cases/results are maintained in `/docs/boyer_moore_notes.md`.**

---

## Known Limitations (for v1)

- Case-sensitive by default; will not match "PATTERN" if searching for "pattern".
- ASCII-only support at this stage.
- No deep optimization yet applied (initial model).
- Pattern and text read from plain text files in `/benchmarks`.

---

## Future Work

- **Algorithm expansion:** Implement KMP and Suffix Tree for direct comparison.
- **Benchmarking:** Develop a timing and memory-measurement harness to empirically evaluate speed and memory.
- **Optimization and tuning:** Perform code refactoring and algorithmic tuning for performance.
- **Flexibility:** Add case-insensitive and Unicode/encoding-agnostic modes.
- **Enhanced docs:** Update `/docs` and this README as the project evolves.

---

## Knuth-Morris-Pratt (KMP) String Matching Algorithm

### Implementation Overview

- Initial Java implementation of the KMP algorithm with efficient string searching using the **Longest Prefix Suffix (LPS)** preprocessing array.
- Supports finding **all occurrences** of a pattern within a text, including overlapping matches.
- Inputs are read from external text files in the `/benchmarks/` directory to ensure consistent and comprehensive testing.
- The search is **case-sensitive** and character-exact, designed for ASCII-compatible text.

### Testing and Validation

- Thoroughly tested against a wide range of scenarios using a complex test file (`sample_test3.txt`) that includes:
    - Repeated and overlapping patterns
    - Patterns with special symbols and punctuation
    - Multi-line patterns
    - Case sensitivity checks
    - Non-ASCII characters (exact matches only)
- Results demonstrate that all expected occurrences are found accurately with no false positives.
- Patterns absent from the text are correctly reported as not found.

### Sample Test Results

| Pattern File   | Found Indices                                                  | Pattern Used |
|----------------|----------------------------------------------------------------|--------------|
| case_sensitive | [128]                                                          | PATTERN      |
| newline        | [29, 37, 45, 98, 148, 182, 206, 264, 271, 329, 365, 385, 409, 417, 425] | pattern      |
| nonascii       | [320]                                                          | pattern      |
| notfound       | Pattern not found                                               | xyz          |
| overlap        | [236, 238, 246, 248]                                           | ana          |
| withsymbol     | [118]                                                          | patt@ern     |
| pattern        | [29, 37, 45, 98, 148, 182, 206, 264, 271, 329, 365, 385, 409, 417, 425] | pattern      |

### Known Limitations

- Case-sensitive matching only; no automatic case normalization.
- Unicode and extended character sets are not currently supported.
- The implementation focuses on correctness and robustness; performance optimizations and benchmarking will be planned for later phases.

### Next Steps

- Introduce case-insensitive search support.
- Extend testing to include larger and more diverse datasets.
- Implement and benchmark additional algorithms (e.g., Boyer-Moore, Suffix Trees).
- Develop benchmarking framework for accurate time and memory usage comparisons.

For detailed implementation notes and test logs, please see `/docs/kmp_notes.md`.


## Quick Start

1. Place test files in `/benchmarks`.
2. Use provided Java classes and utilities in `src/algorithm` and `src/utils`.
3. Build and run with any Java 8+ compatible IDE.
4. See `/docs` for detailed implementation notes and current testing/benchmarking status.

---

## Author & License

*Project by Saurabh Kumar Yadav. License and contribution guidelines to be updated in future releases.*
