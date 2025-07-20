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

## Quick Start

1. Place test files in `/benchmarks`.
2. Use provided Java classes and utilities in `src/algorithm` and `src/utils`.
3. Build and run with any Java 8+ compatible IDE.
4. See `/docs` for detailed implementation notes and current testing/benchmarking status.

---

## Author & License

*Project by Saurabh Kumar Yadav. License and contribution guidelines to be updated in future releases.*
