# Unit Test Case: TeacherFrame - loadEssays()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LE_01** | **Normal Path - No Filters** | `submitted.txt` contains multiple essays. | The `essayTableModel` should contain all essays. | None | N/A | All essays are loaded correctly. | N |
| **TC_LE_02** | **Normal Path - Filter by Student ID** | `submitted.txt` contains essays from multiple students. Filter is set to "student1". | The `essayTableModel` should contain only essays from `student1`. | None | N/A | The student ID filter works correctly. | N |
| **TC_LE_03** | **Normal Path - Filter by Topic ID** | `submitted.txt` contains essays for multiple topics. Filter is set to "T2". | The `essayTableModel` should contain only essays for topic `T2`. | None | N/A | The topic ID filter works correctly. | N |
| **TC_LE_04** | **Normal Path - Combined Filters** | `submitted.txt` contains multiple essays. Filters are set for `student1` AND `T2`. | The `essayTableModel` should contain only the essay(s) that match both criteria. | None | N/A | Combined filtering works correctly. | N |
| **TC_LE_05** | **Normal Path - Filter with No Matches** | `submitted.txt` contains essays, but the filter matches none of them. | The `essayTableModel` should be empty. | None | N/A | The system correctly returns no results when filters don't match. | N |
| **TC_LE_06** | **Boundary - Empty File** | `submitted.txt` exists but is empty. | The `essayTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LE_07** | **Boundary - Non-Existent File** | `submitted.txt` does not exist. | The `essayTableModel` should be empty. | None | N/A | The application handles a missing file without crashing. | B |
| **TC_LE_08** | **Abnormal - Malformed Data** | `submitted.txt` contains a mix of valid and malformed lines. | The `essayTableModel` should contain only the valid essays. | None | N/A | The application ignores corrupted lines and does not crash. | A |
