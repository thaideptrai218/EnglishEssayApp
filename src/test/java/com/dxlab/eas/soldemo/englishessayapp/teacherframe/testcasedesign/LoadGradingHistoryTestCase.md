# Unit Test Case: TeacherFrame - loadGradingHistory()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LGH_01** | **Normal Path - History for Current Teacher** | `grading_history.txt` contains entries for the current teacher and others. | The `historyTableModel` should contain only the history for the current teacher. | None | N/A | The history for the current teacher is loaded correctly. | N |
| **TC_LGH_02** | **Normal Path - No History for Current Teacher** | `grading_history.txt` contains entries, but none for the current teacher. | The `historyTableModel` should be empty. | None | N/A | The table correctly shows no history for the teacher. | N |
| **TC_LGH_03** | **Boundary - Empty File** | `grading_history.txt` exists but is empty. | The `historyTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LGH_04** | **Boundary - Non-Existent File** | `grading_history.txt` does not exist. | The `historyTableModel` should be empty. | None | N/A | The application handles a missing file without crashing. | B |
| **TC_LGH_05** | **Abnormal - Malformed Data** | `grading_history.txt` contains a mix of valid and malformed lines. | The `historyTableModel` should contain only the valid entries for the current teacher. | None | N/A | The application ignores corrupted lines and does not crash. | A |
