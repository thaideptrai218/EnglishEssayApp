# Unit Test Case: StudentFrame - loadSubmitted()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LS_01** | **Normal Path - Graded and Ungraded Essays** | `submitted.txt` contains entries for the current student and others. `graded.txt` contains a grade for one of the student's essays. | The `submittedTableModel` should contain 2 rows, with correct grade summaries. | None | N/A | The table model is populated correctly with the student's essays. | N |
| **TC_LS_02** | **Normal Path - No Essays for Student** | `submitted.txt` contains entries, but none for the current student. | The `submittedTableModel` should be empty. | None | N/A | The table model correctly shows no essays for the student. | N |
| **TC_LS_03** | **Boundary - Empty File** | `submitted.txt` exists but is empty. | The `submittedTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LS_04** | **Boundary - Non-Existent File** | `submitted.txt` does not exist. | The `submittedTableModel` should be empty. | None | N/A | The application handles a missing file without crashing. | B |
| **TC_LS_05** | **Abnormal - Malformed Data** | `submitted.txt` contains a mix of valid lines and one malformed line. | The `submittedTableModel` should contain only the valid essays. | None | N/A | The application ignores the corrupted line and does not crash. | A |
