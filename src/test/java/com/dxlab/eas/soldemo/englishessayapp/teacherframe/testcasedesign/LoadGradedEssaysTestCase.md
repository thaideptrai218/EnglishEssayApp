# Unit Test Case: TeacherFrame - loadGradedEssays()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LGE_01** | **Normal Path - File with Graded Essays** | `graded.txt` contains multiple, correctly formatted entries. | The `gradedTableModel` should contain all graded essays. | None | N/A | All graded essays are loaded correctly. | N |
| **TC_LGE_02** | **Boundary - Empty File** | `graded.txt` exists but is empty. | The `gradedTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LGE_03** | **Boundary - Non-Existent File** | `graded.txt` does not exist. | The `gradedTableModel` should be empty. | None | N/A | The application handles a missing file without crashing. | B |
| **TC_LGE_04** | **Abnormal - Malformed Data** | `graded.txt` contains a mix of valid and malformed lines. | The `gradedTableModel` should contain only the valid essays. | None | N/A | The application ignores corrupted lines and does not crash. | A |
