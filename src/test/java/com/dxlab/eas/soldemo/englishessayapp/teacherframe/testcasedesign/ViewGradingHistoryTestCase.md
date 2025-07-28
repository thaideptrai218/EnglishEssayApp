# Test Plan - TeacherFrame.viewGradingHistory(JTable table)

## Purpose

To verify that the `viewGradingHistory()` method correctly displays the grading detail dialog for selected history entries, including proper parsing from `grading_history.txt`.

---

## Test Cases

| UTCID         | Condition                               | Precondition                                                                                      | Expected Behavior                                                  | Exception | Result Type  |
| ------------- | --------------------------------------- | ------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------ | --------- | ------------ |
| **TC_VGH_01** | View history normally (valid selection) | Selected row exists and matches a valid entry in `grading_history.txt` for the current teacher ID | Shows grading details dialog with correct content                  | None      | N (Normal)   |
| **TC_VGH_02** | No row selected                         | Table has data but no row is selected                                                             | Shows warning dialog: “Please select a grading record!”            | None      | A (Abnormal) |
| **TC_VGH_03** | Entry not found in file                 | Selected essayId and timestamp do not exist in file                                               | No dialog shown; method exits silently                             | None      | B (Boundary) |
| **TC_VGH_04** | File is empty                           | `grading_history.txt` exists but contains no lines                                                | Nothing is displayed; no crash                                     | None      | B (Boundary) |
| **TC_VGH_05** | File does not exist                     | `grading_history.txt` file is missing                                                             | Error is caught and method exits gracefully                        | None      | B (Boundary) |
| **TC_VGH_06** | Malformed line in grading history file  | File contains one well-formed entry and one malformed line                                        | Only valid line is processed. Malformed line is skipped. No crash. | None      | A (Abnormal) |

---

## Notes

- Method reads file line by line and looks for teacher ID + essay ID + timestamp match.
- Expected dialog text format includes all grading components (task, coherence, lexical, grammar, comment).
