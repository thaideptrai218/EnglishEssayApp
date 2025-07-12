# Unit Test Case: StudentFrame - loadDrafts()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LD_01** | **Normal Path - Drafts for Student** | `drafts.txt` contains entries for the current student and others. | The `draftTableModel` should contain only the drafts for the current student. | None | N/A | The table model is populated correctly with the student's drafts. | N |
| **TC_LD_02** | **Normal Path - No Drafts for Student** | `drafts.txt` contains entries, but none for the current student. | The `draftTableModel` should be empty. | None | N/A | The table model correctly shows no drafts for the student. | N |
| **TC_LD_03** | **Boundary - Empty File** | `drafts.txt` exists but is empty. | The `draftTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LD_04** | **Boundary - Non-Existent File** | `drafts.txt` does not exist. | The `draftTableModel` should be empty. | None | N/A | The application handles a missing file without crashing. | B |
| **TC_LD_05** | **Abnormal - Malformed Data** | `drafts.txt` contains a mix of valid lines and one malformed line. | The `draftTableModel` should contain only the valid drafts. | None | N/A | The application ignores the corrupted line and does not crash. | A |
