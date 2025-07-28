# Test Plan: `TeacherFrame.viewEssay(boolean isGraded, JTable gradedTable, JTable essayTable)`

## Overview

This method allows a teacher to view an essay, optionally including grading details if `isGraded` is `true`.

---

## Test Cases

| UTCID        | Condition                                   | Precondition                                                                                    | Confirm/Return                                                          | Exception | Log Message               | Result                                         | Type |
| ------------ | ------------------------------------------- | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------- | --------- | ------------------------- | ---------------------------------------------- | ---- |
| **TC_VE_01** | View ungraded essay (normal path)           | `submitted.txt` contains an essay by student; `graded.txt` may exist but not include that essay | Essay content is shown in dialog; no grading details shown              | None      | `view_log.txt` is updated | Essay content is visible without grade         | N    |
| **TC_VE_02** | View graded essay (normal path)             | Essay exists in both `submitted.txt` and `graded.txt`                                           | Dialog shows both essay and grading details (task, coherence, etc.)     | None      | `view_log.txt` is updated | Essay and grade are both visible               | N    |
| **TC_VE_03** | Essay not found in submitted.txt            | Essay exists in table but not in `submitted.txt`                                                | Show error message: "Error loading essay: ..."                          | None      | None                      | Application handles gracefully, shows error    | A    |
| **TC_VE_04** | `submitted.txt` does not exist              | File is deleted or missing                                                                      | Show error dialog (file read exception), but application does not crash | None      | None                      | Application handles missing file without crash | B    |
| **TC_VE_05** | Essay exists in submitted.txt but malformed | `submitted.txt` contains line missing delimiters or fields                                      | Essay is skipped or error message shown if matching ID is malformed     | None      | None                      | Malformed lines are ignored, no crash          | A    |
| **TC_VE_06** | Essay selected but no row is selected       | Table has rows bu                                                                               |
