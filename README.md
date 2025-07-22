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

---

## Suffix Tree Algorithm

### Implementation Overview

- Full implementation of Ukkonen’s suffix tree construction algorithm in Java, built from scratch.
- Supports efficient substring search queries returning **all occurrences** of a pattern in the text.
- Constructed suffix tree accurately handles overlapping, multi-line, special symbols, and non-ASCII characters (exact match only).
- Pattern search traverses the suffix tree to retrieve matching suffix start indices.

### Testing and Validation

- Extensively tested using a complex sample text file (`sample_test3.txt`) containing diverse scenarios:
  - Multiple repeated patterns
  - Overlapping and boundary cases
  - Special symbols and multi-line patterns
- Successfully finds patterns such as `"pattern"`, `"ana"`, `"PATTERN"`, `"patt@ern"`, and more.
- Correctly reports absence for non-existing patterns (e.g., `"xyz"`).

### Key Observations

- The suffix tree returns **all matching indices but in an unsorted order**.
- This happens because child edges are stored in a `HashMap`, which does not preserve insertion or sorted order.
- This contrasts with Boyer-Moore and KMP implementations that return sorted lists of indices.
- Sorting can be implemented post-search or by using an order-preserving map (e.g., `LinkedHashMap`) for the children.

### Sample Test Results

| Pattern File   | Found Indices                                                    | Pattern Used |
|----------------|-----------------------------------------------------------------|--------------|
| case_sensitive | [128]                                                           | PATTERN      |
| newline        | [148, 98, 45, 271, 425, 264, 329, 385, 417, 409, 182, 37, 29, 365, 206] | pattern      |
| nonascii       | [320]                                                           | pattern      |
| notfound       | Pattern not found                                               | xyz          |
| overlap        | [238, 248, 236, 246]                                           | ana          |
| withsymbol     | [118]                                                           | patt@ern     |
| pattern        | [148, 98, 45, 271, 425, 264, 329, 385, 417, 409, 182, 37, 29, 365, 206] | pattern      |

### Known Limitations

- Child nodes use `HashMap` resulting in unordered traversal and match indices.
- Case-sensitive and exact matching only; no out-of-the-box Unicode normalization or case-insensitive mode.
- Benchmarking and performance optimization are pending.

---

## Future Work

- **Complete Benchmarking:**  
  Run systematic timing and memory comparisons against Boyer-Moore and KMP algorithms on standardized datasets.

- **Algorithmic Tuning and Refactoring:**  
  Improve tree construction efficiency and reduce memory footprint where possible.

- **Match Ordering Improvements:**  
  Consider using order-preserving maps or sorting match indices post-search to consistently return sorted results.

- **Feature Enhancements:**  
  Add case-insensitive search and Unicode/encoding-agnostic support to broaden applicability.

- **Expanded Testing:**  
  Include more extensive and real-world test datasets to validate robustness.

- **Documentation Updates:**  
  Continuously update `/docs` and README with benchmarks, optimizations, and new features as the project evolves.

---

*For detailed implementation notes and test logs, see `/docs/suffix_tree_notes.md`.*


## Quick Start

1. Place test files in `/benchmarks`.
2. Use provided Java classes and utilities in `src/algorithm` and `src/utils`.
3. Build and run with any Java 8+ compatible IDE.
4. See `/docs` for detailed implementation notes and current testing/benchmarking status.

---

## Author & License

*Project by Saurabh Kumar Yadav. License and contribution guidelines to be updated in future releases.*
