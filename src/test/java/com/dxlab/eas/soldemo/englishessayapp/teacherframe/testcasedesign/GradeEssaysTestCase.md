# Test Plan - TeacherFrame.gradeEssay(JTable essayTable)

## Purpose

To verify that grading essays via `gradeEssay()` behaves correctly in normal and edge cases, including file writing and model updates.

---

## Test Cases

| UTCID        | Condition                              | Precondition                                                           | Expected Behavior                                                                           | Exception   | Result Type  |
| ------------ | -------------------------------------- | ---------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- | ----------- | ------------ |
| **TC_GE_01** | Grade essay normally (mark as FINAL)   | Essay exists in `submitted.txt`, not in `graded.txt`. Row is selected. | Saves entry to `grading_history.txt` and `graded.txt`. Essay appears in `gradedTableModel`. | None        | N (Normal)   |
| **TC_GE_02** | No row selected                        | No row is selected in `essayTable`.                                    | Shows warning dialog, exits early.                                                          | None        | A (Abnormal) |
| **TC_GE_03** | Essay already graded                   | Essay already present in `graded.txt`.                                 | Shows warning dialog: already graded. Does not allow regrading.                             | None        | A (Abnormal) |
| **TC_GE_04** | Essay not found in submitted.txt       | Essay ID not found in `submitted.txt`                                  | Shows error dialog: essay not found. Skips grading.                                         | None        | B (Boundary) |
| **TC_GE_05** | submitted.txt file missing             | File deleted or missing                                                | Shows error dialog on file reading. No crash.                                               | None        | B (Boundary) |
| **TC_GE_06** | Grade essay but NOT final (DRAFT only) | Essay exists, grading done but "Mark as Final" is not checked          | Only saved to `grading_history.txt`. Nothing added to `graded.txt`.                         | None        | N (Normal)   |
| **TC_GE_07** | grading_history.txt is unwritable      | `grading_history.txt` exists but is read-only                          | Shows error message, does not crash. Grading not saved.                                     | IOException | B (Boundary) |

---

## Notes

- Tests focus on file manipulation and data updates, not UI interactions.
- `graded.txt` must be verified for presence or absence of essay entries depending on “FINAL” checkbox.
- `grading_history.txt` should contain all grading attempts.
